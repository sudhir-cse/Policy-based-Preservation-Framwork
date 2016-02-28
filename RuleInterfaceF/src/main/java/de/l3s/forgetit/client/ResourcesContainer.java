package de.l3s.forgetit.client;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.l3s.forgetit.model.Resources;
import de.l3s.forgetit.presenter.RuleServices;
import de.l3s.forgetit.presenter.RuleServicesAsync;
/*
 * This class contains all the resources and also handles the paging
 */
public class ResourcesContainer extends Composite implements ClickHandler,ChangeHandler{

	private String userName = "";
	private final RuleServicesAsync serverServices = GWT.create(RuleServices.class);
	FocusPanel fPanel = new FocusPanel();
	private VerticalPanel mainContainer  = new VerticalPanel();
	private HorizontalPanel headerOfContainer  = new HorizontalPanel();
	private VerticalPanel bodyOfContainer  = new VerticalPanel();
	private HorizontalPanel footerOfContainer = new HorizontalPanel();
	private ArrayList<Resources> resList = new ArrayList<Resources>();
	private Label pageNoDisp = new Label("");
	private ListBox sortCriterionList = new ListBox(); //for displaying  lists of the sorting criterion

	private Button back  = new Button("Back to options");
	private Button prev = new Button("Previous");
	private Button save = new Button("Save");
	private Button next = new Button("Next");
	private Button refresh = new Button("Refresh");

	//these variables are used for paging
	private int resListSize=0;
	private int pageSize = 10; //Number of entries displayed on each page
	private int startPtr=0;
	private int endPtr=0;
	private int currentPtr=0; // points to the resList

	//These variables are used for maintaining the page number
	private int totalPages = 1;
	private int currentPageNo = 1;

	private MainViewController mainViewConteroller;

	public ResourcesContainer(ArrayList<Resources> resList1, MainViewController mvctrl){

		initWidget(fPanel);
		fPanel.add(mainContainer);

		resList = resList1;
		mainViewConteroller = mvctrl;

		//sorting the resources list by their name upon loading the page
		Collections.sort(resList, Resources.SortResByName);

		resListSize = resList.size();

		fPanel.addClickHandler(this);

		mainContainer.setStyleName("mainContainer");
		mainContainer.add(headerOfContainer);

		//set onclick handler on the button at footer
		back.addClickHandler(this);
		prev.addClickHandler(this);
		save.addClickHandler(this);
		next.addClickHandler(this);
		refresh.addClickHandler(this);

		//Disable these buttons while loading the page
		next.setEnabled(false);
		prev.setEnabled(false);
		save.setEnabled(false);

		//computation for the total page number
		int temp =(int) (resListSize/pageSize);

		if(temp == 0)
			totalPages = 1;
		else if((pageSize*temp) == resListSize)
			totalPages = temp;
		else
			totalPages = temp+1;

		//-------------------------------------header of container----------------------------
		headerOfContainer.setWidth("996px");
		headerOfContainer.setStyleName("headerOfContainer");
		Label headerDescription = new Label("All your documents");
		headerDescription.setStyleName("headerDescription");
		headerDescription.getElement().getStyle().setWidth(750, Style.Unit.PX);
		headerOfContainer.add(headerDescription);

		//for specifying sorting mechanism
		Label sortBy = new Label("Sort By");
		sortBy.getElement().getStyle().setMarginTop(10, Style.Unit.PX);
		sortBy.getElement().getStyle().setMarginBottom(10, Style.Unit.PX);
		sortBy.getElement().getStyle().setMarginLeft(10, Style.Unit.PX);
		sortBy.getElement().getStyle().setMarginRight(5, Style.Unit.PX);
		headerOfContainer.add(sortBy);

		//list box for the sorting criterion lists
		sortCriterionList.getElement().getStyle().setMarginTop(10, Style.Unit.PX);
		sortCriterionList.getElement().getStyle().setMarginBottom(10, Style.Unit.PX);

		sortCriterionList.addItem("Doc. Name");
		sortCriterionList.addItem("Published date");

		sortCriterionList.setVisibleItemCount(1);

		headerOfContainer.add(sortCriterionList);
		sortCriterionList.addChangeHandler(this);

		//---------------------------------body of  MainContainer-----------------------------

		mainContainer.add(bodyOfContainer);
		loadFirstPage();


		//---------------------------------footer of container-------------------------------------
		mainContainer.add(footerOfContainer);
		footerOfContainer.setWidth("996px");
		footerOfContainer.add(back);
		back.getElement().getStyle().setMarginLeft(10, Style.Unit.PX);
		back.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
		back.getElement().getStyle().setMarginBottom(5, Style.Unit.PX);
		//back.setWidth("75px");

		footerOfContainer.add(prev);
		prev.getElement().getStyle().setMarginLeft(190, Style.Unit.PX);
		prev.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
		prev.getElement().getStyle().setMarginBottom(5, Style.Unit.PX);
		prev.setWidth("75px");

		footerOfContainer.add(save);
		save.getElement().getStyle().setMarginLeft(15, Style.Unit.PX);
		save.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
		save.getElement().getStyle().setMarginBottom(5, Style.Unit.PX);
		save.setWidth("75px");

		footerOfContainer.add(next);
		next.getElement().getStyle().setMarginLeft(15, Style.Unit.PX);
		next.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
		next.getElement().getStyle().setMarginBottom(5, Style.Unit.PX);
		next.setWidth("75px");

		footerOfContainer.add(refresh);
		refresh.getElement().getStyle().setMarginLeft(225, Style.Unit.PX);
		refresh.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
		refresh.getElement().getStyle().setMarginBottom(5, Style.Unit.PX);
		refresh.setWidth("75px");

		pageNoDisp.setText("Page : "+currentPageNo+"/"+totalPages);
		footerOfContainer.add(pageNoDisp);
		pageNoDisp.getElement().getStyle().setMarginLeft(20, Style.Unit.PX);
		pageNoDisp.getElement().getStyle().setMarginTop(5, Style.Unit.PX);
		pageNoDisp.getElement().getStyle().setMarginBottom(5, Style.Unit.PX);

	}

	//------------------------------------all events handlers------------------------------------
	public void onClick(ClickEvent event) {
		Widget sender = (Widget)event.getSource();

		//Enabling the save button 
		if(sender == fPanel){
			if(!save.isEnabled()){
				for(int i = 0; i<resListSize; i++){
					if(resList.get(i).isStatus()){
						save.setEnabled(true);
						break;
					}
				}
			}
		}

		if(sender == back){
			mainViewConteroller.openUpdatePrefOrManageDoc();
		}
		//for the previous button
		if(sender  == prev){

			next.setEnabled(true);
			bodyOfContainer.clear();
			endPtr = startPtr;
			startPtr = endPtr-pageSize;
			if(startPtr == 0)
				prev.setEnabled(false);
			for(currentPtr = startPtr; currentPtr<endPtr; currentPtr++){
				bodyOfContainer.add(new ResourcePageView(resList.get(currentPtr)));
			}

			//page number display
			currentPageNo--;
			pageNoDisp.setText("Page : "+currentPageNo+"/"+totalPages);

		}

		if(sender == save){
			PopupPanel pop = new PopupPanel(true);
			String msg = "The documents changed are : ";
			for(Resources r : resList){
				if(r.isStatus())
					msg = msg+r.getName()+";";
			}
			pop.add(new Label(msg));
			Widget wid = (Widget)event.getSource();
			int left = wid.getAbsoluteLeft() +40;
			int top = wid.getAbsoluteTop()+40;
			pop.setPopupPosition(left, top);
			pop.show();
		}

		if(sender == next){

			startPtr =  currentPtr;
			endPtr = currentPtr+pageSize;
			prev.setEnabled(true);
			bodyOfContainer.clear();
			if(endPtr <= resListSize){
				for(currentPtr = startPtr; currentPtr<endPtr;currentPtr++){
					bodyOfContainer.add(new ResourcePageView(resList.get(currentPtr)));
				}
			}
			else{
				next.setEnabled(false);
				for(currentPtr = startPtr; currentPtr<resListSize;currentPtr++){
					bodyOfContainer.add(new ResourcePageView(resList.get(currentPtr)));
				}
			}
			//page number display
			currentPageNo++;
			pageNoDisp.setText("Page : "+currentPageNo+"/"+totalPages);
		}

		if(sender == refresh){

			mainViewConteroller.refreshPage(resList);
			//mainViewConteroller.hellowRefresh();
		}
	}

	// This is change handler for the sorting list box
	public void onChange(ChangeEvent event) {

		if(sortCriterionList.getSelectedIndex() == 0){      //meaning sorting by name


			bodyOfContainer.clear();
			Collections.sort(resList, Resources.SortResByName);
			currentPageNo = 1;
			prev.setEnabled(false);
			loadFirstPage();


		}
		else{            // meaning sorting has to be done with respect to published date

			bodyOfContainer.clear();
			Collections.sort(resList, Resources.SortResByPublishedDate);
			currentPageNo = 1;
			prev.setEnabled(false);
			loadFirstPage();

		}	
	}

	//this method load the first page
	public void loadFirstPage(){

		pageNoDisp.setText("Page : "+currentPageNo+"/"+totalPages);

		//this is when there is not yet any resources for the user
		if(resList.size() == 0){

			Label message = new Label("You don't have yet any resources");
			message.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
			message.getElement().getStyle().setMarginTop(15, Style.Unit.PX);
			message.getElement().getStyle().setMarginBottom(15, Style.Unit.PX);
			message.getElement().getStyle().setMarginLeft(400, Style.Unit.PX);
			footerOfContainer.getElement().setAttribute("id", "resContainerFooterForEmpityRes");
			refresh.setEnabled(false);
			bodyOfContainer.add(message);
		}

		//check if the number of elements to be displayed are less than the page size
		else if(resList.size() <= pageSize ){

			for(currentPtr=0;currentPtr<resList.size();currentPtr++){
				bodyOfContainer.add(new ResourcePageView(resList.get(currentPtr)));
			}
		}
		//if elements to be displayed are more than number of the page size then do the following
		else{

			for(currentPtr=0;currentPtr<pageSize;currentPtr++){
				bodyOfContainer.add(new ResourcePageView(resList.get(currentPtr)));
				next.setEnabled(true);
			}
		}
	}

	//getter and setter methods
	public String getUserName() {
		serverServices.returnUserName(new AsyncCallback<String>() {

			public void onSuccess(String arg0) {
				setUserName(arg0);
				//Window.alert("User Name :"+arg0);
			}

			public void onFailure(Throwable arg0) {
				Window.alert("Unable to get server responce "+arg0.getMessage());

			}
		});
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}
