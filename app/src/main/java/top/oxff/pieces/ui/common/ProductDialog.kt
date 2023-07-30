@file:Suppress("NAME_SHADOWING")

package top.oxff.pieces.ui.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.BubbleChart
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.loper7.date_time_picker.dialog.CardDatePickerDialog
import top.oxff.pieces.day.PieceEarningsByDayEvent
import top.oxff.pieces.day.PieceEarningsByDayState
import top.oxff.pieces.types.DialogType
import top.oxff.pieces.util.DecimalFormatter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ProductDialog(
    state: PieceEarningsByDayState, onEvent: (PieceEarningsByDayEvent) -> Unit
) {

    val editable = state.dialogType == DialogType.ADD || state.dialogType == DialogType.UPDATE
    val titleContentDescription = if (state.dialogType == DialogType.ADD) {
        "新增图标"
    } else {
        "编辑图标"
    }

    val titleIcon = if (state.dialogType == DialogType.ADD) {
        Icons.Default.Add
    } else {
        Icons.Default.EditCalendar
    }

    val context = LocalContext.current
    val contextState = remember { mutableStateOf(context) }
    val zoneId = ZoneId.of("Asia/Shanghai")
    val zonedDateTime = ZonedDateTime.now(zoneId)
    val millisecond = zonedDateTime.toInstant().toEpochMilli()

    val priceDecimalFormatter = remember { DecimalFormatter() }
    val quantityDecimalFormatter = remember { DecimalFormatter() }

    AlertDialog(onDismissRequest = {
        onEvent(PieceEarningsByDayEvent.HideDialog)
    }, title = { Icon(titleIcon, contentDescription = titleContentDescription) }, text = {
        Column {
            OutlinedTextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.AttachMoney,
                        contentDescription = "单价",
                        tint = Color.Blue
                    )
                },
                label = {
                    Text(text = "单价")
                },
                trailingIcon = @Composable {//设置右边图标
                    Image(imageVector = Icons.Filled.Clear,
                        contentDescription = "clear",
                        modifier = Modifier.clickable {
                            onEvent(PieceEarningsByDayEvent.OnPriceChange(""))
                        }) // 添加点击清空事件
                },
                value = state.price,
                onValueChange = { newValue ->
                    onEvent(
                        PieceEarningsByDayEvent.OnPriceChange(
                            priceDecimalFormatter.cleanup(
                                newValue
                            )
                        )
                    )
                },
                enabled = editable,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.BubbleChart,
                        contentDescription = "数量",
                        tint = Color.Blue
                    )
                },
                label = {
                    Text(text = "数量")
                },
                trailingIcon = @Composable {//设置右边图标
                    Image(imageVector = Icons.Filled.Clear,
                        contentDescription = "clear",
                        modifier = Modifier.clickable { onEvent(PieceEarningsByDayEvent.OnQuantityChange("")) })
                },
                value = state.quantity,
                onValueChange = { newValue ->
                    onEvent(
                        PieceEarningsByDayEvent.OnQuantityChange(
                            quantityDecimalFormatter.cleanup(
                                newValue
                            )
                        )
                    )
                },
                enabled = editable,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = "时间",
                        tint = Color.Blue
                    )
                },
                label = {
                    Text(text = "时间")
                },
                value = state.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                onValueChange = {
                },
                enabled = false,
                modifier = Modifier
                    .clickable {
                        kotlin.run {
                            Log.d("ProductDialog", "clickable")
                        }
                        onEvent(
                            PieceEarningsByDayEvent.OnChangeShowDatePicker(
                                true
                            )
                        )
                    }
                    .padding(bottom = 8.dp)
            )
        }
    }, confirmButton = {
        Button(onClick = {
            if (state.dialogType == DialogType.ADD) {
                onEvent(PieceEarningsByDayEvent.AddPieceEarningsByDay)
            } else if (state.dialogType == DialogType.UPDATE) {
                onEvent(PieceEarningsByDayEvent.UpdatePiece)
            }
            onEvent(PieceEarningsByDayEvent.HideDialog)
        }) {
            Text(text = "确认")
        }
    }, dismissButton = {
        Button(onClick = {
            onEvent(PieceEarningsByDayEvent.HideDialog)

            if (state.dialogType == DialogType.UPDATE) {
                onEvent(PieceEarningsByDayEvent.OnPriceChange(""))
                onEvent(PieceEarningsByDayEvent.OnQuantityChange(""))
                onEvent(PieceEarningsByDayEvent.OnDateTimeChange(LocalDateTime.now()))
            }

        }) {
            Text(text = "取消")
        }
    })

    if (state.showDatePicker) {
        CardDatePickerDialog.builder(contextState.value)
            .setTitle("选择日期时间")
            .setDefaultTime(millisecond)
            .setDisplayType()
            .setOnCancel {
                run {
                    Log.d("ProductDialog", "setOnCancel")
                }
                onEvent(PieceEarningsByDayEvent.OnChangeShowDatePicker(false))
            }
            .setOnChoose { millisecond ->
                Log.d("ProductDialog", "setOnChoose")
                onEvent(PieceEarningsByDayEvent.OnChangeShowDatePicker(false))
                val instant = Instant.ofEpochMilli(millisecond)
                val zoneId = ZoneId.of("Asia/Shanghai")
                onEvent(
                    PieceEarningsByDayEvent.OnDateTimeChange(
                        LocalDateTime.ofInstant(
                            instant,
                            zoneId
                        )
                    )
                )
            }.build().show()
    }
}