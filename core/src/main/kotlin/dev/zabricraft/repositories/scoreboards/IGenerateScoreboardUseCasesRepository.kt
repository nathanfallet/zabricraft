package dev.zabricraft.repositories.scoreboards

import dev.zabricraft.usecases.scoreboards.IGenerateScoreboardUseCase

interface IGenerateScoreboardUseCasesRepository {

    fun list(): List<IGenerateScoreboardUseCase>
    fun add(value: IGenerateScoreboardUseCase)
    fun clear()

}
