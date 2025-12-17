# sealed UiState (если sealed)
-keep class com.exxuslee.domain.model.UiState { *; }
-keep class com.exxuslee.domain.model.UiState$* { *; }

# Koin domain module (top-level Kotlin file)
-keep class com.exxuslee.domain.di.DomainModuleKt { *; }