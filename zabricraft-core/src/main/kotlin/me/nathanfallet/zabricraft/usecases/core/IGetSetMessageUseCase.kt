package me.nathanfallet.zabricraft.usecases.core

import me.nathanfallet.usecases.base.IPairUseCase
import me.nathanfallet.usecases.base.IUseCase

interface IGetSetMessageUseCase : IUseCase<String, String>, IPairUseCase<String, String, Unit>
