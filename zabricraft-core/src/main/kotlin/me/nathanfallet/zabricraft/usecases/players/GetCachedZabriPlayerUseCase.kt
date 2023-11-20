package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.zabricraft.models.players.CachedPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository
import java.util.*

class GetCachedZabriPlayerUseCase(
    private val repository: IZabriPlayersRepository
) : IGetCachedZabriPlayerUseCase {

    override fun invoke(input: UUID): CachedPlayer {
        return repository.getCached(input)
    }

}
