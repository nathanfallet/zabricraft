package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.usecases.base.IUnitUseCase
import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard

interface IGetLeaderboardsUseCase : IUnitUseCase<MutableMap<String, Leaderboard>> {

    fun clear()

}
