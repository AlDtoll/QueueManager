package com.example.queuemanager.presenter;

import com.example.queuemanager.database.DataBase;
import com.example.queuemanager.model.Post;
import com.example.queuemanager.model.TaskQueue;
import com.example.queuemanager.task.AddPostTask;
import com.example.queuemanager.task.LoadPostsTask;
import com.example.queuemanager.task.TaskForQueue;
import com.example.queuemanager.task.UpdatePostTask;
import com.example.queuemanager.view.MainActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Presenter {

    /**
     * Сколько секунд ждать перед следующей попыткой
     */
    private final static int TIMEOUT = 7;

    private MainActivity activity;
    private TaskQueue taskQueue = new TaskQueue();

    public Presenter(MainActivity mainActivity) {
        this.activity = mainActivity;
    }

    public void buttonWasClicked() {
        fillQueue();
        executeTasks();
    }

    private void fillQueue() {
        taskQueue.addTask(new AddPostTask(this));
        taskQueue.addTask(new LoadPostsTask(this));
        taskQueue.addTask(new UpdatePostTask(this));
        taskQueue.addTask(new AddPostTask(this));
        taskQueue.addTask(new LoadPostsTask(this));
    }

    private void executeTasks() {
        taskQueue.executeNext();
    }

    public void savePosts(List<Post> posts) {
        for (Post post : posts) {
            DataBase.makePost(activity, post.getUserId(), post.getId(), post.getTitle(), post.getBody());
        }
    }

    public void onPostExecute() {
        taskQueue.executeNext();
    }

    /**
     * Если такса завершилась неудачно, то ждем, а потом пробуем снова
     * И снова
     * И снова
     *
     * @param task задача, которую нужно повторить
     */
    //todo добавить счетчик попыток
    public void onTaskError(TaskForQueue task) {
        task.cancel(true);
        try {
            TimeUnit.SECONDS.sleep(TIMEOUT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        taskQueue.addTaskAtFrontOfQueue(task);
    }
}
