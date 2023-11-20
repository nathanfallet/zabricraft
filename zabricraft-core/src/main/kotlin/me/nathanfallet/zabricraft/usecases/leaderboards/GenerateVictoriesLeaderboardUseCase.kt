package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository

class GenerateVictoriesLeaderboardUseCase(
    private val repository: IZabriPlayersRepository
) : IGenerateLeaderboardUseCase {

    override val title = "Victories"

    override fun invoke(input: Int): List<String> {
        return repository.getTopVictories(input).map(ZabriPlayer::name)
    }

}

