package com.aplimelta.todos.data.response

sealed class ApiResponse<out R> {
    data class Success<out T>(val data: T): ApiResponse<T>()
    data class Error(val message: String): ApiResponse<Nothing>()
    data object Loading: ApiResponse<Nothing>()
}