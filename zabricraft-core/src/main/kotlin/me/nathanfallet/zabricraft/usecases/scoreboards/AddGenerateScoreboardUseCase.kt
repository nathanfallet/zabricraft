package me.nathanfallet.zabricraft.usecases.scoreboards

import me.nathanfallet.zabricraft.repositories.scoreboards.IGenerateScoreboardUseCasesRepository

class AddGenerateScoreboardUseCase(
    private val repository: IGenerateScoreboardUseCasesRepository
) : IAddGenerateScoreboardUseCase {

    override fun invoke(input: IGenerateScoreboardUseCase) {
        repository.add(input)
    }

}
