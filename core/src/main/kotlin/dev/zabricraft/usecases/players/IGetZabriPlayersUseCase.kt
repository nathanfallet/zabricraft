package dev.zabricraft.usecases.players

import dev.kaccelero.usecases.IUnitUseCase
import dev.zabricraft.models.players.ZabriPlayer

interface IGetZabriPlayersUseCase : IUnitUseCase<List<ZabriPlayer>>
