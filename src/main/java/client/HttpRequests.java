package client;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class HttpRequests {

    private OkHttpClient client = new OkHttpClient();
    private String url;
    public String responseString;

    HttpRequests(String url){
        this.url = url;
    }

    void sendPost(Integer numero, Boolean ehPrimo) throws InterruptedException {
        RequestBody requestBody = new FormBody.Builder()
                .add(numero.toString(), ehPrimo.toString())
                .build();
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
    }

    void sendGet(){
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                countDownLatch.countDown();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                responseString = response.body().string();
                System.out.println(responseString);
                countDownLatch.countDown();
            }
        });
    }
}

//    RequestBody requestBody = new FormBody.Builder()
//            .add("chave", valor)
//            .build();
//    final Request request = new Request.Builder()
//            .url(loginUrl)
//            .post(requestBody)
//            .build();
