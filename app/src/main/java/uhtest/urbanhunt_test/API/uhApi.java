package uhtest.urbanhunt_test.API;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;
import uhtest.urbanhunt_test.Dummy;

/**
 * Created by Jinesh on 14/03/16.
 */

/* Interface API to make network calls*/

public interface uhApi {
    @POST("/auth/convert-token")
    public void createFBUser(@Query("grant_type") String grant_type ,@Query("client_id") String client_id,
                             @Query("client_secret") String client_secret,@Query("backend") String backend,
                             @Query("token") String token , Callback<Dummy> response);
}
