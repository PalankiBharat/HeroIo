package ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import heroio.composeapp.generated.resources.Res
import heroio.composeapp.generated.resources.height_scale
import heroio.composeapp.generated.resources.weight_scale
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun HeightWeightStats(height: String, weight: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp)
            .clip(shape = RoundedCornerShape(size = 10.dp)).background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.DarkGray.copy(alpha = 0.9f),
                        Color.DarkGray.copy(alpha = 0.5f),
                        Color.Black.copy(alpha = 0.5f),
                        Color.Black.copy(alpha = 0.2f),
                    )
                )
            ).padding(vertical = 20.dp).height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Scale(
            modifier = Modifier.weight(1f),
            text = "Weight",
            value = weight,
            icon = Res.drawable.weight_scale
        )
        VerticalDivider(modifier = Modifier.fillMaxHeight(0.7f))
        Scale(
            modifier = Modifier.weight(1f),
            text = "Height",
            value = height,
            icon = Res.drawable.height_scale
        )
    }
}

@Composable
fun Scale(modifier: Modifier = Modifier, text: String, value: String, icon: DrawableResource) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(resource = icon),
                contentDescription = text,
                modifier = Modifier.size(14.dp),
                tint = Color.White.copy(alpha = 0.7f)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(text = value, color = Color.White)
        }
        Text(text = text, modifier = Modifier.padding(top = 6.dp), color = Color.White)
    }
}