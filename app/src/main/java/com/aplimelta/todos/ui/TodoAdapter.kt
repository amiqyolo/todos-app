package com.aplimelta.todos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aplimelta.todos.data.response.TodosResponseItem
import com.aplimelta.todos.databinding.ItemRowTodoListBinding

class TodoAdapter :
    ListAdapter<TodosResponseItem, TodoAdapter.TodoViewHolder>(TodoAdapterComparator()) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(id: String)
    }

    inner class TodoViewHolder(private val binding: ItemRowTodoListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodosResponseItem) {
            binding.tvTodoTitle.text = item.title
        }
    }

    class TodoAdapterComparator : DiffUtil.ItemCallback<TodosResponseItem>() {
        override fun areItemsTheSame(
            oldItem: TodosResponseItem,
            newItem: TodosResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TodosResponseItem,
            newItem: TodosResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            ItemRowTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(currentItem.id.toString()) }
        }
    }

}
