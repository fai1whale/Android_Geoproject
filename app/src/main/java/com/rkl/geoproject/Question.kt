package com.rkl.geoproject

import androidx.annotation.StringRes

//constructor to question
data class Question (@StringRes val textResId: Int, val answer: Boolean)