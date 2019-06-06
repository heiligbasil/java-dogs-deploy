package com.lambdaschool.dogsinitial.model

import java.util.concurrent.atomic.AtomicLong

data class Dog ( var breed: String, var weight: Int, var isApartmentSuitable : Boolean ) {
    var id: Long = counter.incrementAndGet()

    companion object {
        private val counter = AtomicLong()
    }
}