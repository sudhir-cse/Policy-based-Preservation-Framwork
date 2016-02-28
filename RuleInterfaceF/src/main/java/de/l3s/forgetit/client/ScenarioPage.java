package de.l3s.forgetit.client;

import java.util.ArrayList;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.l3s.forgetit.model.QuestionData;
import de.l3s.forgetit.model.ScenarioData;
import de.l3s.forgetit.presenter.RuleServices;
import de.l3s.forgetit.presenter.RuleServicesAsync;

public class ScenarioPage extends Composite implements ClickHandler{

	private VerticalPanel scenarioMainPanel= new VerticalPanel();
	private Grid grid = new Grid(2, 3);
	private HorizontalPanel footerPanel = new HorizontalPanel();
	private MainViewController mvc = new MainViewController();

	private Button backBtn = new Button("Back to options");
	private Button advanceBtnSetting = new Button("Advance setting");
	
	private final RuleServicesAsync serverServices = GWT.create(RuleServices.class);
	
	private String userPref;

	public ScenarioPage(MainViewController mvc, String userPref){
		initWidget(scenarioMainPanel);

		this.mvc = mvc;
		this.userPref = userPref;

		//lets first build the six scenarios
		ScenarioData s1 = new ScenarioData("Training & Career","images/scenario/education.jpg","Training & Career");
		ScenarioData s2 = new ScenarioData("Event","images/scenario/event.jpg","Event");
		ScenarioData s3 = new ScenarioData("Meeting","images/scenario/meeting.jpg","Meeting");
		ScenarioData s4 = new ScenarioData("Research","images/scenario/personnel.jpg","Research");
		ScenarioData s5 = new ScenarioData("Public Relations","images/scenario/professional.jpg","Public Relations");
		ScenarioData s6 = new ScenarioData("Vacation","images/scenario/vacation1.jpg","Personnel & Repositories");

		ArrayList<ScenarioData> sList = new ArrayList<ScenarioData>();
		sList.add(s1);
		sList.add(s2);
		sList.add(s3);
		sList.add(s4);
		sList.add(s5);
		sList.add(s6);

		int temp = 0;
		int numRows = grid.getRowCount();
		int numColumns = grid.getColumnCount();
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numColumns; col++) {
				grid.setWidget(row, col, new Scenario(sList.get(temp++)) );
			}
		}

		//here is for adding some text and instructions for the scenario
		HTMLPanel descriptionPanel = new HTMLPanel("<div><span>Choose scenario to specify your preferences:</span></div>");
		descriptionPanel.setWidth("666PX");
		descriptionPanel.getElement().getStyle().setMargin(6, Style.Unit.PX);
		descriptionPanel.getElement().getStyle().setMarginBottom(10, Style.Unit.PX);
		descriptionPanel.getElement().getStyle().setFontSize(16, Style.Unit.PX);
		descriptionPanel.getElement().getStyle().setFontWeight(FontWeight.BOLD);

		scenarioMainPanel.add(descriptionPanel);
		scenarioMainPanel.add(grid);

		//now position the scenarioMainPanel to the center
		int absLeft = Window.getClientWidth()/2 - 333;
		int margin = (Window.getClientWidth() - 1000)/2;
		int left = absLeft-margin;

		//		int htmlPanelHeight = descriptionPanel.getOffsetHeight();
		//		int gridHeight = grid.getOffsetHeight();
		//		int scenarioMainPanelHeight = htmlPanelHeight+gridHeight+10;
		//		int top = Window.getClientHeight()/2 + scenarioMainPanelHeight/2;

		scenarioMainPanel.getElement().getStyle().setMarginLeft(left, Style.Unit.PX);
		scenarioMainPanel.getElement().getStyle().setMarginTop(50, Style.Unit.PX);
		
		//footer goes here------------------
		footerPanel.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
		footerPanel.getElement().getStyle().setPadding(10, Style.Unit.PX);
		scenarioMainPanel.add(footerPanel);
		
		//add back button
		footerPanel.add(backBtn);
		backBtn.addClickHandler(this);
//		int top = 20;
//		backBtn.getElement().getStyle().setMarginTop(top, Style.Unit.PX);
//		backBtn.getElement().getStyle().setMarginLeft(10, Style.Unit.PX);
		
		advanceBtnSetting.getElement().getStyle().setMarginLeft(450, Style.Unit.PX);
		footerPanel.add(advanceBtnSetting);
		advanceBtnSetting.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent arg0) {
				Window.Location.assign("http://forgetit.l3s.uni-hannover.de:8787/policy-advanced");
			}
			
		});
		//((HasHorizontalAlignment) advanceBtnSetting).setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
				

	}

	//this is the custom widget
	public class Scenario extends Composite implements ClickHandler{

		private FocusPanel fPanel = new FocusPanel();
		private ScenarioData sd = new ScenarioData();

		public Scenario(ScenarioData scen){
			initWidget(fPanel);

			this.sd = scen;

			VerticalPanel vPanel = new VerticalPanel();
			fPanel.add(vPanel);

			fPanel.addClickHandler(this);
			fPanel.getElement().getStyle().setBorderColor("#96c11f");
			fPanel.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			fPanel.getElement().getStyle().setBorderWidth(1, Style.Unit.PX);
			fPanel.getElement().getStyle().setMargin(6, Style.Unit.PX);

			fPanel.setTitle(sd.getToolTip());

			Label label = new Label(sd.getToolTip());
			vPanel.add(label);
			label.getElement().getStyle().setMargin(5, Style.Unit.PX);

			Image image = new Image(sd.getImgeURL());
			image.setSize("210PX", "120PX");
			vPanel.add(image);

		}
		//all the click handlers go here
		public void onClick(ClickEvent event) {
			
			//Element el = Element.as(event.getNativeEvent().getEventTarget());
			
			Widget sender = (Widget)event.getSource();
			if(sender == fPanel){
				
				serverServices.returnQuestionsByScenarionName(sd.getLabel(), userPref, 
						new AsyncCallback<ArrayList<QuestionData>>() {
					
					public void onSuccess(ArrayList<QuestionData> arg0) {						
						mvc.openQuestionPage(arg0);
					}
					
					public void onFailure(Throwable arg0) {						
						Window.alert("Unable to obtain server response: "+ arg0.getMessage());
					}
				});				
			}
		}
	}

	public void onClick(ClickEvent arg0) {

		Widget sender = (Widget)arg0.getSource();
		
		if(sender == backBtn){
			mvc.openUpdatePrefOrManageDoc();
		}
		
	}

}

