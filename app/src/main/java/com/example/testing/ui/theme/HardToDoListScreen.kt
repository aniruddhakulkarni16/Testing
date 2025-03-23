package com.example.testing.ui.theme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ToDoListScreenRoot(modifier: Modifier) {
    val viewModel: HardToDoListViewModel = viewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    HardToDoList(modifier = modifier, state = state, onAction = viewModel::onAction)
}

@Composable
private fun HardToDoList(
    modifier: Modifier = Modifier,
    state: TodoList,
    onAction: (TodoListAction) -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(state.todos) { todo ->
                TodoItem(
                    modifier = Modifier.fillMaxWidth(),
                    todo = todo,
                    onCheckedChange = { onAction(TodoListAction.OnTodoCompleteToggle(todo)) },
                    onDeleteClick = { onAction(TodoListAction.OnDeleteClick(todo)) }
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                TextField(
                    value = state.newTitle,
                    onValueChange = { onAction(TodoListAction.OnTitleUpdate(it)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = state.newDescription,
                    onValueChange = { onAction(TodoListAction.OnDescriptionUpdate(it)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("Description") }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { onAction(TodoListAction.OnAddClick) }
            ) {
                Text("Add")
            }
        }
    }
}

@Composable
private fun TodoItem(
    modifier: Modifier = Modifier,
    todo: Todo,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = todo.title,
                fontWeight = FontWeight.Bold,
                textDecoration = if (todo.isChecked) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = todo.description,
                textDecoration = if (todo.isChecked) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                }
            )
        }
        Checkbox(
            checked = todo.isChecked,
            onCheckedChange = onCheckedChange
        )
        IconButton(
            onClick = onDeleteClick
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HardToDoListPreview() {
    ToDoListScreenRoot(modifier = Modifier)
}