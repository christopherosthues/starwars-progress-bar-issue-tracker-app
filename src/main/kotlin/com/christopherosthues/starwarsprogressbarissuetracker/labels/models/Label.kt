package com.christopherosthues.starwarsprogressbarissuetracker.labels.models

data class Label(
    var projectId: String,
    var id: String,
    var title: String,
    var description: String?,
    var color: String,
    var textColor: String,
)
