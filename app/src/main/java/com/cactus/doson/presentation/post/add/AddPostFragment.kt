package com.cactus.doson.presentation.post.add

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.cactus.doson.DoSonApplication
import com.cactus.doson.R
import com.cactus.doson.common.BaseFragment
import com.cactus.doson.common.Constants.RETROFIT_TAG
import com.cactus.doson.common.DeviceUtil
import com.cactus.doson.data.body.AddPostBody
import com.cactus.doson.data.response.post.AddPostResponse
import com.cactus.doson.databinding.FragmentAddPostBinding
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddPostFragment: BaseFragment(R.layout.fragment_add_post) {
    private var binding: FragmentAddPostBinding? = null
    private lateinit var callback: OnBackPressedCallback
    private var category: String = "sight"
    private var imageOn: Boolean = true
    private var locationOn: Boolean = true

    private var selected_lat : Double ?=null
    private var selected_lon : Double?=null

    private val filePath: String by lazy {
        "${requireActivity().externalCacheDir?.absolutePath}/image.jpeg"
    }
    private var uri: Uri? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentAddPostBinding = FragmentAddPostBinding.bind(view)
        binding = fragmentAddPostBinding
        initImageButton()
        initCategoryButtons()
        initSelectLocationButton()
        initPostButton()

        var bundle: Bundle
        if(arguments!=null){
            bundle = arguments as Bundle
            selected_lat = bundle.getDouble("selected_lat")
            selected_lon = bundle.getDouble("selected_lon")
        }

    }


    /** 이미지 추가**/
    private val galleryCallback =
        registerForActivityResult(ActivityResultContracts.GetContent()) { u ->
            uri = u
            binding?.ivAddImage?.setImageURI(uri)
        }

    private val galleryPermissionCallback =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                galleryCallback.launch("image/*")
            } else {
                showToast("권한을 허용해주셔야 이용하실 수 있습니다.")
            }
        }

    private fun openGalleryIfPermissionGranted() {
        if (hasExtrernalStoragePermission(requireContext())) {
            galleryCallback.launch("image/*")
        } else {
            galleryPermissionCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun hasExtrernalStoragePermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun initImageButton() {
        binding?.apply {
            ivAddImage.setOnClickListener {
                openGalleryIfPermissionGranted()
            }
        }
    }

    /** category **/
    private fun initCategoryButtons() {
        binding?.apply {
            ivSights.setOnClickListener {
                offAllCategoryButton()
                ivSights.setImageResource(R.drawable.btn_32_sights_on)
                category = "sight"
            }
            ivRestauranct.setOnClickListener {
                offAllCategoryButton()
                ivRestauranct.setImageResource(R.drawable.btn_32_restaurant_on)
                category = "restaurant"
            }
            ivCafe.setOnClickListener {
                offAllCategoryButton()
                ivCafe.setImageResource(R.drawable.btn_32_cafe_on)
                category = "cafe"
            }
        }
    }

    private fun offAllCategoryButton() {
        binding?.apply {
            ivSights.setImageResource(R.drawable.btn_32_sights_off)
            ivRestauranct.setImageResource(R.drawable.btn_32_restaurant_off)
            ivCafe.setImageResource(R.drawable.btn_32_cafe_off)
        }
    }
    private fun initSelectLocationButton() {
        binding?.tvSelectLocation?.setOnClickListener {
            //moveToFragment()
        }

    }

    private fun initPostButton() {
        binding?.tvPost?.setOnClickListener {
            if (checkPossible()) {
                Log.d("asdf", uri.toString())
                val saveFile = File(filePath)
                var bitmap: Bitmap? = null
                try {
                    bitmap = if (DeviceUtil.isAndroid9Later()) {
                        val source = ImageDecoder.createSource(requireContext().contentResolver, uri!!)
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
                    }
                    bitmap!!.compress(Bitmap.CompressFormat.JPEG, 40, saveFile.outputStream())
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@setOnClickListener
                }


                //val file = File(path)
                Log.d("asdf", saveFile.toString())
                val requestBody = RequestBody.create(MediaType.parse("image/*"), saveFile)
                val part = MultipartBody.Part.createFormData("file", saveFile.name, requestBody)
                Log.d("asdf", part.toString())

                val houseIdPart = RequestBody.create(MediaType.parse("text/plain"), "1")
                val categoryPart = RequestBody.create(MediaType.parse("text/plain"), category)
                val titlePart =  RequestBody.create(MediaType.parse("text/plain"), binding?.etTitle?.text!!.toString())
                val contentPart =  RequestBody.create(MediaType.parse("text/plain"), binding?.etDescription?.text!!.toString())
                val latitudePart =  RequestBody.create(MediaType.parse("text/plain"), "33.4592739520701")
                val longitudePart =  RequestBody.create(MediaType.parse("text/plain"), "126.31238355628")



                val partMap = HashMap<String, RequestBody>()
                partMap["houseId"] = houseIdPart
                partMap["category"] = categoryPart
                partMap["title"] = titlePart
                partMap["content"] = contentPart
                partMap["latitude"] = latitudePart
                partMap["longitude"] = longitudePart


                DoSonApplication.retrofit.addPostApi(
                    partMap = partMap,
                    file = part
                )?.enqueue(object : Callback<AddPostResponse> {
                    override fun onResponse(
                        call: Call<AddPostResponse>,
                        response: Response<AddPostResponse>
                    ) {
                        Log.e(RETROFIT_TAG, response.body().toString())
                    }

                    override fun onFailure(call: Call<AddPostResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })

            }
        }
    }

    private fun checkPossible(): Boolean = imageOn && locationOn &&
            binding!!.etTitle!!.text!!.toString()!!.isNotEmpty() &&
            binding!!.etDescription!!.toString()!!.isNotEmpty()




}