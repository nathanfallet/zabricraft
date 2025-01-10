package dev.zabricraft.usecases.leaderboards

import dev.zabricraft.repositories.leaderboards.IGenerateLeaderboardUseCasesRepository

class GetGenerateLeaderboardUseCase(
    private val repository: IGenerateLeaderboardUseCasesRepository,
) : IGetGenerateLeaderboardUseCase {

    override fun invoke(input: String): IGenerateLeaderboardUseCase? {
        return repository.get(input)
    }

}
