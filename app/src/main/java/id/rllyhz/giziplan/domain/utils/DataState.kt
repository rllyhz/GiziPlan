package id.rllyhz.giziplan.domain.utils

data class DataStateError(
    val exception: Exception,
    val message: String
)

sealed class DataState<T>(
    val error: DataStateError? = null,
    val data: T? = null
) {
    class Loading<T> : DataState<T>()

    class Error<T>(
        exception: Exception,
        message: String
    ) : DataState<T>(
        DataStateError(exception, message),
        null
    )

    class SuccessWithNullableData<T>(data: T?) : DataState<T>(null, data)

    class Success<T>(data: T) : DataState<T>(null, data)
}