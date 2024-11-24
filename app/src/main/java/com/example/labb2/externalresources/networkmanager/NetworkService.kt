package com.example.labb2.externalresources.networkmanager

import com.example.labb2.model.WeathersState

interface NetworkService {
    data class runService(val runnableService: RunnableService)
}

interface RunnableService{
    data class RetrofitRunner(
        val localWeathersState: WeathersState,
        // TODO: Change between the type of service
        //val typeOfNetworkService: TypeOfNetworkService = TypeOfNetworkService.MACEOTESTJSON):RunnableService
        val typeOfNetworkService: TypeOfNetworkService = TypeOfNetworkService.GETEXTERNALJSON
    ): RunnableService
}