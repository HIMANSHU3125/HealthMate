package com.example.loginscreen.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.loginscreen.R
import com.example.loginscreen.core.model.DoctorModel
import com.example.loginscreen.feature.detail.component.ActionItem
import com.example.loginscreen.feature.detail.component.RatingStat
import com.example.loginscreen.feature.detail.component.VerticalDivider
import com.example.loginscreen.feature.detail.component.stateColumn

@Composable
fun DetailBody(
    item: DoctorModel,
    onOpenWebsite: (String) -> Unit,
    onSendSms: (mobile: String, body: String) -> Unit,
    onDial: (mobile: String) -> Unit,
    onDirection: (locationUrl: String) -> Unit,
    onShare: (subject: String, text: String) -> Unit,
    onBookAppointment: (String, String) -> Unit
) {
    val darkPurple= colorResource(R.color.darkPurple)
    val gray= colorResource(R.color.gray)
    val lightPurple= colorResource(R.color.lightPurple)

    Column(modifier= Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        Text(
            text = item.Name?:"title",
            color=Color.Black,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Text(
            text = item.Special?:"special",
            color=Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Row(verticalAlignment  =Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 16.dp, end=16.dp,top=16.dp)) {

            Image(painter = painterResource(R.drawable.location), contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(
                text=item.Address?:"address",
                color=darkPurple,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier=Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            stateColumn(title = "Patients", value = item.Patiens?:"_")
            VerticalDivider(color = gray)
            stateColumn("Experience", value = "${item.Expriense ?: 0} Years")
            VerticalDivider(color = gray)
            RatingStat(title = "Rating", rating = item.Rating ?:0.0)
        }
        Text(
            text="Biography",
            color=Color.Black,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)

        )
        Text(text = item.Biography?:"Bio",
            color=Color.Black,
            modifier = Modifier.padding(start = 16.dp,top=8.dp,end=16.dp))

        Row(
            modifier= Modifier
                .fillMaxWidth()
                .padding(top=16.dp,bottom=24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ActionItem(
                label = "Website",
                icon=R.drawable.website,
                lightPurple=lightPurple,
                enabled = !item.Site.isNullOrBlank()
            ) {
                item.Site?.let(onOpenWebsite)


            }
            ActionItem(
                label = "Message",
                icon=R.drawable.message,
                lightPurple=lightPurple,
                enabled = !item.Site.isNullOrBlank()
            ) {
                onSendSms(item.Mobile,"the Sms Text")
            }
            ActionItem(
                label = "Call",
                icon=R.drawable.call,
                lightPurple=lightPurple,
                enabled = !item.Site.isNullOrBlank()
            ) {
                onDial(item.Mobile)

            }
            ActionItem(
                label = "Direction",
                icon=R.drawable.direction,
                lightPurple=lightPurple,
                enabled = !item.Site.isNullOrBlank()
            ) {
                item.Location?.let{onDirection}
            }
            ActionItem(
                label = "share",
                icon=R.drawable.share,
                lightPurple=lightPurple,
                enabled = true
            ) {
                val subject=item.Name
                val text="${item.Name} ${item.Address} ${item.Mobile}"
                onShare(subject,text)

            }
        }
        Button(onClick = { onBookAppointment(item.Id.toString(), item.Name ?: "") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.darkPurple),
                contentColor = Color.White)

            ) {
            Text(text="Make Appointment",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
                )
        }
        Spacer(Modifier.height(8.dp))










    }
}

