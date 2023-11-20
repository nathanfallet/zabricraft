package me.nathanfallet.zabricraft.usecases.games

import me.nathanfallet.zabricraft.models.games.IGame

class GetAddGamesUseCase : IGetAddGamesUseCase {

    private val games = mutableListOf<IGame>()

    override fun clear() {
        games.clear()
    }

    override fun invoke(): List<IGame> {
        return games
    }

    override fun invoke(input: IGame) {
        games.add(input)
    }

}
