package com.example.pizzaservicegui.pizzaDelivery;

/**
 * Gets thrown when a ScreenController tries to make an unknown transition between two screens.
 */
public class UnknownTransitionException extends RuntimeException {



  private String from;
  private String to;

  /**
   * Creates an UnknownTransitionException.
   *
   * @param message message of the exception
   * @param from name of the screen where the transition should start
   * @param to name of the screen where the transition should end
   */
  public UnknownTransitionException(final String message, final String from, final String to) {
    super(message);
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }
}
