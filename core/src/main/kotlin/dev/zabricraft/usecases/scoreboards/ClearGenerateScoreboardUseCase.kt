package dev.zabricraft.usecases.scoreboards

import dev.zabricraft.repositories.scoreboards.IGenerateScoreboardUseCasesRepository

class ClearGenerateScoreboardUseCase(
    private val repository: IGenerateScoreboardUseCasesRepository,
) : IClearGenerateScoreboardUseCase {

    override fun invoke() {
        repository.clear()
    }

}
