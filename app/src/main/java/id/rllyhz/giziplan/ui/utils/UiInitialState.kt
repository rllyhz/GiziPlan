package id.rllyhz.giziplan.ui.utils

sealed class UiInitialState<T> {
    data class Loading<T>(val data: T?) : UiInitialState<T>()

    data class HasData<T>(val data: T) : UiInitialState<T>()

    data class Error<T>(val data: T?) : UiInitialState<T>()
}