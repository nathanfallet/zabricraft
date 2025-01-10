package dev.zabricraft.usecases.spawn

import dev.kaccelero.usecases.IUnitUseCase
import dev.kaccelero.usecases.IUseCase
import org.bukkit.Location

interface IGetSetSpawnUseCase : IUnitUseCase<Location>, IUseCase<Location, Unit>
