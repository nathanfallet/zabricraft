package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.usecases.base.IUseCase

interface IGenerateLeaderboardUseCase : IUseCase<Int, List<String>> {

    val title: String

}
