package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.usecases.base.IPairUseCase

interface ISetGenerateLeaderboardUseCase : IPairUseCase<String, IGenerateLeaderboardUseCase, Unit>
