package top.jplayer.networklibrary.net.interceptor;


import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import top.jplayer.networklibrary.NetworkApplication;

/**
 * Created by Obl on 2018/3/13.
 * top.jplayer.baseprolibrary.net.interceptor
 * 设置公共头信息 例如 token,cookie
 */

public class CommentHearderInterceptor implements Interceptor {
    @Override
    public Response intercept(@Nullable Chain chain) throws IOException {
        assert chain != null;
        Request request = chain.request();
        Request.Builder newBuilder = request.newBuilder();
        Request build = newBuilder
                .headers(Headers.of(NetworkApplication.mHeaderMap))
                .method(request.method(), request.body())
                .build();
        return chain.proceed(build);
    }
}
