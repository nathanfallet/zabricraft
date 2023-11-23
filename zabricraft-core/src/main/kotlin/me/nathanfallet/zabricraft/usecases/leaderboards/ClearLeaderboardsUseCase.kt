package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard
import me.nathanfallet.zabricraft.repositories.leaderboards.ILeaderboardsRepository

class ClearLeaderboardsUseCase(
    private val repository: ILeaderboardsRepository
) : IClearLeaderboardsUseCase {

    override fun invoke() {
        repository.list().forEach(Leaderboard::kill)
        repository.clear()
    }

}
