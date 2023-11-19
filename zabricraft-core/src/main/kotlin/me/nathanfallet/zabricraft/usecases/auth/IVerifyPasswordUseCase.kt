package me.nathanfallet.zabricraft.usecases.auth

import me.nathanfallet.usecases.base.IPairUseCase

interface IVerifyPasswordUseCase : IPairUseCase<String, String, Boolean>
