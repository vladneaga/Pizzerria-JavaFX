package com.example.pizzaservicegui.pizzaDelivery;


import java.util.ArrayList;
import java.util.List;

/**
 * Simple and straight-forward implementation of the Order interface.
 */
public class SimpleOrder  {


  private List<SimplePizza> pizzaList;
  private int id;
  private static int ORDER_ID=0;
  private int value;

  public SimpleOrder() {
    this.pizzaList = new ArrayList<>();
    this.id = ORDER_ID++;
  }


  public int getOrderId() {
    return this.id;
  }


  public List<SimplePizza> getPizzaList() {
    return this.pizzaList;
  }


  public int getValue() {
    value = 0;
    this.value  = this.pizzaList.stream().mapToInt(SimplePizza::getPrice).sum();

    return this.value;
  }

  public void setPizzaList(List<SimplePizza> pizzaList) {
    this.pizzaList = pizzaList;
  }

  public void setValue(int value) {
    this.value = value;
  }
  public void addPizza(SimplePizza pizza){
    pizzaList.add(pizza);
  }
}
