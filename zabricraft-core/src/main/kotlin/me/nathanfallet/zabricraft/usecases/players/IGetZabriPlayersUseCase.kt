package me.nathanfallet.zabricraft.usecases.players

import dev.kaccelero.usecases.IUnitUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer

interface IGetZabriPlayersUseCase : IUnitUseCase<List<ZabriPlayer>>
