package com.christopherosthues.starwarsprogressbarissuetracker.issues.models

enum class IssueState(i: Int) {
    Unknown(0),
    Opened(1),
    Closed(2),
    Locked(3),
    All(4),
}
