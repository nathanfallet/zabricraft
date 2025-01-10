package dev.zabricraft.usecases.scoreboards

import dev.zabricraft.repositories.scoreboards.IGenerateScoreboardUseCasesRepository

class ListGenerateScoreboardUseCase(
    private val repository: IGenerateScoreboardUseCasesRepository,
) : IListGenerateScoreboardUseCase {

    override fun invoke(): List<IGenerateScoreboardUseCase> {
        return repository.list()
    }

}
