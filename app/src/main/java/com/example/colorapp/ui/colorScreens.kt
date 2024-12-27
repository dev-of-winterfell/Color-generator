package com.example.colorapp.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sync // Add this import
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // Add this import
import androidx.compose.ui.unit.dp

import com.example.colorapp.ui.ViewModel.ColorViewModel
import com.example.colorapp.ui.data.ColorEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@Composable
fun ColorScreen(
    viewModel: ColorViewModel = viewModel()
) {
    val colors by viewModel.colors.collectAsState()
    val unsyncedCount by viewModel.unsyncedCount.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {  // Wrap everything in a Box
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // App bar with title and sync button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Color App",
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(onClick = { viewModel.syncColors() }) {
                    BadgedBox(
                        badge = {
                            if (unsyncedCount > 0) {
                                Badge { Text(unsyncedCount.toString()) }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Sync,
                            contentDescription = "Sync"
                        )
                    }
                }
            }

            // Grid of colors
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)  // Add weight to fill available space
            ) {
                items(colors) { color ->
                    ColorItem(color)
                }
            }
        }

        // Floating Action Button
        ExtendedFloatingActionButton(
            onClick = { viewModel.addRandomColor() },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
            content = {  // Changed from 'text' to 'content'
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Add Color")
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        )
    }
}
@Composable
fun ColorItem(color: ColorEntity) {
    Card(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor(color.color))
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = color.color.uppercase(),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium
                )
                Divider(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .width(60.dp),
                    color = Color.White.copy(alpha = 0.7f),
                    thickness = 2.dp
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Created at",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        .format(Date(color.timestamp)),
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}