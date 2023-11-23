package me.nathanfallet.zabricraft.usecases.games

import me.nathanfallet.zabricraft.models.games.IGame
import me.nathanfallet.zabricraft.repositories.games.IGamesRepository

class ClearGamesUseCase(
    private val repository: IGamesRepository
) : IClearGamesUseCase {

    override fun invoke() {
        repository.list().forEach(IGame::reset)
        repository.clear()
    }

}
