package dev.zabricraft.usecases.games

import dev.zabricraft.repositories.games.IGamesRepository

class SaveGamesUseCase(
    private val repository: IGamesRepository,
    private val saveSignsUseCase: ISaveSignsUseCase,
) : ISaveGamesUseCase {

    override fun invoke() = repository.list().forEach { game ->
        saveSignsUseCase(game)
    }

}
