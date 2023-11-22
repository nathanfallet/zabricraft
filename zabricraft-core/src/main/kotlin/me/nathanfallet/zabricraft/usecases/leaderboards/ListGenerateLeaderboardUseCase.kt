package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.repositories.leaderboards.IGenerateLeaderboardUseCasesRepository

class ListGenerateLeaderboardUseCase(
    private val repository: IGenerateLeaderboardUseCasesRepository
) : IListGenerateLeaderboardUseCase {

    override fun invoke(): Map<String, IGenerateLeaderboardUseCase> {
        return repository.list()
    }

}
