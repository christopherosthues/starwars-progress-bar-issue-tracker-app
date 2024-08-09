package milestones

import RepositoryBase
import com.christopherosthues.starwarsprogressbarissuetracker.graphql.MilestonesQuery

class MilestoneRepository : RepositoryBase() {
    suspend fun getMilestones(): List<MilestonesQuery.Milestone> {
        val milestonesResponse = graphQLClient.query(MilestonesQuery()).execute()
        return milestonesResponse.dataAssertNoErrors.milestones
    }
}