package dev.zabricraft.usecases.games

import dev.zabricraft.models.games.IGame
import dev.zabricraft.repositories.games.IGamesRepository

class AddGameUseCase(
    private val repository: IGamesRepository,
    private val getSignsUseCase: IGetSignsUseCase,
) : IAddGameUseCase {

    override fun invoke(input: IGame) = repository.add(input.apply {
        signs.addAll(getSignsUseCase(input))
    })

}
