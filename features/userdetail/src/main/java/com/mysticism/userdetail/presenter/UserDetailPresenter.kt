package com.mysticism.userdetail.presenter

import com.github.terrakok.cicerone.Router
import com.mysticism.data.models.Data
import com.mysticism.data.repository.FavoritesContactRepository
import com.mysticism.data.repository.UserIdRepository
import com.mysticism.userdetail.ui.UserDetailContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserDetailPresenter(
    private val contactRepository: UserIdRepository,
    private val favoritesContactRepository: FavoritesContactRepository,
    private val router: Router
) : UserDetailContract.Presenter {

    private var view: UserDetailContract.View? = null
    private var user: Data? = null

    override fun attachView(view: UserDetailContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override suspend fun loadUserDetails(userId: Int) {
        view?.showLoading()
        try {
            user = withContext(Dispatchers.IO) { contactRepository.getUserById(userId) }
            view?.hideLoading()
            user?.let {
                view?.showUserDetails(it)
            } ?: view?.showError("User not found")
        } catch (e: Exception) {
            view?.hideLoading()
            view?.showError("Error loading user details: ${e.message}")
        }
    }

    override fun onQuitClicked() {
        router.exit()
    }

    suspend fun toggleFavoriteStatus() {
        user?.let {
            val newFavoriteStatus = !it.isFavorite
            withContext(Dispatchers.IO) {
                favoritesContactRepository.updateFavoriteStatus(it.id, newFavoriteStatus)
                user = user?.copy(isFavorite = newFavoriteStatus)
            }
            view?.updateFavoriteIcon(newFavoriteStatus)
        }
    }
}
