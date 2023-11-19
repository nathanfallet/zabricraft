package me.nathanfallet.zabricraft.usecases.spawn

import me.nathanfallet.usecases.base.IUnitUseCase
import me.nathanfallet.usecases.base.IUseCase
import org.bukkit.Location

interface IGetSetSpawnUseCase : IUnitUseCase<Location>, IUseCase<Location, Unit>
