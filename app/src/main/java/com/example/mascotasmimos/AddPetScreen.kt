package com.example.mascotasmimos

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.mascotasmimos.ui.theme.MascotasmimosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(onBack: () -> Unit) {
    var petName by remember { mutableStateOf("") }
    var petAge by remember { mutableStateOf("") }
    var petType by remember { mutableStateOf("") }
    var petBreed by remember { mutableStateOf("") }
    var petImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> petImageUri = uri }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen de la mascota
        if (petImageUri != null) {
            Image(
                bitmap = context.contentResolver
                    .openInputStream(petImageUri!!)
                    ?.use { android.graphics.BitmapFactory.decodeStream(it) }
                    ?.asImageBitmap()!!,
                contentDescription = "Imagen de la mascota",
                modifier = Modifier.size(120.dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.todosanimales),
                contentDescription = "Imagen de la mascota",
                modifier = Modifier.size(120.dp)
            )
        }

        Button(
            onClick = { imagePicker.launch("image/*") },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Seleccionar imagen")
        }

        // Campos del formulario
        OutlinedTextField(
            value = petName,
            onValueChange = { petName = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = petAge,
            onValueChange = { petAge = it },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = petType,
            onValueChange = { petType = it },
            label = { Text("Tipo (Perro, Gato, etc.)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = petBreed,
            onValueChange = { petBreed = it },
            label = { Text("Raza") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Validación y guardado
                if (petName.isNotBlank() && petAge.isNotBlank() && petType.isNotBlank()) {
                    // Aquí iría la lógica para guardar
                    onBack() // Volver atrás después de guardar
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar mascota")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddPetScreenPreview() {
    MascotasmimosTheme {
        AddPetScreen(onBack = {})
    }
}