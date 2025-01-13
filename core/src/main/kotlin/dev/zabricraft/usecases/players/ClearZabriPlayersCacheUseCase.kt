package dev.zabricraft.usecases.players

import dev.zabricraft.repositories.players.IZabriPlayersRepository

class ClearZabriPlayersCacheUseCase(
    private val repository: IZabriPlayersRepository,
) : IClearZabriPlayersCacheUseCase {

    override fun invoke() = repository.clearCache()

}
