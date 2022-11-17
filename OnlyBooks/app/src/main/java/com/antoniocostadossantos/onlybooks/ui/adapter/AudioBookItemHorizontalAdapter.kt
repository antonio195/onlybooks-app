package com.antoniocostadossantos.onlybooks.ui.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.antoniocostadossantos.onlybooks.R
import com.antoniocostadossantos.onlybooks.databinding.ItemAudiobookHorizontalBinding
import com.antoniocostadossantos.onlybooks.model.AudioBookModel
import com.antoniocostadossantos.onlybooks.ui.fragments.AudioBookDetailsFragment
import com.antoniocostadossantos.onlybooks.util.show
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class AudioBookItemHorizontalAdapter(val context: Context) :
    RecyclerView.Adapter<AudioBookItemHorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemAudiobookHorizontalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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

    class ViewHolder(val binding: ItemAudiobookHorizontalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val image = binding.imageEbook
        private val genre = binding.genreEbook
        private val title = binding.titleAudiobook

        fun bind(audioBook: AudioBookModel) {
            genre.text = audioBook.genreAudioBook
            title.text = audioBook.nameAudioBook

            binding.audioCover.show()
            binding.imageEbook.alpha = 0.9F

            when (audioBook.classificacao) {
                "Livre" -> {
                    binding.parentalRating.setImageResource(R.drawable.class_indicativa_livre)
                }
                "10" -> {
                    binding.parentalRating.setImageResource(R.drawable.class_indicativa_10)
                }
                "12" -> {
                    binding.parentalRating.setImageResource(R.drawable.class_indicativa_12)
                }
                "14" -> {
                    binding.parentalRating.setImageResource(R.drawable.class_indicativa_14)
                }
                "16" -> {
                    binding.parentalRating.setImageResource(R.drawable.class_indicativa_16)
                }
                "18" -> {
                    binding.parentalRating.setImageResource(R.drawable.class_indicativa_18)
                }
            }

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.baixando_capa)
                .error(R.drawable.erro_capa)

            Glide.with(binding.imageEbook)
                .applyDefaultRequestOptions(requestOptions)
                .load(audioBook.urlAudioBook)
                .into(image)

        }
    }
}