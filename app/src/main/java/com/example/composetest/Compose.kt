package com.example.composetest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ContactDetails(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RoundInitials("${contact.name.take(1)}${contact.familyName.take(1)}", contact.imageRes)

        Spacer(modifier = Modifier.height(16.dp))

        ContactColum(contact.name, contact.surname, contact.familyName, contact.isFavorite)

        Spacer(modifier = Modifier.height(32.dp))

        InfoRow(stringResource(R.string.phone), contact.phone.ifBlank { "---" })
        InfoRow(stringResource(R.string.address), contact.address.ifBlank { "---" })
        if (contact.email != null) {
            InfoRow(stringResource(R.string.email), contact.email)
        }
    }
}

@Composable
fun RoundInitials(initials: String, imageRes: Int?) {
    if (imageRes != null) {
        Image(
            modifier = Modifier.size(102.dp),
            contentScale = ContentScale.Crop,
            painter = painterResource(imageRes),
            contentDescription = null
        )
    } else {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.circle),
                contentDescription = null,
                tint = Color.LightGray
            )
            Text(
                style = MaterialTheme.typography.titleMedium,
                text = initials
            )
        }
    }
}

@Composable
fun ContactColum(name: String, surname: String?, familyName: String, isFavorite: Boolean) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        text = "$name ${surname.orEmpty()}",
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            style = MaterialTheme.typography.titleLarge,
            text = familyName
        )
        if (isFavorite) {
            Icon(
                modifier = Modifier.padding(start = 8.dp),
                painter = painterResource(android.R.drawable.star_big_on),
                contentDescription = null
            )
        }
    }
}

@Composable
fun InfoRow(label: String, infoText: String) {
    Row(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(0.5f),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic,
            text = "$label:",
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(0.5f),
            style = MaterialTheme.typography.bodyMedium,
            text = infoText
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun ContactNotNullPreview() {
    ContactDetails(
        contact = Contact(
            "Ivan",
            "Andreevich",
            "Drobitko",
            R.drawable.image,
            true,
            "+7 999 999 99 99",
            "Тверская улица, д. 1, кв. 10, Москва",
            "drobitkoivan@yandex.ru"
        )
    )
}

@Preview(showSystemUi = true)
@Composable
fun ContactNullPreview() {
    ContactDetails(
        contact = Contact(
            "Ivan",
            null,
            "Drobitko",
            null,
            false,
            "",
            "Тверская улица, д. 1, кв. 10, Москва"
        )
    )
}