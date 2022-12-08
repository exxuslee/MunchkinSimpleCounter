package com.exxuslee.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exxuslee.domain.usecases.UseCase
import com.exxuslee.domain.utils.HandleResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val getIDUseCase: UseCase.Base) : ViewModel() {
    private var selectedID = 0

    private val _ids: MutableStateFlow<Map<Int, String>> = MutableStateFlow(mapOf())
    fun ids() = _ids.asStateFlow()

    private val _dataFetchState = MutableStateFlow(true)
    fun dataFetchState() = _dataFetchState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    fun isLoading() = _isLoading.asStateFlow()

    private var handleResult = object : HandleResult<Pair<Int, String>> {
        override fun handleError(message: String) {
            _isLoading.value = false
            _dataFetchState.value = false
        }

        override fun handleSuccess(data: Pair<Int, String>) {
            _isLoading.value = false
            _dataFetchState.value = true
            _ids.value = _ids.value.plus(data)
        }
    }

    fun getRandomNumber() {
        _isLoading.value = true
        viewModelScope.launch {
            //withContext(Dispatchers.IO) { getIDUseCase.getRandom().handle(handleResult) }
        }
    }

    fun getNumber(number: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            //withContext(Dispatchers.IO) { getIDUseCase.getNumber(number).handle(handleResult) }
        }
    }

    fun getSelectedID(): Int = selectedID

    fun navigate(content: String, view: View, pos: Int) {
        selectedID = pos
        val bundle = Bundle()
        bundle.putString("content", content)
        //Navigation.findNavController(view).navigate(R.id.action_1fragment_to_2frafment, bundle)
    }

    fun removeNumber(key: Int) {
        _ids.value = _ids.value.minus(key)
        Log.d(TAG, _ids.value.toString())
    }

    companion object {
        const val TAG = "testNumbers"
    }
}