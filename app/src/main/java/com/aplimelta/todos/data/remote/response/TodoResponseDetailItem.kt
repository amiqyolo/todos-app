package com.aplimelta.todos.data.remote.response

import com.google.gson.annotations.SerializedName

data class TodoResponseDetailItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("completed")
	val completed: Boolean,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("userId")
	val userId: Int
)