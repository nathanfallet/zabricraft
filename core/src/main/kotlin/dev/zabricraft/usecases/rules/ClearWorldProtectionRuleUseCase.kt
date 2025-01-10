package dev.zabricraft.usecases.rules

import dev.zabricraft.repositories.rules.IWorldProtectionRuleUseCaseRepository

class ClearWorldProtectionRuleUseCase(
    private val repository: IWorldProtectionRuleUseCaseRepository,
) : IClearWorldProtectionRuleUseCase {

    override fun invoke() {
        repository.clear()
    }

}
