package dev.zabricraft.usecases.leaderboards

import dev.zabricraft.models.leaderboards.Leaderboard
import dev.zabricraft.repositories.leaderboards.ILeaderboardsRepository

class ClearLeaderboardsUseCase(
    private val repository: ILeaderboardsRepository,
) : IClearLeaderboardsUseCase {

    override fun invoke() {
        repository.list().forEach(Leaderboard::kill)
        repository.clear()
    }

}
