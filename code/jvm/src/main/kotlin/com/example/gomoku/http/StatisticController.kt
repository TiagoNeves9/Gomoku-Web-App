package com.example.gomoku.http

import com.example.gomoku.domain.UserStatistics
import com.example.gomoku.http.model.OutputModel
import com.example.gomoku.http.model.RankingOutputModel
import com.example.gomoku.service.StatisticService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticController(val statisticService: StatisticService) {
    fun insertUserStatistics(username: String, score: Int = 0) =
        statisticService.insertUserStatistics(username, score)


    /* Present also number of total played games? */
    @GetMapping(PathTemplate.RANKINGS)
    fun getRankings():  SirenModel<OutputModel> {
        val rankings = statisticService.getRankings()
        return siren(
            RankingOutputModel(
            rankingList = rankings
        )
        ){
            clazz("Rankings")
        }
    }

    @GetMapping(PathTemplate.USER_RANKING)
    fun getUserRanking(@PathVariable username: String): UserStatistics =
        statisticService.getUserRanking(username)
}