package com.example.myplants

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker.Builder.datePicker
import com.google.android.material.shape.CornerFamily
import kotlinx.android.synthetic.main.activity_add_plant.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class AddPlantActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)
        val datePickerWater = findViewById<DatePicker>(R.id.lastWatered)
        val datePickerFertilize = findViewById<DatePicker>(R.id.lastFertilized)
        val today = Calendar.getInstance()
        datePickerWater.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)){ view, year, month, day ->
        }

        cancel.setOnClickListener {
            this.finish()
        }
        this.setupNumPickers()
        addPlant.setOnClickListener {
            var day = datePickerWater.getDayOfMonth()
            var month = datePickerWater.getMonth()
            var year = datePickerWater.getYear()
            var calendar = Calendar.getInstance()
            calendar[year, month] = day

            val format =  SimpleDateFormat("yyyy-MM-dd")
            val waterDate = format.format(calendar.time)
            day = datePickerFertilize.getDayOfMonth()
            month = datePickerFertilize.getMonth()
            year = datePickerFertilize.getYear()
            calendar = Calendar.getInstance()
            calendar[year, month] = day
            val fertilizeDate = format.format(calendar.time)

            val plant = UserData.Plant(
                UUID.randomUUID().toString(),
                name?.text.toString(),
                description?.text.toString(),
                waterDate,
                fertilizeDate.toString(),
                waterInterval.value,
                fertilizeInterval.value
            )

            if (this.plantImagePath != null) {
                plant.imageName = UUID.randomUUID().toString()
                plant.image = this.plantImage

                Backend.storeImage(this.plantImagePath!!, plant.imageName!!)
            }


            Backend.createPlant(plant)

            // add it to UserData, this will trigger a UI refresh
            UserData.addPlant(plant)

            // close activity
            this.finish()
        }

        captureImage.setOnClickListener {
            val i = Intent(
                Intent.ACTION_GET_CONTENT,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(i, SELECT_PHOTO)
        }

        image.shapeAppearanceModel = image.shapeAppearanceModel
            .toBuilder()
            .setAllCorners(CornerFamily.ROUNDED, 150.0f)
            .build()

    }

    companion object {
        private const val TAG = "AddPlantActivity"
        private const val SELECT_PHOTO = 100

    }
    private var plantImagePath : String? = null
    private var plantImage : Bitmap? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        Log.d(TAG, "Select photo activity result : $imageReturnedIntent")
        when (requestCode) {
            SELECT_PHOTO -> if (resultCode == RESULT_OK) {
                val selectedImageUri : Uri? = imageReturnedIntent!!.data

                // read the stream to fill in the preview
                var imageStream: InputStream? = contentResolver.openInputStream(selectedImageUri!!)
                val selectedImage = BitmapFactory.decodeStream(imageStream)
                val ivPreview: ImageView = findViewById<View>(R.id.image) as ImageView
                ivPreview.setImageBitmap(selectedImage)

                // store the image to not recreate the Bitmap every time
                this.plantImage = selectedImage

                // read the stream to store to a file
                imageStream = contentResolver.openInputStream(selectedImageUri)
                val tempFile = File.createTempFile("image", ".image")
                copyStreamToFile(imageStream!!, tempFile)

                // store the path to create a note
                this.plantImagePath = tempFile.absolutePath

                Log.d(TAG, "Selected image : ${tempFile.absolutePath}")
            }
        }
    }

    private fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
                output.close()
            }
        }
    }

    private fun setupNumPickers() {
        waterInterval.minValue = 0
        waterInterval.maxValue = 100
        fertilizeInterval.minValue =0
        fertilizeInterval.maxValue =100
    }


}