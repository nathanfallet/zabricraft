package dev.zabricraft.replica.usecases.players

import dev.kaccelero.usecases.IPairUseCase
import dev.zabricraft.replica.models.ReplicaPlayer
import org.bukkit.entity.Player

interface IResetReplicaPlayerUseCase : IPairUseCase<Player, ReplicaPlayer, Unit>
