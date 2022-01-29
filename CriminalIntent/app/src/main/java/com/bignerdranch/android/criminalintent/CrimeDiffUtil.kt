package com.bignerdranch.android.criminalintent

import androidx.recyclerview.widget.DiffUtil

//챌린지 12
class CrimeDiffUtil : DiffUtil.ItemCallback<Crime>(){

    override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean =
        oldItem == newItem

    override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean =
        oldItem.id == newItem.id
}