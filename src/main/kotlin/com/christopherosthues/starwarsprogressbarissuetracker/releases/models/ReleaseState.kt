package com.christopherosthues.starwarsprogressbarissuetracker.releases.models

enum class ReleaseState(i: Int) {
    Unknown(0),
    Opened(1),
    Closed(2),
    Locked(3),
    All(4),
}
