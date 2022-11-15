package com.antoniocostadossantos.onlybooks.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ItemEbookHorizontalBinding
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.ui.fragments.EbookDetailsFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class EbookItemHorizontalAdapter(val context: Context) :
    RecyclerView.Adapter<EbookItemHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemEbookHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(items[position])

                holder.itemView.setOnClickListener {
                    val transaction =
                        (context as FragmentActivity).supportFragmentManager.beginTransaction()

                    transaction.replace(R.id.nav_host_fragment, EbookDetailsFragment(items[position]))
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private var items: List<EbookModel> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(liveList: List<EbookModel>) {
        this.items = liveList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemEbookHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.imageEbook
        private val genre = binding.genreEbook

        fun bind(ebook: EbookModel) {
            genre.text = ebook.genreEbook

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.baixando_capa)
                .error(R.drawable.erro_capa)

            Glide.with(binding.imageEbook)
                .applyDefaultRequestOptions(requestOptions)
                .load(ebook.url)
                .into(image)

        }
    }
}