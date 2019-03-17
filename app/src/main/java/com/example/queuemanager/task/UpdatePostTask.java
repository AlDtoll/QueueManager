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

public class UpdatePostTask extends TaskForQueue {

    public UpdatePostTask(Presenter presenter) {
        super(presenter, true, PriorityEnum.LOW);
    }

    @Override
    protected String doInBackground(String... strings) {
        RequestBody text = RequestBody.create(
                MediaType.parse("application/json"),
                "{\n" +
                        "    \"title\": \"Iovi\",\n" +
                        "    \"body\": \"Quod licet bovi, non licet Iovi\",\n" +
                        "    \"userId\": \"1\"\n" +
                        "}");
        Call<Post> post = postsApi.refreshPost("application/json", 10, text);
        try {
            Response<Post> response = post.execute();
            updatePost(response);
        } catch (IOException e) {
            e.printStackTrace();
            presenter.onTaskError(this);
        }
        return "success";
    }

    private void updatePost(Response<Post> response) {
        if (response.isSuccessful()) {
            Post post = response.body();
            Log.d(ConstantEnum.TAG.getCode(), "updatePost " + post);
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
