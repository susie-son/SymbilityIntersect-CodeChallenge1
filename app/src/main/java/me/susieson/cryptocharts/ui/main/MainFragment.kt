package me.susieson.cryptocharts.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import me.susieson.cryptocharts.R
import me.susieson.cryptocharts.model.Cryptocurrency
import javax.inject.Inject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainFragmentViewModel
    private lateinit var cryptoListAdapter: CryptoListAdapter
    private lateinit var recyclerView: RecyclerView
    private val cryptoListObserver = Observer<PagedList<Cryptocurrency>> { list ->
        cryptoListAdapter.submitList(list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.crypto_list_fragment, container, false)
        recyclerView = view.findViewById(R.id.crypto_list_rv)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, layoutManager.orientation))
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainFragmentViewModel::class.java)
        cryptoListAdapter = CryptoListAdapter(viewModel, context)
        recyclerView.adapter = cryptoListAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.getCompleteList().observe(this, cryptoListObserver)
    }

    override fun onStop() {
        super.onStop()
        viewModel.getCompleteList().removeObservers(this)
    }
}
