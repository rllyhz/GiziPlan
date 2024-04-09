package id.rllyhz.giziplan.domain.utils

data class DataStateError(
    val errorType: Exception,
    val message: String
)

sealed class DataState<T>(
    val error: DataStateError? = null,
    val data: T? = null
) {
    class Loading<T> : DataState<T>()

    class Error(
        private val exception: Exception,
        private val message: String
    ) : DataState<Unit>(
        DataStateError(exception, message),
        null
    )

    class Success<T>(data: T) : DataState<T>(null, data)
}