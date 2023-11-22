package me.nathanfallet.zabricraft.usecases.rules

import me.nathanfallet.zabricraft.repositories.rules.IWorldProtectionRuleUseCaseRepository

class ClearWorldProtectionRuleUseCase(
    private val repository: IWorldProtectionRuleUseCaseRepository
) : IClearWorldProtectionRuleUseCase {

    override fun invoke() {
        repository.clear()
    }

}
