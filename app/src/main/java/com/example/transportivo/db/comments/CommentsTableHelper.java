package com.example.transportivo.db.comments;

import com.example.transportivo.model.Comment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentsTableHelper {
    public static final String TABLE_NAME = "Comments";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Comment.Fields.author + " TEXT, "
            + Comment.Fields.userId + " TEXT, "
            + Comment.Fields.comment + " DATETIME);";
}
