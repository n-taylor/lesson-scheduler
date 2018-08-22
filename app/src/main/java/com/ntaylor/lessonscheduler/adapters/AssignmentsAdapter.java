package com.ntaylor.lessonscheduler.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.AssignmentsPresenter;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.HashMap;
import java.util.List;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.ViewHolder> {

    private static final String null_teacher = "Hmm. Not sure who the teacher is.";
    private static final String null_class = "Strange. Can't tell which class this is for.";

    private List<Assignment> assignments;
    private HashMap<String, String> teachers;
    private HashMap<String, String> classes;
    private AssignmentsPresenter presenter;

    public AssignmentsAdapter(@NonNull List<Assignment> assignments, @NonNull HashMap<String, String> classes,
                              @NonNull HashMap<String, String> teachers, AssignmentsPresenter presenter){
        this.assignments = assignments;
        this.teachers = teachers;
        this.classes = classes;
        this.presenter = presenter;
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
     * onBindViewHolder(). Since it will be re-used to display
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
        // Get the views
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_item_layout, parent, false);
        ImageView image = (ImageView)view.findViewById(R.id.assignment_image);
        TextView topText = (TextView)view.findViewById(R.id.assignment_top_label);
        TextView middleText = (TextView)view.findViewById(R.id.assignment_middle_label);
        TextView bottomText = (TextView)view.findViewById(R.id.assignment_bottom_label);
        ImageButton delete = (ImageButton)view.findViewById(R.id.assignment_delete_button);

        // Use the views to create a ViewHolder
        return new ViewHolder(view, image, topText, middleText, bottomText, delete);
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
        final Assignment assignment = assignments.get(position);

        // Set the top text to the date
        holder.topText.setText(SimpleDate.deserializeDate(assignment.getDate(), true).toString());

        // Middle text is the name of the teacher
        String teacher = teachers.get(assignment.getTeacherId());
        String middle = (teacher == null) ? null_teacher : teacher;
        holder.middleText.setText(middle);

        // Bottom text is the class name
        String classroom = classes.get(assignment.getClassId());
        String bottom = (classroom == null) ? null_class : classroom;
        holder.bottomText.setText(bottom);

        // Set up onclick listener
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onDeletePressed(assignment);
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
        return assignments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View assignmentView;
        public ImageView image;
        public TextView topText;
        public TextView middleText;
        public TextView bottomText;
        public ImageButton deleteButton;

        public ViewHolder(View assignmentView, ImageView image, TextView topText, TextView middleText,
                          TextView bottomText, ImageButton deleteButton) {
            super(assignmentView);
            this.image = image;
            this.topText = topText;
            this.middleText = middleText;
            this.bottomText = bottomText;
            this.deleteButton = deleteButton;
        }
    }
}
