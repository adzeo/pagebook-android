package com.example.pagebook.ui.comments.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pagebook.R;
import com.example.pagebook.models.CommentDTO;
import com.example.pagebook.ui.comments.CommentsActivity;

import java.util.List;

public class CommentsRecyclerViewAdapter extends RecyclerView.Adapter<CommentsRecyclerViewAdapter.ViewHolder> {

    private final List<CommentDTO> mCommentsDataList;
    private final CommentsActivity commentsActivity;

    public CommentsRecyclerViewAdapter(List<CommentDTO> commentsDataList, CommentsActivity commentsActivity) {
        this.mCommentsDataList = commentsDataList;
        this.commentsActivity = commentsActivity;
    }

    @NonNull
    @Override
    public CommentsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        return new CommentsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsRecyclerViewAdapter.ViewHolder holder, int position) {
        CommentDTO commentDTO = mCommentsDataList.get(position);

        // set user name, image and time for the post
        holder.tvCommentUserName.setText(commentDTO.getUserName());
        holder.tvComment.setText(commentDTO.getComment().getText());
        holder.tvCommentReply.setOnClickListener(v -> {
            Intent intent = new Intent(commentsActivity, CommentsActivity.class);
            intent.putExtra("postId", commentDTO.getComment().getPostId());
            intent.putExtra("parentCommentId", commentDTO.getComment().getCommentId());
            holder.rootView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mCommentsDataList == null ? 0 : mCommentsDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCommentUserName;
        private final TextView tvComment;
        private final TextView tvCommentReply;
        private final View rootView;

        public ViewHolder(View view) {
            super(view);
            rootView = view;
            tvCommentUserName = view.findViewById(R.id.tv_comment_user_name);
            tvComment = view.findViewById(R.id.tv_comment_text);
            tvCommentReply = view.findViewById(R.id.tv_comment_reply);
        }
    }
}