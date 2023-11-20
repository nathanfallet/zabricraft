package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.usecases.base.IUseCase
import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard

interface IUpdateLeaderboardUseCase : IUseCase<Leaderboard, Unit>
