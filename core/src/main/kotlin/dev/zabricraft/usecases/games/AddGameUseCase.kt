package dev.zabricraft.usecases.games

import dev.zabricraft.models.games.IGame
import dev.zabricraft.repositories.games.IGamesRepository

class AddGameUseCase(
    private val repository: IGamesRepository,
) : IAddGameUseCase {

    override fun invoke(input: IGame) {
        repository.add(input)
    }

}
