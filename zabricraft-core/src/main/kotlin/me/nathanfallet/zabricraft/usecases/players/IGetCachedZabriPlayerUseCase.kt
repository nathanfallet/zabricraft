package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.usecases.base.IUseCase
import me.nathanfallet.zabricraft.models.players.CachedPlayer
import java.util.*

interface IGetCachedZabriPlayerUseCase : IUseCase<UUID, CachedPlayer>
