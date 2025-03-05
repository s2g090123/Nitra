package com.jiachian.more.ui

import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jiachian.common.ui.DSTheme
import com.jiachian.common.ui.NitraTheme
import com.jiachian.lock.util.BiometricPromptManager
import com.jiachian.more.R
import com.jiachian.more.ui.event.MoreEvent
import com.jiachian.more.ui.model.MoreState

@Composable
fun MoreScreen(
    state: MoreState,
    onEvent: (MoreEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(horizontal = DSTheme.sizes.dp12, vertical = DSTheme.sizes.dp16)
            .verticalScroll(rememberScrollState()),
    ) {
        LockEnabledCheckBox(
            checked = state.lockEnabled,
            onCheckedChange = {
                onEvent(MoreEvent.LockChanged(it))
            }
        )
    }
}

@Composable
private fun LockEnabledCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val activity = context as? AppCompatActivity

    if (activity != null) {
        val promptManager = remember { BiometricPromptManager(activity) }
        val enrollLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.StartActivityForResult(),
            onResult = {},
        )

        Row(
            modifier = modifier
                .clickable {
                    val isChecked = !checked
                    if (isChecked) {
                        if (!promptManager.canBiometricPrompt()) {
                            if (Build.VERSION.SDK_INT >= 30) {
                                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                    putExtra(
                                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                                    )
                                }
                                enrollLauncher.launch(enrollIntent)
                            }
                        } else {
                            onCheckedChange(true)
                        }
                    } else {
                        onCheckedChange(false)
                    }
                }
                .padding(DSTheme.sizes.dp12),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                modifier = modifier,
                checked = checked,
                colors = CheckboxDefaults.colors(checkedColor = DSTheme.colors.primary),
                onCheckedChange = null,
            )
            Spacer(modifier = Modifier.width(DSTheme.sizes.dp8))
            Text(
                text = stringResource(R.string.checkbox_app_lock),
                style = DSTheme.fonts.semiBold16.copy(DSTheme.colors.black),
            )
        }
    }
}

@Preview
@Composable
fun MoreScreenPreview() {
    NitraTheme {
        MoreScreen(
            state = MoreState(),
            onEvent = {},
        )
    }
}