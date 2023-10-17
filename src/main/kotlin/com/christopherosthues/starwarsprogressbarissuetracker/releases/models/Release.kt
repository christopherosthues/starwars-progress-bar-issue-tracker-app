package com.christopherosthues.starwarsprogressbarissuetracker.releases.models

import java.util.Date

data class Release(
    var iid: String,
    var id: String,
    var issueLinkId: Int,
    var projectId: String,
    var title: String,
    var releaseNotes: String?,
    var state: ReleaseState,
    var releaseDate: Date?,
)
