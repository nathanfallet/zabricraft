package me.nathanfallet.zabricraft.usecases.games

import me.nathanfallet.zabricraft.models.games.IGame
import me.nathanfallet.zabricraft.repositories.games.IGamesRepository

class ListGameUseCase(
    private val repository: IGamesRepository
) : IListGameUseCase {

    override fun invoke(): List<IGame> {
        return repository.list()
    }

}
