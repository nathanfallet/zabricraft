package dev.zabricraft.usecases.players

import dev.zabricraft.models.players.CachedPlayer
import dev.zabricraft.repositories.players.IZabriPlayersRepository
import java.util.*

class GetCachedZabriPlayerUseCase(
    private val repository: IZabriPlayersRepository,
) : IGetCachedZabriPlayerUseCase {

    override fun invoke(input: UUID): CachedPlayer {
        return repository.getCached(input)
    }

}
