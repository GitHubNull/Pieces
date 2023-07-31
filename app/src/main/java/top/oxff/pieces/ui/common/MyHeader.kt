package top.oxff.pieces.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderScreen(mainTitle: String, subTitle: String) {
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .height(180.dp),
        color = Color(0xFFFFD700),
        contentColor = LocalContentColor.current,
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp,
        border = null
    ){
//        var fontSize by remember { mutableStateOf(56.sp) }

        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(
                text = mainTitle,
                style = TextStyle(
                    color = LocalContentColor.current,
                    fontSize = 56.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subTitle,
                style = TextStyle(
                    color = LocalContentColor.current,
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}