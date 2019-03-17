package com.example.queuemanager.task;

import android.util.Log;

import com.example.queuemanager.common.ConstantEnum;
import com.example.queuemanager.common.PriorityEnum;
import com.example.queuemanager.model.Post;
import com.example.queuemanager.presenter.Presenter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

public class AddPostTask extends TaskForQueue {

    public AddPostTask(Presenter presenter) {
        super(presenter, true, PriorityEnum.NORMAL);
    }

    @Override
    protected String doInBackground(String... strings) {
        RequestBody text = RequestBody.create(
                MediaType.parse("application/json"),
                "{\n" +
                        "    \"title\": \"Publius Terentius Afer\",\n" +
                        "    \"body\": \"Quod licet Iovi, non licet bovi\",\n" +
                        "    \"userId\": 1\n" +
                        "}");
        Call<Post> post = postsApi.addPost("application/json", text);
        try {
            Response<Post> response = post.execute();
            addPost(response);
        } catch (IOException e) {
            e.printStackTrace();
            presenter.onTaskError(this);
        }
        return "success";
    }

    private void addPost(Response<Post> response) {
        if (response.isSuccessful()) {
            Post post = response.body();
            Log.d(ConstantEnum.TAG.getCode(), "addPost " + post);
        }
    }
}
