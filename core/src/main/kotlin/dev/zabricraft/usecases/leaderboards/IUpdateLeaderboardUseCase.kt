package dev.zabricraft.usecases.leaderboards

import dev.kaccelero.usecases.IUseCase
import dev.zabricraft.models.leaderboards.Leaderboard

interface IUpdateLeaderboardUseCase : IUseCase<Leaderboard, Unit>
