package com.example.queuemanager.task;

import android.util.Log;

import com.example.queuemanager.common.ConstantEnum;
import com.example.queuemanager.common.PriorityEnum;
import com.example.queuemanager.model.Post;
import com.example.queuemanager.presenter.Presenter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LoadPostsTask extends TaskForQueue {

    public LoadPostsTask(Presenter presenter) {
        super(presenter, false, PriorityEnum.HIGH);
    }

    @Override
    protected String doInBackground(String... strings) {
        Call<List<Post>> posts = postsApi.getPosts();
        try {
            Response<List<Post>> response = posts.execute();
            savePosts(response);
        } catch (IOException e) {
            e.printStackTrace();
            presenter.onTaskError(this);
        }
        return "success";
    }

    private void savePosts(Response<List<Post>> response) {
        if (response.isSuccessful()) {
            List<Post> posts = response.body();
            presenter.savePosts(posts);
            Log.d(ConstantEnum.TAG.getCode(), "savePosts");
        }
    }
}
