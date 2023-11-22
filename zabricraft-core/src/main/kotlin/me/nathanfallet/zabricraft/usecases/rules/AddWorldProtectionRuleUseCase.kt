package me.nathanfallet.zabricraft.usecases.rules

import me.nathanfallet.zabricraft.repositories.rules.IWorldProtectionRuleUseCaseRepository

class AddWorldProtectionRuleUseCase(
    private val repository: IWorldProtectionRuleUseCaseRepository
) : IAddWorldProtectionRuleUseCase {

    override fun invoke(input: IWorldProtectionRuleUseCase) {
        return repository.add(input)
    }

}
