package com.llucs.mist.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.llucs.mist.MainActivity
import com.llucs.mist.data.model.WeatherState

class MistWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            MistWidgetContent(
                temperature = "--",
                condition = "Loading...",
                location = "Mist"
            )
        }
    }

    suspend fun updateWidget(context: Context, id: GlanceId, state: WeatherState) {
        provideContent {
            MistWidgetContent(
                temperature = state.temperature,
                condition = state.condition,
                location = state.locationName,
                maxTemp = state.dailyForecast.firstOrNull()?.maxTemp,
                minTemp = state.dailyForecast.firstOrNull()?.minTemp
            )
        }
    }
}

@Composable
fun MistWidgetContent(
    temperature: String,
    condition: String,
    location: String,
    maxTemp: String? = null,
    minTemp: String? = null
) {
    val surfaceColor = GlanceTheme.colors.widgetBackground
    val onSurface = GlanceTheme.colors.onBackground
    val primary = GlanceTheme.colors.primary

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(surfaceColor)
            .padding(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = temperature,
            style = TextStyle(
                fontSize = 42.sp,
                fontWeight = FontWeight.Light,
                color = ColorProvider(onSurface)
            )
        )
        Text(
            text = condition,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ColorProvider(onSurface)
            )
        )
        Spacer(modifier = GlanceModifier.height(4.dp))
        Text(
            text = location,
            style = TextStyle(
                fontSize = 12.sp,
                color = ColorProvider(primary)
            )
        )
        if (maxTemp != null && minTemp != null) {
            Row(
                modifier = GlanceModifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "H: $maxTemp",
                    style = TextStyle(fontSize = 11.sp, color = ColorProvider(onSurface))
                )
                Spacer(modifier = GlanceModifier.width(8.dp))
                Text(
                    text = "L: $minTemp",
                    style = TextStyle(fontSize = 11.sp, color = ColorProvider(onSurface))
                )
            }
        }
    }
}
