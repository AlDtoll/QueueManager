package com.example.queuemanager.network;

import com.example.queuemanager.model.Post;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Апи для взаимодействия с сервером
 */
public interface PostsApi {
    /**
     * @return список постов
     * [
     *     {
     *         "userId": 1,
     *         "id": 1,
     *         "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
     *         "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
     *     },
     *     {
     *         "userId": 1,
     *         "id": 2,
     *         "title": "qui est esse",
     *         "body": "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla"
     *     },
     *     {
     *         "userId": 1,
     *         "id": 3,
     *         "title": "ea molestias quasi exercitationem repellat qui ipsa sit aut",
     *         "body": "et iusto sed quo iure\nvoluptatem occaecati omnis eligendi aut ad\nvoluptatem doloribus vel accusantium quis pariatur\nmolestiae porro eius odio et labore et velit aut"
     *     }
     * ]
     */
    @GET("posts")
    Call<List<Post>> getPosts();

    /**
     * добавить пост
     * @param type тип
     * @param text тело поста
     * @return результат добавления
     *{
     *     "title": "Publius Terentius Afer",
     *     "body": "Quod licet Iovi, non licet bovi",
     *     "userId": 1,
     *     "id": 101
     * }
     */
    @POST("posts")
    Call<Post> addPost(@Header("Content-Type") String type, @Body RequestBody text);

    /**
     * @param type тип
     * @param id индетификатор поста, который нужно обновить
     * @param text тело поста
     * @return результат обновления
     * {
     *     "title": "Iovi",
     *     "body": "Quod licet bovi, non licet Iovi",
     *     "userId": "1",
     *     "id": 10
     * }
     */
    @PUT("posts/{id}")
    Call<Post> refreshPost(@Header("Content-Type") String type, @Path ("id") int id, @Body RequestBody text);

}
