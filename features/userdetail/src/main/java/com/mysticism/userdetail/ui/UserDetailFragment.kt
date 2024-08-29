package com.mysticism.userdetail.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mysticism.data.models.Data
import com.mysticism.userdetail.R
import com.mysticism.userdetail.databinding.FragmentUserDetailBinding
import com.mysticism.userdetail.presenter.UserDetailPresenter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class UserDetailFragment : Fragment(), UserDetailContract.View {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var userGender: TextView
    private lateinit var userIpAddress: TextView
    private lateinit var back: ImageView
    private lateinit var favoritesIcon: ImageView
    private lateinit var image: ImageView

    private val presenter: UserDetailPresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userId = arguments?.getInt(ARG_USER_ID) ?: return
        progressBar = binding.progressBar
        userName = binding.textViewName
        userEmail = binding.textViewEmail
        userGender = binding.textViewGender
        userIpAddress = binding.textViewIpAddress
        back = binding.back
        image = binding.imageView2
        favoritesIcon = binding.favorites

        presenter.attachView(this)
        viewLifecycleOwner.lifecycleScope.launch {
            presenter.loadUserDetails(userId)
        }

        back.setOnClickListener {
            presenter.onQuitClicked()
        }

        favoritesIcon.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                presenter.toggleFavoriteStatus()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun showUserDetails(user: Data) {
        userName.text = "${user.firstName} ${user.lastName}"
        userEmail.text = user.email
        userGender.text = "Gender: ${user.gender}"
        userIpAddress.text = user.ipAddress
        Glide.with(this)
            .load(user.imagePath)
            .placeholder(R.drawable.progile_avatar_bg)
            .into(image)
        updateFavoriteIcon(user.isFavorite)
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showError(message: String) {
        //
    }

    override fun updateFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            favoritesIcon.setImageResource(R.drawable.profile_favorin_on_icon)
        } else {
            favoritesIcon.setImageResource(R.drawable.profile_favorin_off_icon)
        }
    }

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Int): UserDetailFragment {
            val fragment = UserDetailFragment()
            val args = Bundle()
            args.putInt(ARG_USER_ID, userId)
            fragment.arguments = args
            return fragment
        }
    }
}
