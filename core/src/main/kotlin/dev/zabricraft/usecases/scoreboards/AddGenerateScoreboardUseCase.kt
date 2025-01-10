package dev.zabricraft.usecases.scoreboards

import dev.zabricraft.repositories.scoreboards.IGenerateScoreboardUseCasesRepository

class AddGenerateScoreboardUseCase(
    private val repository: IGenerateScoreboardUseCasesRepository,
) : IAddGenerateScoreboardUseCase {

    override fun invoke(input: IGenerateScoreboardUseCase) {
        repository.add(input)
    }

}
