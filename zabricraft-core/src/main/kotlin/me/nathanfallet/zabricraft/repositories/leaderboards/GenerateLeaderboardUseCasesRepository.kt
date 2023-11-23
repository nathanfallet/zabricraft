package me.nathanfallet.zabricraft.repositories.leaderboards

import me.nathanfallet.zabricraft.usecases.leaderboards.IGenerateLeaderboardUseCase

class GenerateLeaderboardUseCasesRepository : IGenerateLeaderboardUseCasesRepository {

    private val usecases: MutableMap<String, IGenerateLeaderboardUseCase> = mutableMapOf()

    override fun list(): Map<String, IGenerateLeaderboardUseCase> {
        return usecases
    }

    override fun get(id: String): IGenerateLeaderboardUseCase? {
        return usecases[id]
    }

    override fun set(id: String, value: IGenerateLeaderboardUseCase) {
        usecases[id] = value
    }

    override fun clear() {
        usecases.clear()
    }

}
