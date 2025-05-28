package dev.zabricraft.usecases.leaderboards

import dev.zabricraft.repositories.leaderboards.IGenerateLeaderboardUseCasesRepository

class ListGenerateLeaderboardUseCase(
    private val repository: IGenerateLeaderboardUseCasesRepository,
) : IListGenerateLeaderboardUseCase {

    override fun invoke(): Map<String, IGenerateLeaderboardUseCase> = repository.list()

}
