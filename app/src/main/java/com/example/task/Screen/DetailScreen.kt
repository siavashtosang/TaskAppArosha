package com.example.task.Screen

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.task.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navigation: NavHostController,
    onClear: () -> Unit,
    sharedPreferences: SharedPreferences,
) {
    val activity = (LocalContext.current as? Activity)
    val defaultValue = sharedPreferences.all

    Scaffold(topBar = {
        TopAppBar(title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "Test Application")
            }
        },
            navigationIcon = {
                IconButton(onClick = { navigation.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back"
                    )
                }
            })
    },
        bottomBar = {
            Row( verticalAlignment = Alignment.CenterVertically, ) {

                Button(
                    onClick = {
                        activity?.finish()
                    },
                    modifier = Modifier.weight(1f).padding(start = 24.dp, end = 8.dp, bottom = 24.dp),
                    shape = ShapeDefaults.Small
                ) {
                    Text(
                        text = stringResource(R.string.exit),
                    )
                }

                Button(
                    onClick = {
                        activity?.finish()
                        onClear()
                    },
                    modifier = Modifier.weight(1f).padding(start = 8.dp, end = 24.dp, bottom = 24.dp),
                    shape = ShapeDefaults.Small
                ) {
                    Text(
                        text = stringResource(R.string.sign_out),
                    )
                }
            }
        }
    ) { paddingValues ->
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .padding(24.dp)
                        .height(250.dp),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            UserDetailInfo(
                                title = stringResource(id = R.string.name),
                                value = defaultValue["name"]
                            )
                            UserDetailInfo(
                                title = stringResource(id = R.string.lastname),
                                value = defaultValue["lastName"]
                            )

                        }
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            UserDetailInfo(
                                title = stringResource(id = R.string.bithday),
                                value = defaultValue["birthday"]
                            )
                            UserDetailInfo(
                                title = stringResource(id = R.string.code),
                                value = defaultValue["code"]
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun UserDetailInfo(title: String, value: Any?) {
    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Text(
            text = value.toString(),
            modifier = Modifier.padding(top = 8.dp),
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black.copy(alpha = 0.5f)
        )
    }
}
