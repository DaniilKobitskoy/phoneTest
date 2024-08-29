package com.mysticism.core.navigation

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.terrakok.cicerone.Back
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.Replace
import com.github.terrakok.cicerone.Screen

class AppNavigator(
    private val fragmentManager: FragmentManager,
    private val containerId: Int
) : Navigator {

    private fun getFragmentForScreen(screen: Screen): Fragment {
        return when (screen) {
            is ContactListScreen -> screen.createFragment()
            is FavoriteContactsScreen -> screen.createFragment()
            is UserIdScreen -> screen.createFragment()
            else -> throw IllegalArgumentException("Unknown screen type: $screen")
        }
    }

    override fun applyCommands(commands: Array<out Command>) {
        commands.forEach { command ->
            when (command) {
                is Back -> handleBack()
                is Forward -> handleForward(command.screen)
                is Replace -> handleReplace(command.screen)
                else -> Unit
            }
        }
    }

    private fun handleBack() {
        fragmentManager.popBackStack()
    }

    private fun handleForward(screen: Screen) {
        val fragment = getFragmentForScreen(screen)
        fragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }


    private fun handleReplace(screen: Screen) {
        val fragment = getFragmentForScreen(screen)
        fragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

}