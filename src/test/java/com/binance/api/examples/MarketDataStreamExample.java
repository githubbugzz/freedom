package com.binance.api.examples;

import com.binance.api.client.BinanceApiCallback;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.event.AggTradeEvent;
import com.binance.api.client.domain.market.CandlestickInterval;
import com.binance.api.client.exception.BinanceApiException;
import com.binance.api.domain.utils.ConcurrentHashMapCacheUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Market data stream endpoints examples.
 *
 * It illustrates how to create a stream to obtain updates on market data such as executed trades.
 */
public class MarketDataStreamExample {

  public static void main(String[] args) throws InterruptedException, IOException {
    BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();

    // Listen for aggregated trade events for ETH/BTC
    client.onAggTradeEvent("btcusdt", new BinanceApiCallback<AggTradeEvent>() {
        @Override
        public void onResponse(AggTradeEvent response){
//            AggTradeEvent event = mapper.readValue(response.toString(), AggTradeEvent.class);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            //将信息存储在缓存中，在缓存中添加监听，然后看是否能触发合约交易。
            ConcurrentHashMapCacheUtils.setCache(String.valueOf(response.getEventTime()),response,10*1000);

            /*System.out.print(dateFormat.format(new Date(response.getEventTime()))+":");
            System.out.println(response);*/
        }
    });

    // Listen for changes in the order book in ETH/BTC
//    client.onDepthEvent("ethbtc", response -> System.out.println(response));

    // Obtain 1m candlesticks in real-time for ETH/BTC
//    client.onCandlestickEvent("ethbtc", CandlestickInterval.ONE_MINUTE, response -> System.out.println(response));
  }
}
