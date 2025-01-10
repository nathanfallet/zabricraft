package dev.zabricraft.usecases.players

import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.repositories.players.IZabriPlayersRepository

class GetZabriPlayersUseCase(
    private val repository: IZabriPlayersRepository,
) : IGetZabriPlayersUseCase {

    override fun invoke(): List<ZabriPlayer> {
        return repository.list()
    }

}
