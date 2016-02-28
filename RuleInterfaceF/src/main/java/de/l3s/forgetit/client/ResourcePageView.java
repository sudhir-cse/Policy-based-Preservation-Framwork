package de.l3s.forgetit.client;

import java.util.Map;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import de.l3s.forgetit.model.Resources;
import de.l3s.forgetit.presenter.RuleServices;
import de.l3s.forgetit.presenter.RuleServicesAsync;

public class ResourcePageView extends Composite implements ClickHandler{

	private final RuleServicesAsync serverServices = GWT.create(RuleServices.class);
	HorizontalPanel mainResPanel = new HorizontalPanel();
	private Resources res;
	private double pvValue;

	private int[] mbWindow = {0,0,0,0,0};
	private int[] pvWindow  = {0,0,0,0,0};

	//for MB
	private Label mbLabel; 
	private Button hotBtn; //0.99
	private Button warmBtn; //0.8
	private Button lukeWarmBtn; //0.5
	private Button coldBtn;  //0.2
	private Button forgetBtn; //0.1
	private Button mbExplainBtn;

	//for PV
	Label pvLabel;
	Button gold;    //keep
	Button silver;  //protect
	Button bronze; //neglect
	Button wood;   //ignore
	Button ash;   //delete
	Button pvExplainBtn;

	public ResourcePageView(Resources res1){

		initWidget(mainResPanel);

		res= res1;

		mainResPanel.setStyleName("mainResPanel");
		mainResPanel.setWidth("996px");
		//this panel contains all documents details
		HorizontalPanel docSectionPanel = new HorizontalPanel();
		mainResPanel.add(docSectionPanel);
		// docSectionPanel.getElement().getStyle().setWidth(300, Style.Unit.PX);
		docSectionPanel.setWidth("770px");

		// docSectionPanel.getElement().getStyle().setMarginRight(250, Style.Unit.PX);

		//choosing the logo based on the type of the resource
		String logoUrl = null;
		if(res.getType().equalsIgnoreCase("email"))
			logoUrl = "images/email_logo.png";

		else if(res.getType().equalsIgnoreCase("pdf"))
			logoUrl = "images/pdf-icon.png";

		else if(res.getType().equalsIgnoreCase("pptx"))
			logoUrl = "images/pptx.png";

		else if(res.getType().equalsIgnoreCase("docx"))
			logoUrl = "images/docx.png";

		else if(res.getType().equalsIgnoreCase("doc"))
			logoUrl = "images/doc.png";

		else if(res.getType().equalsIgnoreCase("xls"))
			logoUrl = "images/xls.png";
		
		else if(res.getType().equalsIgnoreCase("xlsx"))
			logoUrl = "images/xlsx.png";

		else if(res.getType().equalsIgnoreCase("tar.gz"))
			logoUrl = "images/tar-gz.png";

		else if(res.getType().equalsIgnoreCase("zip"))
			logoUrl = "images/zip.png";

		else if(res.getType().equalsIgnoreCase("ppt"))
			logoUrl = "images/ppt.png";

		else if(res.getType().equalsIgnoreCase("png"))
			logoUrl = "images/png.png";

		else if(res.getType().equalsIgnoreCase("jpg"))
			logoUrl = "images/image_logo.png";
		else
			logoUrl = "images/file_logo.png";

		Image logo = new Image(logoUrl);

		//docSectionPanel.getElement().getStyle().clearWidth();
		//docSectionPanel.getElement().getStyle().clearHeight();
		docSectionPanel.add(logo);

		logo.getElement().getStyle().setHeight(64, Style.Unit.PX);
		logo.getElement().getStyle().setWidth(64, Style.Unit.PX);
		logo.getElement().getStyle().setMarginLeft(10, Style.Unit.PX);

		logo.getElement().getStyle().setMarginRight(10, Style.Unit.PX);
		logo.getElement().getStyle().setMarginLeft(10, Style.Unit.PX);

		VerticalPanel docDetails = new VerticalPanel();
		docDetails.setWidth("710px");
		docSectionPanel.add(docDetails);

		final Label docName = new Label(res.getName());
		if (res.getName().endsWith("pdf") || res.getName().endsWith("png") ||res.getName().endsWith("jpg")) {
			docName.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent clickEvent) {
					PopupPanel pp = new PopupPanel(true);
					pp.setHeight("300px");
					pp.setWidth("400px");
					//pp.setPopupPosition(240, 120);
					String path = com.google.gwt.core.client.GWT.getHostPageBaseURL() + "files/" +
							(( res.getName().endsWith("png") ||res.getName().endsWith("jpg")) ?
								res.getName() : res.getName().substring(0,
									res.getName().length() - 4)+"_1111.png");
					Image img = new Image(path);
					img.setPixelSize(400, img.getHeight()*400/img.getWidth());
					pp.add(img);
					pp.showRelativeTo(docName);
				}
			});
		}
		docDetails.add(docName);
		docName.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		docName.getElement().getStyle().setFontSize(15, Style.Unit.PX);
		//	docName.getElement().getStyle().setMarginBottom(2, Style.Unit.PX);	

		Label author = new Label("Author : "+res.getAuthor());
		docDetails.add(author);
		author.getElement().getStyle().setFontSize(12, Style.Unit.PX);

		Label publishedDate = new Label("Published : "+res.getPublishedDate());
		docDetails.add(publishedDate);
		publishedDate.getElement().getStyle().setFontSize(12, Style.Unit.PX);

		//this section will contains mb and pv.
		VerticalPanel mbPvSectionPanel = new VerticalPanel();
		// mbPvSectionPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		mbPvSectionPanel.setWidth("");
		mainResPanel.add(mbPvSectionPanel);

		//for MB
		HorizontalPanel mbPanel = new HorizontalPanel();
		mbPvSectionPanel.add(mbPanel);
		mbPanel.getElement().getStyle().setPaddingBottom(5, Style.Unit.PX);

		mbLabel = new Label("MB");
		mbPanel.add(mbLabel);
		mbLabel.setStyleName("mbLabel");

		hotBtn = new Button();
		hotBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
		mbPanel.add(hotBtn);
		hotBtn.setSize("25px", "15px");
		hotBtn.setStyleName("hotBtn");
		//hotBtn.getElement().setPropertyString("id", "hotBtn");
		//hotBtn.getElement().setId("hotBtn");
		hotBtn.setTitle("its work");
		hotBtn.addClickHandler(this);

		warmBtn = new Button();
		mbPanel.add(warmBtn);
		warmBtn.setSize("25px", "15px");
		warmBtn.setStyleName("warmBtn");
		warmBtn.addClickHandler(this);
		warmBtn.setTitle("warm");

		lukeWarmBtn = new Button();
		mbPanel.add(lukeWarmBtn);
		lukeWarmBtn.setSize("25px", "15px");
		lukeWarmBtn.setStyleName("lukeWarmBtn");
		lukeWarmBtn.addClickHandler(this);
		lukeWarmBtn.setTitle("luke-warm");

		coldBtn = new Button();
		mbPanel.add(coldBtn);
		coldBtn.setSize("25px", "15px");
		coldBtn.setStyleName("coldBtn");
		coldBtn.addClickHandler(this);
		coldBtn.setTitle("cold");

		forgetBtn = new Button();
		mbPanel.add(forgetBtn);
		forgetBtn.setSize("25px", "15px");
		forgetBtn.setStyleName("forgetBtn");
		forgetBtn.addClickHandler(this);
		forgetBtn.setTitle("forget");

		mbExplainBtn = new Button("Explain");
		mbPanel.add(mbExplainBtn);
		mbExplainBtn.setHeight("15px");
		mbExplainBtn.setStyleName("mbExplainBtn");
		mbExplainBtn.addClickHandler(this);

		//during loading time; highlighting corresponding MB button
		if(res.getCorrectMB() == 0){
			hotBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			hotBtn.getElement().getStyle().setBorderColor("black");
			mbWindow[0]=1;
		}

		if(res.getCorrectMB() == 1){
			warmBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			warmBtn.getElement().getStyle().setBorderColor("black");
			mbWindow[1]=1;
		}

		if(res.getCorrectMB() == 2){
			lukeWarmBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			lukeWarmBtn.getElement().getStyle().setBorderColor("black");
			mbWindow[2]=1;
		}

		if(res.getCorrectMB() == 3){
			coldBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			coldBtn.getElement().getStyle().setBorderColor("black");
			mbWindow[3]=1;
		}

		if(res.getCorrectMB() == 4){
			forgetBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			forgetBtn.getElement().getStyle().setBorderColor("black");
			mbWindow[4]=1;
		}
		else{	
		}

		//for PV
		HorizontalPanel pvPanel = new HorizontalPanel();
		mbPvSectionPanel.add(pvPanel);

		pvLabel = new Label("PV");
		pvPanel.add(pvLabel);
		pvLabel.setStyleName("pvLabel");

		gold = new Button();
		pvPanel.add(gold);
		gold.setSize("25px", "15px");
		gold.setStyleName("gold");
		gold.addClickHandler(this);

		silver = new Button();
		pvPanel.add(silver);
		silver.setSize("25px", "15px");
		silver.setStyleName("silver");
		silver.addClickHandler(this);

		bronze = new Button();
		pvPanel.add(bronze);
		bronze.setSize("25px", "15px");
		bronze.setStyleName("bronze");
		bronze.addClickHandler(this);

		wood = new Button();
		pvPanel.add(wood);
		wood.setSize("25px", "15px");
		wood.setStyleName("wood");
		wood.addClickHandler(this);

		ash = new Button();
		pvPanel.add(ash);
		ash.setSize("25px", "15px");
		ash.setStyleName("ash");
		ash.addClickHandler(this);


		pvExplainBtn = new Button("Explain");
		pvPanel.add(pvExplainBtn);
		pvExplainBtn.setHeight("15px");
		pvExplainBtn.setStyleName("pvExplainBtn");
		pvExplainBtn.addClickHandler(this);

		//during loading-highlighting corresponding PV button.

		//here label are not in order with the color; changes made just for demo purpose
		if(res.getCorrectPV() == 0.0){
			gold.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			gold.getElement().getStyle().setBorderColor("black");
			pvWindow[0]=1;
		}

		else if(res.getCorrectPV() == 1.0){
			silver.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			silver.getElement().getStyle().setBorderColor("black");
			pvWindow[1]=1;
		}

		else if(res.getCorrectPV() == 2.0){
			bronze.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			bronze.getElement().getStyle().setBorderColor("black");
			pvWindow[2]=1;
		}

		else if(res.getCorrectPV() == 3.0){
			wood.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			wood.getElement().getStyle().setBorderColor("black");
			pvWindow[3]=1;
		}

		else if(res.getCorrectPV() == 4.0){
			ash.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			ash.getElement().getStyle().setBorderColor("black");
			pvWindow[4]=1;
		}

		else {

		}
	}

	//Handle all the click event here
	public void onClick(ClickEvent event) {

		Widget sender = (Widget)event.getSource();

		//for MB
		if(sender == hotBtn){
			hotBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			hotBtn.getElement().getStyle().setBorderColor("black");

			warmBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			lukeWarmBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			coldBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			forgetBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);

			//set the mb value
			res.setCorrectMB(0.99);

			clearWindow("mbWindow");
			mbWindow[0]=1;

			//this indicate that this button has been changed and used in the resource_container class
			res.setStatus(true);

			//Window.alert("MB = 0.99");
		}
		if(sender == warmBtn){

			warmBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			warmBtn.getElement().getStyle().setBorderColor("black");

			hotBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			lukeWarmBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			coldBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			forgetBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);

			//set the mb value
			res.setCorrectMB(0.8);

			clearWindow("mbWindow");
			mbWindow[1]=1;

			//this indicate that this button has been changed
			res.setStatus(true);

		}
		if(sender == lukeWarmBtn){
			lukeWarmBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			lukeWarmBtn.getElement().getStyle().setBorderColor("black");

			hotBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			warmBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			coldBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			forgetBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);

			//set the mb value
			res.setCorrectMB(0.5);

			clearWindow("mbWindow");
			mbWindow[2]=1;

			//this indicate that this button has been changed
			res.setStatus(true);

		}
		if(sender == coldBtn){
			coldBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			coldBtn.getElement().getStyle().setBorderColor("black");

			hotBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			warmBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			lukeWarmBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			forgetBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);

			//set the mb value
			res.setCorrectMB(0.2);

			clearWindow("mbWindow");
			mbWindow[3]=1;

			//this indicate that this button has been changed
			res.setStatus(true);

		}
		if(sender == forgetBtn){
			forgetBtn.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			forgetBtn.getElement().getStyle().setBorderColor("black");

			hotBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			warmBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			lukeWarmBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			coldBtn.getElement().getStyle().setBorderStyle(BorderStyle.NONE);

			//set the mb value
			res.setCorrectMB(0.1);
			clearWindow("mbWindow");
			mbWindow[4]=1;

			//this indicate that this button has been changed
			res.setStatus(true);

		}
		if(sender == mbExplainBtn){
			
			PopupPanel pop = new PopupPanel(true);
			pop.add(new Label("This module is under development"));
			pop.center();
			pop.show();

			//identify which MB button has been selected
			int pos=-1;
			for(int i = 0;i<5;i++){
				if(mbWindow[i]==1){
					pos = i;
					break;
				}
			}
		}

		//-------------------------for PV--------------------------------------------
		if(sender == gold){
			gold.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			gold.getElement().getStyle().setBorderColor("black");

			silver.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			bronze.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			wood.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			ash.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			
			setPvValue(0);
			res.setCorrectPV(0);
		}
		if(sender == silver){
			
			silver.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			silver.getElement().getStyle().setBorderColor("black");

			gold.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			bronze.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			wood.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			ash.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			
			setPvValue(1);
			res.setCorrectPV(1);

		}
		if(sender == bronze){

			bronze.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			bronze.getElement().getStyle().setBorderColor("black");

			silver.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			gold.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			wood.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			ash.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			
			setPvValue(2);
			res.setCorrectPV(2);
		}
		if(sender == wood){

			wood.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			wood.getElement().getStyle().setBorderColor("black");

			silver.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			bronze.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			gold.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			ash.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			
			setPvValue(3);
			res.setCorrectPV(3);
		}
		if(sender == ash){

			ash.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			ash.getElement().getStyle().setBorderColor("black");

			silver.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			bronze.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			wood.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			gold.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
			
			setPvValue(4);
			res.setCorrectPV(4);
		}
		if(sender == pvExplainBtn){

			serverServices.returnRuleSetDescription(new AsyncCallback<Map<String,String>>() {

				public void onSuccess(Map<String, String> ruleDescription) {

//					String msg = "Learned PV: "+res.getGenMB()+"\nCorrected MB: "+res.getCorrectMB();
//					msg = msg+"\nRule applied: "+ruleDescription.get(res.getRuleSet());

					VerticalPanel vPanel = new VerticalPanel();
					vPanel.add(new Label("Learned PV: "+res.getGenPV()));
					vPanel.add(new Label("Corrected PV: "+res.getCorrectPV()));
					vPanel.add(new Label("Rule applied: "+ruleDescription.get(res.getRuleSet())));
					//vPanel.add(new Label(ruleDescription.get("demo1-session")));

					PopupPanel pop = new PopupPanel(true);
					pop.add(vPanel);
					pop.center();
					pop.show();

				}

				public void onFailure(Throwable caught) {
					Window.alert("Unable to get server responce : "+caught.getMessage());

				}
			});

		}
	}

	//this method is used to clear the window 
	public void clearWindow(String window){
		if(window.equalsIgnoreCase("mbWindow"))
		{
			for(int i=0;i<5;i++)
				mbWindow[i]=0;
		}

		//it is pvWindow then, clean the pvWindow
		else{
			for(int i=0;i<5;i++){
				pvWindow[i]=0;
			}
		}
	}

	public double getPvValue() {
		return pvValue;
	}

	public void setPvValue(double pvValue) {
		this.pvValue = pvValue;
	}
	
	
}
