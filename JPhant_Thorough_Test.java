package jPhant_Test;

// *** Features *** //
//		Fully Configurable (Public/Private/Delete Keys, FieldNames, BaseURL, use GET or POST.)
//		Can Handle multiple simultaneous Phant Data Streams in same program.
//		Can use GET or POST (Using POST will encrypt your PrivateKey and Data in transit.)
//		Can Send/Receive any Character 0 through 127
//		Support for Paging (Data > 50K.)

//		* Add Data using Get or Post Methods (Configurable.)
//		* Get Data in efficient Format: String[][] aaExample = { {"Field1", "Field2"}, {"Value1", "Value2"}... }
//		*	(Or Get Data in Raw Formats - Csv, Json, Jsonp.)
//		* Clear all Data
//		* Get Status in efficient Format (a Stats class encapsulating Cap, PageCount, Remaining, and Used.)
//		*	(Or Get Status in Raw Formats - Csv, Json, Jsonp.)
//		* Get Rate Limits (Limit, Remaining, Reset - only valid after addData().)
//		* Convert to/from supported Data Structures (String[][] and ArrayList<ArrayList<String>>.)
//		* Smart CSV Extraction (handles embedded Quotes, Commas, Carriage Returns, and Line Feeds.)
//		* Automatic Retries on HTTPConnection Errors (up to 5 retries.)

// ----------- ToDo --------------
// What happens if you try to Create a "timestamp" Field?
// What happens if you try to Update "timestamp"?
// Test Empty Fields
// * Test Characters > 127
// Include String getTimestamp()
// Bulk Updates when String[x>2][]?
// A good ReadMe explaining Data Structure(s), Configuration and usage.

// ----------- Completed ------------
// Better DataStructure than HashMap<String, String>
//		String[][] aaExample = { {"Field1", "Field2"}, {"Value1", "Value2"}... }
//		Support to/from ArrayList<ArrayList<String>>()
//		String[][] JPhant.toStringArrayOfStringArrays(ArraList<ArrayList<String>>)
//		ArraList<ArrayList<String>> JPhant.toStringArrayListOfStringArrayLists(String[][])
//		Passes to addData, aaExample must be aaExample[0]== JPhantConfig.getFields() && aaExample[1]==Values
//		Returned from aaExample=getData(iMax=1), aaExample[0]==JPhantConfig.getFields() && aaExample[1...]==Values
// Test Commas & Quotes in Data
// Get & Post Methods (in Config?)
// Test Characters 0 thru 127
// Retries on Errors?
// * Rcvd:'[[nullData]???

import jPhant.JPhant;
import jPhant.JPhant.Stats;

public class JPhant_Thorough_Test {
	public static void main(String[] sArgs) {
		final JPhant jPhant0 = new JPhant(new JPhant_Config_0());
		//final JPhant jPhant1 = new JPhant(new JPhant_Config_1());

		
		// Initial Test0: Clear, Test Stats
//		System.out.println("---------------------------------");
//		System.out.println("Test0a, Clear & Test Stats");
//		final boolean bTest0aClear = jPhant0.clear();
//		System.out.println("\tjPhant0.clear()="+bTest0aClear);
//		//System.out.println("\tjPhant0.getStatus(JPhant.Format.csv)=\r\n"+jPhant0.getStatus(JPhant.Format.csv));
//		final jPhant.Stats jpsStats0a = jPhant0.getStatus();
//		//assert jpsStats0a.getPageCount()==0 && jpsStats0a.getUsed()==0 : "Initial Test0a Failed on getPageCount()==0 && jpsStats0a.getUsed()==0";
//		System.out.println("\tjpsStats0a="+jpsStats0a.toString());
//		System.out.println("\tTest0a: "+((bTest0aClear==true && jpsStats0a.getPageCount()==0 && jpsStats0a.getUsed()==0)?"Passed":"Failed"));
//		System.out.println();
//		
//		System.out.println("---------------------------------");
//		System.out.println("Test0b, Add Data, Verify & Test Stats");
//		String[][] aasTest0bData = jPhant0.toStringArrayOfStringArrays(null);
//		final String sTest0bThisIsATest = "This is a Test, with a \"comma\"";
//		aasTest0bData[1] = new String[] { sTest0bThisIsATest };
//		final boolean bTest0bAddData = jPhant0.addData(aasTest0bData);
//		System.out.println("\tbTest0bAddData="+bTest0bAddData);
//		final jPhant.Stats jpsStats0b = jPhant0.getStatus();
//		System.out.println("\tjpsStats0b="+jpsStats0b.toString());
//		//System.out.println("\tjPhant0.addData(hmData0)="+bTest0bAddData);
//		String[][] aasData = jPhant0.getData();
//		System.out.println("\tjPhant0.getData()="+jPhant0.toStringArrayListOfStringArrayLists(aasData).toString());
//		System.out.println("\tTest0b: "+((aasData[1][0].equals(sTest0bThisIsATest)==true && bTest0bAddData==true && jpsStats0b.getPageCount()==1 && jpsStats0b.getUsed()>0)?"Passed":"Failed"));
//		
//		
//		System.out.println("\tTest Raw Formats (Visual Verification)...");
//		System.out.println("\t\tgetData.CSV="+jPhant0.getData(JPhant.Format.csv).replace("\r", "\\r").replace("\n", "\\n"));
//		System.out.println("\t\tgetData.JSON="+jPhant0.getData(JPhant.Format.json).replace("\r", "\\r").replace("\n", "\\n"));
//		System.out.println("\t\tgetData.JSONP="+jPhant0.getData(JPhant.Format.jsonp).replace("\r", "\\r").replace("\n", "\\n"));
//		System.out.println("\t\tgetStatus.CSV="+jPhant0.getStatus(JPhant.Format.csv).replace("\r", "\\r").replace("\n", "\\n"));
//		System.out.println("\t\tgetStatus.JSON="+jPhant0.getStatus(JPhant.Format.json).replace("\r", "\\r").replace("\n", "\\n"));
//		System.out.println("\t\tgetStatus.JSONP="+jPhant0.getStatus(JPhant.Format.jsonp).replace("\r", "\\r").replace("\n", "\\n"));
//		System.out.println("\tTest Get Rate Limits...");
//		System.out.println("\t\tgetXRateLimitLimit()="+jPhant0.getXRateLimitLimit());
//		System.out.println("\t\tgetXRateLimitRemaining()="+jPhant0.getXRateLimitRemaining());
//		System.out.println("\t\tgetXRateLimitReset()="+jPhant0.getXRateLimitReset());
//		System.out.println();
		
		jPhant0.clear();
		final String[] asTests = {
				"No Quote"
				,"\"Leading Quote"
				,"Imbebbed \" Quote"
				,"Trailing Quote\""
				,"Imbebbed , Comma"
				,"\"Leading & Trailing Quote\""
				,"\"Leading Quote, w/ Comma"
				,"Trailing Quote, w/ Comma\""
				,"\"Leading & Trailing Quote, w/ Comma\""
				,"Imbebbed \r\n CRLF"
				};
		for(String sTest : asTests) {
			String[][] aasTestData = jPhant0.toStringArrayOfStringArrays(null);
			aasTestData[1] = new String[] { sTest };
			System.out.println("\tSend:'"+jPhant0.toStringArrayListOfStringArrayLists(aasTestData).toString()+"'");
			final boolean bTest = jPhant0.addData(aasTestData);
			System.out.println("\tbTest="+bTest);
			String[][] aasReturnData = jPhant0.getData();
			System.out.println("\tRcvd:'"+jPhant0.toStringArrayListOfStringArrayLists(aasReturnData).toString()+"'");
			System.out.println("\tSent:'"+sTest+"'");
			System.out.println("\tRecv:'"+aasReturnData[1][0]+"'");
			System.out.println("\t"+sTest.equals(aasReturnData[1][0]));
			System.out.println();
		}
//		// Test Char 0 thru 32
//		String sCharTest = "";
//		for(char c=0 ; c<32 ; c++) {
//			sCharTest = "Character("+((int)c)+")="+new Character(c).toString(); 
//			String[][] aasCharTest = jPhant0.toStringArrayOfStringArrays(null);
//			aasCharTest[1] = new String[] { sCharTest };
//			System.out.println("\tSend:'"+jPhant0.toStringArrayListOfStringArrayLists(aasCharTest).toString()+"'");
//			final boolean bTest = jPhant0.addData(aasCharTest);
//		//	System.out.println("\tbTest="+bTest);
//			String[][] aasReturnData = jPhant0.getData();
//			System.out.println("\tRcvd:'"+jPhant0.toStringArrayListOfStringArrayLists(aasReturnData).toString()+"'");
//			System.out.println("\tSent:'"+sCharTest+"'");
//			System.out.println("\tRecv:'"+aasReturnData[1][0]+"'");
//			System.out.println("\t"+sCharTest.equals(aasReturnData[1][0]));
//			System.out.println();
//		}

		String sLongTest = "";
		for(char c=0 ; c<128 ; c++) {
			sLongTest += new Character(c).toString(); 
		}
		String[][] aasTestLong = jPhant0.toStringArrayOfStringArrays(null);
		aasTestLong[1] = new String[] { sLongTest };
		System.out.println("\tSend:'"+jPhant0.toStringArrayListOfStringArrayLists(aasTestLong).toString()+"'");
		final boolean bTest = jPhant0.addData(aasTestLong);
		System.out.println("\tbTest="+bTest);
		String[][] aasReturnData = jPhant0.getData();
		System.out.println("\tRcvd:'"+jPhant0.toStringArrayListOfStringArrayLists(aasReturnData).toString()+"'");
		System.out.println("\tSent:'"+sLongTest+"'");
		System.out.println("\tRecv:'"+aasReturnData[1][0]+"'");
		System.out.println("\t"+sLongTest.equals(aasReturnData[1][0]));
		System.out.println();

		System.out.println("\tTest Get Rate Limits...");
		System.out.println("\t\tgetXRateLimitLimit()="+jPhant0.getXRateLimitLimit());
		System.out.println("\t\tgetXRateLimitRemaining()="+jPhant0.getXRateLimitRemaining());
		System.out.println("\t\tgetXRateLimitReset()="+jPhant0.getXRateLimitReset());
		System.out.println();

		final Stats jpsStats0a = jPhant0.getStatus();
		System.out.println("\tjpsStats0a="+jpsStats0a.toString());
System.exit(0x0000);
		
//		// Initial Test1: Clear, Test Stats
//		System.out.println("Initial Test1, Clear & Test Stats");
//		System.out.println("\tjPhant1.clear()="+jPhant1.clear());
//		System.out.println("\tjPhant1.getStatus(JPhant.Format.CSV)="+jPhant1.getStatus(JPhant.Format.csv));
//		final jPhant.Stats jpsStats1 = jPhant1.getStatus();
//		System.out.println("\tjPhant1.getStatus()="+jpsStats1.toString());
//		System.out.println("-----");
//
//		System.out.println("Initial Test1b, Add Data & Test Stats");
//		HashMap<String, String> hmData1 =  new HashMap<String, String>();
//		hmData1.put(JPhant_Config_1.Fields.ID.toString(), "1");
//		hmData1.put(JPhant_Config_1.Fields.DATA.toString(), "This is a another Test");
//		jPhant1.addData(hmData1);
//		System.out.println("\tjPhant1.getData(JPhant.Format.csv)="+jPhant1.getData(JPhant.Format.csv));
//		System.out.println("\tjPhant1.getData(JPhant.Format.json)="+jPhant1.getData(JPhant.Format.json));
//		System.out.println("\tjPhant1.getData(JPhant.Format.jsonp)="+jPhant1.getData(JPhant.Format.jsonp));
//		System.out.println("\tjPhant1.getStatus(JPhant.Format.csv)="+jPhant1.getStatus(JPhant.Format.csv));
//		System.out.println("\tjPhant1.getStatus(JPhant.Format.json)="+jPhant1.getStatus(JPhant.Format.json));
//		System.out.println("\tjPhant1.getStatus(JPhant.Format.jsonp)="+jPhant1.getStatus(JPhant.Format.jsonp));
//		final jPhant.Stats jpsStats1b = jPhant1.getStatus();
//		System.out.println("\tjpsStats1b="+jpsStats1b.toString());
//		System.out.println("\tjPhant1.getXRateLimitLimit()="+jPhant1.getXRateLimitLimit());
//		System.out.println("\tjPhant1.getXRateLimitRemaining()="+jPhant1.getXRateLimitRemaining());
//		System.out.println("\tjPhant1.getXRateLimitReset()="+jPhant1.getXRateLimitReset());
//		System.out.println("-----");
	}
}
