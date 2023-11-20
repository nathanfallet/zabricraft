package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository
import java.util.*

class ClearZabriPlayerCacheUseCase(
    private val repository: IZabriPlayersRepository
) : IClearZabriPlayerCacheUseCase {

    override fun invoke(input: UUID) {
        repository.clearCache(input)
    }

}
