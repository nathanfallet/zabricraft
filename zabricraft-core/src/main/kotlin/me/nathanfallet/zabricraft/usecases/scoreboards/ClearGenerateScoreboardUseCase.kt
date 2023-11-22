package me.nathanfallet.zabricraft.usecases.scoreboards

import me.nathanfallet.zabricraft.repositories.scoreboards.IGenerateScoreboardUseCasesRepository

class ClearGenerateScoreboardUseCase(
    private val repository: IGenerateScoreboardUseCasesRepository
) : IClearGenerateScoreboardUseCase {

    override fun invoke() {
        repository.clear()
    }

}
