package me.nathanfallet.zabricraft.usecases.games

import dev.kaccelero.usecases.IPairUseCase
import org.bukkit.Location

interface IGetSignsUseCase : IPairUseCase<String, Int, List<Location>>
