package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.repositories.leaderboards.IGenerateLeaderboardUseCasesRepository

class ClearGenerateLeaderboardUseCase(
    private val repository: IGenerateLeaderboardUseCasesRepository
) : IClearGenerateLeaderboardUseCase {

    override fun invoke() {
        repository.clear()
    }

}
