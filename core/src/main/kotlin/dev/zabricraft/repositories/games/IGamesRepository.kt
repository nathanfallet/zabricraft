package dev.zabricraft.repositories.games

import dev.zabricraft.models.games.IGame

interface IGamesRepository {

    fun list(): List<IGame>
    fun add(game: IGame)
    fun clear()

}
