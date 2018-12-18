package com.binance.api.client.impl;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.exception.BinanceApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Binance API WebSocket listener.
 */
public class BinanceApiWebSocketListener<T> extends WebSocketListener {

  private BinanceApiWebSocketClient client;

  private BinanceApiCallback<T> callback;

  private Class<T> eventClass;

  public BinanceApiWebSocketListener(BinanceApiCallback<T> callback, Class<T> eventClass,BinanceApiWebSocketClient client) {
    this.client = client;
    this.callback = callback;
    this.eventClass = eventClass;
  }

  public BinanceApiWebSocketListener(BinanceApiCallback<T> callback, Class<T> eventClass ) {
    this.callback = callback;
    this.eventClass = eventClass;
  }
  @Override
  public void onMessage(WebSocket webSocket, String text) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      T event = mapper.readValue(text, eventClass);
      callback.onResponse(event);
    } catch (IOException e) {
      throw new BinanceApiException(e);
    }
  }

  @Override
  public void onFailure(WebSocket webSocket, Throwable t, Response response) {
    throw new BinanceApiException(t);
  }
  /*   public void onOpen(WebSocket webSocket, Response response) {
    }

    public void onMessage(WebSocket webSocket, String text) {
    }

    public void onMessage(WebSocket webSocket, ByteString bytes) {
    }

    public void onClosing(WebSocket webSocket, int code, String reason) {
    }

    public void onClosed(WebSocket webSocket, int code, String reason) {
    }*/
  @Override
  public void onOpen(WebSocket webSocket, Response response) {
    Date date = new Date();
    System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date));
    System.out.println("正在打开...");
  }
  @Override
  public void onClosed(WebSocket webSocket, int code, String reason) {
    System.out.print("onClosed:");

    reconnection(client);
  }
  public void onClosing(WebSocket webSocket, int code, String reason) {
    System.out.print("onClosing:");
    reconnection(client);
  }

  public void reconnection(BinanceApiWebSocketClient client){
    Date date = new Date();
    System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date));
    this.client = client;
    client=BinanceApiClientFactory.newInstance().newWebSocketClient();
    System.out.println("正在重连...");
  }
}