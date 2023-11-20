package me.nathanfallet.zabricraft.usecases.scoreboards

class GetGenerateScoreboardsUseCase : IGetGenerateScoreboardsUseCase {

    private val usecases: MutableList<IGenerateScoreboardUseCase> = mutableListOf()

    override fun invoke(): MutableList<IGenerateScoreboardUseCase> {
        return usecases
    }

    override fun clear() {
        usecases.clear()
    }

}
