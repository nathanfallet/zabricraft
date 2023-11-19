package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository

class ClearZabriPlayersCacheUseCase(
    private val repository: IZabriPlayersRepository
) : IClearZabriPlayersCacheUseCase {

    override fun invoke() {
        repository.clearCache()
    }

}
