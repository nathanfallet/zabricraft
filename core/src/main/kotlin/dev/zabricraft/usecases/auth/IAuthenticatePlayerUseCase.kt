package dev.zabricraft.usecases.auth

import dev.kaccelero.usecases.IPairUseCase
import dev.zabricraft.models.players.ZabriPlayer

interface IAuthenticatePlayerUseCase : IPairUseCase<ZabriPlayer, String, Boolean>
