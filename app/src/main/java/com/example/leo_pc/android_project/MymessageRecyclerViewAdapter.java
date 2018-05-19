package com.example.leo_pc.android_project;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leo_pc.android_project.messageFragment.OnListFragmentInteractionListener;
import com.example.leo_pc.android_project.dummy.DummyContent.DummyItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MymessageRecyclerViewAdapter extends RecyclerView.Adapter<MymessageRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private final List<Message> mValues;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private final DateFormat dateFormat = new SimpleDateFormat("hh:mm");

    public MymessageRecyclerViewAdapter(Context context, List<Message> items) {
        mValues = items;
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = (Message) mValues.get(position);
        /* here we should check if sender is equal to current user like: message.getSender().getNickname().equals(App.getCurrentUser().getUserId())
        *   TO DO
         */
        if (message.getSender().getNickname().equals(user.getEmail())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_message, parent, false);
            return new ViewHolder(view);
        }
        else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_message_received, parent, false);
            return new ViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mContentView.setText(mValues.get(position).getMessage());
        holder.mMessageTime.setText(dateFormat.format(mValues.get(position).getCreatedAt()));
        holder.mMessageSender.setText(mValues.get(position).getSender().getNickname());
    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView mMessageTime;
        public final TextView mMessageSender;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mContentView = (TextView) view.findViewById(R.id.text_message_body);
            mMessageTime = (TextView) view.findViewById(R.id.text_message_time);
            mMessageSender = (TextView) view.findViewById(R.id.name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
