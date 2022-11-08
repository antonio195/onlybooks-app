package com.antoniocostadossantos.onlybooks.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ItemEbookVerticalBinding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.ui.fragments.AudioBookDetailsFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class AudiobookItemVerticalAdapter(val context: Context) :
    RecyclerView.Adapter<AudiobookItemVerticalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding =
            ItemEbookVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(items[position])

                holder.itemView.setOnClickListener {
                    val transaction =
                        (context as FragmentActivity).supportFragmentManager.beginTransaction()

                    transaction.replace(
                        R.id.nav_host_fragment,
                        AudioBookDetailsFragment(items[position])
                    )
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private var items: List<AudioBookModel> = ArrayList()

    fun setList(liveList: List<AudioBookModel>) {
        this.items = liveList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemEbookVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.imageEbook
        private val genre = binding.genreEbook

        fun bind(audioBookModel: AudioBookModel) {
            genre.text = audioBookModel.genreAudioBook

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.baixando_capa)
                .error(R.drawable.erro_capa)

            Glide.with(binding.imageEbook)
                .applyDefaultRequestOptions(requestOptions)
                .load(audioBookModel.urlAudioBook)
                .into(image)

        }
    }
}