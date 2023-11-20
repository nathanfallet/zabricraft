package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.usecases.base.IUnitUseCase

interface IGetGenerateLeaderboardsUseCase : IUnitUseCase<MutableMap<String, IGenerateLeaderboardUseCase>> {

    fun clear()

}
