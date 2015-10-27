package software.credible.commentslistapp;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class CommentsDataSource {

    private Context context;

    public CommentsDataSource(Context context) {
        this.context = context;
    }

    public Comment createComment(String commentString) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString());
        comment.setComment(commentString);
		try(Realm r = Realm.getInstance(context)) {
            r.beginTransaction();
            r.copyToRealm(comment);
            r.commitTransaction();
            comment = detachComment(comment);
        } catch (Throwable t) {
            throw new RuntimeException("Cannot save comment", t);
        }
        return comment;
	}

	public void deleteComment(Comment comment) {
        if(!TextUtils.isEmpty(comment.getId())) {
            try(Realm r = Realm.getInstance(context)) {
                r.beginTransaction();
                r.where(Comment.class)
                        .equalTo("id", comment.getId())
                        .findFirst()
                        .removeFromRealm();
                r.commitTransaction();
            }
        }
	}

    public void deleteAllComments() {
        try (Realm r = Realm.getInstance(context)) {
            r.beginTransaction();
            r.where(Comment.class).findAll().clear();
            r.commitTransaction();
        }
    }

	public Comment getCommentByValue(String commentText) {
        Comment result = null;
        try (Realm r = Realm.getInstance(context)) {
            result = detachComment(r.where(Comment.class).equalTo("comment", commentText).findFirst());
        }
        return result;
	}

	public List<Comment> getAllComments() {
        List<Comment> result = new ArrayList<>();
        try (Realm r = Realm.getInstance(context)) {
            for(Comment c : r.where(Comment.class).findAll()) {
                result.add(detachComment(c));
            }
        }
        return result;
	}

    private Comment detachComment(Comment comment) {
        if(comment == null) {
            return null;
        }

        Comment detatchedComment = new Comment(){
            @Override
            public String toString() {
                return getComment();
            }
        };
        detatchedComment.setId(comment.getId());
        detatchedComment.setComment(comment.getComment());
        return detatchedComment;
    }

}
