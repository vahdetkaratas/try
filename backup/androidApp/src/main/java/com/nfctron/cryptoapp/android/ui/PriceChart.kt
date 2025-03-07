import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun PriceChart(
    prices: List<Double>,
    modifier: Modifier = Modifier
) {
    if (prices.isEmpty()) return

    Canvas(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
    ) {
        val path = Path()
        val points = prices.mapIndexed { index, price ->
            Offset(
                x = size.width * (index.toFloat() / (prices.size - 1)),
                y = size.height * (1f - (price / prices.maxOrNull()!!).toFloat())
            )
        }

        path.moveTo(points.first().x, points.first().y)
        points.forEach { point ->
            path.lineTo(point.x, point.y)
        }

        drawPath(
            path = path,
            color = Color.Green,
            style = Stroke(width = 2.dp.toPx())
        )
    }
} 