package com.example.task.Screen

import android.content.SharedPreferences
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.task.R
import com.example.task.data.UserInfo
import com.example.task.navigation.Screen
import com.razaghimahdi.compose_persian_date.PersianDatePickerDialog
import com.razaghimahdi.compose_persian_date.core.rememberPersianDatePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterScreen(
    navigation: NavHostController,
    userInfo: UserInfo,
    onNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onBirthdayChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    sharedPreferences: SharedPreferences
) {
    var onSave by remember { mutableStateOf(false) }
    var enableButton by remember { mutableStateOf(false) }
    enableButton = userInfo.name.isNotEmpty() && userInfo.lastName.isNotEmpty() && userInfo.birthday.isNotEmpty() && userInfo.code.isNotEmpty()
    if (onSave) {
        with(sharedPreferences.edit()) {
            putString("name", userInfo.name)
            putString("lastName", userInfo.lastName)
            putString("birthday", userInfo.birthday)
            putString("code", userInfo.code)
            commit()
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Test Application")
                }
            },
        )
    },
        bottomBar = {
            Button(
                onClick = {
                    onSave = !onSave
                    navigation.navigate(Screen.DetailScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                shape = ShapeDefaults.Small,
                enabled = enableButton,
            ) {
                Text(
                    text = stringResource(R.string.signup),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    ) { paddingValue ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Box(modifier = Modifier.padding(paddingValue)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    val rememberPersianDatePicker = rememberPersianDatePicker()
                    var showCalender by remember { mutableStateOf(false) }
                    rememberPersianDatePicker.updateMinYear(1300)
                    Text(
                        text = stringResource(R.string.input_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        value = userInfo.name,
                        onValueChange = { onNameChange.invoke(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 24.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        label = { Text(text = stringResource(R.string.name)) },
                        singleLine = true
                    )
                    Text(
                        text = stringResource(R.string.input_last_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        value = userInfo.lastName,
                        onValueChange = { onLastNameChange.invoke(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 24.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        label = { Text(text = stringResource(R.string.lastname)) },
                        singleLine = true
                    )
                    Text(
                        text = stringResource(R.string.input_birthday),
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        value = userInfo.birthday,
                        onValueChange = { onBirthdayChange.invoke(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 24.dp),
                        label = { Text(text = stringResource(R.string.bithday)) },
                        singleLine = true,
                        readOnly = true,
                        interactionSource = remember { MutableInteractionSource() }
                            .also { interactionSource ->
                                LaunchedEffect(interactionSource) {
                                    interactionSource.interactions.collect {
                                        if (it is PressInteraction.Release) {
                                            showCalender = !showCalender
                                        }
                                    }
                                }
                            }
                    )
                    if (showCalender) {
                        PersianDatePickerDialog(
                            rememberPersianDatePicker,
                            Modifier.fillMaxWidth(),
                            onDismissRequest = { showCalender = false },
                            onDateChanged = { year, month, day ->
                                onBirthdayChange.invoke("$year/$month/$day")
                            })
                    }
                    Text(
                        text = stringResource(R.string.input_code),
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        value = userInfo.code,
                        onValueChange = {
                            if (it.length <= 10) {
                                onCodeChange.invoke(it)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 24.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(text = stringResource(R.string.code)) },
                        singleLine = true
                    )
                }
            }

        }
    }
}