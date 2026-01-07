package com.example.cx_android

import android.os.Bundle
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cx_android.ui.theme.Cx_androidTheme

class MainActivity : AppCompatActivity() {

    private var isShowingReactNative = false
    private val RN_FRAGMENT_TAG = "rn_fragment"
    private val RN_CONTAINER_ID = ViewGroup.generateViewId()

    private val backCallback = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (isShowingReactNative) {
                val fragment = supportFragmentManager.findFragmentByTag(RN_FRAGMENT_TAG)
                if (fragment != null) {
                    supportFragmentManager.beginTransaction()
                        .remove(fragment)
                        .commit()
                }
                showNativeScreen()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, backCallback)
        enableEdgeToEdge()
        showNativeScreen()
    }

    private fun showNativeScreen() {
        isShowingReactNative = false
        backCallback.isEnabled = false
        setContent {
            Cx_androidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        onOpenReactNative = { showReactNativeScreen() }
                    )
                }
            }
        }
    }

    private fun showReactNativeScreen() {
        isShowingReactNative = true
        backCallback.isEnabled = true

        // Create a FrameLayout as container for the Fragment
        val container = FrameLayout(this).apply {
            id = RN_CONTAINER_ID
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
        setContentView(container)

        // Add RN Fragment
        supportFragmentManager.beginTransaction()
            .replace(RN_CONTAINER_ID, RNFragment.newInstance("RNApp"), RN_FRAGMENT_TAG)
            .commit()
    }

}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onOpenReactNative: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Android Nativo",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "cx_android",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onOpenReactNative) {
            Text("Abrir React Native")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    Cx_androidTheme {
        MainScreen()
    }
}
