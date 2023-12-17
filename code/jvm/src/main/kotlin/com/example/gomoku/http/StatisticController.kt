package com.example.gomoku.http

import com.example.gomoku.http.model.OutputModel
import com.example.gomoku.http.model.RankingsOutputModel
import com.example.gomoku.http.model.UserRankingOutputModel
import com.example.gomoku.service.StatisticsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class StatisticController(val statisticService: StatisticsService) {
    @GetMapping(PathTemplate.RANKINGS)
    fun getRankings(): ResponseEntity<SirenModel<OutputModel>> {
        val rankings = statisticService.getRankings()
        return ResponseEntity.status(200).body(
            siren(RankingsOutputModel(rankings)) { clazz("Rankings") }
        )
    }

    @GetMapping(PathTemplate.USER_RANKING)
    fun getUserRanking(@PathVariable username: String): ResponseEntity<SirenModel<OutputModel>> {
        val userRanking = statisticService.getUserRanking(username)
        return ResponseEntity.status(200).body(
            siren(UserRankingOutputModel(userRanking)) { clazz("UserRanking") }
        )
    }
}