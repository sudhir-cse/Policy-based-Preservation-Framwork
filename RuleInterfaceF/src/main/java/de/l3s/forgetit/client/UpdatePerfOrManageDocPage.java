package de.l3s.forgetit.client;


import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UpdatePerfOrManageDocPage extends Composite implements ClickHandler {
	
	private MainViewController mvc;
	private VerticalPanel mainPanel = new VerticalPanel();
	private Button updatePrefBtn;
	private Button manageDocBtn;
	
	public UpdatePerfOrManageDocPage(MainViewController mvc){
		
		initWidget(mainPanel);
		this.mvc = mvc;
		
		
		mainPanel.getElement().setAttribute("id", "UpdatePerfOrManageDocMainPanel");
		//position the mainPanel to the center of the screen
		int absLeft = Window.getClientWidth()/2 - 100;
		int absTop = Window.getClientHeight()/2 - 100;
		int margin = (Window.getClientWidth() - 1000)/2;
		int left = absLeft-margin;
		int top = absTop-150;
		mainPanel.getElement().getStyle().setMarginLeft(left, Style.Unit.PX);
		mainPanel.getElement().getStyle().setMarginTop(top, Style.Unit.PX);
		
		
		
		
		updatePrefBtn = new Button("Update the Preferences");
		mainPanel.add(updatePrefBtn);
		updatePrefBtn.addClickHandler(this);
		updatePrefBtn.getElement().getStyle().setMarginTop(70, Style.Unit.PX);
		updatePrefBtn.getElement().getStyle().setMarginBottom(10, Style.Unit.PX);
		updatePrefBtn.getElement().getStyle().setMarginLeft(20, Style.Unit.PX);
		updatePrefBtn.getElement().getStyle().setMarginRight(20, Style.Unit.PX);
		
			
		manageDocBtn = new Button("Manage the documents");
		mainPanel.add(manageDocBtn);
		manageDocBtn.addClickHandler(this);
		manageDocBtn.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
		manageDocBtn.getElement().getStyle().setMarginBottom(70, Style.Unit.PX);
		manageDocBtn.getElement().getStyle().setMarginLeft(20, Style.Unit.PX);
		manageDocBtn.getElement().getStyle().setMarginRight(20, Style.Unit.PX);
		
	}

	//all click the handlers goes here
	public void onClick(ClickEvent event) {
		Widget sender = (Widget)event.getSource();
		
		if(sender == updatePrefBtn ){
			
			MainViewController mmvc = mvc;
			mvc.openScenarioPage(mmvc);
//			mvc.setUserName("Hi, Mr. "+mvc.getUserName());
//			mvc.getLogout().setText("Logout");
			
		}
		if(sender == manageDocBtn ){
			mvc.openResourcesContainer();
		}
	}

}
