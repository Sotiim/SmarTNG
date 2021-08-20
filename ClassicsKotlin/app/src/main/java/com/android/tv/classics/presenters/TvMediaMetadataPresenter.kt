package com.android.tv.classics.presenters

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import coil.api.load
import com.android.tv.classics.models.TvMediaMetadata
import com.android.tv.classics.utils.TvLauncherUtils

/** Default height in DP used for card presenters, larger than this results in rows overflowing */
const val DEFAULT_CARD_HEIGHT: Int = 400

/** [Presenter] used to display a metadata item as an image card */
class TvMediaMetadataPresenter(private val cardHeight: Int = DEFAULT_CARD_HEIGHT) : Presenter() {

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) = Unit

    override fun onCreateViewHolder(parent: ViewGroup) =
            Presenter.ViewHolder(ImageCardView(parent.context).apply {
                isFocusable = true
                isFocusableInTouchMode = true
                // Hecho por Alberto Aranda
                setBackgroundColor(Color.DKGRAY)
                infoVisibility = View.GONE
            })

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
        val metadata = item as TvMediaMetadata
        val card = viewHolder.view as ImageCardView

        val cardWidth = TvLauncherUtils.parseAspectRatio(metadata.artAspectRatio).let {
            cardHeight * it.numerator / it.denominator
        }

        card.titleText = metadata.title
        card.contentDescription = metadata.title
        card.setMainImageDimensions(cardWidth, cardHeight)
        card.mainImageView.load(metadata.artUri)
    }
}
