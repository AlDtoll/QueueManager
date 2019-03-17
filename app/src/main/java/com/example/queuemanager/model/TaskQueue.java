package com.example.queuemanager.model;

import com.example.queuemanager.presenter.Presenter;
import com.example.queuemanager.task.TaskForQueue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Очередь заданий
 * содержит в себе список задач для очереди {@link com.example.queuemanager.task.TaskForQueue}
 */
public class TaskQueue {

    private List<TaskForQueue> taskForQueues = new ArrayList<>();
    private int high = 0;
    private int normal = 0;
    private int low = 0;

    /**
     * Если при выполнении задачи произошла ошибка, то ставим в начало очереди новую аналогичную (потому что кто-то использует AsynkTask), чтобы запустить заново
     * и так до судного дня
     *
     * @param task поломанное сообщение
     */
    public void addTaskAtFrontOfQueue(TaskForQueue task) {
        //todo надо убрать этого монстра
        Class<? extends TaskForQueue> taskClass = task.getClass();
        try {
            Constructor<?> constructor = taskClass.getConstructor(Presenter.class);
            Object newInstance = constructor.newInstance(task.getPresenter());
            high = addTask(0, 0, (TaskForQueue) newInstance);
            executeNext();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * По приоритету задачи добавляет ее в нужное место очереди
     * @param task задча, которую нужно добавить
     */
    public void addTask(TaskForQueue task) {
        switch (task.getPriority()) {
            case HIGH:
                high = addTask(0, high, task);
                break;
            case NORMAL:
                normal = addTask(high, normal, task);
                break;
            case LOW:
                low = addTask(high + normal, low, task);
                break;
            default:
                throw new IllegalArgumentException("задача с таким приоритетом не может быть обработана");
        }
    }

    private int addTask(int shift, int i, TaskForQueue task) {
        if (!task.isDuplicated()) {
            //todo ограничить перебор приоритетом
            for (TaskForQueue taskForQueue : taskForQueues) {
                if (taskForQueue.getClass().getSimpleName().equals(task.getClass().getSimpleName())) {
                    taskForQueues.remove(taskForQueue);
                    i--;
                    break;
                }
            }

        }
        taskForQueues.add(shift + i, task);
        return ++i;
    }

    public void executeNext() {
        if (!taskForQueues.isEmpty()) {
            TaskForQueue task = taskForQueues.remove(0);
            moveCursorBack();
            task.execute();
        }
    }

    /**
     * Стягиваем курсор к началу очереди
     */
    private void moveCursorBack() {
        if (high != 0) {
            high--;
        } else if (normal != 0) {
            normal--;
        } else if (low != 0) {
            low--;
        }
    }
}
