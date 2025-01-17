package dev.zabricraft.usecases.games

import dev.kaccelero.usecases.IUseCase
import dev.zabricraft.models.games.IGame
import org.bukkit.Location

interface IGetSignsUseCase : IUseCase<IGame, List<Location>>
