package me.nathanfallet.zabricraft.repositories.scoreboards

import me.nathanfallet.zabricraft.usecases.scoreboards.IGenerateScoreboardUseCase

class GenerateScoreboardUseCasesRepository : IGenerateScoreboardUseCasesRepository {

    private val usecases: MutableList<IGenerateScoreboardUseCase> = mutableListOf()

    override fun list(): List<IGenerateScoreboardUseCase> {
        return usecases
    }

    override fun add(value: IGenerateScoreboardUseCase) {
        usecases.add(value)
    }

    override fun clear() {
        usecases.clear()
    }

}
