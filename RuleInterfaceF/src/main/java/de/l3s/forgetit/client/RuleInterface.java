package de.l3s.forgetit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class RuleInterface implements EntryPoint {
  
  public void onModuleLoad() {
	  
	  RootPanel.get("main-conten").add(new MainViewController());
  }
}
