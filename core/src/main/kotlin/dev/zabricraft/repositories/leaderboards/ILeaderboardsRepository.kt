package dev.zabricraft.repositories.leaderboards

import dev.kaccelero.repositories.IModelRepository
import dev.zabricraft.models.leaderboards.CreateLeaderboardPayload
import dev.zabricraft.models.leaderboards.Leaderboard
import dev.zabricraft.models.leaderboards.UpdateLeaderboardPayload

interface ILeaderboardsRepository :
    IModelRepository<Leaderboard, String, CreateLeaderboardPayload, UpdateLeaderboardPayload> {

    fun save()
    fun clear()

}
