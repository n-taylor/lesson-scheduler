package com.ntaylor.lessonscheduler.util;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;

import java.util.List;

/**
 * Implementing this interface allows a presenter to be informed when updates to data are available
 */
public interface DataObserver {

    /**
     * This will be called when the list of assignments has been modified.
     * @param assignments The class-teacher assignments.
     */
    void onAssignmentsUpdated(List<Assignment> assignments);

    /**
     * this will be called when the list of classes for the organization has been modified.
     * @param classes The list of classes.
     */
    void onClassesUpdated(List<Classroom> classes);

}
