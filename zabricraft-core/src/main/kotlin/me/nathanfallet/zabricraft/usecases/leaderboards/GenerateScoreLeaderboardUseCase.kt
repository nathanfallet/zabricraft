package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository

class GenerateScoreLeaderboardUseCase(
    private val repository: IZabriPlayersRepository
) : IGenerateLeaderboardUseCase {

    override val title = "Score"

    override fun invoke(input: Int): List<String> {
        return repository.getTopScore(input).map(ZabriPlayer::name)
    }

}

