package com.example.labb2.networkmanager

import com.example.labb2.model.WeathersState
import com.example.labb2.roommanager.WeatherDao

interface NetworkService {
    data class runService(val runnableService: RunnableService)
}

interface RunnableService{
    data class RetrofitRunner(
        val localWeathersState: WeathersState,
        // TODO: Change between the type of service
        //val typeOfNetworkService: TypeOfNetworkService = TypeOfNetworkService.MACEOTESTJSON):RunnableService
        val typeOfNetworkService: TypeOfNetworkService = TypeOfNetworkService.GETEXTERNALJSON):RunnableService
}