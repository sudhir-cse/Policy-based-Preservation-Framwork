package de.l3s.forgetit.client;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import de.l3s.forgetit.model.QuestionData;
import de.l3s.forgetit.presenter.RuleServices;
import de.l3s.forgetit.presenter.RuleServicesAsync;

public class QuestionPage extends Composite implements ClickHandler {

	private final RuleServicesAsync serverServices = GWT.create(RuleServices.class);
	private MainViewController mvc = new MainViewController();
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel headerPanel = new HorizontalPanel();
	private VerticalPanel bodyPanel = new VerticalPanel();
	private HorizontalPanel footerPanel = new HorizontalPanel(); 
	private Button backBtn = new Button("Back to options");
	private Button prevBtn = new Button("Previous");
	private Button saveBtn = new Button("Save");
	private Button nextBtn = new Button("Next");
	private Label pageNoDisp = new Label("");

	//paging
	private int pageSize = 10;
	private int totalPageNo;
	private int currentPageNo;
	private int temp; //this is just a temproary variable used to store the intermediate results

	ArrayList<QuestionWidget> questionWidgetList = new ArrayList<QuestionWidget>();
	ArrayList<QuestionData> questionList = new ArrayList<QuestionData>();

	public QuestionPage(MainViewController mvc, ArrayList<QuestionData> qDataList){
		initWidget(mainPanel);

		this.mvc = mvc;
		this.questionList = qDataList;
		
		//
		for(QuestionData questionData: questionList){
			
			questionWidgetList.add(new QuestionWidget(questionData) );
		}

//		//------------populating the data--------------------
//		QuestionData q1 = new QuestionData();
//		q1.setQuestion("What is the capital city of Tunesia1");
//		q1.setQuestionNo(1);
//		q1.setType("mc");
//		HashMap<String,Integer> ops = new HashMap<String,Integer>();
//		ops.put("tunis", 1);
//		ops.put("sfax", 2);
//		ops.put("sousse", 3);
//		ops.put("monastir", 4);
//		q1.setOptions(ops);
//		
//		
//		QuestionData q2 = new QuestionData();
//		q2.setQuestion("What is the capital city of Nepal");
//		q2.setQuestionNo(2);
//		q2.setType("mc");
//		HashMap<String,Integer> ops1 = new HashMap<String,Integer>();
//		ops1.put("Kathamandu", 10);
//		ops1.put("Birgunj", 11);
//		ops1.put("Pokhara", 12);
//		ops1.put("Biratnagar", 13);
//		ops1.put("Pokharia", 14);
//		ops1.put("sikata", 14);
//		q2.setOptions(ops1);
//		
//		QuestionData q3 = new QuestionData();
//		q3.setQuestion("Please privide your name here");
//		q3.setQuestionNo(3);
//		q3.setType("fc");
//		
//		QuestionData q4 = new QuestionData();
//		q4.setQuestion("Please privide the name of your friend here");
//		q4.setQuestionNo(4);
//		q4.setType("fc");
//		
//		QuestionData q5 = new QuestionData();
//		q5.setQuestion("Do you think the photos from the last meeting is important");
//		q5.setQuestionNo(5);
//		q5.setType("ync");
//		
//		QuestionData q6 = new QuestionData();
//		q6.setQuestion("Did you enjoyed your last travell to Turkey");
//		q6.setQuestionNo(6);
//		q6.setType("ync");
//		
//	
//
//		questionList.add(q1);
//		questionList.add(q2);
//		questionList.add(q3);
//		questionList.add(q4);
//		questionList.add(q5);
//		questionList.add(q6);
//		
//
//		questionWidgetList.add(new QuestionWidget(q1));
//		questionWidgetList.add(new QuestionWidget(q3));
//		questionWidgetList.add(new QuestionWidget(q2));
//		questionWidgetList.add(new QuestionWidget(q6));
//
//		questionWidgetList.add(new QuestionWidget(q5));
//		questionWidgetList.add(new QuestionWidget(q4));
		

		//------------populating the data--------------------

		prevBtn.setEnabled(false);
		saveBtn.setEnabled(false);
		nextBtn.setEnabled(false);

		//Header--------------------
		mainPanel.add(headerPanel);
		headerPanel.setWidth("1000PX");
		Label headerDescription = new Label("Choose one answer the fits your preferences most");
		headerDescription.getElement().getStyle().setFontSize(17, Style.Unit.PX);
		headerDescription.getElement().getStyle().setFontWeight(FontWeight.BOLD);
		headerDescription.getElement().getStyle().setMargin(10, Style.Unit.PX);
		headerPanel.add(headerDescription);
		headerPanel.getElement().getStyle().setMarginBottom(10, Style.Unit.PX);
		headerPanel.getElement().getStyle().setBackgroundColor("#96c11f");

		//body---------------------
		mainPanel.add(bodyPanel);
		if(pageSize >= questionWidgetList.size()){
			for(QuestionWidget qw1 : questionWidgetList ){
				bodyPanel.add(qw1);
			}
			pageNoDisp.setText("Page : 1/1");
			saveBtn.setEnabled(true);
		}
		else{
			//compute the total no of pages
			temp = questionWidgetList.size()/pageSize;
			if(temp*pageSize == questionWidgetList.size())
				totalPageNo = temp;
			else
				totalPageNo = temp+1;

			//display the first page
			for(temp = 0; temp < pageSize; temp++)
				bodyPanel.add(questionWidgetList.get(temp));

			currentPageNo = 1;
			pageNoDisp.setText("Page : "+currentPageNo+"/"+totalPageNo);

			nextBtn.setEnabled(true);

		}
		//footer-------------------
		mainPanel.add(footerPanel);
		footerPanel.add(backBtn);
		footerPanel.add(prevBtn);
		footerPanel.add(saveBtn);
		footerPanel.add(nextBtn);
		footerPanel.add(pageNoDisp);
		footerPanel.setWidth("1000PX");
		footerPanel.getElement().setAttribute("id", "QpFooterPanel");
		footerPanel.getElement().getStyle().setMarginBottom(15, Style.Unit.PX);

		backBtn.getElement().getStyle().setMarginLeft(10, Style.Unit.PX);
		//backBtn.setWidth("75px");

		prevBtn.getElement().getStyle().setMarginLeft(275, Style.Unit.PX);
		prevBtn.getElement().getStyle().setMarginRight(20, Style.Unit.PX);
		prevBtn.setWidth("75px");

		//saveBtn.getElement().getStyle().clearMargin();
		saveBtn.getElement().getStyle().setMarginRight(20, Style.Unit.PX);
		saveBtn.setWidth("75px");

		nextBtn.setWidth("75px");

		pageNoDisp.getElement().getStyle().setMarginLeft(270, Style.Unit.PX);

		backBtn.addClickHandler(this);
		prevBtn.addClickHandler(this);
		saveBtn.addClickHandler(this);
		nextBtn.addClickHandler(this);

	}

	//This class will render all type of questions and options
	public class QuestionWidget extends Composite implements ClickHandler{

		private HorizontalPanel mPanel = new HorizontalPanel(); 
		private QuestionData qd;
		
		private CheckBox cbOption;
		
		private TextBox fcOption;
		
		private RadioButton yesRbOptions;
		private RadioButton noRbOptions;

		// these two variables are used to store the multiple choice questions into binary formats
		private String[] optionsName;
		private int[] binaryAns;
		private String tempAns;

		public QuestionWidget(QuestionData qd1){
			initWidget(mPanel);

			setQd(qd1);

			mPanel.getElement().getStyle().clearFontSize();
			mPanel.getElement().setAttribute("id", "qMainPanel");

			//to display the question no.
			VerticalPanel qNoPanel = new VerticalPanel();
			mPanel.add(qNoPanel);
			Label qNoLabel = new Label(qd.getQuestionNo()+".");
			qNoPanel.add(qNoLabel);

			//to display the questions and their options
			VerticalPanel questionAndOptionPanel = new VerticalPanel();
			mPanel.add(questionAndOptionPanel);
			Label questionLabel = new Label(qd.getQuestion()); //this the question
			questionAndOptionPanel.add(questionLabel);

			/*Render the options based on its type
			 * 
			 * Options is presented by the following type
			 * 1. multiple choice : answers are stored as a concatination of all the selected options in the String variable
			 * 2. free options : a text box will be provided as a option where user can input the answer and again stored in the string variable
			 * 3. yes/no : answers are stored as either "0" or "1"
			 *
			 * option type should one of the following
			 * 1. mc (for multiple choice)
			 * 2. fc (for free choice)
			 * 2. ync (for yes/no)
			 */

			//multiple choice options
			if(qd.getType().equalsIgnoreCase("mc")){
				
				optionsName = new String[qd.getOptions().keySet().size()];
				binaryAns = new int[qd.getOptions().keySet().size()];
				
				//Initialized these two variables
				int i=0;
				for(String opt : qd.getOptions().keySet()){
					optionsName[i++] = opt;// store all the options here 
				}
				for(int j=0;j<binaryAns.length;j++) 
					binaryAns[j]=0; //meaning all the options are unchecked at the first place
				
				for(String opt : qd.getOptions().keySet()){

					cbOption = new CheckBox(opt);
					cbOption.setTitle(opt);
					questionAndOptionPanel.add(cbOption);
					int optId = qd.getDefaultOption();
					if (optId == qd.getOptions().get(opt)) {
						cbOption.setValue(Boolean.TRUE);
					}
					cbOption.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {

							boolean checked = ((CheckBox) event.getSource()).getValue();
							
							Widget sender = (Widget) event.getSource();
							
							if(checked){

								binaryAns[Arrays.asList(optionsName).indexOf(sender.getTitle())] = 1;
								
//								temp = "Checked - clicked on: "+cbOption.getTitle()+"\n"+" ,Its index is: "+Arrays.asList(optionsName).indexOf(cbOption.getTitle())+"value in binaryAns: "+binaryAns[Arrays.asList(optionsName).indexOf(cbOption.getTitle())];                       
//								Window.alert(temp);
							}
							//uncheck
							else{
								
								binaryAns[Arrays.asList(optionsName).indexOf(sender.getTitle())] = 0;
//								temp = "clicked on: "+cbOption.getTitle()+"\n"+" ,Its index is: "+Arrays.asList(optionsName).indexOf(cbOption.getTitle())+"value in binaryAns: "+binaryAns[Arrays.asList(optionsName).indexOf(cbOption.getTitle())];
//								Window.alert(temp);
							}
							
							
							tempAns = "";
							//now populate the answers field of the qd object
							for(int key : binaryAns){
								tempAns = tempAns+key;
							}
							qd.setAnswers(tempAns);
						}
					});
				}
			}

			//free choice questions/options
			else if(qd.getType().equalsIgnoreCase("fc")){

				fcOption = new TextBox();
				fcOption.setWidth("200px");
				//fcOption.getElement().getStyle().setPadding(5, Style.Unit.PX);
				fcOption.getElement().getStyle().setBorderStyle(BorderStyle.NONE);

				//onFocus event
				fcOption.addFocusHandler(new FocusHandler() {

					public void onFocus(FocusEvent arg0) {
						fcOption.getElement().getStyle().setBackgroundColor("#FFFFD0");
					}
				});
				
				// onFocusLost event
				fcOption.addBlurHandler(new BlurHandler() {
					
					public void onBlur(BlurEvent arg0) {
						fcOption.getElement().getStyle().setBackgroundColor("white");
						qd.setAnswers(fcOption.getText());
					}
				});
					
				questionAndOptionPanel.add(fcOption);
			}

			// yes/no type options
			else if (qd.getType().equalsIgnoreCase("ync")){

				yesRbOptions = new RadioButton(qd.getQuestion(),"Yes");
				noRbOptions = new RadioButton(qd.getQuestion(),"No");

				yesRbOptions.addClickHandler(this);
				noRbOptions.addClickHandler(this);

				questionAndOptionPanel.add(yesRbOptions);
				questionAndOptionPanel.add(noRbOptions);

			}
			else{
				
			}

		}

		//All the click handler goes here
		public void onClick(ClickEvent arg0) {
			
			Widget sender = (Widget)arg0.getSource();
			
			if(sender == yesRbOptions){
				qd.setAnswers("1");
			}
			
			if(sender == noRbOptions){
				qd.setAnswers("0");
			}
		}
		
		public QuestionData getQd() {
			return qd;
		}

		public void setQd(QuestionData qd) {
			this.qd = qd;
		}

		
	}

	//make QuestionWidget class to cell
	public class QuestionWidgetCell extends AbstractCell<QuestionWidget> implements Cell<QuestionWidget>{

		public QuestionWidgetCell(){
			super("click");
		}

		@Override
		public void render(com.google.gwt.cell.client.Cell.Context context,QuestionWidget value, SafeHtmlBuilder sb) {

			sb.appendHtmlConstant("<div style=\"padding:0px;\">");
			if (value != null) {
				sb.appendHtmlConstant(value.toString());
			}
			sb.appendHtmlConstant("</div>");        	
		}	
	}

	//all the click handler goes here
	public void onClick(ClickEvent event) {

		Widget sender = (Widget)event.getSource();

		if(sender == backBtn){
			mvc.openUpdatePrefOrManageDoc();
		}

		if(sender == prevBtn){

			currentPageNo = currentPageNo-1;
			
			saveBtn.setEnabled(false); //save will always be enable on the last page

			pageNoDisp.setText("Page : "+currentPageNo+"/"+totalPageNo);

			if(currentPageNo>=1){

				if(!nextBtn.isEnabled())
					nextBtn.setEnabled(true);

				bodyPanel.clear();
				for(temp = (currentPageNo*pageSize-pageSize); temp < (currentPageNo*pageSize); temp++){
					bodyPanel.add(questionWidgetList.get(temp));
				}

			}
			if(currentPageNo <= 1){
				prevBtn.setEnabled(false);
				bodyPanel.clear();
				for(temp = 0; temp < pageSize; temp++)
					bodyPanel.add(questionWidgetList.get(temp));
			}	

		}
		if(sender == saveBtn){
			saveQuestions(questionList);
			
//			//this just for the testing; display all answers from all the qd object
//			String str = "";
//			for(QuestionData q : questionList){
//				str = str+q.getAnswers()+" - ";
//			}
//			Window.alert(str);
			
		}
		if(sender == nextBtn){

			currentPageNo = currentPageNo+1;

			pageNoDisp.setText("Page : "+currentPageNo+"/"+totalPageNo);

			if(!prevBtn.isEnabled())
				prevBtn.setEnabled(true);

			if(currentPageNo < totalPageNo){

				bodyPanel.clear();
				for(temp = (currentPageNo*pageSize-pageSize); temp < (currentPageNo*pageSize); temp++){
					bodyPanel.add(questionWidgetList.get(temp));
				}

			}
			else if(currentPageNo == totalPageNo){

				bodyPanel.clear();
				for(temp = ((currentPageNo-1)*pageSize); temp< questionWidgetList.size(); temp++ ){

					bodyPanel.add(questionWidgetList.get(temp));
				}
				nextBtn.setEnabled(false);
				saveBtn.setEnabled(true);
			}
			else{


			}
		}
	}

	private void saveQuestions(ArrayList<QuestionData> qlist){
		
		serverServices.changeQuestionIntoJSONAndSave(qlist, new AsyncCallback<Boolean>(){

			public void onFailure(Throwable caught) {
				Window.alert("Unable to obtain server response: "+ caught.getMessage());
			}

			public void onSuccess(Boolean caught) {
				Window.alert("All the questions are saves successfully");
			}

		});
	}

	public List<QuestionData> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(ArrayList<QuestionData> questionList) {
		this.questionList = questionList;
	}


}
