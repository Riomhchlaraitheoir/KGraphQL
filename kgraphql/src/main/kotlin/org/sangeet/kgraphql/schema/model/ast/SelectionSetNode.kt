package org.sangeet.kgraphql.schema.model.ast

data class SelectionSetNode(
    override val loc: Location?,
    val selections: List<SelectionNode>
): ASTNode()
