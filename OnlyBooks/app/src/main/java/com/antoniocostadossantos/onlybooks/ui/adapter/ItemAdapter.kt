package com.antoniocostadossantos.onlybooks.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ItemEbookBinding
import com.antoniocostadossantos.onlybooks.model.EbookModel
import com.antoniocostadossantos.onlybooks.ui.fragments.EbookDetailsFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class ItemAdapter(val context: Context) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding =
            ItemEbookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(items[position])

//                holder.itemView.setOnClickListener(object : View.OnClickListener {
//                    override fun onClick(p0: View?) {
//                        val transaction =
//                            (context as FragmentActivity).supportFragmentManager.beginTransaction()
//                        transaction.replace(R.id.nav_host_fragment, EbookDetailsFragment())
//                        transaction.addToBackStack(null)
//                        transaction.commit()
//                    }
//                })

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

    fun setList(liveList: List<EbookModel>) {
        this.items = liveList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemEbookBinding) : RecyclerView.ViewHolder(binding.root) {
        private val image = binding.imageEbook
        private val genre = binding.genreEbook

        fun bind(ebook: EbookModel) {
            genre.text = ebook.genreEbook

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_baseline_cloud_download_24)
                .error(R.drawable.ic_baseline_error_24)

            Glide.with(binding.imageEbook)
                .applyDefaultRequestOptions(requestOptions)
                .load(ebook.url)
                .into(image)

        }
    }
}