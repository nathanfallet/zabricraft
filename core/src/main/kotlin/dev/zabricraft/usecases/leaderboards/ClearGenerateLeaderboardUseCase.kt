package dev.zabricraft.usecases.leaderboards

import dev.zabricraft.repositories.leaderboards.IGenerateLeaderboardUseCasesRepository

class ClearGenerateLeaderboardUseCase(
    private val repository: IGenerateLeaderboardUseCasesRepository,
) : IClearGenerateLeaderboardUseCase {

    override fun invoke() {
        repository.clear()
    }

}
