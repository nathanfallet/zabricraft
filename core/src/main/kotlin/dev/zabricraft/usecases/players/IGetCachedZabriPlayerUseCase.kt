package dev.zabricraft.usecases.players

import dev.kaccelero.usecases.IUseCase
import dev.zabricraft.models.players.CachedPlayer
import java.util.*

interface IGetCachedZabriPlayerUseCase : IUseCase<UUID, CachedPlayer>
