package com.example.testing.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HardToDoListViewModel : ViewModel() {

    private val _state = MutableStateFlow(TodoList())
    val state = _state.asStateFlow()

    fun onAction(action: TodoListAction) {
        when (action) {
            TodoListAction.OnAddClick -> {
                if (_state.value.newTitle.isNotBlank()) {
                    val todo = Todo(_state.value.newTitle, _state.value.newDescription, false)
                    _state.update {
                        it.copy(
                            todos = it.todos + todo,
                            newTitle = "",
                            newDescription = ""
                        )
                    }
                }
            }

            is TodoListAction.OnDeleteClick -> {
                _state.update {
                    it.copy(todos = it.todos.filter { item -> item != action.todo })
                }
            }

            is TodoListAction.OnTodoCompleteToggle -> {
                _state.update {
                    it.copy(
                        todos = it.todos.map { item ->
                            if (item == action.todo) {
                                item.copy(isChecked = !item.isChecked)
                            } else {
                                item
                            }
                        }
                    )
                }
            }

            is TodoListAction.OnDescriptionUpdate -> _state.update { it.copy(newDescription = action.description) }
            is TodoListAction.OnTitleUpdate -> _state.update { it.copy(newTitle = action.title) }
        }
    }

}

sealed interface TodoListAction {
    data object OnAddClick : TodoListAction
    class OnDeleteClick(val todo: Todo) : TodoListAction
    class OnTitleUpdate(val title: String) : TodoListAction
    class OnDescriptionUpdate(val description: String) : TodoListAction
    class OnTodoCompleteToggle(val todo: Todo) : TodoListAction
}

data class Todo(val title: String, val description: String, val isChecked: Boolean)

data class TodoList(
    val todos: List<Todo> = (1..10).map {
        Todo(
            title = "Title $it", description = "Description $it", isChecked = false
        )
    },
    val newTitle: String = "",
    val newDescription: String = "",
)