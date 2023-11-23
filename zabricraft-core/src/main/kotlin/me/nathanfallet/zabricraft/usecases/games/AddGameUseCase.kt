package me.nathanfallet.zabricraft.usecases.games

import me.nathanfallet.zabricraft.models.games.IGame
import me.nathanfallet.zabricraft.repositories.games.IGamesRepository

class AddGameUseCase(
    private val repository: IGamesRepository
) : IAddGameUseCase {

    override fun invoke(input: IGame) {
        repository.add(input)
    }

}
