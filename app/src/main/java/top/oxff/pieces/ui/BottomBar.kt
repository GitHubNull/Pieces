package top.oxff.pieces.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import top.oxff.pieces.R

@Composable
fun TabItem(@DrawableRes iconId: Int, title: String, modifier: Modifier = Modifier, tint: Color = Color.Black) {
    Column (modifier.padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Icon(painter = painterResource(id = iconId), contentDescription = title, Modifier.size(24.dp), tint = tint)
        Text(text = title, fontSize = 11.sp, color = tint)
    }
}

@Composable
fun BottomBar(selected: Int, onSelectedChanged: (Int)->Unit) {
    Row (Modifier.background(Color.LightGray)){
        TabItem(
            R.drawable.rili,
            "日",
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChanged(0)
                },
            if (0 == selected) Color.Green else Color.Black)

        TabItem(
            R.drawable.yueliang,
            "月",
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChanged(1)
                },
            if (1 == selected) Color.Green else Color.Black)

        TabItem(
            R.drawable.calendar,
            "年",
            Modifier
                .weight(1f)
                .clickable {
                    onSelectedChanged(2)
                },
            if (2 == selected) Color.Green else Color.Black)
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TabItemPreview() {
//    PiecesTheme {
//        TabItem(R.drawable.rili, "日")
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun BottomPreview() {
//    PiecesTheme {
//        var selectTab by remember { mutableStateOf(0) }
//        BottomBar(selectTab){
//            selectTab = it
//        }
//    }
//}