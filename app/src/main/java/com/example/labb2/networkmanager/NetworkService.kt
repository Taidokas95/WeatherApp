package com.example.labb2.networkmanager

import com.example.labb2.model.WeathersState
import kotlinx.coroutines.flow.MutableStateFlow

interface NetworkService {
    data class runService(val runnableService: RunnableService)
}

interface RunnableService{
    data class RetrofitRunner(val localWeathersState: WeathersState):RunnableService
}