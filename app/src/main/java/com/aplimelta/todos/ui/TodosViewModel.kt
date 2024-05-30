package com.aplimelta.todos.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplimelta.todos.data.response.ApiResponse
import com.aplimelta.todos.data.response.TodosResponseItem
import com.aplimelta.todos.data.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodosViewModel: ViewModel() {

    private val _todos = MutableLiveData<ApiResponse<List<TodosResponseItem>>>()
    val todos: LiveData<ApiResponse<List<TodosResponseItem>>> get() = _todos

    private val _todo = MutableLiveData<ApiResponse<TodosResponseItem>>()
    val todo: LiveData<ApiResponse<TodosResponseItem>> get() = _todo

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        ApiResponse.Error(throwable.message.toString())
    }

    fun getTodos() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _todos.postValue(ApiResponse.Loading)
            try {
                val response = ApiConfig.getApiService().getTodos()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null)
                        _todos.value = ApiResponse.Success(responseBody)
                    } else {
                        _todos.value = ApiResponse.Error(response.errorBody().toString())
                    }
                }
            } catch (e: Exception) {
                _todos.postValue(ApiResponse.Error(e.message.toString()))
            }
        }
    }

    fun getTodoItem(id: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _todo.postValue(ApiResponse.Loading)
            try {
                val response = ApiConfig.getApiService().getTodoItem(id)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null)
                            _todo.value = ApiResponse.Success(responseBody)
                    } else {
                        _todo.value = ApiResponse.Error(response.errorBody().toString())
                    }
                }
            } catch (e: Exception) {
                _todo.postValue(ApiResponse.Error(e.message.toString()))
            }
        }
    }

}