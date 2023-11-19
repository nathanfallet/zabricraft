package me.nathanfallet.zabricraft.usecases.players

import me.nathanfallet.usecases.models.get.IGetModelUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import java.util.*

interface IGetZabriPlayerUseCase : IGetModelUseCase<ZabriPlayer, UUID>
