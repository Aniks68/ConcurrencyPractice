package com.company.HTTPRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    private static HttpURLConnection connection;

    public static void main(String[] args) throws IOException {
        // Method 1: java.net.HttpURLConnection

//        BufferedReader reader;
//        String line;
//        StringBuffer responseContent = new StringBuffer();
////        HttpURLConnection connection = null;
//        try {
//            URL url = new URL("https://jsonplaceholder.typicode.com/albums");
//            connection = (HttpURLConnection) url.openConnection();
//
//            // Request setup
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(5000);
//            connection.setReadTimeout(5000);
//
//            int status = connection.getResponseCode();
//            System.out.println(status);
//
//            if(status > 299) {
//                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
//            } else {
//                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//            }
//            while((line = reader.readLine()) != null) {
//                responseContent.append(line);
//            }
//            reader.close();
////            System.out.println(responseContent.toString());
//            parse(responseContent.toString());
//        } finally {
//            connection.disconnect();
//        }

        // Method 2: java.net.http.HttpClient
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://jsonplaceholder.typicode.com/albums")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(Main::parse)
                .join();
    }

    public static String parse(String responseBody) {
        JSONArray albums = new JSONArray(responseBody);
        for(int i = 0; i < albums.length(); i++) {
            JSONObject album = albums.getJSONObject(i);
            int id = album.getInt("id");
            int userId = album.getInt("userId");
            String title = album.getString("title");
            System.out.println(id + " " + title + " " + userId);
        }
        return null;
    }
}
