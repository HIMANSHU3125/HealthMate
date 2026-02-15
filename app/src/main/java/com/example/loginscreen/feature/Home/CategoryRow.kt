package com.example.loginscreen.feature.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.loginscreen.R
import com.example.loginscreen.core.model.CategoryModel
import com.example.loginscreen.core.model.DoctorModel

@Composable
private fun CategoryItem(item: CategoryModel) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(color = colorResource(R.color.lightPurple)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = item.Picture,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            text = item.Name ?: "",
            color = colorResource(R.color.darkPurple)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryItemPreview() {
    val item = CategoryModel(Id = 1, Name = "Himanshu", Picture = "https://via.placeholder.com/150")
    CategoryItem(item = item)
}

@Composable
fun CategoryRow(item: List<CategoryModel>,
                onClick:(DoctorModel)-> Unit) {
    Box(
        Modifier.fillMaxWidth().heightIn(min = 100.dp)
    ) {
        if (item.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                items(item) { category ->
                    CategoryItem(category)
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun CategoryRowPreview() {
//    val sampleList = listOf(
//        CategoryModel(1, "Himanshu", "https://via.placeholder.com/150"),
//        CategoryModel(2, "Test", "https://via.placeholder.com/150")
//    )
//    CategoryRow(item = sampleList, onClick =)
//}
