package dev.zabricraft.repositories.leaderboards

import dev.zabricraft.usecases.leaderboards.IGenerateLeaderboardUseCase

interface IGenerateLeaderboardUseCasesRepository {

    fun list(): Map<String, IGenerateLeaderboardUseCase>
    fun get(id: String): IGenerateLeaderboardUseCase?
    fun set(id: String, value: IGenerateLeaderboardUseCase)
    fun clear()

}
