package com.example.pizzaservicegui.pizzaDelivery;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple and straight-forward implementation of the Pizza interface.
 */
public class SimplePizza  {



  private List<Topping> toppings;
  private PizzaSize size;
  private int price;
  private static int PIZZA_ID=0;
  private int id;

  private int toppingCount;

  /*public SimplePizza(List<Topping> list, PizzaSize size, int price) {
    this.list = list;
    this.size = size;
    this.price = price;
    id=PIZZA_ID++;
  }
*/

  public SimplePizza() {
    id=PIZZA_ID++;
    this.toppingCount = 0;
    price = 0;
    toppings = new ArrayList<>();
  }

  public int getPizzaId() {
    return this.id;
  }


  public List<Topping> getToppings() {
    return this.toppings;
  }


  public PizzaSize getSize() {
    return this.size;
  }
  public static Map<PizzaSize, Integer> getPizzaSizePriceList() {
    Map<PizzaSize, Integer> map = new HashMap<>();
    map.put(PizzaSize.SMALL, 500);
    map.put(PizzaSize.MEDIUM, 700);
    map.put(PizzaSize.LARGE, 900);
    map.put(PizzaSize.EXTRA_LARGE, 1100);
    return map;
  }


  public static Map<Topping, Integer> getToppingsPriceList() {
    Map<Topping, Integer> map = new HashMap<>();
    map.put(Topping.TOMATO,  30);
    map.put(Topping.CHEESE,  60);
    map.put(Topping.SALAMI,  50);
    map.put(Topping.HAM,  70);
    map.put(Topping.PINEAPPLE,  70);
    map.put(Topping.VEGETABLES,  20);
    map.put(Topping.SEAFOOD,  150);

    return map;
  }

  public int getPrice() {
    this.price = 0;
    for(Topping topping: toppings) {
      price += getToppingsPriceList().get(topping);
    }
    price += getPizzaSizePriceList().get(size);

    return this.price;
  }

  public List<Topping> getList() {
    return toppings;
  }

  public void setList(List<Topping> list) {
    this.toppings = list;
  }

  public void setSize(PizzaSize size) {
    this.size = size;
  }

  public void setPrice(int price) {
    this.price = price;
  }
  public void addTopping(Topping topping) throws TooManyToppingsException {
    if(toppingCount == 6) throw new TooManyToppingsException("Too many toppings. ");
    if(this.toppings.stream().anyMatch(topping1 -> topping1 == topping)) throw new TooManyToppingsException("Topping already exists");
    this.toppings.add(topping);
    toppingCount++;
  }
}
