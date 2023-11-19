package me.nathanfallet.zabricraft.usecases.games

import me.nathanfallet.usecases.base.IPairUseCase
import org.bukkit.Location

interface IGetSignsUseCase : IPairUseCase<String, Int, List<Location>>
