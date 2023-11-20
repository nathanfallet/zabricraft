package me.nathanfallet.zabricraft.usecases.games

import me.nathanfallet.usecases.base.IUnitUseCase
import me.nathanfallet.usecases.base.IUseCase
import me.nathanfallet.zabricraft.models.games.IGame

interface IGetAddGamesUseCase : IUnitUseCase<List<IGame>>, IUseCase<IGame, Unit> {

    fun clear()

}
