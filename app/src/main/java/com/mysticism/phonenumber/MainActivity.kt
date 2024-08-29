package com.mysticism.phonenumber

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mysticism.core.navigation.AppNavigator
import com.mysticism.core.navigation.ContactListScreen
import com.mysticism.core.navigation.FavoriteContactsScreen
import com.mysticism.core.navigation.UserIdScreen
import com.mysticism.phonelist.ui.ContactListNavigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), ContactListNavigator {

    private val cicerone: Cicerone<Router> by inject()
    private val router: Router get() = cicerone.router
    private val navigatorHolder: NavigatorHolder get() = cicerone.getNavigatorHolder()
    private lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appNavigator = AppNavigator(supportFragmentManager, R.id.fragment_container)

        if (savedInstanceState == null) {
            router.navigateTo(ContactListScreen())
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_contacts -> router.replaceScreen(ContactListScreen())
                R.id.nav_favorites -> router.replaceScreen(FavoriteContactsScreen())
            }
            true
        }
    }

    override fun openUserDetails(userId: Int) {
        router.navigateTo(UserIdScreen(userId))
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(appNavigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}
