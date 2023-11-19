package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.usecases.base.IUnitUseCase
import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard

interface IGetLeaderboardsUseCase : IUnitUseCase<Map<String, Leaderboard>> {

    fun clear()

}
