package com.example.transportivo.globals;

public class CommentCallableFunctions implements FirebaseCallable {
    @Override
    public String getAll() {
        return "getComments";
    }

    @Override
    public String getOne() {
        return "getComment";
    }

    @Override
    public String create() {
        return "createComment";
    }

    @Override
    public String update() {
        return "updateComment";
    }
}
