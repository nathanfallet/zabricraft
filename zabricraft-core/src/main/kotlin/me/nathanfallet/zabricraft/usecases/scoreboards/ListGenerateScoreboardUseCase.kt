package me.nathanfallet.zabricraft.usecases.scoreboards

import me.nathanfallet.zabricraft.repositories.scoreboards.IGenerateScoreboardUseCasesRepository

class ListGenerateScoreboardUseCase(
    private val repository: IGenerateScoreboardUseCasesRepository
) : IListGenerateScoreboardUseCase {

    override fun invoke(): List<IGenerateScoreboardUseCase> {
        return repository.list()
    }

}
