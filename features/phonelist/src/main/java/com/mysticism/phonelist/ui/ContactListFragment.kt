package com.mysticism.phonelist.ui

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mysticism.phonelist.databinding.FragmentContactListBinding
import com.mysticism.phonelist.ui.adapter.ContactAdapter
import com.mysticism.phonelist.viewModel.ContactListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContactListFragment : Fragment() {

    private lateinit var binding: FragmentContactListBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ContactAdapter
    private var recyclerViewState: Parcelable? = null

    private val viewModel: ContactListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerViewContacts
        recyclerView.layoutManager = LinearLayoutManager(context)

        adapter = ContactAdapter(
            onFavoriteClick = { contact ->
                viewModel.onFavoriteClicked(contact)
            },
            onItemClick = { contact ->

                val navigator = activity as? ContactListNavigator
                if (navigator != null) {
                    navigator.openUserDetails(contact.id)
                } else {
                }
            }
        )

        recyclerView.adapter = adapter

        viewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            adapter.submitList(contacts)
            (recyclerView.layoutManager as LinearLayoutManager).onRestoreInstanceState(
                recyclerViewState
            )
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            //
        }

        if (savedInstanceState != null) {
            recyclerViewState = savedInstanceState.getParcelable("recyclerViewState")
        }

        viewModel.fetchContacts()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(
            "recyclerViewState",
            recyclerView.layoutManager?.onSaveInstanceState()
        )
    }
}
