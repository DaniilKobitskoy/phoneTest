package com.mysticism.core.navigation

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Screen
import com.mysticism.userdetail.ui.UserDetailFragment

class UserIdScreen(private val userId: Int) : Screen {
    fun createFragment(): Fragment {
        return UserDetailFragment.newInstance(userId)
    }
}

