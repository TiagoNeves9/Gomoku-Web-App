package com.example.gomoku.http

import com.example.gomoku.domain.UserRanking
import com.example.gomoku.service.StatisticService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticController(
    val statisticService: StatisticService
) {


    /*
    * Present also number of total played games?
    * */
    @GetMapping(PathTemplate.RANKINGS)
    fun getRankings(): List<UserRanking> {
        val nTotalGames = statisticService.getNumberOfGames()
        //TODO - make response with rankings and nTotalGames
        return statisticService.getRankings()
    }

    @GetMapping(PathTemplate.USER_RANKING)
    fun getUserRanking(@PathVariable username : String) : UserRanking {
        return statisticService.getUserRanking(username)
    }

}