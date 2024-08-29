package com.mysticism.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mysticism.data.models.Data
import com.mysticism.favorites.databinding.FragmentFavoriteContactsBinding
import com.mysticism.favorites.presenter.FavoriteContactsPresenter
import com.mysticism.favorites.ui.FavoriteContactsContract
import com.mysticism.favorites.ui.adapter.FavoritesContactAdapter
import org.koin.android.ext.android.inject
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import com.mysticism.phonelist.ui.ContactListNavigator

class FavoriteContactsFragment : Fragment(), FavoriteContactsContract.View {

    private lateinit var binding: FragmentFavoriteContactsBinding
    private lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesContactAdapter
    private lateinit var emptyTextView: TextView
    private val presenter: FavoriteContactsPresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressBar = binding.progressBar
        recyclerView = binding.recyclerViewContacts
        emptyTextView = binding.textViewEmpty

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = FavoritesContactAdapter(
            onFavoriteClick = { contact ->
                viewLifecycleOwner.lifecycleScope.launch {
                    presenter.onFavoriteClicked(contact)

                }
            },
            onItemClick = { contact ->
                val navigator = activity as? ContactListNavigator
                navigator?.openUserDetails(contact.id)
            }
        )

        recyclerView.adapter = adapter

        presenter.attachView(this)

        viewLifecycleOwner.lifecycleScope.launch {
            presenter.fetchContacts()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
        emptyTextView.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showContacts(contacts: List<Data>) {
        adapter.submitList(contacts)
    }

    override fun showError(message: String) {
        recyclerView.visibility = View.GONE
        emptyTextView.visibility = View.VISIBLE
        emptyTextView.text = message
    }
}
