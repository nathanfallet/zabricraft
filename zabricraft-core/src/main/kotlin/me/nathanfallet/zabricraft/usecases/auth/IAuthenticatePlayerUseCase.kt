package me.nathanfallet.zabricraft.usecases.auth

import dev.kaccelero.usecases.IPairUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer

interface IAuthenticatePlayerUseCase : IPairUseCase<ZabriPlayer, String, Boolean>
