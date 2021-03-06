package org.knowm.xchange.cryptofacilities.service;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancel;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenPositions;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesTradeServiceRaw extends CryptoFacilitiesBaseService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CryptoFacilitiesOrder sendCryptoFacilitiesLimitOrder(LimitOrder order) throws IOException {
    String orderType = "lmt";
    String symbol = order.getCurrencyPair().base.toString();
    String side = "buy";
    if (order.getType().equals(OrderType.ASK)) {
      side = "sell";
    }
    BigDecimal size = order.getOriginalAmount();
    BigDecimal limitPrice = order.getLimitPrice();

    CryptoFacilitiesOrder ord = cryptoFacilities
        .sendOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), orderType, symbol, side, size,
            limitPrice);

    if (ord.isSuccess()) {
      return ord;
    } else {
      throw new ExchangeException("Error sending CF limit order: " + ord.getError());
    }
  }

  public CryptoFacilitiesCancel cancelCryptoFacilitiesOrder(String uid) throws IOException {
    CryptoFacilitiesCancel res = cryptoFacilities
        .cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory(), uid);

    if (res.isSuccess()) {
      return res;
    } else {
      throw new ExchangeException("Error cancelling CF order: " + res.getError());
    }
  }

  public CryptoFacilitiesOpenOrders getCryptoFacilitiesOpenOrders() throws IOException {
    CryptoFacilitiesOpenOrders openOrders = cryptoFacilities
        .openOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());

    if (openOrders.isSuccess()) {
      return openOrders;
    } else {
      throw new ExchangeException("Error getting CF open orders: " + openOrders.getError());
    }
  }

  public CryptoFacilitiesFills getCryptoFacilitiesFills() throws IOException {
    CryptoFacilitiesFills fills = cryptoFacilities
        .fills(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());

    if (fills.isSuccess()) {
      return fills;
    } else {
      throw new ExchangeException("Error getting CF fills: " + fills.getError());
    }
  }

  public CryptoFacilitiesOpenPositions getCryptoFacilitiesOpenPositions() throws IOException {
    CryptoFacilitiesOpenPositions openPositions = cryptoFacilities
        .openPositions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());

    if (openPositions.isSuccess()) {
      return openPositions;
    } else {
      throw new ExchangeException("Error getting CF open positions: " + openPositions.getError());
    }
  }
}
