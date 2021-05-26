package com.decagon.android.sq007.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.decagon.android.sq007.repository.Repository

class ViewModelFactory (private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository) as T
    }
}