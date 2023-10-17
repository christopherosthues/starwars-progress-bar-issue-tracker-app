package com.christopherosthues.starwarsprogressbarissuetracker.issues.models

data class IssueDescription(
    var description: String?,
    var priority: Int,
    var engineColor: String?,
    var translations: Collection<Translation> = listOf(),
)
