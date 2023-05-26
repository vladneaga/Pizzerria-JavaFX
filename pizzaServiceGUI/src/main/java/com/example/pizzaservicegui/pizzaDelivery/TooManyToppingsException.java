package com.example.pizzaservicegui.pizzaDelivery;

/**
 * Gets thrown when the pizza order has too many toppings.
 */
public class TooManyToppingsException extends Exception {

  public TooManyToppingsException() {
  }

  public TooManyToppingsException(final String message) {
    super(message);
  }

  public TooManyToppingsException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
