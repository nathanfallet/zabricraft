package me.nathanfallet.zabricraft.usecases.auth

import me.nathanfallet.usecases.base.IPairUseCase
import me.nathanfallet.zabricraft.models.players.ZabriPlayer

interface IAuthenticatePlayerUseCase : IPairUseCase<ZabriPlayer, String, Boolean>
