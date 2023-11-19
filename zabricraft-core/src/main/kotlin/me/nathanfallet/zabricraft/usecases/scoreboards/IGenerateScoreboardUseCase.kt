package me.nathanfallet.zabricraft.usecases.scoreboards

import me.nathanfallet.usecases.base.IPairUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import org.bukkit.entity.Player

interface IGenerateScoreboardUseCase : IPairUseCase<Player, ZabriPlayer, List<String>>
