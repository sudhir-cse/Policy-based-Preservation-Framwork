package de.l3s.forgetit.server;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import datamodels.rules_document_person;
import datamodels.rules_document_relation;
import datamodels.rules_external_resources;
import datamodels.rules_image;
import datamodels.rules_person;
import datamodels.rules_task;
import datamodels.rules_task_document;
import de.l3s.forgetit.model.Organization;
import de.l3s.forgetit.model.OrganizationalUnit;
import de.l3s.forgetit.model.Person;
import de.l3s.forgetit.model.QuestionData;
import de.l3s.forgetit.model.Resources;
import de.l3s.forgetit.model.Role;
import de.l3s.forgetit.model.SignupFormData;
import de.l3s.forgetit.model.StringPair;

/*
 * This class contains all the APIs
 */

public class ContentHandling {

	//declaring class variables
	public Connection con;
	private PreparedStatement pstmt;
	private ResultSet res;

	private PreparedStatement pstmt1;
	private ResultSet res1;
	private ResultSet res2;
	/**
	 * Constructor
	 */
	public ContentHandling()
	{
		con = DBHandler.getConnection();
	}

	/**
	 * Constructor 
	 * @param con   Connection object
	 */

	public ContentHandling(Connection con)
	{
		this.con = con;
	}

	//this method is used to insert into person table return the id
	public int insertIntoPerson(Person person){

		int p_id=0;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try {
				if(person.getName() != null && !person.getName().isEmpty()){

					pstmt = con.prepareStatement("insert ignore into rules_person(name,sex,age,emailAddress,password,userName,pref) values(?,?,?,?,?,?,?)");
					pstmt.setString(1,person.getName()); 
					pstmt.setString(2, person.getSex());
					pstmt.setString(3, person.getAge());
					pstmt.setString(4,person.getEmail());
					pstmt.setString(5,person.getPassword());
					pstmt.setString(6, person.getUserName());
					pstmt.setString(7, person.getPref());
					pstmt.executeUpdate();

					pstmt = con.prepareStatement("select id from rules_person where userName = ?");
					pstmt.setString(1,person.getUserName());
					res = pstmt.executeQuery(); 
					if(res.next())
						p_id = res.getInt("id");        
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return p_id;
	}

	//this method is used to insert into organization table return the id
	public int insertIntoOrganization(Organization org){

		int orgID=0;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try {
				if(org.getName() != null && !org.getName().isEmpty()){

					pstmt = con.prepareStatement("insert ignore into rules_organization(name,description) values(?,?)");
					pstmt.setString(1,org.getName()); 
					pstmt.setString(2, org.getDescription());
					pstmt.executeUpdate();

					pstmt = con.prepareStatement("select id from rules_organization where name = ?");
					pstmt.setString(1,org.getName());
					res = pstmt.executeQuery(); 
					if(res.next())
						orgID = res.getInt("id");        
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return orgID;
	}

	//this method is used to insert into organizationalUnit table return the id
	public int insertIntoOrganizationalUnit(OrganizationalUnit orgUnit){

		int orgUnitID=0;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try {
				if(orgUnit.getName() != null && !orgUnit.getName().isEmpty()){

					pstmt = con.prepareStatement("insert ignore into rules_organizationalUnit(name,description,locationID,organizationID) values(?,?,?,?)");
					pstmt.setString(1,orgUnit.getName()); 
					pstmt.setString(2, orgUnit.getDescription());
					pstmt.setInt(3, orgUnit.getLocationID());
					pstmt.setInt(4, orgUnit.getOrgID());
					pstmt.executeUpdate();

					pstmt = con.prepareStatement("select id from rules_organizationalUnit where name = ?");
					pstmt.setString(1,orgUnit.getName());
					res = pstmt.executeQuery(); 
					if(res.next())
						orgUnitID = res.getInt("id");        
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return orgUnitID;
	}

	//this method is used to insert into roles table return the id
	public void insertIntoRoles(Role role){

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try {

				pstmt = con.prepareStatement("insert ignore into rules_roles(personID,orgUnitID,orgID,role) values(?,?,?,?)");
				pstmt.setInt(1,role.getPersonID()); 
				pstmt.setInt(2, role.getOrgUnitID());
				pstmt.setInt(3, role.getOrgID());
				pstmt.setString(4, role.getRole());
				pstmt.executeUpdate();

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}
	}

	//This is used for user Authencation.
	public int userAuth(String email, String userName, String password)
	{
		int userID = 0; // 0 means this user does not exists 

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 
				//first look for emailAddress and password
				pstmt = con.prepareStatement("select * from rules_person where (emailAddress = ? OR userName = ?) AND password = ?");
				pstmt.setString(1, email);
				pstmt.setString(2, userName);
				pstmt.setString(3, password);
				res = pstmt.executeQuery(); 
				if(res.next())
				{
					userID = res.getInt("id");
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return userID;
	}

	//This method returns names of all the organizations
	public ArrayList<String> returnOrgNames(){

		ArrayList<String> orgNames = new ArrayList<String>();

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 
				//first look for emailAddress and password
				pstmt = con.prepareStatement("select * from rules_organization");
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					orgNames.add(res.getString("name"));
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return orgNames;
	}


	//This method returns roles name
	public ArrayList<String> returnRoleNames(){

		ArrayList<String> roleNames = new ArrayList<String>();

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 
				//first look for emailAddress and password
				pstmt = con.prepareStatement("select name from rules_roles");
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					roleNames.add(res.getString("name"));
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return roleNames;
	}


	//This method convert organization name into organizationID
	public int returnOrgID(String orgName)
	{
		int orgID=0;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try {
				if(orgName != null && !orgName.isEmpty()){

					pstmt1 = con.prepareStatement("select id from rules_organization where name = ?");
					pstmt1.setString(1,orgName);
					res1 = pstmt1.executeQuery(); 
					if(res1.next())
						orgID = res1.getInt("id");     
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt1);
				DBHandler.closeResultSet(res1);
			}
		} else {
		}

		return orgID;
	}
	//This method accept organization name as a parameter and returns names of all the corresponding organizationalUnit
	public ArrayList<String> returnOrgUnitNames(String orgName){

		ArrayList<String> orgUnitNames = new ArrayList<String>();

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 
				//first look for emailAddress and password
				pstmt = con.prepareStatement("select name from rules_organizationalUnit where organizationID = ?");
				pstmt.setInt(1, this.returnOrgID(orgName));
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					orgUnitNames.add(res.getString("name"));
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return orgUnitNames;
	}


	//This method build and return list of resources objects
	/*public ArrayList<Resources> returnResList1(int uerId){

		ArrayList<Resources> resList = new ArrayList<Resources>();

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try {

				//Build resources from rules_email table
				pstmt = con.prepareStatement("select rules_email.emailAddress, rules_email.date, rules_person.name from rules_email, rules_person where rules_email.sentBy = rules_person.id");
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					Resources r = new Resources();
					r.setName(res.getString("name"));
					r.setPublishedDate(res.getString("date"));
					r.setAuthor(res.getString("name"));
					r.setType("email");
					r.setCorrectMB(0.9);
					r.setCorrectPV(4);
					resList.add(r);
				}

				//Build resources from rules_file table
				pstmt = con.prepareStatement("select rules_file.fileName, rules_file.createdAt, rules_person.name from rules_file, rules_person where rules_file.authorID = rules_person.id");
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					Resources r = new Resources();
					r.setName(res.getString("fileName"));
					r.setPublishedDate(res.getString("createdAt"));
					r.setAuthor(res.getString("name"));
					r.setType("file");
					r.setCorrectMB(0.5);
					r.setCorrectPV("keep");
					resList.add(r);
				}

				//Build resources from rules_image table
				pstmt = con.prepareStatement("select rules_image.name, rules_image.createdAt, rules_person.name from rules_image, rules_person where rules_image.authorID = rules_person.id");
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					Resources r = new Resources();
					r.setName(res.getString(1));
					r.setPublishedDate(res.getString(2));
					r.setAuthor(res.getString(3));
					r.setType("photo");
					r.setCorrectMB(0.2);
					r.setCorrectPV("neglect");
					resList.add(r);
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return resList;
	}*/

	//----------this is to build the Resources object list and return it

	public ArrayList<Resources> returnResList(int userId){

		ArrayList<Resources> resList = new ArrayList<Resources>();

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try {

				//This will return all the documents of the current user

				pstmt = con.prepareStatement("select * from rules_document inner join rules_document_person on rules_document.id = rules_document_person.docID where rules_document_person.personID =? and scenario = 'Meeting'");
				pstmt.setInt(1, userId);
				res = pstmt.executeQuery();
				while(res.next()){

					Resources resource = new Resources();
					resource.setId(res.getInt("id"));
					resource.setScenario(res.getString("scenario"));
					resource.setSemanticType(res.getString("semanticType"));
					if(res.getString("docType").equalsIgnoreCase("file")){
						//query the rules_file table and build the file resources			
						pstmt = con.prepareStatement("select * from rules_file where fileName = ?");
						pstmt.setString(1, res.getString("docName"));
						res1 = pstmt.executeQuery();
						while(res1.next()){

							resource.setType(res1.getString("type"));

							resource.setName(res1.getString("fileName"));
							resource.setAuthor(returnPersonNameById(res1.getInt("authorID")).getLeft());		
							resource.setPublishedDate(res1.getString("createdAt"));

							resource.setCorrectMB(res.getDouble("correctedMB"));
							resource.setCorrectPV(res.getDouble("correctedPV"));
							resource.setGenMB(res.getDouble("genMB"));
							resource.setGenPV(res.getString("genPV"));

						}		
					}
					else if(res.getString("docType").equalsIgnoreCase("email")){
						//query the rules_email table and build the email resources 
						pstmt = con.prepareStatement("select * from rules_email where emailAddress = ?");
						pstmt.setString(1, res.getString("docName"));
						res1 = pstmt.executeQuery();
						while(res1.next()){

							resource.setType("email");

							resource.setName(res1.getString("emailAddress"));
							resource.setAuthor(returnPersonNameById(res1.getInt("sentBy")).getLeft());		
							resource.setPublishedDate(res1.getString("date"));

							resource.setCorrectMB(res.getDouble("correctedMB"));
							resource.setCorrectPV(res.getDouble("correctedPV"));
							resource.setGenMB(res.getDouble("genMB"));
							resource.setGenPV(res.getString("genPV"));
						}
					}

					//meaning 
					else if(res.getString("docType").equalsIgnoreCase("photo")){
						//now query the rules_image and build the image resource imageName

						pstmt = con.prepareStatement("select * from rules_image where name = ?");
						pstmt.setString(1, res.getString("docName"));
						res1 = pstmt.executeQuery();
						while(res1.next()){

							resource.setType(res1.getString("type"));

							resource.setName(res1.getString("name"));
							resource.setAuthor(returnPersonNameById(res1.getInt("authorID")).getLeft());		
							resource.setPublishedDate(res1.getString("createdAt"));

							resource.setCorrectMB(res.getDouble("correctedMB"));
							resource.setCorrectPV(res.getDouble("correctedPV"));
							resource.setGenMB(res.getDouble("genMB"));
							resource.setGenPV(res.getString("genPV"));

						}

					}
					else{
						//do nothing, this condition will never be happen
					}

					//resource.setUri("files/"+resource.getName());
					resList.add(resource);
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeResultSet(res1);

				DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return resList;
	}

	//this method is used to insert into roles table return the id
	public boolean insertIntoRulesQa(int userId, String scenario, String json){

		boolean isInserted = false;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try {

				//first check weather the entry exists already or not
				pstmt = con.prepareStatement("select * from rules_qa where userID =? and scenario = ?");
				pstmt.setInt(1, userId);
				pstmt.setString(2, scenario);
				res = pstmt.executeQuery();
				if(res.next()){
					// then update the only the content field of the table
					pstmt = con.prepareStatement("update rules_qa set content = ? where userID=? and scenario = ?");
					pstmt.setString(1, json);
					pstmt.setInt(2, userId);
					pstmt.setString(3, scenario);
					pstmt.executeUpdate();
					System.out.println("--------rules_qa table has been updated---------");
				}
				else{
					pstmt = con.prepareStatement("insert ignore into rules_qa(userID,scenario,content) values(?,?,?)");
					pstmt.setInt(1,userId); 
					pstmt.setString(2,scenario);
					pstmt.setString(3,json);
					pstmt.executeUpdate();
					isInserted = true;
				}
			}catch(SQLException sqle) {
				isInserted = false;
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}

		new MachineLearner().generateARFFString(userId, scenario);

		return isInserted;
	}

	//this method returns organizations and corresponding units
	public HashMap<String,ArrayList<String>> returnOrgAndOrgUnits(){

		HashMap<String,ArrayList<String>> result = new HashMap<String,ArrayList<String>>();

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT id,name FROM rules_organization");
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					pstmt1 = con.prepareStatement("SELECT name FROM rules_organizationalUnit where organizationID=?");
					pstmt1.setInt(1, res.getInt("id"));
					res1 = pstmt1.executeQuery();

					ArrayList<String> orgUnitList = new ArrayList<String>();
					while(res1.next()){
						orgUnitList.add(res1.getString("name"));
					}

					result.put(res.getString("name"), orgUnitList);

				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}

		for (String key : result.keySet()){
			System.out.println(key+"->");
			ArrayList<String> values = result.get(key);
			for(String value : values)
				System.out.print(value+",");

			System.out.println("----------------------------");
		}

		return result;
	}

	//this function returns the id of the person by its name
	public int returnPersonIdByUserName(String userName){

		int personId=0;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT id FROM rules_person where userName=?");
				pstmt.setString(1, userName);
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					personId = res.getInt("id");                
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return personId;
	}

	//This function returns the name of the person by its id
	public StringPair returnPersonNameById(int userId){
		StringPair personName = null;

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT name,pref FROM rules_person where id=?");
				pstmt.setInt(1, userId);
				res2 = pstmt.executeQuery(); 
				if(res2.next())
				{
					String pn = res2.getString("name");
					String pr = res2.getString("pref");
					personName = new StringPair(pn, pr);
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				//DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res2);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return personName;
	}

	//This function returns the userName by its id
	public String returnUserNameById(int userId){
		String userName = null;

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT userName FROM rules_person where id=?");
				pstmt.setInt(1, userId);
				res2 = pstmt.executeQuery(); 
				if(res2.next())
				{
					userName = res2.getString("userName");                
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				//DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res2);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return userName;
	}

	// this method takes the name of orginazation as a parameter and returns its name
	public int returnOrgId(String orgName){

		int orgId=0;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT id FROM rules_organization where name=?");
				pstmt.setString(1, orgName);
				res = pstmt.executeQuery(); 
				if(res.next())
				{
					orgId = res.getInt("id");                
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return orgId;
	}

	//This method is takes organization as parameter and returns its id
	public int returnOrgUnitId(String orgUnitName){

		int orgUnitId=0;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT id FROM rules_organizationalUnit where name=?");
				pstmt.setString(1, orgUnitName);
				res = pstmt.executeQuery(); 
				if(res.next())
				{
					orgUnitId = res.getInt("id");                
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return orgUnitId;
	}


	private void insertIntoPerson_OrgUnit_Org_role(int personId, int orgUniId, int orgId, String role) {

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try {

				pstmt = con.prepareStatement("insert ignore into rules_person_orgUnit_org_role(personID,orgUnitID,orgID,role) values(?,?,?,?)");
				pstmt.setInt(1,personId);
				pstmt.setInt(2, orgUniId);
				pstmt.setInt(3, orgId);
				pstmt.setString(4, role);
				pstmt.executeUpdate();

			}catch(SQLException sqle) {

				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}

	}

	//This method is used to store the data from signup page

	public void saveSignUpData(SignupFormData sd){

		ContentHandling ch = new ContentHandling();

		//Build the person object out of the singupformdata
		Person person = new Person();
		person.setAge(sd.getAge());
		person.setEmail(sd.getEmail());
		person.setName(sd.getFirstName()+" "+sd.getLastName());
		person.setPassword(sd.getPassword());
		person.setSex(sd.getSex());
		person.setUserName(sd.getUserName());
		person.setPref(sd.getPersonPref());
		int personId = ch.insertIntoPerson(person);

		int orgId = ch.returnOrgId(sd.getOrg());

		int orgUnitId = ch.returnOrgUnitId(sd.getOrgUnit());

		String role = sd.getRole();

		ch.insertIntoPerson_OrgUnit_Org_role(personId,orgUnitId,orgId,role);

	}

	//This method returns all the questions for the particular scenario
	public ArrayList<QuestionData> returnQuestionsByScenarionName(String scenarioName, String userPref){

		ArrayList<QuestionData> questionList = new ArrayList<QuestionData>();
		QuestionData questionData;
		HashMap<String,Integer> hashMap;

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT * FROM rules_question where scenario=?");
				pstmt.setString(1, scenarioName);
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					questionData = new QuestionData();
					hashMap = new HashMap<String,Integer>();

					questionData.setScenarioName(scenarioName);
					// mc : means quesiton is of multiple choice
					if(res.getString("type").equalsIgnoreCase("mc")){

						questionData.setType("mc");
						questionData.setQuestion(res.getString("question_sentence"));
						questionData.setQuestionID(res.getInt("questionid"));

						//now fetch all the options 
						pstmt = con.prepareStatement("SELECT * FROM rules_question_option where questionid=?");
						pstmt.setInt(1, res.getInt("questionid"));
						res1 = pstmt.executeQuery();

						while(res1.next()){
							int optId = res1.getInt("optionid");
							hashMap.put(res1.getString("text"), optId);
							String scn = res1.getString("default_profile");
							if (userPref.contains(scn)) {
								questionData.setDefaultOption(optId);
							}
						}
						questionData.setOptions(hashMap);
					}

					//fc : meaning question being free choice
					else if(res.getString("type").equalsIgnoreCase("fc")){

						questionData.setType("fc");
						questionData.setQuestion(res.getString("question_sentence"));
						questionData.setQuestionID(res.getInt("questionid"));

					}

					//ync : meaning question being yes or no type
					else if(res.getString("type").equalsIgnoreCase("ync")){

						questionData.setType("ync");
						questionData.setQuestion(res.getString("question_sentence"));
						questionData.setQuestionID(res.getInt("questionid"));

					}
					else{

					}
					questionList.add(questionData);
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				DBHandler.closeResultSet(res1);
				DBHandler.closeDBConnection(con);
			}
		} else {
		}

		//add question number to all the quesiotns
		int qNom = 1;
		for(QuestionData q : questionList){

			q.setQuestionNo(qNom++);
		}

		return questionList;

	}

	//this method returns the question type for the question id
	public String returnQuestionTypeById(int quesId){
		String qustionType = null;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT type FROM rules_question where questionid=?");
				pstmt.setInt(1, quesId);
				res = pstmt.executeQuery(); 
				if(res.next())
				{
					qustionType = res.getString("type");                
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return qustionType;
	}

	//this method returns the content field of the rules_qa which contains all the question and its answer as string in the JOSN format
	public String returnFromrules_qa(int userId, String scenario){
		String result = null;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT content FROM rules_qa where userID = ? and scenario = ?");
				pstmt.setInt(1, userId);
				pstmt.setString(2, scenario);
				res = pstmt.executeQuery(); 
				if(res.next())
				{
					result = res.getString("content");                
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return result;
	}

	//this method takes userId as parameter and returns the age
	public String returnAge(int userId){
		String result = null;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT age FROM rules_person where id = ?");
				pstmt.setInt(1, userId);
				res = pstmt.executeQuery(); 
				if(res.next())
				{
					result = res.getString("age");                
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return result;
	}

	//This method take userID as parameter and return the sex of the user
	//this method takes userId as parameter and returns the age
	public String returnSex(int userId){
		String result = null;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT sex FROM rules_person where id = ?");
				pstmt.setInt(1, userId);
				res = pstmt.executeQuery(); 
				if(res.next())
				{
					result = res.getString("sex");                
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return result;
	}

	//this method takes userid as a input parameter and returns the role of the user
	//this method takes userId as parameter and returns the age
	public String returnRole(int userId){
		String result = null;
		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT role FROM rules_person_orgUnit_org_role where personID = ?");
				pstmt.setInt(1, userId);
				res = pstmt.executeQuery(); 
				if(res.next())
				{
					result = res.getString("role");                
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}
		return result;
	}

	//----------------populating the drools data models---------------

	public rules_person returnFromPerson(int userid){

		System.out.println("******userID : "+ userid);

		rules_person person = new rules_person();

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT * FROM rules_person where id=?");
				pstmt.setInt(1, userid);
				res = pstmt.executeQuery(); 
				if(res.next())
				{
					person.setId(res.getInt("id"));
					person.setName(res.getString("name"));
					person.setSex(res.getString("sex"));
					person.setAge(res.getString("age"));
					person.setEmailAddress(res.getString("emailAddress"));
					person.setUserName(res.getString("userName"));
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return person;
	}

	public ArrayList<rules_document_person> returnFromDocumentPerson(int userid){

		ArrayList<rules_document_person> docPersonList = new ArrayList<rules_document_person>();
		rules_document_person docPerson;

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT * FROM rules_document_person where personID=?");
				pstmt.setInt(1, userid);
				res = pstmt.executeQuery(); 
				while(res.next())
				{

					docPerson = new rules_document_person();
					docPerson.setPersonID(userid);
					docPerson.setDocID(res.getInt("docID"));

					docPersonList.add(docPerson);

				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return docPersonList;
	}


	public ArrayList<rules_document_relation> returnFromDocumentRelation(int docID){
		ArrayList<rules_document_relation>rdrList = new ArrayList<rules_document_relation>();
		rules_document_relation rdr;

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT * FROM rules_document_relation where id1=?");
				pstmt.setInt(1, docID);
				res = pstmt.executeQuery(); 
				while(res.next())
				{

					rdr = new rules_document_relation();
					rdr.setId1(res.getInt("id1"));
					rdr.setDoc1(res.getString("doc1"));
					rdr.setId2(res.getInt("id2"));
					rdr.setDoc2(res.getString("doc2"));
					rdr.setType(res.getString("type"));

					rdrList.add(rdr);

				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return rdrList;
	}

	public ArrayList<rules_external_resources> returnFromExternalRes(int docID){
		ArrayList<rules_external_resources> rerList = new ArrayList<rules_external_resources>();
		rules_external_resources rer;

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT * FROM rules_external_resource where id=?");
				pstmt.setInt(1, docID);
				res = pstmt.executeQuery(); 
				while(res.next())
				{

					rer = new rules_external_resources();
					rer.setId(res.getInt("id"));
					rer.setUri(res.getString("uri"));
					rer.setType(res.getString("type"));

					rerList.add(rer);

				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return rerList;
	}

	public ArrayList<rules_image> returnFromImage(String imageName){
		ArrayList<rules_image> imageList = new ArrayList<rules_image>();
		rules_image image;

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT * FROM rules_image where name=?");
				pstmt.setString(1, imageName);
				res = pstmt.executeQuery(); 
				while(res.next())
				{

					image = new rules_image();
					image.setImageName(res.getString("name"));
					image.setTitle(res.getString("title"));
					image.setCreatedAt(res.getString("createdAt"));
					image.setAuthorID(res.getInt("authorID"));
					image.setTags(res.getString("tags"));
					image.setType(res.getString("type"));
					image.setSize(res.getInt("size"));
					image.setQuality(res.getString("quality"));

					imageList.add(image);
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return imageList;
	}

	public ArrayList<rules_task_document> returnFromTaskDocument(int docid){
		ArrayList<rules_task_document> rtdList = new ArrayList<rules_task_document>();
		rules_task_document rtd;

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("SELECT * FROM rules_task_document where id=?");
				pstmt.setInt(1, docid);
				res = pstmt.executeQuery(); 
				while(res.next())
				{

					rtd = new rules_task_document();
					rtd.setDocID(docid);
					rtd.setTaskID(res.getInt("task"));

					rtdList.add(rtd);
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return rtdList;
	}

	public ArrayList<rules_task> returnFromTask(int docid){
		ArrayList<rules_task> taskList = new ArrayList<rules_task>();
		rules_task task;

		if(DBHandler.isClosed(con)) {
			con = DBHandler.getConnection();
		}
		if(!DBHandler.isClosed(con)) {
			try { 

				pstmt = con.prepareStatement("select * from rules_task inner join rules_task_document on rules_task.id = rules_task_document.task where rules_task_document.id = ?");
				pstmt.setInt(1, docid);
				res = pstmt.executeQuery(); 
				while(res.next())
				{
					task = new rules_task();
					task.setId(res.getInt("id"));
					task.setDescription(res.getString("description"));
					task.setStatus(res.getString("status"));

					taskList.add(task);
				}

			}catch(SQLException sqle) {
				sqle.printStackTrace();
			}finally {
				DBHandler.closePStatement(pstmt);
				DBHandler.closeResultSet(res);
				//DBHandler.closeDBConnection(con);
			}
		} else {
		}

		return taskList;
	}
}
















