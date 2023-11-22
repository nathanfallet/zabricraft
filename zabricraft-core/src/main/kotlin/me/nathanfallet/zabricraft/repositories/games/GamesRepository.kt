package me.nathanfallet.zabricraft.repositories.games

import me.nathanfallet.zabricraft.models.games.IGame

class GamesRepository : IGamesRepository {

    private val games = mutableListOf<IGame>()

    override fun list(): List<IGame> {
        return games
    }

    override fun add(game: IGame) {
        games.add(game)
    }

    override fun clear() {
        games.clear()
    }

}
