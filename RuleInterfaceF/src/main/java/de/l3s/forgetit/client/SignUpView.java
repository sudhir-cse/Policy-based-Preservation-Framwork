package de.l3s.forgetit.client;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.l3s.forgetit.model.SignupFormData;
import de.l3s.forgetit.presenter.RuleServices;
import de.l3s.forgetit.presenter.RuleServicesAsync;

//this has margin-left : 250px and margin-bottom : 15px
public class SignUpView extends Composite {

	private AbsolutePanel singnUpMainPage = new AbsolutePanel();

	private HashMap<String,ArrayList<String>> orgAndUnitList;

	private final RuleServicesAsync serverServices = GWT.create(RuleServices.class);
	private MainViewController mvc;

	private TextBox fNameTb;
	private TextBox lNameTb;
	private TextBox userNameTb;
	private TextBox emailTb;
	private PasswordTextBox passwordTb;
	private PasswordTextBox rePasswordTb;
	private ListBox listBoxSex;
	private ListBox ageListBox;
	private ListBox orgListBox;
	private ListBox orgUnitListBox;
	private ListBox roleListBox;
	private VerticalPanel preservationPrefPanel;
	private RadioButton conservative;
	private RadioButton moderate;
	private RadioButton aggressive;
	private String personPref = "";

	//private static Logger rootLogger = Logger.getLogger("");

	public SignUpView(MainViewController mvc)
	{
		initWidget(singnUpMainPage);

		this.mvc = mvc;

		//populate the orgAndUnitList
		serverServices.returnOrgAndOrgUnits(new AsyncCallback<HashMap<String,ArrayList<String>>>() {

			public void onFailure(Throwable arg0) {

				Window.alert("Unable to obtain server response: "+ arg0.getMessage());
			}

			public void onSuccess(HashMap<String, ArrayList<String>> arg0) {

				orgAndUnitList = arg0;

				singnUpMainPage.getElement().setAttribute("id", "singnUpMainPage");

				singnUpMainPage.setSize("879px", "700px");
				
				singnUpMainPage.getElement().getStyle().setFontSize(18, Style.Unit.PX);

				Label lblSignUp = new Label("Your profile:");
				singnUpMainPage.add(lblSignUp, 219, 10);
				//DOM.setElementAttribute(lblSignUp.getElement(), "id", "lblSignUp");

				lblSignUp.getElement().setAttribute("id", "lblSignUp");

				Label lblFirstName = new Label("First Name");
				singnUpMainPage.add(lblFirstName, 71, 72);
				fNameTb = new TextBox();
				singnUpMainPage.add(fNameTb, 190, 66);
				fNameTb.setSize("168px", "16px");

				Label lblLastName = new Label("Last Name");
				singnUpMainPage.add(lblLastName, 72, 117);
				lNameTb = new TextBox();
				singnUpMainPage.add(lNameTb, 190, 111);
				lNameTb.setSize("168px", "16px");

				Label userName = new Label("Username");
				userNameTb = new TextBox();
				singnUpMainPage.add(userName, 76, 152);
				singnUpMainPage.add(userNameTb, 190, 152);
				userNameTb.setSize("168px", "16px");

				Label emaiLabel = new Label("E-mail");
				emailTb = new TextBox();
				singnUpMainPage.add(emaiLabel, 98, 198);
				singnUpMainPage.add(emailTb, 190, 192);
				emailTb.setSize("168px", "16px");

				Label passwordLabel = new Label("Password");
				passwordTb = new PasswordTextBox();
				singnUpMainPage.add(passwordLabel, 78, 233);
				singnUpMainPage.add(passwordTb, 190, 227);
				passwordTb.setSize("168px", "16px");

				Label rePasswordLabel = new Label("Re-enter password");
				rePasswordTb = new PasswordTextBox();
				singnUpMainPage.add(rePasswordLabel, 27, 277);
				singnUpMainPage.add(rePasswordTb, 190, 267);
				rePasswordTb.setSize("168px", "16px");

				Label lblSex = new Label("Sex");
				singnUpMainPage.add(lblSex, 112, 313);

				listBoxSex = new ListBox();
				singnUpMainPage.add(listBoxSex, 190, 309);
				listBoxSex.setSize("82px", "22px");
				listBoxSex.setVisibleItemCount(1);
				listBoxSex.addItem("Male");
				listBoxSex.addItem("Femal");

				Label lblAge = new Label("Age");
				singnUpMainPage.add(lblAge, 112, 349);

				ageListBox = new ListBox();
				singnUpMainPage.add(ageListBox, 190, 345);
				ageListBox.setSize("82px", "22px");
				ageListBox.setVisibleItemCount(1);
				ageListBox.addItem("0-18", "1");
				ageListBox.addItem("18-23", "2");
				ageListBox.addItem("23-30", "3");
				ageListBox.addItem("30-40", "4");
				ageListBox.addItem("over 40", "5");

				Label orgLabel = new Label("Organization");
				singnUpMainPage.add(orgLabel, 71, 391);

				orgListBox = new ListBox();
				singnUpMainPage.add(orgListBox, 190, 387);
				orgListBox.setSize("176px", "22px");
				orgListBox.setVisibleItemCount(1);

				//get and the list of organizations name
				for(String key1 : orgAndUnitList.keySet()){
					orgListBox.addItem(key1);
				}
				//add change-listener to the orgListBox
				orgListBox.addChangeHandler(new OrgListBoxChangeHandler());

				Label orgUnitLabel = new Label("Organizational Unit");
				singnUpMainPage.add(orgUnitLabel, 25, 432);

				orgUnitListBox = new ListBox();
				singnUpMainPage.add(orgUnitListBox, 190, 428);
				orgUnitListBox.setSize("176px", "22px");
				orgUnitListBox.setVisibleItemCount(1);

				//add all the units of the corresponding organization

				for(String key : orgAndUnitList.keySet()){

					ArrayList<String> values = orgAndUnitList.get(key);

					for(String value : values){

						orgUnitListBox.addItem(value);
					}

					break;
				}

				Button signUpBtn = new Button("Sign Up");
				signUpBtn.setStyleName("signUpPage-signUpBtn");
				//DOM.setElementAttribute(signUpBtn.getElement(), "id", "signUpPage-signUpBtn");
				singnUpMainPage.add(signUpBtn, 190, 620);
				signUpBtn.setSize("176px", "29px");
				signUpBtn.addClickHandler(new SignUpBtnClickHandler());

				Label roleLabel = new Label("Role");
				singnUpMainPage.add(roleLabel, 109, 469);

				roleListBox = new ListBox();
				singnUpMainPage.add(roleListBox, 190, 465);
				roleListBox.setSize("108px", "22px");
				roleListBox.setVisibleItemCount(1);
				//

				serverServices.returnRoleNames(new AsyncCallback<ArrayList<String>>(){

					public void onFailure(Throwable caught) {
						Window.alert("Unable to obtain server response: "+ caught.getMessage());

					}

					public void onSuccess(ArrayList<String> result) {

						for(String orgUnit : result)
						{
							roleListBox.addItem(orgUnit);
						}
					}
				});

			}
		});
		
		//add the preservation preservation here		
		preservationPrefPanel = new VerticalPanel();
		preservationPrefPanel.getElement().getStyle().setPaddingBottom(10, Style.Unit.PX);
		
		// singnUpMainPage.add(preservationPrefPanel,109,490);
		
		HorizontalPanel prefLabelPanel = new HorizontalPanel();
		Label prefQuestion = new Label("How do you classify yourself in preserving your data ?");
		prefLabelPanel.add(prefQuestion);
		
		singnUpMainPage.add(prefLabelPanel,71,520);
		
		singnUpMainPage.add(preservationPrefPanel,71,550);
		
		conservative = new RadioButton("pp","Conservative: I am very reluctant with "
				+ "deletions- You never know, what you still need. ");
		preservationPrefPanel.add(conservative);
		conservative.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				personPref = "conservative";
				
			}
		});
		
		moderate = new RadioButton("pp","Moderate: I am ready to delete unnecessary "
				+ "things, but still careful not to delete too much");
		preservationPrefPanel.add(moderate);
		moderate.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				personPref = "moderate";
				
			}
		});
		
		aggressive = new RadioButton("pp","Aggressive: I only keep, what is really "
				+ " and what I cannot get from elsewhere at a later point in time");
		preservationPrefPanel.add(aggressive);
		aggressive.addClickHandler(new ClickHandler() {
			
			public void onClick(ClickEvent arg0) {
				personPref = "aggressive";
				
			}
		});
	
	}

	public class SignUpBtnClickHandler implements ClickHandler{

		public void onClick(ClickEvent event) {

			/*
			 * validate all the form data
			 * populate the signUpFormData object
			 * call the save method
			 * 
			 */

			//come back here again for the form validation stuff*********************

			SignupFormData sd = new SignupFormData();
			sd.setFirstName(fNameTb.getText());
			sd.setLastName(lNameTb.getText());
			sd.setUserName(userNameTb.getText());
			sd.setEmail(emailTb.getText());
			sd.setPassword(passwordTb.getText());
			sd.setSex(listBoxSex.getItemText(listBoxSex.getSelectedIndex()));
			sd.setAge(ageListBox.getItemText(ageListBox.getSelectedIndex()));
			sd.setOrg(orgListBox.getItemText(orgListBox.getSelectedIndex()));
			sd.setOrgUnit(orgUnitListBox.getItemText(orgUnitListBox.getSelectedIndex()));
			sd.setRole(roleListBox.getItemText(roleListBox.getSelectedIndex()));
			sd.setPersonPref(personPref);

			//saveSignUpData() method save the data in the DB, create session and return the name of the person to be displayed on the screen
			serverServices.saveSignUpData(sd, new AsyncCallback<String>() {

				public void onFailure(Throwable arg0) {
					Window.alert("Unable to obtain server response: "+ arg0.getMessage());
				}

				public void onSuccess(String arg0) {

					mvc.setUserName("Hi, Mr. "+arg0.toUpperCase()+"("+personPref+")");
					mvc.setPersonName(arg0);

				}
			});

			mvc.openUpdatePrefOrManageDoc();
			mvc.getLogout().setText("Logout");
		}
	}

	public class OrgListBoxChangeHandler implements ChangeHandler{

		public void onChange(ChangeEvent event) {

			String changedOrgName = orgListBox.getItemText(orgListBox.getSelectedIndex());

			ArrayList<String> values = orgAndUnitList.get(changedOrgName);

			orgUnitListBox.clear();

			for(String value : values)
				orgUnitListBox.addItem(value);
		}
	}

}
