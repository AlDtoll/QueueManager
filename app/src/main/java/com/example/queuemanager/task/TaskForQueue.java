package com.example.queuemanager.task;

import android.os.AsyncTask;

import com.example.queuemanager.common.PriorityEnum;
import com.example.queuemanager.network.MyRetrofit;
import com.example.queuemanager.network.PostsApi;
import com.example.queuemanager.presenter.Presenter;

/**
 * Базовая задача для очереди задач
 * Конкретная задача должна указывать свой приоритет и может ли быть не единственной
 */
public abstract class TaskForQueue extends AsyncTask<String, Void, String> {

    protected Presenter presenter;
    private boolean isDuplicated;
    private PriorityEnum priority;
    protected PostsApi postsApi = MyRetrofit.postsApi;

    public TaskForQueue(Presenter presenter, boolean isDuplicated, PriorityEnum priority) {
        this.presenter = presenter;
        this.isDuplicated = isDuplicated;
        this.priority = priority;
    }

    public boolean isDuplicated() {
        return isDuplicated;
    }

    public PriorityEnum getPriority() {
        return priority;
    }


    @Override
    protected void onPostExecute(String s) {
        presenter.onPostExecute();
        super.onPostExecute(s);
    }

    public Presenter getPresenter() {
        return presenter;
    }
}
