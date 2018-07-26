package com.ntaylor.lessonscheduler.assign;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.AssignPresenter;
import com.ntaylor.lessonscheduler.room.entities.User;

import java.util.List;
import java.util.Locale;

public class AssignAdapter extends RecyclerView.Adapter<AssignAdapter.ViewHolder> {

    private static final String subtext = "Last taught: %1$s, Next assignment: %2$s";
    private static final String neverTaught = "";
    private static final String noUpcomingAssignment = "";

    private List<User> users;
    private AssignPresenter presenter;

    public AssignAdapter(AssignPresenter presenter, List<User> users){
        this.presenter = presenter;
        this.users = users;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // get the views
        View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        TextView header = (TextView)userView.findViewById(R.id.user_name_label);
        TextView subtext = (TextView)userView.findViewById(R.id.user_subtext);

        // create the view holder
        return new ViewHolder(userView, header, subtext);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override onBindViewHolder instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        String lastTaught = user.getLastClass();
        lastTaught = (lastTaught == null) ? neverTaught : lastTaught;

        String nextAssignment = user.getNextClass();
        nextAssignment = (nextAssignment == null) ? noUpcomingAssignment : nextAssignment;

        // Set the header and subtext for the user
        holder.nameText.setText(user.getUserName());
        holder.subtext.setText(String.format(Locale.US, subtext, lastTaught, nextAssignment));

        final int pos = position;

        // set up the on-click listener
        holder.userView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onTeacherPressed(users.get(pos));
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View userView;
        public TextView nameText;
        public TextView subtext;
        public ViewHolder(View userView, TextView nameText, TextView subtext){
            super(userView);
            this.userView = userView;
            this.nameText = nameText;
            this.subtext = subtext;
        }

    }


}
