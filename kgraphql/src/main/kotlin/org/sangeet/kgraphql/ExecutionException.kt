package com.apurebase.kgraphql

import com.apurebase.kgraphql.schema.execution.Execution
import com.apurebase.kgraphql.schema.model.ast.ASTNode
import com.apurebase.kgraphql.schema.model.log

class ExecutionException(
    message: String,
    node: ASTNode? = null,
    cause: Throwable? = null
) : GraphQLError(
    message,
    nodes = node?.let(::listOf),
    originalError = cause
) {
    constructor(message: String, node: Execution, cause: Throwable? = null): this(message, node.selectionNode, cause)
}
