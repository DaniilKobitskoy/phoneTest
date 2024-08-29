package com.mysticism.core.navigation

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Screen
import com.mysticism.favorites.FavoriteContactsFragment

class FavoriteContactsScreen : Screen {
    fun createFragment(): Fragment {
        return FavoriteContactsFragment()
    }
}