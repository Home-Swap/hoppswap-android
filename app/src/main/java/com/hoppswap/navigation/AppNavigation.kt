package com.hoppswap.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.hoppswap.feature.auth.forgotpassword.ForgotPasswordScreen
import com.hoppswap.feature.auth.login.LoginScreen
import com.hoppswap.feature.auth.signup.SignUpScreen
import com.hoppswap.feature.home.HomeScreen
import com.hoppswap.feature.profile.details.ProfileDetailsScreen
import com.hoppswap.feature.profile.settings.ProfileSettingsScreen
import com.hoppswap.feature.property.addedit.AddEditPropertyScreen
import com.hoppswap.feature.property.details.PropertyDetailsScreen

@Composable
fun AppNavigation(contentPadding: PaddingValues) {
    val controller = rememberNavController()
    NavHost(controller, startDestination = LoginScreen) {
        composable<LoginScreen> {
            LoginScreen({ controller.navigate(HomeScreen) }, { controller.navigate(SignUpScreen) })
        }
        composable<SignUpScreen> {
            SignUpScreen(controller)
        }
        composable<ForgotPasswordScreen> {
            ForgotPasswordScreen(controller)
        }
        composable<HomeScreen> {
            HomeScreen({ controller.navigate(it) }, { controller.navigate(it) })
        }
        composable<ProfileDetailsScreen> {
            ProfileDetailsScreen(controller)
        }
        composable<ProfileSettingsScreen> {
            ProfileSettingsScreen(controller)
        }
        composable<AddEditPropertyScreen> {
            AddEditPropertyScreen(controller)
        }
        composable<PropertyDetailsScreen> {
            PropertyDetailsScreen(it.toRoute())
        }
    }
}