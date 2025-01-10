package dev.zabricraft.usecases.leaderboards

import dev.kaccelero.usecases.IUseCase

interface IGenerateLeaderboardUseCase : IUseCase<Int, List<String>> {

    val title: String

}
