package com.example.midterm_labact.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.midterm_labact.data.TodoDto
import com.example.midterm_labact.vm.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel) {
    val pagingItems = viewModel.todos.collectAsLazyPagingItems()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Todos") }) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TodoList(pagingItems)

            // Initial load (refresh)
            when (val refresh = pagingItems.loadState.refresh) {
                is LoadState.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is LoadState.Error -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Initial load failed: ${refresh.error.message ?: "Unknown error"}")
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { pagingItems.retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun TodoList(pagingItems: LazyPagingItems<TodoDto>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pagingItems.itemCount) { index ->
            val todo = pagingItems[index]
            if (todo != null) TodoItem(todo)
        }

        // Bottom loader / retry (append)
        item {
            when (val append = pagingItems.loadState.append) {
                is LoadState.Loading -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is LoadState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Load failed: ${append.error.message ?: "Unknown error"}")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { pagingItems.retry() }) {
                            Text("Retry")
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun TodoItem(todo: TodoDto) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text("#${todo.id} • ${todo.title}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(6.dp))
            Text(
                text = if (todo.completed) "Status: Completed ✅" else "Status: Pending ⏳",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
