package com.salesianostriana.keepdam

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.salesianostriana.keepdam.data.viewmodel.KeepDAMViewModel
import com.salesianostriana.keepdam.di.MyApp

import com.salesianostriana.keepdam.dummy.DummyContent
import com.salesianostriana.keepdam.dummy.DummyContent.DummyItem
import com.salesianostriana.keepdam.retrofit.response.NotaResponse
import javax.inject.Inject


class NotasFragment : Fragment() {
    @Inject
    lateinit var keepDAMViewModel: KeepDAMViewModel
    private lateinit var notasAdapter: MyNotasRecyclerViewAdapter
    private var notas: List<NotaResponse> = arrayListOf()
    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as MyApp).getAppCompontent().inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notas_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item_add -> {
                val intent : Intent = Intent(context, NewNotaActivity::class.java).apply {
                    putExtra("editar", false)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notas_list, container, false)

        notasAdapter = MyNotasRecyclerViewAdapter()
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = notasAdapter
            }
        }

        keepDAMViewModel.viewAllNota().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                notas = it
                notasAdapter.setData(notas)
            }
        })
        return view
    }


}
