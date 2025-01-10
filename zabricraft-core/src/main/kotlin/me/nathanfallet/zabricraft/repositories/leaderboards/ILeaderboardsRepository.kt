package me.nathanfallet.zabricraft.repositories.leaderboards

import dev.kaccelero.repositories.IModelRepository
import me.nathanfallet.zabricraft.models.leaderboards.CreateLeaderboardPayload
import me.nathanfallet.zabricraft.models.leaderboards.Leaderboard
import me.nathanfallet.zabricraft.models.leaderboards.UpdateLeaderboardPayload

interface ILeaderboardsRepository :
    IModelRepository<Leaderboard, String, CreateLeaderboardPayload, UpdateLeaderboardPayload> {

    fun save()
    fun clear()

}
