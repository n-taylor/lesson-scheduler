package com.ntaylor.lessonscheduler.presenters;

import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class AssignmentsPresenter extends Presenter {

    private AssignmentsView activity;
    private DataProvider provider;

    public AssignmentsPresenter(AssignmentsView activity){
        this.activity = activity;
        this.provider = DataProviderFactory.getDataProviderInstance();
        provider.addObserver(this);
    }

    public interface AssignmentsView {

        void destroySelf();
    }
}
