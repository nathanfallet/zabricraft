package me.nathanfallet.zabricraft.usecases.scoreboards

import me.nathanfallet.usecases.base.IUnitUseCase

interface IGetGenerateScoreboardsUseCase : IUnitUseCase<MutableList<IGenerateScoreboardUseCase>> {

    fun clear()

}

