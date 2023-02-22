package com.example.kite.scanlicense

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.databinding.FragmentScanLicenseBinding
import com.example.kite.utils.PrefManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class ScanLicenseFragment : Fragment() {

    private lateinit var binding: FragmentScanLicenseBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var outputDirectory: File
    private var count = 1
    private var lensFacing = CameraSelector.DEFAULT_BACK_CAMERA


    companion object {
        private const val TAG = "CameraXGFG"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 20
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.fragment_scan_license, container, false
        )
        setNavigation()
        initialize()
        return binding.root
    }

    private fun initialize() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(), REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun setNavigation() {

        binding.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.imgGoHelp.setOnClickListener {
            findNavController().navigate(ScanLicenseFragmentDirections.actionScanLicenseFragmentToNeedHelpFragment())
        }
        binding.imgCapture.setOnClickListener {
            when (count) {
                1 -> {
                    takePhoto()
                    binding.imgCapture.isClickable = false
                    lifecycleScope.launch {

                        delay(3000)
                        count++
                        binding.txtLicenseText.setText(R.string.select_license_back)
                        binding.imgLicense.setImageResource(R.drawable.ic_license_back)
                        binding.imgCapture.isClickable = true
                    }

                }
                2 -> {

                    takePhoto()
                    binding.imgCapture.isClickable = false
                    lifecycleScope.launch {
                        delay(3000)
                        count++
                        binding.txtLicenseText.setText(R.string.take_user_selfie)
                        binding.imgLicense.setImageResource(R.drawable.ic_selfie_icon)
                        binding.imgCapture.isClickable = true
                        lensFacing = CameraSelector.DEFAULT_FRONT_CAMERA
                        startCamera()
                    }

                }
                3 -> {
                    takePhoto()
                    binding.imgCapture.isClickable = false
                    lifecycleScope.launch {
                        delay(3000)
                        count = 1
                        findNavController().navigate(ScanLicenseFragmentDirections.actionScanLicenseFragmentToDriverLicenseEntryFragment())
                        binding.imgCapture.isClickable = true

                    }
                }
                else -> {
                    binding.imgCapture.isClickable = true
                    binding.txtLicenseText.setText(R.string.select_license_id_front)
                    binding.imgLicense.setImageResource(R.drawable.ic_license_front)
                }
            }
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    savingUrl(count, savedUri.toString())

                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun savingUrl(count: Int, url: String) {
        when (count) {
            1 -> {
                PrefManager.put(url, "LICENCE_FRONT")
            }
            2 -> {
                PrefManager.put(url, "LICENCE_BACK")
            }
            3 -> {
                PrefManager.put(url, "USER_IMAGE")
            }
        }
    }

    private fun getOutputDirectory(): File {
        val mediaDir = activity?.externalMediaDirs?.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir else requireActivity().filesDir
    }


    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({

            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview =
                Preview.Builder().setTargetAspectRatio(AspectRatio.RATIO_4_3).build().also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }
            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, lensFacing, preview, imageCapture
                )
            } catch (exc: Exception) {
                Log.e("MainActivity.TAG", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
              startCamera()
            } else {
                Toast.makeText(
                    requireContext(), "Permissions not granted by the user.", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}

