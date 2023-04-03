package com.example.kite.countrylisting

interface OnCellClickedRegion {
    fun isClickedCountry(data: String, position: Int)
    fun isClickedState(data: String, position: Int)
}