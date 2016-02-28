package de.l3s.forgetit.server;

import java.io.BufferedReader;
import java.io.StringReader;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;

import com.google.gson.Gson;

import de.l3s.forgetit.model.QuestionAns;

/*
 * This class generate arff as a string
 */
public class MachineLearner {

	//This method generate the arff as string return it
	public String generateARFFString(int userId, String scenarioName){

		ContentHandling ch = new ContentHandling();
		String jsonString;

		String arffString = "";

		//Generate header
		arffString = "@RELATION "+scenarioName+"\n\n";
		arffString = arffString+"@ATTRIBUTE userName string\n";
		arffString = arffString+"@ATTRIBUTE age {0-18,18-23,23-30,30-40,over 40}\n";
		arffString = arffString+"@ATTRIBUTE sex {Male,Female}\n";
		arffString = arffString+"@ATTRIBUTE role string\n";

		jsonString = ch.returnFromrules_qa(userId, scenarioName);

		Gson jsonObj = new Gson();
		QuestionAns qaAns = jsonObj.fromJson(jsonString, QuestionAns.class);

		for(int key : qaAns.getQa().keySet()){

			if(ch.returnQuestionTypeById(key).equalsIgnoreCase("mc")){

				String answer = qaAns.getQa().get(key);
				for(int k = 0; k<answer.length(); k++){
					arffString = arffString+"@ATTRIBUTE ques"+key+"."+k+" numeric\n";
				}
			}
			else
				arffString = arffString+"@ATTRIBUTE ques"+key+" numeric\n";
		}

		arffString = arffString+"\n\n\n";

		//now write data
		arffString = arffString+"@DATA\n";


		arffString = arffString+ch.returnUserNameById(userId)+" "+ch.returnAge(userId)+" "+ch.returnSex(userId)+" "+ch.returnRole(userId);
		for(int questionID : qaAns.getQa().keySet()){

			if(qaAns.getQa().get(questionID).equals("?")){

				arffString = arffString+" ?";
			}

			else if(ch.returnQuestionTypeById(questionID).equalsIgnoreCase("mc")){

				String ans = qaAns.getQa().get(questionID);

				for(int i=0;i<ans.length();i++){

					arffString = arffString+" "+ans.charAt(i);

				}

				//arffString = arffString+" "+Integer.parseInt(qaAns.getQa().get(questionID));
			}
			else{

				arffString = arffString+" "+qaAns.getQa().get(questionID).hashCode();
			}
		}

		DBHandler.closeDBConnection(ch.con);

		System.out.println("\n----------ARFF file look like this---------------");
		System.out.println(arffString);
		System.out.println("\n----------end of ARFF file-----------------------");

		return arffString;
	}

	//this method generate and return the ruleset
	public String generateRulesetUsingJ48(int userId, String scenario){

		String ruleSet = null;

		String arffString = generateARFFString(userId, scenario);

		BufferedReader reader = new BufferedReader(new StringReader(arffString));
		Instances instances;
		Classifier fs;
		try {
			instances = new Instances(reader);
			reader.close();

			fs= (Classifier) weka.core.SerializationHelper.read("some model file");
			J48 cf = new J48();
			double clsLabel = fs.classifyInstance(instances.instance(0));
			
			//double clsLabel = instances.instance(0).classValue();
			
			//check the clsLabel for the range and assign the ruleset
			if(clsLabel <= 0.5){
				
				ruleSet = "rulesetA";
			}
			else if(clsLabel == 0.6){
				
				ruleSet = "rulesetB";
			}
			else{
				
				ruleSet = "rulesetC";
			}
			
			System.out.print("label: " + instances.instance(0).classValue());
			System.out.println("*****Number of instances : "+instances.numInstances());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ruleSet;
	}

}
