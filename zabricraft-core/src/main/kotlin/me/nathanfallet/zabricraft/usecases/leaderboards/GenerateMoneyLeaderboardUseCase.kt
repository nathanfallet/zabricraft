package me.nathanfallet.zabricraft.usecases.leaderboards

import me.nathanfallet.zabricraft.models.players.ZabriPlayer
import me.nathanfallet.zabricraft.repositories.players.IZabriPlayersRepository

class GenerateMoneyLeaderboardUseCase(
    private val repository: IZabriPlayersRepository
) : IGenerateLeaderboardUseCase {

    override val title = "Money"

    override fun invoke(input: Int): List<String> {
        return repository.getTopMoney(input).map(ZabriPlayer::name)
    }

}
