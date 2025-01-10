package dev.zabricraft.usecases.leaderboards

import dev.zabricraft.repositories.leaderboards.ILeaderboardsRepository

class SaveLeaderboardsUseCase(
    private val repository: ILeaderboardsRepository,
) : ISaveLeaderboardsUseCase {

    override fun invoke() {
        repository.save()
    }

}
