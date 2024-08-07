package dev.bogibek.mytaxitask.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PermissionNeedScreen(
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {

    Box(modifier = modifier) {
        Column(
            modifier = modifier
                .background(Color.Cyan)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Bu dasturdan foydalanish uchun bizga locationga va post notificationga permission kerak!",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    ),
                    onClick = { onPermissionGranted.invoke() }
                ) {
                    Text(text = "Permission berish")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.White
                    ),
                    onClick = { onPermissionDenied.invoke() }
                ) {
                    Text(text = "Kerak emas.")
                }
            }
        }
    }

}

@Preview
@Composable
private fun PermissionPreview() {
    PermissionNeedScreen(
        modifier = Modifier.fillMaxSize(),
        onPermissionGranted = { /*TODO*/ }) {

    }
}