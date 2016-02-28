package de.l3s.forgetit.model;

import java.io.Serializable;

public class ScenarioData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String label; //this represent the name of the scenario
	private String imgeURL;
	//private int flag; //helps in identifying which scenario has been clicked over
	private String toolTip;
	
	public ScenarioData(){
		
	}
	public ScenarioData(String photoLabel, String photoUrl,String toolTip){
		this.label = photoLabel;
		this.imgeURL = photoUrl;
		this.toolTip = toolTip;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getImgeURL() {
		return imgeURL;
	}
	public void setImgeURL(String imgeURL) {
		this.imgeURL = imgeURL;
	}
	public String getToolTip() {
		return toolTip;
	}
	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}
	
}
