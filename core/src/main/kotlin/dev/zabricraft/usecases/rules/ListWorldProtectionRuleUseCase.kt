package dev.zabricraft.usecases.rules

import dev.zabricraft.repositories.rules.IWorldProtectionRuleUseCaseRepository

class ListWorldProtectionRuleUseCase(
    private val repository: IWorldProtectionRuleUseCaseRepository,
) : IListWorldProtectionRuleUseCase {

    override fun invoke(): List<IWorldProtectionRuleUseCase> {
        return repository.list()
    }

}
