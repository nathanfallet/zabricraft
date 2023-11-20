package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.zabricraft.models.players.UpdateZabriPlayerPayload
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository
import org.bukkit.entity.Player

class CreateUpdateZabriPlayerUseCase(
    private val repository: IZabriPlayersRepository
) : ICreateUpdateZabriPlayerUseCase {

    override fun invoke(input: Player): ZabriPlayer? {
        return repository.get(input.uniqueId)?.let {
            repository.update(it.id, UpdateZabriPlayerPayload(player = input))
            it
        } ?: repository.create(input)
    }

}
