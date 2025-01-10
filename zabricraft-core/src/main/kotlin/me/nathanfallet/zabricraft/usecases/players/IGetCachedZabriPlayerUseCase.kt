package me.nathanfallet.zabricraft.usecases.players

import dev.kaccelero.usecases.IUseCase
import me.nathanfallet.zabricraft.models.players.CachedPlayer
import java.util.*

interface IGetCachedZabriPlayerUseCase : IUseCase<UUID, CachedPlayer>
