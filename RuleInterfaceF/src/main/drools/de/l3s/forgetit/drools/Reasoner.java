package de.l3s.forgetit.drools;

import java.util.ArrayList;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import datamodels.rules_document;
import datamodels.rules_document_person;
import datamodels.rules_document_relation;
import datamodels.rules_external_resources;
import datamodels.rules_image;
import datamodels.rules_person;
import datamodels.rules_task;
import datamodels.rules_task_document;
import de.l3s.forgetit.drools.models.Hello;
import de.l3s.forgetit.model.Resources;
import de.l3s.forgetit.server.ContentHandling;

public class Reasoner {

	private String sessionName;
	
	public String computeSessionName(){
		this.setSessionName("demo2-session");
		return this.getSessionName();
	}

	//This method will compute and return the session name
	public String computeSessionName(int userId, String scenario){
		String jsonString = new ContentHandling().returnFromrules_qa(userId, scenario);
		
		if(Config.qa2ruleSet.get(jsonString) != null){
			this.setSessionName(Config.qa2ruleSet.get(jsonString));
		}
		else
			this.setSessionName("demo1-session");
		
		return this.getSessionName();
	}

	/*
	 * This method is intended jut for the testing
	 * @param
	 * sessionName : this represent the the ruleset in our case
	 */

	public ArrayList<Resources> fireRule(int userId,ArrayList<Resources> resList)
	{		
		ContentHandling ch = new ContentHandling();

		String sessionName = this.computeSessionName(userId, "Meeting");
		
		System.out.println("------this is the session name"+sessionName);
		ArrayList<rules_document> rdList = new ArrayList<rules_document>();
		rules_document rd;
		

		try {
			ReleaseIdImpl releaseId = new ReleaseIdImpl("de.kbs.forgetit", "Ruleset", "1.0");
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.newKieContainer(releaseId);
			KieSession kSession = kContainer.newKieSession(sessionName);
			
			//insert person obj into the session
			rules_person person = ch.returnFromPerson(userId);
			kSession.insert(person);
			
			//insert all the necessary objects in to session
			for(Resources res : resList){
				
				//session name is the rule set name in the resources obj
				res.setRuleSet(sessionName);
				
				//build the rules_document object
				rd = new rules_document();
				rd.setId(res.getId());
				rd.setDocName(res.getName());
				rd.setDocType(res.getType());
				rd.setGenMB(res.getGenMB());
				rd.setGenPV(res.getGenPV());
				rd.setCorrectedMB(res.getCorrectMB());
				rd.setCorrectedPV(String.valueOf(res.getCorrectPV()));
				rd.setScenario(res.getScenario());
				rd.setSemanticType(res.getSemanticType());
				
				rdList.add(rd);
				kSession.insert(rd);
				
				//insert rules_document_person obj to the session
				for(rules_document_person rdp : ch.returnFromDocumentPerson(userId)){
					
					kSession.insert(rdp);
				}

				//insert rules_document_relation objects into session
				for(rules_document_relation rdr : ch.returnFromDocumentRelation(res.getId())){
					kSession.insert(rdr);
				}

				//insert rules_external_resources objects into session
				for(rules_external_resources reres : ch.returnFromExternalRes(res.getId())){
					kSession.insert(reres);
				}

				//insert rules_imaage objects into session
				for(rules_image rm : ch.returnFromImage(res.getName())){

					kSession.insert(rm);
				}

				//insert into the rules_task_document
				for(rules_task_document rtd : ch.returnFromTaskDocument(res.getId())){
					kSession.insert(rtd);
				}

				//insert into rules_task
				for(rules_task rt : ch.returnFromTask(res.getId())){

					kSession.insert(rt);
				}
			}
			
			kSession.fireAllRules();			
			kSession.dispose();

			System.out.println("All the rules seems to be fired successfully");

			//now copy all changes from rules_doculemt back to the resources
			for(Resources r : resList){
				for(rules_document rDocument : rdList){

					if(r.getId() == rDocument.getId()){
						System.out.println(r.getCorrectPV()+" -> "+rDocument.getCorrectedPV());
						r.setCorrectPV(Double.parseDouble(rDocument.getCorrectedPV()));
						break;
					}
				}
			}

		} catch (Throwable t) {
			t.printStackTrace();
		}


		return resList;
	} 

	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	// This method will be consider for the final implementation

	//	public ArrayList<Resources> fireRule(int userId,ArrayList<Resources> resList)
	//	{
	//		MachineLearner machineLearner = new MachineLearner();
	//		String ruleSet = null;
	//		ReleaseIdImpl releaseId;
	//		KieServices ks = KieServices.Factory.get();
	//		KieContainer kContainer;
	//		StatelessKieSession kSession;
	//
	//		try {
	//
	//			for(Resources r : resList){
	//
	//				ruleSet = machineLearner.generateRulesetUsingJ48(userId, r.getScenario());
	//				releaseId = new ReleaseIdImpl("de.l3s.forgetit.drools", ruleSet, "LATEST");
	//				kContainer = ks.newKieContainer(releaseId);
	//				kSession =  kContainer.newStatelessKieSession();
	//				kSession.execute(r);
	//			}
	//
	//
	//		} catch (Throwable t) {
	//			t.printStackTrace();
	//		}
	//
	//		return resList;
	//	}

	//This is for testing purpose
	public ArrayList<Resources> fireHelloRules(){

		ArrayList<Resources> resLists = new ArrayList<Resources>();

		System.out.println("-----------This is start of fileHelloRules()------");

		ArrayList<Hello> helloLists = new ArrayList<Hello>();

		//String url = "http://localhost:8080/kie-drools-wb-distribution-wars-6.0.1.Final-tomcat7.0/maven2/de/l3s/forgetit/drools/RulesFromDroolsWB/1.0/RulesFromDroolsWB-1.0.jar";

		ReleaseIdImpl releaseId = new ReleaseIdImpl("de.l3s.forgetit.drools", "RulesFromDroolsWB", "LATEST");

		System.out.println("--------ReleasedIdImpl object is created-----------");

		KieServices ks = KieServices.Factory.get();

		//ks.getResources().newUrlResource(url);

		System.out.println("---------URL resources has been added to the classpath-------");
		System.out.println("----ReleaseId : "+releaseId.getArtifactId()+"----------");

		KieContainer kContainer = ks.newKieContainer(releaseId);

		System.out.println("------kie container created successfully-------");

		//		// check every 5 seconds if there is a new version at the URL
		//		KieScanner kieScanner = ks.newKieScanner(kContainer);
		//		kieScanner.start(5000L);

		StatelessKieSession kSession =  kContainer.newStatelessKieSession();

		System.out.println("--------kSession is created successfully---------");

		Hello h1 = new Hello();
		h1.setId(4);
		h1.setLabel("h1-rule not fired");
		helloLists.add(h1);

		Hello h2 = new Hello();
		h2.setId(7);
		h2.setLabel("h2-rule not fired");
		helloLists.add(h2);

		kSession.execute(helloLists);

		System.out.println("All the rules seems to be fired successfully");

		//now populate the helloList
		for(Hello obj : helloLists){

			Resources res = new Resources();
			// res.setCorrectPV(obj.getLabel());
			resLists.add(res);
		}



		return resLists;
	}

	//	public Collection findFacts(final KieSession session, final Class className ) 
	//	{ 
	//	        ObjectFilter filter = new ObjectFilter() 
	//	        {  
	//	            public boolean accept( Object object ) 
	//	            { 
	//	            	  return object.getClass().equals(className);  
	//	            } 
	//	        }; 
	//
	//	        Collection results = session.getObjects( filter );
	//	        return results; 
	//	}	

}
