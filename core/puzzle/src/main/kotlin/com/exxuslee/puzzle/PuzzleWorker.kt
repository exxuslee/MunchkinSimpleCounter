package com.exxuslee.puzzle

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.exxuslee.puzzle.crypto.Digest
import fr.acinq.secp256k1.Secp256k1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.math.BigInteger
import java.security.MessageDigest
import java.security.SecureRandom

class PuzzleWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val secp256k1 = Secp256k1.get()
    private val secureRandom = SecureRandom()
    val g = secp256k1.pubkeyCreate(ByteArray(32).also { it[31] = 1 })
    val sha256: MessageDigest = MessageDigest.getInstance("SHA-256")
    val ripemd160 = Digest.ripemd160()


    fun generateRandomPrivateKey(): String {
        val minHex = "0000000000000000000000000000000000000000000000400000000000000000"
        val maxHex = "00000000000000000000000000000000000000000000007fffffffffffffffff"
        val min = BigInteger(minHex, 16)
        val max = BigInteger(maxHex, 16)
        val range = max.subtract(min)
        var randomInRange: BigInteger
        do {
            randomInRange = BigInteger(range.bitLength(), secureRandom)
        } while (randomInRange > range)

        val result = min.add(randomInRange)
        var hexString = result.toString(16).padStart(64, '0')
        hexString = hexString.substring(0, 59) + "00000"
        hexString = "000000000000000000000000000000000000000000000000f7051f27b0900000"
        return hexString
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        try {
            val privateKey = generateRandomPrivateKey()
            println("Generated Private Key: $privateKey")
            var current = secp256k1.pubkeyCreate(privateKey.hexToByteArray())
            val total = 0xFFFFF

            repeat(total) { i ->
                ensureActive()
                val pubkeyCompressed = secp256k1.pubKeyCompress(current)
                val sha = sha256.digest(pubkeyCompressed)
                val hash160 = ripemd160.hash(sha)

                if (i % 50000 == 0) {
                    setProgress(workDataOf("progress" to i, "total" to total))
                    println("Step $i / $total, HASH160: ${hash160.toHexString()}")
                    delay(10000)
                }
                //3ee4133d991f52fdf6a25c9834e0745ac74248a4  - 64 puzzle
                //f6f5431d25bbf7b12e8add9af5e3475c44a0a5b8  - 71 puzzle
                val target = "3ee4133d991f52fdf6a25c9834e0745ac74248a4".hexToByteArray()
                if (target.contentEquals(hash160)) {
                    println("FOUND MATCHING ADDRESS!: $privateKey ${i.toString(16)}")
                    return@withContext Result.success()
                }

                current = secp256k1.pubKeyCombine(arrayOf(current, g))
                yield()
            }

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }

}