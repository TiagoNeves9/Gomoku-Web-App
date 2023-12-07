package com.example.gomoku.http

import com.example.gomoku.http.model.OutputModel
import com.example.gomoku.http.model.RankingsOutputModel
import com.example.gomoku.http.model.UserRankingOutputModel
import com.example.gomoku.service.StatisticsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class StatisticController(val statisticService: StatisticsService) {
    fun insertUserStatistics(username: String, score: Int = 0) =
        statisticService.insertUserStatistics(username, score)

    /* Present also number of total played games? */
    @GetMapping(PathTemplate.RANKINGS)
    fun getRankings(): SirenModel<OutputModel> {
        val rankings = statisticService.getRankings()
        return siren(
            RankingsOutputModel(rankingList = rankings)
        ) { clazz("Rankings") }
    }

    @GetMapping(PathTemplate.USER_RANKING)
    fun getUserRanking(@PathVariable username: String): SirenModel<OutputModel> {
        val userRanking = statisticService.getUserRanking(username)
        return siren(
            UserRankingOutputModel(userRanking = userRanking)
        ) { clazz("UserRanking")}
    }
}