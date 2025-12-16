package com.exxuslee.domain.model

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
}