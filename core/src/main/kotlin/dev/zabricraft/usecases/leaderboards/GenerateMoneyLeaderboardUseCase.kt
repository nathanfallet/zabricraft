package dev.zabricraft.usecases.leaderboards

import dev.zabricraft.models.players.ZabriPlayer
import dev.zabricraft.repositories.players.IZabriPlayersRepository

class GenerateMoneyLeaderboardUseCase(
    private val repository: IZabriPlayersRepository,
) : IGenerateLeaderboardUseCase {

    override val title = "Money"

    override fun invoke(input: Int): List<String> = repository.getTopMoney(input).map(ZabriPlayer::name)

}
