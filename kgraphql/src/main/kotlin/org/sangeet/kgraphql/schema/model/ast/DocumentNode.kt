package org.sangeet.kgraphql.schema.model.ast

data class DocumentNode(
    val loc: Location?,
    val definitions: List<DefinitionNode>

)
