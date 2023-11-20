package me.nathanfallet.zabricraft.usecases.leaderboards

class GetGenerateLeaderboardsUseCase : IGetGenerateLeaderboardsUseCase {

    private val usecases: MutableMap<String, IGenerateLeaderboardUseCase> = mutableMapOf()

    override fun invoke(): MutableMap<String, IGenerateLeaderboardUseCase> {
        return usecases
    }

    override fun clear() {
        usecases.clear()
    }

}
