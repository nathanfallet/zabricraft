package dev.zabricraft.usecases.rules

import dev.zabricraft.repositories.rules.IWorldProtectionRuleUseCaseRepository

class AddWorldProtectionRuleUseCase(
    private val repository: IWorldProtectionRuleUseCaseRepository,
) : IAddWorldProtectionRuleUseCase {

    override fun invoke(input: IWorldProtectionRuleUseCase) {
        return repository.add(input)
    }

}
