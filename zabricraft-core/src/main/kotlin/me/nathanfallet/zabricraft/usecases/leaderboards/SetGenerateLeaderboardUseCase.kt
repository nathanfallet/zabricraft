package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.repositories.leaderboards.IGenerateLeaderboardUseCasesRepository

class SetGenerateLeaderboardUseCase(
    private val repository: IGenerateLeaderboardUseCasesRepository
) : ISetGenerateLeaderboardUseCase {

    override fun invoke(input1: String, input2: IGenerateLeaderboardUseCase) {
        repository.set(input1, input2)
    }

}
