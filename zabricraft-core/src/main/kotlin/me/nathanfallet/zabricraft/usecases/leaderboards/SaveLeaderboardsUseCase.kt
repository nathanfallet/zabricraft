package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.repositories.leaderboards.ILeaderboardsRepository

class SaveLeaderboardsUseCase(
    private val repository: ILeaderboardsRepository
) : ISaveLeaderboardsUseCase {

    override fun invoke() {
        repository.save()
    }

}
