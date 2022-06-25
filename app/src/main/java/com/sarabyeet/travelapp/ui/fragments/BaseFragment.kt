package com.sarabyeet.travelapp.ui.fragments

import androidx.fragment.app.Fragment
import com.sarabyeet.travelapp.ui.MainActivity

abstract class BaseFragment: Fragment() {
    protected val navController by lazy {
        (activity as MainActivity).navController
    }
}