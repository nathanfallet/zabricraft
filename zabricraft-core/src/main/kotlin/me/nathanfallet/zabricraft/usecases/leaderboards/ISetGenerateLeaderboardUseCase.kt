package me.nathanfallet.zabricraft.usecases.leaderboards

import dev.kaccelero.usecases.IPairUseCase

interface ISetGenerateLeaderboardUseCase : IPairUseCase<String, IGenerateLeaderboardUseCase, Unit>
