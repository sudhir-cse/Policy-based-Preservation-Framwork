package de.l3s.forgetit.client;

/**
 * Interface to represent the messages contained in resource bundle:
 * 	D:/GWT_WorkSpace/ReviewDemo/RuleInterfaceF/src/main/resources/de/l3s/forgetit/client/Messages.properties'.
 */
public interface Messages extends com.google.gwt.i18n.client.Messages {
  
  /**
   * Translated "Enter your name".
   * 
   * @return translated "Enter your name"
   */
  @DefaultMessage("Enter your name")
  @Key("nameField")
  String nameField();

  /**
   * Translated "Send".
   * 
   * @return translated "Send"
   */
  @DefaultMessage("Send")
  @Key("sendButton")
  String sendButton();
}
