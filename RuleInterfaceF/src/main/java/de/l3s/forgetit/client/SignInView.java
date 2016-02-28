package de.l3s.forgetit.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.l3s.forgetit.model.StringPair;
import de.l3s.forgetit.presenter.RuleServices;
import de.l3s.forgetit.presenter.RuleServicesAsync;

public class SignInView extends Composite {
	
	private HorizontalPanel singInMainPanel = new HorizontalPanel();
	private final RuleServicesAsync serverServices = GWT.create(RuleServices.class);
	
	private VerticalPanel signInImagePanel;
	private VerticalPanel signInFormPanel;
	
	private TextBox userID;
	private PasswordTextBox password;
	private Label errorMsg;
	private MainViewController mvc;
	
	public SignInView(MainViewController mvc){
		initWidget(singInMainPanel);
		this.mvc = mvc;
		singInMainPanel.setWidth("900px");
		
		//populate signInImagePanel with image
		signInImagePanel = new VerticalPanel();
		singInMainPanel.add(signInImagePanel);
		signInImagePanel.add(new Image("images/Managed_Forgetting.png"));
		signInImagePanel.getElement().setAttribute("id", "signInImagePanel");
		
		//populate signInForm panel
		signInFormPanel = new VerticalPanel();
		singInMainPanel.add(signInFormPanel);
		signInFormPanel.getElement().setAttribute("id", "signInFormPanel");
		
		Image forgetITLogo = new Image("images/forgetIT_logo.png");
		forgetITLogo.getElement().setAttribute("id", "signInFormLogo");
		signInFormPanel.add(forgetITLogo);
		
		signInFormPanel.add(new Label("UserID / E-mail-ID"));
		userID = new TextBox();
		userID.setText("tran");
		userID.setStyleName("signInFormElements"); //setting class for applying css
		signInFormPanel.add(userID);
		
		signInFormPanel.add(new Label("Password"));
		password = new PasswordTextBox();
		password.setText("welcome");
		password.setStyleName("signInFormElements"); //setting class for applying css
		signInFormPanel.add(password);
		
		Button signIn = new Button("Sign In");
		signIn.setStyleName("signInFormElements");
		signIn.getElement().setAttribute("id", "signInBtn");
		signInFormPanel.add(signIn);
		signIn.addClickHandler(new SignInBtnClickHandler());
		
//		Anchor signInHelp = new Anchor("I can't access my account");
//		//signInHelp.setStyleName("signInFormElements");
//		//DOM.setElementAttribute(signInHelp.getElement(), "id", "signInHelp");
//		signInHelp.getElement().setAttribute("id", "signInHelp");
//		signInFormPanel.add(signInHelp);
		
		Button signUp = new Button("Sign Up");
		signUp.setStyleName("signInFormElements");
		signUp.getElement().setAttribute("id", "signUpBtn");
		signInFormPanel.add(signUp);
		signUp.addClickHandler(new SignUpBtnClickHandler());
		
		errorMsg = new Label("");
		signInFormPanel.add(errorMsg);
		errorMsg.setStyleName("signInFormElements");
		errorMsg.getElement().setAttribute("id", "errorMsg");
	}
	
	//onclick event handler for signIn button
	private class SignInBtnClickHandler implements ClickHandler{
		
		private String email;
		private String userName;
		private String userPassword;
		
		public void onClick(ClickEvent event) {
			
			email = userID.getText();
			userName = userID.getText();
		    userPassword = password.getText();
			
			if(userName == null || userName.isEmpty()){
				
				errorMsg.setText("");
				errorMsg.setText("Please enter UesrName / email");
			}
			else if(userPassword == null || userPassword.isEmpty())
			{
				errorMsg.setText("");
				errorMsg.setText("Please enter your password");
			}
			else{
				
				serverServices.userAuth(email, userName, userPassword, new AsyncCallback<StringPair>(){

					public void onFailure(Throwable caught) {
						
						Window.alert("Unable to obtain server response: "+ caught.getMessage());
					}

					public void onSuccess(StringPair result) {
						
						if(!result.equals("nop")) //"nop" indicates that the user does not exists
						{
							//errorMsg.setText("success !!");
							mvc.openUpdatePrefOrManageDoc();
							mvc.setUserName("Hi, Mr. "+result.getLeft().toUpperCase() + " [type: " + result.getRight() + "]");							
							mvc.getLogout().setText("Logout");
							mvc.setPersonName(result.getLeft()); // this is to store the person name for later use
							mvc.setUserPreference(result.getRight());

						}
						else{
							errorMsg.setText("");
							errorMsg.setText("UserName or Password is incorect");
						}
					}
					
				});
			}
		}
		
	}
	
	//onClick event handler for the signUp button 
	private class SignUpBtnClickHandler implements ClickHandler{

		public void onClick(ClickEvent event) {
			
			mvc.openSignUpPage();
			
			//Also set the click handler to the login label to redirect to the the signInPage
			mvc.getLogout().setText("Login");
			
//			mvc.getLogout().addClickHandler(new ClickHandler() {
//				
//				public void onClick(ClickEvent arg0) {
//					
//					mvc.openSignInPage();
//					
//				}
//			});
		}
		
	}

}
