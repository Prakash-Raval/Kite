package com.example.kite.driverlicenceentry

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kite.R
import com.example.kite.constants.Constants
import com.example.kite.databinding.FragmentDriverLicenseEntryBinding
import com.example.kite.utils.PrefManager
import com.trulioo.normalizedapi.ApiCallback
import com.trulioo.normalizedapi.ApiClient
import com.trulioo.normalizedapi.ApiException
import com.trulioo.normalizedapi.api.ConnectionApi
import com.trulioo.normalizedapi.api.VerificationsApi
import com.trulioo.normalizedapi.model.*
import java.util.logging.Level
import java.util.logging.Logger


class DriverLicenseEntryFragment : Fragment() {
    private lateinit var binding: FragmentDriverLicenseEntryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.fragment_driver_license_entry,
            container,
            false
        )
        setUpToolBar()
        checkTruliooStatus()
        return binding.root
    }

    private fun setUpToolBar() {
        binding.inDriverLicenseEntry.imgBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.inDriverLicenseEntry.txtToolbarHeader.setText(R.string.driver_license_entry)
    }

    private fun checkTruliooStatus() {
        binding.btnSaveContinue.setOnClickListener {
            setTruliooData()
        }
    }

    private fun setTrulioo() {
        //Set client here
        val apiClient = ApiClient()
        apiClient.setUsername("Kite_DocV_Sandbox_API")
        apiClient.setPassword("Brain@2022#")
        //apiClient.setVerifyingSsl(false); Ignore ssl error if needed
        //apiClient.setConnectTimeout(60000); Customise timeout if needed

        //ConnectionApi
        val connectionClient = ConnectionApi(apiClient)
        val testResult = connectionClient.testAuthentication()
        Log.d("TAG", "setTrulioo: $testResult")
        val verificationClient = VerificationsApi(apiClient)
        val request = VerifyRequest()
            .acceptTruliooTermsAndConditions(java.lang.Boolean.TRUE)
            .demo(false)
            .configurationName("Identity Verification")
            .countryCode("CA")
            .consentForDataSources(ArrayList())
            .dataFields(
                DataFields()
                    .personInfo(
                        PersonInfo()
                            .firstGivenName("Kaufman")
                            .firstSurName("Murray")
                            .yearOfBirth(1969)
                            .monthOfBirth(7)
                            .dayOfBirth(8)
                    )
                    .location(
                        Location()
                            .postalCode("V5Y3K8")
                            .buildingNumber("123")
                            .streetName("W 1st")
                            .streetType("Ave")
                            .stateProvinceCode("BC")
                            .city("Vancouver")
                    )
                    .nationalIds(
                        listOf(
                            NationalId().number("540223144").type("SocialService")
                        )
                    )
                    .document(
                        Document()
                            .documentBackImage("test image back".toByteArray())
                            .documentFrontImage("test image front".toByteArray())
                            .documentType("DrivingLicence")
                    )
            )

        //verify
        val result = verificationClient.verify(request)
        println(result)

        //verifyAsync
        verificationClient.verifyAsync(request, object : ApiCallback<VerifyResult?> {
            override fun onFailure(ae: ApiException, i: Int, map: Map<String, List<String>>) {
                Logger.getLogger(SdkJavaV1Sample::class.java.name).log(Level.SEVERE, null, ae)
            }

            override fun onSuccess(t: VerifyResult?, i: Int, map: Map<String, List<String>>) {
                println(t)
            }

            override fun onUploadProgress(l: Long, l1: Long, bln: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }

            override fun onDownloadProgress(l: Long, l1: Long, bln: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }
        })
    }


    private fun setTruliooData() {
        //calling up trulioo helper class
        val truliooHelper = TruliooVerificationHelper()
        //trulioo helper initialization
        truliooHelper.init()
        //getting up the client code
        val connectionClient = truliooHelper.getConnectionClient()

        //testing up the connection for trulioo
        connectionClient.testAuthenticationAsync(object : ApiCallback<String> {
            override fun onFailure(
                e: ApiException,
                statusCode: Int,
                responseHeaders: Map<String, List<String>>?
            ) {
                //setResultBox("failed\n"+e.message)
                //Toast.makeText(requireContext(), "Failed ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("TruliooConnection", "onFailure: ${e.message}")

            }

            override fun onSuccess(
                result: String,
                statusCode: Int,
                responseHeaders: Map<String, List<String>>
            ) {
                // setResultBox(result+"\n connected to: "+ truliooHelper.getBasePath())
                Log.d(
                    "TruliooConnection",
                    result + "\n connected to: " + truliooHelper.getBasePath()
                )
                //if connection was success verifying user driving license details
                verifyTrulioo()
            }

            override fun onUploadProgress(bytesWritten: Long, contentLength: Long, done: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }

            override fun onDownloadProgress(bytesRead: Long, contentLength: Long, done: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }
        })
    }


    private fun verifyTrulioo() {
        // val verificationClient: VerificationsApi = truliooVerificationHelper.getVerificationClient()
        //calling trulioo helper and initialize
        val truliooHelper = TruliooVerificationHelper()
        truliooHelper.init()
        //getting the client for helper class
        val verificationClient = truliooHelper.getVerificationClient()

        //getting the saved data from directory
        val front = PrefManager.get<String>("LICENCE_FRONT")
        val back = PrefManager.get<String>("LICENCE_BACK")
        val image = PrefManager.get<String>("USER_IMAGE")

        var request: VerifyRequest? = null
        try {
            if (front != null) {
                if (back != null) {
                    if (image != null) {
                        //making the request for driving license
                        request = VerifyRequest()
                            .acceptTruliooTermsAndConditions(java.lang.Boolean.TRUE)
                            .demo(false)
                            .verboseMode(true)
                            .cleansedAddress(false)
                            .configurationName("Identity Verification")
                            .callBackUrl(Constants.BASE_URL + "thirdparty/trullio/webhook")
                            .countryCode("IN")
                            .dataFields(
                                DataFields()
                                    .personInfo(
                                        PersonInfo()
                                            //.firstGivenName(binding.edtLicenseFirstName.text.toString())
                                            /*.firstGivenName("Kyle Antwon")
                                            .middleName("")
                                            .firstSurName("Hertz")*/
                                            //.firstSurName(binding.edtLicenseLastName.text.toString())
                                            .firstGivenName("MAYANK")
                                            .middleName("M")
                                            .firstSurName("KANKRECHA")
                                            .yearOfBirth(2000)
                                            .monthOfBirth(7)
                                            .dayOfBirth(6)

                                    )
                                    .driverLicence(
                                        DriverLicence()
                                            //.number(binding.edtLicenseNumber.text.toString())
                                            //.state(binding.edtLicenseState.text.toString())
                                            .number("GJ3220190005116")
                                            .state("GJ")
                                            .dayOfExpiry(30)
                                            .monthOfExpiry(6)
                                            .yearOfExpiry(2039)
                                    )
                                    .document(
                                        Document()
                                            .documentFrontImage(front.toByteArray())
                                            .documentBackImage(back.toByteArray())
                                            .livePhoto(image.toByteArray())
                                            .documentType("DrivingLicence")

                                    )
                            )
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //VerifyResult result = verificationClient.verify(request);
        //making up the request
        verificationClient.verifyAsync(request, object : ApiCallback<VerifyResult> {
            override fun onFailure(
                e: ApiException,
                statusCode: Int,
                responseHeaders: Map<String, List<String>>
            ) {
                Log.d("TruliooConnection2", "onFailure: ${e.message}")
                /* UiThreadStatement.runOnUiThread(Runnable {
                     truliooDialog.dismiss()
                     showTruliooErrorDialog(mActivity)
                 })*/
            }

            override fun onSuccess(
                result: VerifyResult,
                statusCode: Int,
                responseHeaders: Map<String, List<String>>
            ) {
                Log.d(
                    "TruliooConnection2",
                    result.toString() + "\n connected to: " + truliooHelper.getBasePath()
                )
                /*UiThreadStatement.runOnUiThread(Runnable {
                    submitLicenseDataApi(
                        commonResponseModel.data.accessToken,
                        result.transactionID
                    )
                    if (result.record.recordStatus.equals("match", ignoreCase = true)) {
                        truliooDialog.dismiss()
                        navigateToScanner()
                    } else {
                        showTruliooErrorDialog(mActivity);
                    }
                })*/
            }

            override fun onUploadProgress(bytesWritten: Long, contentLength: Long, done: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }

            override fun onDownloadProgress(bytesRead: Long, contentLength: Long, done: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }
        })
    }

}