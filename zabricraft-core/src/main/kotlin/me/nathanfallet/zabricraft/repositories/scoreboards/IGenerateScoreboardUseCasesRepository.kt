package me.nathanfallet.zabricraft.repositories.scoreboards

import me.nathanfallet.zabricraft.usecases.scoreboards.IGenerateScoreboardUseCase

interface IGenerateScoreboardUseCasesRepository {

    fun list(): List<IGenerateScoreboardUseCase>
    fun add(value: IGenerateScoreboardUseCase)
    fun clear()

}
