package de.l3s.forgetit.drools;

import java.util.ArrayList;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import de.l3s.forgetit.drools.models.Hello;
import de.l3s.forgetit.model.Resources;
import de.l3s.forgetit.server.MachineLearner;

public class ReasonMB {

	public ArrayList<Resources> fireRule(int userId,ArrayList<Resources> resList)
	{
		MachineLearner machineLearner = new MachineLearner();
		String ruleSet = null;
		ReleaseIdImpl releaseId;
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer;
		StatelessKieSession kSession;

		try {

			for(Resources r : resList){

				ruleSet = machineLearner.generateRulesetUsingJ48(userId, r.getScenario());
				releaseId = new ReleaseIdImpl("de.l3s.forgetit.drools", ruleSet, "LATEST");
				kContainer = ks.newKieContainer(releaseId);
				kSession =  kContainer.newStatelessKieSession();
				kSession.execute(r);
			}


		} catch (Throwable t) {
			t.printStackTrace();
		}

		return resList;
	}

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

