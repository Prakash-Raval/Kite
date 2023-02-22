package com.example.kite.driverlicenceentry

import com.trulioo.normalizedapi.ApiCallback
import com.trulioo.normalizedapi.ApiClient
import com.trulioo.normalizedapi.ApiException
import com.trulioo.normalizedapi.api.ConfigurationApi
import com.trulioo.normalizedapi.api.ConnectionApi
import com.trulioo.normalizedapi.api.VerificationsApi
import com.trulioo.normalizedapi.model.*
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger


object SdkJavaV1Sample {
    @Throws(ApiException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        //Set client here
        val apiClient = ApiClient()
        apiClient.setUsername("UserName")
        apiClient.setPassword("Password")
        //apiClient.setVerifyingSsl(false); Ignore ssl error if needed
        //apiClient.setConnectTimeout(60000); Customise timeout if needed

        //ConnectionApi
        val connectionClient = ConnectionApi(apiClient)

        //sayHello
        val hello = connectionClient.sayHello("sayHello")
        println(hello)

        //sayHelloAsync
        connectionClient.sayHelloAsync("sayHello async", object : ApiCallback<String?> {
            override fun onFailure(
                e: ApiException,
                statusCode: Int,
                responseHeaders: Map<String, List<String>>
            ) {
                Logger.getLogger(SdkJavaV1Sample::class.java.name).log(Level.SEVERE, null, e)
            }

            override fun onSuccess(
                result: String?,
                statusCode: Int,
                responseHeaders: Map<String, List<String>>
            ) {
                println(result)
            }

            override fun onUploadProgress(bytesWritten: Long, contentLength: Long, done: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }

            override fun onDownloadProgress(bytesRead: Long, contentLength: Long, done: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }
        })

        //testAuthentication
        val testResult = connectionClient.testAuthentication()
        println(testResult)

        //testAuthenticationAsync
        connectionClient.testAuthenticationAsync(object : ApiCallback<String?> {
            override fun onFailure(ae: ApiException, i: Int, map: Map<String, List<String>>) {
                Logger.getLogger(SdkJavaV1Sample::class.java.name).log(Level.SEVERE, null, ae)
            }

            override fun onSuccess(t: String?, i: Int, map: Map<String, List<String>>) {
                println(t)
            }

            override fun onUploadProgress(l: Long, l1: Long, bln: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }

            override fun onDownloadProgress(l: Long, l1: Long, bln: Boolean) {
                //To change body of generated methods, choose Tools | Templates.
            }
        })


        //VerificationsApi
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
                        Arrays.asList(
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

        //getTransactionRecord
        val recordResult =
            verificationClient.getTransactionRecord("8dfffd53-405a-46e7-88e2-3c51e5c80cc5")
        println(recordResult)

        //getTransactionRecordAsync
        verificationClient.getTransactionRecordAsync(
            "8dfffd53-405a-46e7-88e2-3c51e5c80cc5",
            object : ApiCallback<TransactionRecordResult?> {
                override fun onFailure(ae: ApiException, i: Int, map: Map<String, List<String>>) {
                    Logger.getLogger(SdkJavaV1Sample::class.java.name).log(Level.SEVERE, null, ae)
                }

                override fun onSuccess(
                    t: TransactionRecordResult?,
                    i: Int,
                    map: Map<String, List<String>>
                ) {
                    println(t)
                }

                override fun onUploadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }

                override fun onDownloadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }
            })


        //ConfigurationApi
        val configurationClient = ConfigurationApi(apiClient)

        //getConsents
        val r = configurationClient.getConsents("US", "Identity Verification")
        println(r)

        //getConsentsAsync
        configurationClient.getConsentsAsync(
            "US",
            "Identity Verification",
            object : ApiCallback<List<String?>?> {
                override fun onFailure(ae: ApiException, i: Int, map: Map<String, List<String>>) {
                    Logger.getLogger(SdkJavaV1Sample::class.java.name).log(Level.SEVERE, null, ae)
                }

                override fun onSuccess(t: List<String?>?, i: Int, map: Map<String, List<String>>) {
                    println(t)
                }

                override fun onUploadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }

                override fun onDownloadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }
            })

        //getCountryCodes
        val countryCodes = configurationClient.getCountryCodes("Identity Verification")
        println(countryCodes)

        //getCountryCodesAsync
        configurationClient.getCountryCodesAsync(
            "Identity Verification",
            object : ApiCallback<List<String?>?> {
                override fun onFailure(ae: ApiException, i: Int, map: Map<String, List<String>>) {
                    Logger.getLogger(SdkJavaV1Sample::class.java.name).log(Level.SEVERE, null, ae)
                }

                override fun onSuccess(t: List<String?>?, i: Int, map: Map<String, List<String>>) {
                    println(t)
                }

                override fun onUploadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }

                override fun onDownloadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }
            })

        //getCountrySubdivisions
        val countrySubdivisions = configurationClient.getCountrySubdivisions("AU")
        println(countrySubdivisions)
        configurationClient.getCountrySubdivisionsAsync(
            "AU",
            object : ApiCallback<List<CountrySubdivision?>?> {
                override fun onFailure(ae: ApiException, i: Int, map: Map<String, List<String>>) {
                    Logger.getLogger(SdkJavaV1Sample::class.java.name).log(Level.SEVERE, null, ae)
                }

                override fun onSuccess(
                    t: List<CountrySubdivision?>?,
                    i: Int,
                    map: Map<String, List<String>>
                ) {
                    println(t)
                }

                override fun onUploadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }

                override fun onDownloadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }
            })

        //getFields
        val o = configurationClient.getFields("US", "Identity Verification")
        println(o)

        //getFieldsAsync
        configurationClient.getFieldsAsync(
            "US",
            "Identity Verification",
            object : ApiCallback<Any?> {
                override fun onFailure(ae: ApiException, i: Int, map: Map<String, List<String>>) {
                    Logger.getLogger(SdkJavaV1Sample::class.java.name).log(Level.SEVERE, null, ae)
                }

                override fun onSuccess(t: Any?, i: Int, map: Map<String, List<String>>) {
                    println(t)
                }

                override fun onUploadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }

                override fun onDownloadProgress(l: Long, l1: Long, bln: Boolean) {
                    //To change body of generated methods, choose Tools | Templates.
                }
            })

        //getDocumentTypes
        val documentTypes = configurationClient.getDocumentTypes("US")
        println(documentTypes)

        //getDocumentTypesAsync
        configurationClient.getDocumentTypesAsync(
            "US",
            object : ApiCallback<Map<String?, List<String?>?>?> {
                override fun onFailure(ae: ApiException, i: Int, map: Map<String, List<String>>) {
                    Logger.getLogger(SdkJavaV1Sample::class.java.name).log(Level.SEVERE, null, ae)
                }

                override fun onSuccess(
                    t: Map<String?, List<String?>?>?,
                    i: Int,
                    map: Map<String, List<String>>
                ) {
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
}