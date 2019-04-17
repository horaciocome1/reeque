package io.github.horaciocome1.reaque.ui.users

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.github.horaciocome1.reaque.databinding.FragmentEditProfileBinding
import io.github.horaciocome1.reaque.ui.MainActivity
import io.github.horaciocome1.reaque.util.Constants
import kotlinx.android.synthetic.main.fragment_edit_profile.*

class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView2.setOnClickListener {
            pickImageFromGallery()
        }
    }

    override fun onStart() {
        super.onStart()
        binding.let {
            it.lifecycleOwner = this
            it.viewmodel = viewModel
        }
        viewModel.me.observe(this, Observer {
            viewModel.run {
                name.value = it.name
                bio.value = it.bio
                address.value = it.address
                binding.user = it
                binding.viewmodel = this
            }
        })
        viewModel.name.observe(this, Observer {
            viewModel.user.name = it
            submit_button.isEnabled = isProfileReady
        })
        viewModel.bio.observe(this, Observer {
            viewModel.user.bio = it
            submit_button.isEnabled = isProfileReady
        })
        viewModel.address.observe(this, Observer {
            viewModel.user.address = it
            submit_button.isEnabled = isProfileReady
        })
    }

    override fun onResume() {
        super.onResume()
        activity.run {
            if (this is MainActivity)
                supportActionBar?.hide()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    Constants.PICK_IMAGE_FROM_GALLERY_REQUEST_CODE -> {
                        data?.data?.let { uri ->
                            binding.viewmodel = viewModel.apply {
                                imageUri = uri
                            }
                        }
                    }
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val mimeTypes = arrayOf("image/jpeg", "image/png")
                putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
        }
        startActivityForResult(intent, Constants.PICK_IMAGE_FROM_GALLERY_REQUEST_CODE)
    }

    private val isProfileReady: Boolean
        get() {
            viewModel.user.run {
                return name.isNotBlank() && bio.isNotBlank() && address.isNotBlank()
            }
        }

}