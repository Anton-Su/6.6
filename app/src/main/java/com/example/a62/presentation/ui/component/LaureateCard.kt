package com.example.a62.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.a62.domain.model.Laureate
import com.example.a62.navigation.Screen

@Composable
fun LaureateCard(item: Laureate, navHostController: NavHostController) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable { navHostController.navigate(Screen.PhotoDetailScreen.createRoute(item.id.hashCode())) }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "${item.year} • ${item.category}", style = MaterialTheme.typography.bodyLarge)
                Text(text = item.fullName, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
