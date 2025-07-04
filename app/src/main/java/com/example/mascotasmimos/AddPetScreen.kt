package com.example.mascotasmimos

import android.net.Uri
import android.widget.Toast
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

    // Inicia Firebase Storage
    val storage = remember { com.google.firebase.storage.FirebaseStorage.getInstance() }
    val storageRef = remember { storage.reference.child("pets") }

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
        // Imagen seleccionada o placeholder
        val bitmap = petImageUri?.let { uri ->
            context.contentResolver.openInputStream(uri)?.use {
                android.graphics.BitmapFactory.decodeStream(it)
            }
        }?.asImageBitmap()

        if (bitmap != null) {
            Image(bitmap = bitmap, contentDescription = null, modifier = Modifier.size(120.dp))
        } else {
            Image(
                painter = painterResource(id = R.drawable.todosanimales),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )
        }

        Button(onClick = { imagePicker.launch("image/*") }, modifier = Modifier.padding(vertical = 16.dp)) {
            Text("Seleccionar imagen")
        }

        OutlinedTextField(value = petName, onValueChange = { petName = it }, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = petAge, onValueChange = { petAge = it }, label = { Text("Edad") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = petType, onValueChange = { petType = it }, label = { Text("Tipo") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = petBreed, onValueChange = { petBreed = it }, label = { Text("Raza") }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            if (petName.isBlank() || petAge.isBlank() || petType.isBlank()) {
                Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@Button
            }
            if (petImageUri != null) {
                // Subida a Firebase Storage
                val uri = petImageUri!!
                val fileRef = storageRef.child("${System.currentTimeMillis()}.jpg")
                fileRef.putFile(uri)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Mascota guardada", Toast.LENGTH_SHORT).show()
                        onBack()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Error al subir imagen: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Mascota guardada", Toast.LENGTH_SHORT).show()
                onBack()
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Guardar mascota")
        }
    }
}
