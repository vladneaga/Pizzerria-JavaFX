package com.example.pizzaservicegui;

import com.example.pizzaservicegui.pizzaDelivery.UnknownTransitionException;
import javafx.scene.layout.Pane;


import java.io.IOException;

/**
 * Simple and straight-forward implementation of a ScreenController for the PizzaDeliveryService.
 */
public class PizzaDeliveryScreenController  {

Pane pane;
    CreateOrderScreen createOrderScreen;
    ShowOrderScreen showOrderScreen;
  public PizzaDeliveryScreenController(final Pane pane) {
this.pane = pane;
  }

    public Pane getPane() {
        return pane;
    }


  public void switchTo(final String fromScreen, final String toScreen)
            throws UnknownTransitionException, IOException {

      if(toScreen.equals(CreateOrderScreen.SCREEN_NAME)) {

if(createOrderScreen==null)
        createOrderScreen  = new CreateOrderScreen(this);
          pane.getChildren().clear();
          pane.getChildren().add(createOrderScreen);
      } else if (toScreen.equals(ShowOrderScreen.SCREEN_NAME)) {

          if(showOrderScreen == null)
              showOrderScreen = new ShowOrderScreen(this);


          pane.getChildren().clear();
          showOrderScreen.sync();
          pane.getChildren().add(showOrderScreen);
          showOrderScreen.test();

      } else if (toScreen == EditPizzaScreen.SCREEN_NAME) {
          pane.getChildren().clear();
          pane.getChildren().add(new EditPizzaScreen(this));

      }
        if(toScreen==null) throw new IllegalArgumentException("null path");


          //FXMLLoader loader = new FXMLLoader(getClass().getResource(path));



          /*try {
      Parent root = loader.load();
      Scene scene = new Scene(root, 600, 600);
      Stage stage = new Stage();
      stage.setTitle("Screen changed");
      stage.setScene(scene);
      stage.show();//}
    } catch (IOException e) {
      throw new RuntimeException(e);
    }*/
  }
}
