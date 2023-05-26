package com.example.pizzaservicegui.pizzaDelivery;


import java.util.*;

/**
 * Simple and straight-forward implementation of the PizzaDeliveryService interface.
 */
public class SimplePizzaDeliveryService  {

  private final List<SimpleOrder> orderList;

  public SimplePizzaDeliveryService() {
    this.orderList = new ArrayList<>();

  }


  public int createOrder() {
    SimpleOrder order = new SimpleOrder();
    orderList.add(order);
    return order.getOrderId();
  }


  public int addPizza(final int orderId, final PizzaSize size) throws IllegalArgumentException {
    if(size==null || orderList.stream().noneMatch(order -> order.getOrderId() == orderId))
    {
      throw new IllegalArgumentException("Arguement null or pizza with the given id already exists.");
    }
    SimplePizza pizza = new SimplePizza();
    pizza.setSize(size);
    Optional<SimpleOrder> answer  = orderList.stream().filter(order -> order.getOrderId() == orderId).findAny();
    if(answer.isEmpty()) throw new IllegalArgumentException("Arguement not found");
    answer.get().addPizza(pizza);
    return pizza.getPizzaId();
  }


  public void removePizza(final int orderId, final int pizzaId) throws IllegalArgumentException {
    if(orderList.stream().noneMatch(order -> order.getOrderId() == orderId))
    {
      throw new IllegalArgumentException("Order id not found");
    }
    boolean found = false;
    for(SimpleOrder order:orderList){
      for(SimplePizza pizza: order.getPizzaList()) {
        if(pizza.getPizzaId() == pizzaId) found = true;
      }
    }
    if(!found) throw new IllegalArgumentException("PizzaID not found");

    this.orderList.stream().filter(order ->order.getOrderId() == orderId).findAny().get().getPizzaList()
            .remove( this.orderList.stream()
                    .filter(order ->order.getOrderId() == orderId).findAny().get()
                    .getPizzaList().stream().filter(pizza -> pizza.getPizzaId() == pizzaId).findFirst().get());



  }


  public void addTopping(final int pizzaId, final Topping topping)
          throws IllegalArgumentException, TooManyToppingsException {
    if(topping==null)
    {
      throw new IllegalArgumentException("Topping null!");
    }
    boolean found = false;
    for(SimpleOrder order: this.orderList)
    {
      for(SimplePizza pizza: order.getPizzaList())
      {
        if(pizza.getPizzaId() == pizzaId) found = true;
      }
      if(found) break;
    }
    if(!found) throw new IllegalArgumentException("PizzaID not found!");


    for(SimpleOrder order: orderList)
    {
      for(SimplePizza pizza: order.getPizzaList())
      {
        if(pizza.getToppings().stream().count() == 5) throw new TooManyToppingsException("Too many toppings");
        if(pizza.getPizzaId() == pizzaId) pizza.getToppings().add(topping);
      }
    }


  }


  public void removeTopping(final int pizzaId, final Topping topping)
          throws IllegalArgumentException {
    if(topping==null )
    {
      throw new IllegalArgumentException("Topping null!");
    }
    boolean idFound = false;
    boolean toppingFound = false;
    for(SimpleOrder order: orderList)
    {
      for(SimplePizza pizza: order.getPizzaList())
      {
        if(pizza.getPizzaId() == pizzaId) idFound = true;
        if(pizza.getPizzaId() == pizzaId &&
                pizza.getToppings().stream().anyMatch(topping1 -> topping1 == topping))
          toppingFound = true;
      }
      if(idFound) break;
      if(toppingFound) break;
    }
    if(!idFound) throw new IllegalArgumentException("PizzaID not found!");
    if(!toppingFound) throw new IllegalArgumentException("Topping not found");

    for(SimpleOrder order: orderList)
    {
      for(SimplePizza pizza: order.getPizzaList())
      {
        if(pizza.getPizzaId() == pizzaId &&
                pizza.getToppings().stream().anyMatch(topping1 -> topping1 == topping))
          pizza.getToppings().remove(topping);
      }

    }



  }


  public SimpleOrder getOrder(final int orderId) throws IllegalArgumentException {
    if(orderId<0 || this.orderList.stream().filter(order -> order.getOrderId() == orderId).findAny().isEmpty())
      throw new IllegalArgumentException("BreakPoint1");
    Optional<SimpleOrder> answer = this.orderList.stream().filter(order -> order.getOrderId() == orderId).findAny();
    if(answer.isEmpty()) throw new IllegalArgumentException("Order not found!");

    return answer.get();
  }


  public Map<PizzaSize, Integer> getPizzaSizePriceList() {
    Map<PizzaSize, Integer> map = new HashMap<>();
    map.put(PizzaSize.SMALL, 500);
    map.put(PizzaSize.MEDIUM, 700);
    map.put(PizzaSize.LARGE, 900);
    map.put(PizzaSize.EXTRA_LARGE, 1100);
    return map;
  }


  public Map<Topping, Integer> getToppingsPriceList() {
    Map<Topping, Integer> map = new HashMap<>();
    map.put(Topping.TOMATO,  30);
    map.put(Topping.CHEESE,  60);
    map.put(Topping.SALAMI,  50);
    map.put(Topping.HAM,  70);
    map.put(Topping.PINEAPPLE, 90);
    map.put(Topping.VEGETABLES,  20);
    map.put(Topping.SEAFOOD,  150);

    return map;
  }
  public int getPizzaId(int orderId, int pizzaId) {
    return this.orderList.stream().filter(simpleOrder -> simpleOrder.getOrderId() == orderId).findAny().get().
            getPizzaList().stream().filter(pizza -> pizza.getPizzaId() == pizzaId).findAny().get().getPizzaId();
  }
  public SimplePizza getPizza(int orderId, int pizzaId) {
    return (SimplePizza) this.orderList.stream().filter(simpleOrder -> simpleOrder.getOrderId() == orderId).findAny().get().
            getPizzaList().stream().filter(pizza -> pizza.getPizzaId() == pizzaId).findAny().get();
  }
}
