package dev.zabricraft.usecases.games

import dev.zabricraft.models.games.IGame
import dev.zabricraft.repositories.games.IGamesRepository

class ListGameUseCase(
    private val repository: IGamesRepository,
) : IListGameUseCase {

    override fun invoke(): List<IGame> {
        return repository.list()
    }

}
