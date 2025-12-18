package com.exxuslee.puzzle

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.exxuslee.core.puzzle.BuildConfig
import com.exxuslee.puzzle.crypto.Base58
import com.exxuslee.puzzle.crypto.Digest
import fr.acinq.secp256k1.Secp256k1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import java.math.BigInteger
import java.security.SecureRandom

class PuzzleWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    private val secp256k1 = Secp256k1.get()
    private val secureRandom = SecureRandom()
    val g = secp256k1.pubkeyCreate(ByteArray(32).also { it[31] = 1 })
    val sha256 = Digest.sha256()
    val ripemd160 = Digest.ripemd160()
    val base58 = Base58()

    fun generateRandomPrivateKey(): String {
        val min = BigInteger(BuildConfig.MIN_HEX, 16)
        val max = BigInteger(BuildConfig.MAX_HEX, 16)
        val range = max.subtract(min)
        var randomInRange: BigInteger
        do {
            randomInRange = BigInteger(range.bitLength(), secureRandom)
        } while (randomInRange > range)

        val result = min.add(randomInRange)
        var hexString = result.toString(16).padStart(64, '0')
        hexString = hexString.substring(0, 59) + "00000"
//        println("Generated Private Key: $hexString")
//        hexString = "000000000000000000000000000000000000000000000000f7051f27b0900000"
        return hexString
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.Default) {
        try {
            val privateKey = generateRandomPrivateKey()
            var current = secp256k1.pubkeyCreate(privateKey.hexToByteArray())
            val total = 0xFFFFF
            val target = base58.addressToPubKeyHash("16jY7qLJnxb7CHZyqBP8qca9d51gAjyXQN")

            repeat(total) { i ->
                ensureActive()
                val pubkeyCompressed = secp256k1.pubKeyCompress(current)
                val sha = sha256.hash(pubkeyCompressed)
                val hash160 = ripemd160.hash(sha)

//                if (i % 50000 == 0) {
//                    setProgress(workDataOf("progress" to i, "total" to total))
//                    println("Step $i / $total, HASH160: ${hash160.toHexString()}")
//                    delay(10000)
//                }

                if (target.contentEquals(hash160)) {
                    println("FOUND MATCHING ADDRESS!: $privateKey ${i.toString(16)}")
                    val telegramService = TelegramService()
                    telegramService.sendMessage("$privateKey ${i.toString(16)}", 530667295)
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