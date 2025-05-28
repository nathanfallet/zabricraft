package dev.zabricraft.usecases.leaderboards

import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.repositories.players.IZabriPlayersRepository

class GenerateVictoriesLeaderboardUseCase(
    private val repository: IZabriPlayersRepository,
) : IGenerateLeaderboardUseCase {

    override val title = "Victories"

    override fun invoke(input: Int): List<String> = repository.getTopVictories(input).map(ZabriPlayer::name)

}

