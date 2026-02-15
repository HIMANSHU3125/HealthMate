package com.example.loginscreen.feature.TopDoctors

import android.content.res.ColorStateList
import android.widget.RatingBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import android.R as AndroidR

@Composable
fun ComposeRatingBar(
    rating: Float,
    stars: Int = 5,
) {
    val starTint = Color(0xffffc160).toArgb()

    AndroidView(
        factory = { context ->
            RatingBar(
                context,
                null,
                android.R.attr.ratingBarStyleSmall   // ✅ correct style
            ).apply {
                setNumStars(stars)
                setStepSize(0.5f)
                setIsIndicator(true)
                progressTintList= ColorStateList.valueOf(starTint)
            }
        }, update = {rb->
            rb.setRating(rating)
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ComposeRatingBarPreview() {
    ComposeRatingBar(
        rating = 4f,
        stars = 5
    )
}
