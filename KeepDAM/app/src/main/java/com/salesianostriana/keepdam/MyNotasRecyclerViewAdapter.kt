package com.salesianostriana.keepdam

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.salesianostriana.keepdam.di.MyApp


import com.salesianostriana.keepdam.dummy.DummyContent.DummyItem
import com.salesianostriana.keepdam.retrofit.response.NotaResponse

import kotlinx.android.synthetic.main.fragment_notas.view.*


class MyNotasRecyclerViewAdapter() : RecyclerView.Adapter<MyNotasRecyclerViewAdapter.ViewHolder>() {
    private var notas: List<NotaResponse> = arrayListOf()
    lateinit var id: String
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as NotaResponse

            val detalle: Intent = Intent(MyApp.instance, DetailActivity::class.java).apply {
                putExtra("id", id)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            MyApp.instance.startActivity(detalle)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_notas, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = notas[position]
        id = item.id

        holder.tvAutor.text = item.autor.nick
        holder.tvTitle.text = item.titulo
        holder.tvContent.text = item.contenido




        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    fun setData(notasRecibidas: List<NotaResponse>?) {
        this.notas = notasRecibidas!!
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = notas.size


    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val tvTitle : TextView = mView.textViewTitle
        val tvContent : TextView = mView.textViewContent
        val tvAutor : TextView = mView.textViewNick


    }
}
