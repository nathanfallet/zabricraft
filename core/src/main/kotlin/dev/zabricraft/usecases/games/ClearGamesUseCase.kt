package dev.zabricraft.usecases.games

import dev.zabricraft.models.games.IGame
import dev.zabricraft.repositories.games.IGamesRepository

class ClearGamesUseCase(
    private val repository: IGamesRepository,
) : IClearGamesUseCase {

    override fun invoke() {
        repository.list().forEach(IGame::reset)
        repository.clear()
    }

}
