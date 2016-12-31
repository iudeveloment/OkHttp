package com.kitkat.android.okhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** OkHttp
 *  http://square.github.io/okhttp/
 *
 *  An HTTP & HTTP/2 client for Android and Java applications
 *  HttpURLConnection Support Library
 *  (Android 2.3 이상. Java 1.7 이상)
 *
 *  HTTP Protocol 은 최신 어플리케이션 네트워크.
 *  웹 클라이언트와 웹 서버 사이의 HTML WebPage, 데이터와 미디어를 교환하는 규약, 방법
 *  HTTP 를 효율적으로 사용하면 데이터을 더 빨리 로드하고 대역폭을 절약 할 수 있다.
 *
 *  OkHttp Client
 *      - OkHttp 는 기본적으로 효율적인 HTTP 클라이언트
 *      - HTTP / 2 지원을 통해 동일 호스트에 대한 모든 요청이 소켓을 공유 할 수 있다.
 *      - 연결 풀링은 요청 대기 시간을 줄인다 (HTTP / 2를 사용할 수없는 경우)
 *      - Transparent GZIP 으로 다운로드 크기를 줄인다.
 *      - 응답 캐싱으로 반복 요청에 대해 네트워크를 완전히 피할 수 있다.
 *      - OkHttp를 사용하는 것은 쉽다.
 *        Request / Response API 는 동기식 블로킹 호출과 콜백이 있는 비동기 호출을 모두 지원
 *
 *  OkHttp perseveres when the network is troublesome: it will silently recover from common connection problems.
 *  If your service has multiple IP addresses OkHttp will attempt alternate addresses if the first connect fails.
 *  This is necessary for IPv4+IPv6 and for services hosted in redundant data centers.
 *  OkHttp initiates new connections with modern TLS features (SNI, ALPN), and falls back to TLS 1.0 if the handshake fails.
 *
 *  Dependency for Gradle
 *      compile 'com.squareup.okhttp3:okhttp:3.6.0'
 *
 *  NetworkOnMainThreadException
 *      OkHttp Client 는 Sub Thread 지원 X
 *      반드시 Sub Thread 생성 후 작업 요구
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                try {
                    String content = getString("http://192.168.204.1:8080/dbconnect.jsp");
                    Log.i("OkHttp", content);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();
    }

    // GET A URL
    // This program downloads a URL and print its contents as a string
    public String getString(String url) throws IOException{
        // 1. OkHttpClient Instantiate
        OkHttpClient okHttpClient = new OkHttpClient();

        // 2. Request Instantiate use Request.Builder
        // 웹 클라이언트가 웹 서버에게 요청하면 웹 클라언트는 Request 정보를(String) 자동으로 만든다.
        // 웹 서버는 Request Information 을 포함한 Request 객체를 내부에 생성
        // (Request 객체 : Header, 요청주소..)
        // 웹 서버가 응답 시 만드는 Response 정보를(String) 웹 클라이언트가 Response 객체로 생성
        Request request = new Request.Builder()
                .url(url)
                .build();

        // 3.client.newCall().execute()
        // 이 시점에서 웹 클라이언트와 웹 서버 간 연결 후 웹 서버에서 Response 정보를 전달 받아 Response 객체 생성
        Response response = okHttpClient.newCall(request).execute();

        // 4. 응답받은 Response 객체에서 컨텐츠 내용을 String 으로 반환
        return response.body().string();
    }


    // POST TO A SERVER
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    String post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
