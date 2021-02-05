package com.example.weathermvp.business

import com.example.weathermvp.R

class HttpErrorStrCoder {
     fun getHttpErrorStrCode(code: Int?): Int {
         return when(code){
             404 -> R.string.http404
             408 -> R.string.http408
             500 -> R.string.http500
             503 ->R.string.http503
             else -> R.string.not_load
         }
    }
}