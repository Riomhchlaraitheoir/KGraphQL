package org.sangeet.kgraphql.schema.dsl.operations

import org.sangeet.kgraphql.schema.model.MutationDef

class MutationDSL(
    name: String
) : AbstractOperationDSL(name) {


    internal fun toKQLMutation(): MutationDef<out Any?> {
        val function =
            functionWrapper ?: throw IllegalArgumentException("resolver has to be specified for mutation [$name]")

        return MutationDef(
            name = name,
            resolver = function,
            description = description,
            isDeprecated = isDeprecated,
            deprecationReason = deprecationReason,
            inputValues = inputValues,
            accessRule = accessRuleBlock,
            explicitReturnType = explicitReturnType
        )
    }

}