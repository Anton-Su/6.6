package com.example.a62.presentation.ui.component

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.a62.domain.model.NobelPrize
import com.example.a62.navigation.Screen


@Composable
fun LaureatesCard(
    prize: NobelPrize,
    navHostController: NavHostController
) {
    Log.e("LaureatesCard", "Rendering card for ${prize.laureates}")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navHostController.navigate(
                    Screen.PhotoDetailScreen.createRoute(
                        prize.id.hashCode()
                    )
                )
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${prize.year} • ${prize.category}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                prize.laureates.forEach { laureate ->
                    Text(
                        text = laureate.fullName,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}