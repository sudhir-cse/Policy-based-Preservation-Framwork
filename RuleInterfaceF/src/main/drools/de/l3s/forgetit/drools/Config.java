package de.l3s.forgetit.drools;

import java.util.HashMap;
import java.util.Map;

public class Config {

	public static final Map<String, String> ruleSetDescription = new HashMap<String, String>();
	public static final Map<String, String> qa2ruleSet = new HashMap<String, String>();

	static {
		// key: rule set name --> value: rule set description
		//InputStream in = Config.class.getResourceAsStream("rules.description");

		/*try {
			BufferedReader reader = new BufferedReader(new FileReader("rules-description.txt"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				int i = line.indexOf('\t');
				ruleSetDescription.put(line.substring(0,i), line.substring(i+1));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}*/

		// Hard code here to save Sudhir's life
		ruleSetDescription.put("demo1-session","Relatively aggressive strategy: Do not " +
				"preserve duplicated and derived data, except when in meeting mode");
		ruleSetDescription.put("demo2-session","Moderate strategy: Check carefully beforehand" +
				" if the documents is of any use, then make a decision and move on");
		ruleSetDescription.put("demo3-session","Conservative strategy: Do not want to lose anything");
		ruleSetDescription.put("demo4-session","Very aggressive strategy: Do not keep data in general, " +
				"except it is obviously important");

		qa2ruleSet.put("{\"userName\":\"tran\",\"qa\":{\"1\":\"100\",\"2\":\"10\",\"3\":\"100\",\"4\":\"100\",\"5\":\"100\"}," +
				"\"scenarioName\":\"Meeting\"}","demo1-session");
		qa2ruleSet.put("{\"userName\":\"tran\",\"qa\":{\"1\":\"010\",\"2\":\"01\",\"3\":\"010\",\"4\":\"010\",\"5\":\"001\"}," +
				"\"scenarioName\":\"Meeting\"}","demo2-session");
		qa2ruleSet.put("{\"userName\":\"tran\",\"qa\":{\"1\":\"001\",\"2\":\"01\",\"3\":\"001\",\"4\":\"001\",\"5\":\"100\"}," +
				"\"scenarioName\":\"Meeting\"}","demo3-session");
		qa2ruleSet.put("{\"userName\":\"tran\",\"qa\":{\"1\":\"010\",\"2\":\"10\",\"3\":\"100\",\"4\":\"100\",\"5\":\"100\"}," +
				"\"scenarioName\":\"Meeting\"}","demo4-session");
	}
}
