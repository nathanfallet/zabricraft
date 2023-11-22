package me.nathanfallet.zabricraft.repositories.leaderboards

import me.nathanfallet.usecases.models.repositories.IModelRepository
import me.nathanfallet.zabricraft.models.leaderboards.CreateLeaderboardPayload
import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard
import me.nathanfallet.zabricraft.models.leaderboards.UpdateLeaderboardPayload

interface ILeaderboardsRepository :
    IModelRepository<Leaderboard, String, CreateLeaderboardPayload, UpdateLeaderboardPayload> {

    fun save()
    fun clear()

}
