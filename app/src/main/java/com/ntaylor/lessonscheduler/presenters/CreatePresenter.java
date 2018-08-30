package com.ntaylor.lessonscheduler.presenters;

public class CreatePresenter extends Presenter {

    private CreateView activity;
    private boolean create;

    public CreatePresenter(CreateView activity, boolean create){
        this.activity = activity;
        this.create = create;

        // If the user is creating an organization, don't show the code field
        if (create){
            activity.hideCodeField();
        }
    }

    // public methods ======================================================================

    /**
     * Confirms that the user wants to join the organization
     * @param orgName
     * @param orgCode
     */
    public void onJoinCreatePressed(String orgName, String orgCode){
        if (create){

        }
    }

    /**
     * Determines if the name typed exists already
     * @param orgName The name for which to check availability
     */
    public void onCheckAvailablePressed(String orgName){

    }

    // private methods =====================================================================



    // Overrides ===========================================================================

    /**
     * Unhooks self from the list of data observers and ends the activity.
     */
    @Override
    public void end() {
        activity.destroySelf();
    }

    // Interface ===========================================================================

    public interface CreateView {
        void destroySelf();

        /**
         * Hides the orgCode field from user view
         */
        void hideCodeField();
    }
}
