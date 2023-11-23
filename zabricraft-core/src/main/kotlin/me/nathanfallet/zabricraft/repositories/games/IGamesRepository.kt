package me.nathanfallet.zabricraft.repositories.games

import me.nathanfallet.zabricraft.models.games.IGame

interface IGamesRepository {

    fun list(): List<IGame>
    fun add(game: IGame)
    fun clear()

}
