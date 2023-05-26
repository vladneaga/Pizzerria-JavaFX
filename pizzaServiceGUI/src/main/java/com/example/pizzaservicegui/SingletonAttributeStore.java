package com.example.pizzaservicegui;



import java.util.HashMap;
import java.util.Map;

/**
 * Simple and straight-forward singleton based implementation of an AttributeStore.
 */
public class SingletonAttributeStore  {


  private static final SingletonAttributeStore self = new SingletonAttributeStore();
private Map<String, Object> map;
  public static SingletonAttributeStore getInstance() {
    return self;
  }

  private SingletonAttributeStore() {
    map = new HashMap<>();
  }


  public void setAttribute(final String name, final Object object) throws IllegalArgumentException {
map.put(name, object);
  }


  public Object getAttribute(final String name) throws IllegalArgumentException {
    return map.get(name);
  }


  public void removeAttribute(final String name) {
map.remove(name);
  }
}
