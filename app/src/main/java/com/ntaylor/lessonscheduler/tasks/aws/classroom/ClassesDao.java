package com.ntaylor.lessonscheduler.tasks.aws.classroom;

import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.tasks.aws.util.Parser;
import com.ntaylor.lessonscheduler.tasks.aws.util.RestClient;
import com.ntaylor.lessonscheduler.util.Constants;

import java.util.List;

public class ClassesDao {

    private static final String CLASSES_BY_ID = "/class/byId/";
    private static final String CLASSES_BY_ORG = "/class/byOrg";

    public List<Classroom> getClassesById(String classId){
        String uri = Constants.SERVER_HOST + CLASSES_BY_ID + classId;

        return Parser.parseClasses(RestClient.getGetResponse(uri));
    }

    public List<Classroom> getClassesByOrg(String orgId){
        String uri = Constants.SERVER_HOST + CLASSES_BY_ORG + orgId;

        return Parser.parseClasses(RestClient.getGetResponse(uri));
    }

}
