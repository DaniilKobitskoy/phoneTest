package com.mysticism.core.navigation

import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Screen
import com.mysticism.phonelist.ui.ContactListFragment

class ContactListScreen : Screen {
     fun createFragment(): Fragment {
        return ContactListFragment()
    }
}