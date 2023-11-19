package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository

class GetZabriPlayersUseCase(
    private val repository: IZabriPlayersRepository
) : IGetZabriPlayersUseCase {

    override fun invoke(): List<ZabriPlayer> {
        return repository.getAll()
    }

}
