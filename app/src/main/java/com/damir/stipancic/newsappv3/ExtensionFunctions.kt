package com.damir.stipancic.newsappv3

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun <T : Any?> Fragment.setBackStackData(key: String,data : T) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)

}

fun <T : Any?> Fragment.getBackStackData(key: String, result: (T) -> (Unit)) {
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)?.observe(viewLifecycleOwner) {
        result(it)
    }
}