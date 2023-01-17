package com.example.kite.login.model

data class LoginRequest(
    var customer_email: String = "",
    var password: String = "",
    var device_type: String = "1",
    var device_token: String = "c12oB8vGTyqx6O3veklVDf:APA91bFeE0ZVo69Wf2wJKIEPsZfM4wcJDR4H4Bjqt9GOyOM0wSaBzVUtPEgu-iEEAmFK4VjaJcQE_YnFDktQ2aEPO0FDwY-E4O_U3FtAzDQVzPQzi90uE2gOVuXI_w8WdIxFvPWNn7vJ",
    var app_version: String = "1",
    var lang: Int = 0
)
