package com.example.nano.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navController: NavHostController) {


    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val email = remember { mutableStateOf(TextFieldValue()) }
        val password = remember { mutableStateOf(TextFieldValue()) }


        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "email") },
            value = email.value,
            onValueChange = { email.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            label = { Text(text = "Password") },
            value = password.value,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { password.value = it })

        Spacer(modifier = Modifier.height(20.dp))
        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email.value.text, password.value.text)

                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                                    email.value.text,
                                    password.value.text
                                ).addOnCompleteListener {

                                    if (it.isSuccessful) {

                                        val db = Firebase.firestore
                                        val user = FirebaseAuth.getInstance().currentUser
                                        db.collection("users").document(user?.uid.toString())
                                            .set(
                                                hashMapOf(
                                                    "email" to user?.email,

                                                    "role" to "user",


                                                    "score" to 0,

                                                    "layout" to 5,







                                                )

                                            )
                                            .addOnSuccessListener { documentReference ->
                                                Log.d(
                                                    "TAG",
                                                    "DocumentSnapshot added with ID: ${user?.uid.toString()}"
                                                )
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w("TAG", "Error adding document", e)
                                            }

                                            .addOnSuccessListener { documentReference ->
                                                Log.d(
                                                    "TAG",
                                                    "DocumentSnapshot added with ID: ${user?.uid.toString()}"
                                                )
                                            }
                                            .addOnFailureListener { e ->
                                                Log.w("TAG", "Error adding document", e)
                                            }
                                        Log.d("Login", "signInWithEmail:success")
                                        navController.navigate("Home")
                                    } else {
                                        Log.w("Login", "signInWithEmail:failure", it.exception)
                                    }
                                }

                                Log.d("Register", "createUserWithEmail:success")
                            } else {
                                Log.w("Register", "createUserWithEmail:failure", it.exception)
                            }
                        }


                },
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(text = "Register")
            }
        }


    }
}