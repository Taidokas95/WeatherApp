package com.example.labb2.externalresources.networkmanager

import com.example.labb2.model.WeathersState

/**
 * An interface which represents a network service
 */
interface NetworkService {
    data class runService(val runnableService: RunnableService)
}

/**
 *
 * A runnable service which represents which type of service can be called.
 *
 */
interface RunnableService{
    data class RetrofitRunner(
        val localWeathersState: WeathersState,
        //val typeOfNetworkService: TypeOfNetworkService = TypeOfNetworkService.MACEOTESTJSON):RunnableService
        val typeOfNetworkService: TypeOfNetworkService = TypeOfNetworkService.GETEXTERNALJSON
    ): RunnableService
}