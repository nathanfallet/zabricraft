package dev.zabricraft.replica.usecases.players

import dev.zabricraft.replica.models.ReplicaPlayer
import dev.zabricraft.usecases.spawn.IGetSetSpawnUseCase
import org.bukkit.GameMode
import org.bukkit.entity.Player

class ResetReplicaPlayerUseCase(
    private val getSetSpawnUseCase: IGetSetSpawnUseCase,
) : IResetReplicaPlayerUseCase {

    override fun invoke(input1: Player, input2: ReplicaPlayer) {
        input2.playing = false
        input2.finished = false
        input2.plot = 0
        input2.currentGame = 0
        input1.teleport(getSetSpawnUseCase())
        input1.gameMode = GameMode.SURVIVAL
        input1.inventory.clear()
        input1.updateInventory()
    }

}
