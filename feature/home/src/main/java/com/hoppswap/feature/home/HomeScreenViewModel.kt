package com.hoppswap.feature.home

import com.hoppswap.core.common.base.BaseIntent
import com.hoppswap.core.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(): BaseViewModel() {

    override fun onIntent(intent: BaseIntent) {

    }
}

sealed class HomeScreenIntent : BaseIntent