package me.nathanfallet.zabricraft.usecases.leaderboards

import dev.kaccelero.usecases.IUseCase
import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard

interface IUpdateLeaderboardUseCase : IUseCase<Leaderboard, Unit>
