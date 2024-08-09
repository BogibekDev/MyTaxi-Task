package dev.bogibek.mytaxitask.presentation.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.bogibek.mytaxitask.R
import dev.bogibek.mytaxitask.presentation.ui.theme.custom.appColors

@Composable
fun BottomSheetView(
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.appColors.mainBgColor,
                RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
            )
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.appColors.leftIconBack,
                    RoundedCornerShape(12.dp)
                ),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_switch),
                    contentDescription = "Switch",
                    tint = MaterialTheme.appColors.leftIconTint
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Tarif",
                    style = TextStyle(
                        color = MaterialTheme.appColors.mainTextColor,
                        fontFamily = FontFamily(
                            Font(R.font.lato_regular)
                        ),
                        fontWeight = FontWeight(600),
                        fontSize = 18.sp
                    )
                )

                Text(
                    text = "6 / 8",
                    style = TextStyle(
                        color = MaterialTheme.appColors.leftIconTint,
                        fontFamily = FontFamily(
                            Font(R.font.lato_regular)
                        ),
                        fontWeight = FontWeight(600),
                        fontSize = 18.sp
                    )
                )

                Spacer(modifier = Modifier.width(2.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_right),
                    contentDescription = "Right",
                    tint = MaterialTheme.appColors.rightIconTint
                )

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.appColors.lineColor)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_order),
                    contentDescription = "Order",
                    tint = MaterialTheme.appColors.leftIconTint
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Buyurtmalar",
                    style = TextStyle(
                        color = MaterialTheme.appColors.mainTextColor,
                        fontFamily = FontFamily(
                            Font(R.font.lato_regular)
                        ),
                        fontWeight = FontWeight(600),
                        fontSize = 18.sp
                    )
                )

                Text(
                    text = "0",
                    style = TextStyle(
                        color = MaterialTheme.appColors.leftIconTint,
                        fontFamily = FontFamily(
                            Font(R.font.lato_regular)
                        ),
                        fontWeight = FontWeight(600),
                        fontSize = 18.sp
                    )
                )

                Spacer(modifier = Modifier.width(2.dp))

                Icon(
                    painter = painterResource(id = R.drawable.ic_right),
                    contentDescription = "Right",
                    tint = MaterialTheme.appColors.rightIconTint
                )

            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.appColors.lineColor)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_rocket),
                    contentDescription = "Rocket",
                    tint = MaterialTheme.appColors.leftIconTint
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    modifier = Modifier.weight(1f),
                    text = "Bordur",
                    style = TextStyle(
                        color = MaterialTheme.appColors.mainTextColor,
                        fontFamily = FontFamily(
                            Font(R.font.lato_regular)
                        ),
                        fontWeight = FontWeight(600),
                        fontSize = 18.sp
                    )
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_right),
                    contentDescription = "Right",
                    tint = MaterialTheme.appColors.rightIconTint
                )

            }


        }
    }

}


