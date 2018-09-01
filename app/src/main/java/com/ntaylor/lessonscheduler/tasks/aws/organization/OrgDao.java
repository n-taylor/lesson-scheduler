package com.ntaylor.lessonscheduler.tasks.aws.organization;

import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.tasks.aws.util.Parser;
import com.ntaylor.lessonscheduler.tasks.aws.util.RestClient;
import com.ntaylor.lessonscheduler.util.Constants;

public class OrgDao {

    private static final String GET_ORG_BY_ID = "/org/byId/";
    private static final String GET_ORG_BY_NAME = "/org/byName/";

    public Organization getOrgById(String orgId){
        String uri = Constants.SERVER_HOST + GET_ORG_BY_ID + orgId;

        return Parser.parseOrg(RestClient.getGetResponse(uri));
    }

    public Organization getOrgByName(String orgName){
        String uri = Constants.SERVER_HOST + GET_ORG_BY_NAME + orgName.replace(" ", "%20");

        String response = RestClient.getGetResponse(uri);

        return Parser.parseOrg(response);
    }

}
