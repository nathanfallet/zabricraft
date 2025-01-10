package dev.zabricraft.usecases.players

import dev.zabricraft.models.players.UpdateZabriPlayerPayload
import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.repositories.players.IZabriPlayersRepository
import org.bukkit.entity.Player

class CreateUpdateZabriPlayerUseCase(
    private val repository: IZabriPlayersRepository,
) : ICreateUpdateZabriPlayerUseCase {

    override fun invoke(input: Player): ZabriPlayer? {
        return repository.get(input.uniqueId)?.let {
            repository.update(it.id, UpdateZabriPlayerPayload(player = input))
            it
        } ?: repository.create(input)
    }

}
