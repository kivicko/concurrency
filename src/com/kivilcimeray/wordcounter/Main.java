package com.kivilcimeray.wordcounter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Main {
    private static int THREAD_COUNT = 1;

    public static void main(String[] args) throws IOException {
        String book = new String(Files.readAllBytes(Paths.get("./resources/throughput/war_and_peace.txt")));

        createServer(book);
    }

    private static void createServer(String book) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/search", new WordCountHandler(book));
        Executor executor = Executors.newFixedThreadPool(THREAD_COUNT);

        server.setExecutor(executor);
        server.start();
    }

    private static class WordCountHandler implements HttpHandler {
        private String book;

        public WordCountHandler(String book) {
            this.book = book;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String query = httpExchange.getRequestURI().getQuery();
            String[] split = query.split("=");
            String action = split[0];
            String word = split[1];

            if (!action.equals("word")) {
                httpExchange.sendResponseHeaders(400, 0);
                return;
            }

            long count = countWord(word);

            byte[] response = Long.toString(count).getBytes();
            httpExchange.sendResponseHeaders(200, response.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response);
            outputStream.close();
        }

        private long countWord(String word) {
            long count = 0;
            int index = 0;

            while (index >= 0) {
                index = book.indexOf(word, index);
                if(index >= 0) {
                    count++;
                    index++;
                }
            }
            return count;

        }
    }
}
