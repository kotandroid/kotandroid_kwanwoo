package com.bignerdranch.android.criminalintent

import android.app.Application

class CrimialIntentApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }
}