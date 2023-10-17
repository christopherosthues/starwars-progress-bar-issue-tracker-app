package com.christopherosthues.starwarsprogressbarissuetracker.milestones.models

import com.christopherosthues.starwarsprogressbarissuetracker.issues.models.Issue

data class Milestone(
    var projectId: String,
    var id: String,
    var iid: String,
    var title: String,
    var description: String?,
    var milestoneState: MilestoneState,
    var totalIssuesCount: Int,
    var closedIssuesCount: Int,
    var openIssues: Collection<Issue> = listOf(),
    var closedIssues: Collection<Issue> = listOf(),
) {
    public val percentageCompleted: Float
        get() = if (totalIssuesCount == 0) 1f else closedIssuesCount.toFloat() / totalIssuesCount

    public val completed: Int
        get() = (percentageCompleted * 100).toInt()
}
