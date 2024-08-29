package com.mysticism.userdetail.ui

import com.mysticism.data.models.Data

interface UserDetailContract {
    interface View {
        fun showUserDetails(user: Data)
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
        fun updateFavoriteIcon(isFavorite: Boolean)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        suspend fun loadUserDetails(userId: Int)
        fun onQuitClicked()
    }
}
