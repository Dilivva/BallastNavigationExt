package com.dilivva.ballastnavigationex.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dilivva.ballastnavigationex.presentation.components.BneChatTextField

data class Message(
    val id: Int,
    val isUser: Boolean,
    val message: String,
    val time: String
)

val messages = listOf(
    Message(
        id = 0,
        isUser = true,
        message = "Village political chargers reel belief refine returned, lands referrals impression suppose drivers aging. ",
        "10:44:33"
    ),
    Message(
        id = 88,
        isUser = true,
        message = "Garcia caroline measurement scott byte battlefield consistently, factor metals ago julie organizing sense removal, floral.",
        "19:39:34"
    ),
    Message(
        id = 619,
        isUser = false,
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        "01:35:43"
    ),
    Message(
        id = 4,
        isUser = true,
        message = "Adventures bundle checkout records ruby foundation appraisal, decisions features knight tested society tab. ",
        "22:25:42"
    ),
    Message(id = 78, isUser = false, message = "chart extend offices", "16:18:43"),
    Message(
        id = 22123,
        isUser = false,
        message = "Task art jaguar unit cookbook vulnerability daddy, infrared paperbacks diagram controlling throw francisco insertion, october utils regarding listing sacrifice precious lie, dave stakeholders sunset analog bones believes defining, greg. ",
        "23:22:38"
    ),

    Message(
        id = 9228,
        isUser = true,
        message = "Village political chargers reel belief refine returned, lands referrals impression suppose drivers aging. ",
        "10:44:33"
    ),
    Message(
        id = 7783,
        isUser = true,
        message = "Garcia caroline measurement scott byte battlefield consistently, factor metals ago julie organizing sense removal, floral.",
        "19:39:34"
    ),
    Message(
        id = 1815377,
        isUser = false,
        message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        "01:35:43"
    ),
    Message(
        id = 7205,
        isUser = true,
        message = "Adventures bundle checkout records ruby foundation appraisal, decisions features knight tested society tab. ",
        "22:25:42"
    ),
    Message(id = 7239643, isUser = false, message = "chart extend offices", "16:18:43"),
    Message(
        id = 89,
        isUser = false,
        message = "Task art jaguar unit cookbook vulnerability daddy, infrared paperbacks diagram controlling throw francisco insertion, october utils regarding listing sacrifice precious lie, dave stakeholders sunset analog bones believes defining, greg. ",
        "23:22:38"
    )
)

@Composable
fun DashboardScreen(
    email: String = ""
) {
    DashboardContent()
}

@Composable
fun DashboardContent(

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TopBar()
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true,
            contentPadding = PaddingValues(5.dp)
        ) {
            items(messages, key = { it.id }) {
                MessageItem(it)
            }
        }
        BottomBar()
    }
}

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(50.dp)
                .background(MaterialTheme.colorScheme.inverseSurface, shape = CircleShape)
        )
        Column {
            Text(
                "Jason",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text("online", style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        BneIconButtons(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            icon = Icons.Filled.Phone
        )
        BneIconButtons(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            icon = Icons.Filled.Videocam
        )
    }
}

@Composable
private fun BottomBar() {
    var message by remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth().height(55.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BneIconButtons(
            modifier = Modifier.size(50.dp),
            shape = RoundedCornerShape(8.dp),
            icon = Icons.Filled.Mic
        )
        BneChatTextField(
            modifier = Modifier.weight(1f).height(50.dp),
            value = message,
            onValueChanged = { message = it },
            placeHolder = "Type message",
            trailingIcon = {
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.Send, contentDescription = null)
                }
            }
        )
    }
}

@Composable
private fun BneIconButtons(
    modifier: Modifier = Modifier,
    shape: Shape,
    icon: ImageVector
) {
    FilledIconButton(
        onClick = {},
        shape = shape,
        modifier = modifier,
        colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
    }
}

@Composable
fun MessageItem(
    message: Message
) {
    val alignment = if (message.isUser) Alignment.End else Alignment.Start
    val bg =
        if (message.isUser) MaterialTheme.colorScheme.inverseSurface else MaterialTheme.colorScheme.primary
    val messageColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    val timeColor = if (isSystemInDarkTheme()) Color.White else Color.Black.copy(0.6f)
    val bubbleShape = if (message.isUser)
        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomStart = 8.dp)
    else
        RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp, bottomEnd = 8.dp)
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
        horizontalAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .background(bg, shape = bubbleShape)
                .heightIn(min = 40.dp, max = 100.dp)
                .fillMaxWidth(0.7f)
                .padding(10.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                message.message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = messageColor
                ),
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            message.time,
            style = MaterialTheme.typography.labelMedium.copy(
                color = timeColor
            ),
            overflow = TextOverflow.Ellipsis
        )
    }
}