package com.example.tp3_hci.components.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tp3_hci.R
import com.example.tp3_hci.ui.theme.FitiBlue
import com.example.tp3_hci.ui.theme.FitiGreenButton
import com.example.tp3_hci.ui.theme.FitiWhiteText


@Composable
fun ProfileAvatar(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    onImageUrlChange: ((String) -> Unit)? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        var popUpState by remember {
            mutableStateOf(false)
        }

        val imageModifier = Modifier
            .fillMaxSize(1f)
            .aspectRatio(
                ratio = 1f,
                matchHeightConstraintsFirst = true
            )
            .clip(CircleShape)
            .border(
                shape = CircleShape,
                width = 4.dp,
                color = FitiBlue,
            )

        if(imageUrl == null){
            Image(
                painter = painterResource(id = R.drawable.profile_placeholder),
                contentDescription = "Perfil sin avatar",
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
        } else {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Avatar del perfil",
                modifier = imageModifier,
                contentScale = ContentScale.Crop,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ProfileAvatarPreview(){
    Column(
        modifier = Modifier.padding(50.dp)
    ) {
        ProfileAvatar(
            imageUrl = "https://static.generated.photos/vue-static/face-generator/landing/wall/7.jpg",
            modifier = Modifier.fillMaxHeight(0.3f)
        )
        //ProfileAvatar(imageUrl = null)
    }
}