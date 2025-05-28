package dev.zabricraft.repositories.games

import dev.zabricraft.models.games.IGame

class GamesRepository : IGamesRepository {

    private val games = mutableListOf<IGame>()

    override fun list(): List<IGame> = games

    override fun add(game: IGame) = games.add(game).let {}

    override fun clear() = games.clear()

}
