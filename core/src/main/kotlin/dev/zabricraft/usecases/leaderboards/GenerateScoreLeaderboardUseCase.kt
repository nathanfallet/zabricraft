package dev.zabricraft.usecases.leaderboards

import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.repositories.players.IZabriPlayersRepository

class GenerateScoreLeaderboardUseCase(
    private val repository: IZabriPlayersRepository,
) : IGenerateLeaderboardUseCase {

    override val title = "Score"

    override fun invoke(input: Int): List<String> = repository.getTopScore(input).map(ZabriPlayer::name)

}

