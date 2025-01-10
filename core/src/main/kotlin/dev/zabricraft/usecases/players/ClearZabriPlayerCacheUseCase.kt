package dev.zabricraft.usecases.players

import dev.zabricraft.repositories.players.IZabriPlayersRepository
import java.util.*

class ClearZabriPlayerCacheUseCase(
    private val repository: IZabriPlayersRepository,
) : IClearZabriPlayerCacheUseCase {

    override fun invoke(input: UUID) {
        repository.clearCache(input)
    }

}
