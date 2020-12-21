package com.udacity.asteroidradar

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.api.PictureOfDayStatus

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.hazardous_asteroid)
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = imageView.context.getString(R.string.non_hazardous_asteroid)
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription = imageView.context.getString(R.string.hazardous_asteroid)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription = imageView.context.getString(R.string.non_hazardous_asteroid)
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

@BindingAdapter("goneIfNotNull")
fun goneIfNotNull(view: View, it: Any?) {
    view.visibility = if (it != null) View.GONE else View.VISIBLE
}

@BindingAdapter("pictureOfDay")
fun bindImageOfDay(imageView: ImageView, pictureOfDay: PictureOfDay?) {
    pictureOfDay?.let {
        if (pictureOfDay.isImage) {
            Picasso.with(imageView.context)
                .load(pictureOfDay.url)
                .into(imageView)

            imageView.contentDescription = String.format(
                imageView.context.getString(
                    R.string.image_of_the_day_title,
                    pictureOfDay.title
                )
            )
        }
    }
}

/**
 * Code adapted from andfun-kotlin-mars-realestate.
 */
@BindingAdapter("pictureOfDayStatus")
fun bindPictureOfDayStatus(imageView: ImageView, status: PictureOfDayStatus) {
    when (status) {
        PictureOfDayStatus.LOADING -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.loading_animation)
        }
        PictureOfDayStatus.ERROR -> {
            imageView.visibility = View.VISIBLE
            imageView.setImageResource(R.drawable.ic_connection_error)
        }
        PictureOfDayStatus.DONE -> {
            imageView.visibility = View.GONE
        }
    }
}