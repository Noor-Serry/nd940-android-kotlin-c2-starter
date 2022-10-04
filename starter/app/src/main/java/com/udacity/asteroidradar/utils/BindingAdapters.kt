package com.udacity.asteroidradar



import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.data.room.PictureOfDay

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_status_potentially_hazardous)
        imageView.contentDescription = "this asteroid is Hazardous"
    } else {
        imageView.setImageResource(R.drawable.ic_status_normal)
        imageView.contentDescription = "this asteroid is Not Hazardous"
    }
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
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

@BindingAdapter("image","lifecycle")
fun bindStringToImageView(imageView: ImageView, image: LiveData<List<PictureOfDay>>,lifecycleOwner: LifecycleOwner){
    image.observe(lifecycleOwner
    ) { image ->
        if (image.size>0){
        val imageByte = Base64.decode(image[0].image, Base64.DEFAULT)
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size))
    }}
}

@BindingAdapter("customContentDescription")
fun customContentDescriptionForImageStatus(imageView: ImageView , isHazardous: Boolean){
    if(isHazardous){
        imageView.contentDescription = "this asteroid is Hazardous" }
    else{ imageView.contentDescription = "this asteroid is Not Hazardous"}
}


