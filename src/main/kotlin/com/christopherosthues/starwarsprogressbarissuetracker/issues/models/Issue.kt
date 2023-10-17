package com.christopherosthues.starwarsprogressbarissuetracker.issues.models

import com.christopherosthues.starwarsprogressbarissuetracker.labels.models.Label
import com.christopherosthues.starwarsprogressbarissuetracker.milestones.models.Milestone
import com.christopherosthues.starwarsprogressbarissuetracker.releases.models.Release

data class Issue(
    var iid: String,
    var id: String,
    var projectId: String,
    var issueDescription: IssueDescription,
    var webUrl: String,
    var state: IssueState,
    var labels: Collection<Label> = listOf(),
    var milestone: Milestone?,
    var release: Release?,
)
