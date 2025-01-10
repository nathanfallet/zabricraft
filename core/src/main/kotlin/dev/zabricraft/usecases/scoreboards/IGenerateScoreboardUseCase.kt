package dev.zabricraft.usecases.scoreboards

import dev.kaccelero.usecases.IPairUseCase
import dev.zabricraft.models.players.ZabriPlayer
import org.bukkit.entity.Player

interface IGenerateScoreboardUseCase : IPairUseCase<Player, ZabriPlayer, List<String>>
