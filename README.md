JPhant: Java Library for PHant Access

*** Features ***
  Fully Configurable (Public/Private/Delete Keys, Fieldnames, BaseURL, use GET or POST.)
  Can Handle multiple simultaneous Phant Data Streams in same program.
  Can use GET or POST (Using POST will encrypt your PrivateKey and Data in transit.)
  Can Send/Receive any Character 0 through 127
  Support for Paging (Data > 50K.)

  * Add Data using Get or Post Methods (Configurable.)
  * Get Data in efficient Format: String[][] aaExample = { {"Field1", "Field2"}, {"Value1", "Value2"}... }
  *	(Or Get Data in Raw Formats - Csv, Json, Jsonp.)
  * Clear all Data
  * Get Status in efficient Format (a Stats class encapsulating Cap, PageCount, Remaining, and Used.)
  *	(Or Get Status in Raw Formats - Csv, Json, Jsonp.)
  * Get Rate Limits (Limit, Remaining, Reset - only valid after addData().)
  * Convert to/from supported Data Structures (String[][] and ArrayList<ArrayList<String>>.)
  * Smart CSV Extraction (handles embedded Quotes, Commas, Carriage Returns, and Line Feeds.)
  * Automatic Retries on HTTPConnection Errors (up to 5 retries.)

-------------------------------------------------------------------------
  I'm going to be very busy for the next few months, and can't develop this further.  I hereby put this "as is" in the Public Domain, I hope someone will pick it up and run with it. Here is a quick overview...

-------------------------------------------------------------------------
To use:

1) --- Initialize ---
1a) Create a class that implements JPhantConfig and enter the appropriate Keys and Fieldnames.
    (see JPhant_Config_0.java for an example.)

1) --- Configure ---
2a) Instantiate a JPhant instance, for example...
    JPhant jPhant0 = new JPhant(new JPhant_Config_0());

3) --- High Level Commands ---
3a) To Clear All data...
    jPhant0.clear();

3b) To Add Data (Fieldnames must exactly match Fields Phant Stream was created with...
    String[][] aasAddData = new String[][] { {"Field1", "Field2"}, {"Value1", "Value2"} };
    jPhant0.addData(aasAddData);

3c) To Get Data...
    String[][] aasGetData = jPhant0.getData();
    for(String[] asRow : aasGetData) {
        for(String sField : asRow) {
            System.out.print(sField+", ");
        }
        System.out.println();
    }

3d) To Get Status...
    jPhant.Stats jpsStats0 = jPhant0.getStatus();
    System.out.println("jpsStats0="+jpsStats0.toString());

3e) To Get Rate Limits...
    System.out.println("getXRateLimitLimit()="+jPhant0.getXRateLimitLimit());
    System.out.println("getXRateLimitRemaining()="+jPhant0.getXRateLimitRemainin());
    System.out.println("getXRateLimitReset()="+jPhant0.getXRateLimitReset());

4) --- Low Level Raw Data Commands ---
4a) To get Data in Raw Format...
    System.out.println("getData.CSV="+jPhant0.getData(JPhant.Format.csv).replace("\r", "\\r").replace("\n", "\\n"));
    System.out.println("getData.JSON="+jPhant0.getData(JPhant.Format.json).replace("\r", "\\r").replace("\n", "\\n"));
    System.out.println("getData.JSONP="+jPhant0.getData(JPhant.Format.jsonp).replace("\r", "\\r").replace("\n", "\\n"));

4b) To get Status in Raw Format...
    System.out.println("getStatus.CSV="+jPhant0.getStatus(JPhant.Format.csv).replace("\r", "\\r").replace("\n", "\\n"));
    System.out.println("getStatus.JSON="+jPhant0.getStatus(JPhant.Format.json).replace("\r", "\\r").replace("\n", "\\n"));
    System.out.println("getStatus.JSONP="+jPhant0.getStatus(JPhant.Format.jsonp).replace("\r", "\\r").replace("\n", "\\n"));

-------------------------------------------------------------------------
Phant returns data in CSV Format like...
  Field1, Field2
  Value1, Value2
  ...   , ...

So the most obvious data structure to use is something like...
  String[][] aaExample = { {"Field1", "Field2"}, {"Value1", "Value2"}... }

And the Collection equivalent...
  ArrayList<ArrayList<String>> alalData = new ArrayList<ArrayList<String>>();

There are functions to support converting to/from String[][] and ArrayList<ArrayList<String>>.
  toStringArrayListOfStringArrayLists(String[][])
  toStringArrayOfStringArrays(ArrayList<ArrayList<String>>)

As a convenience, if these two functions are passed null, they return the first element filled in with Fieldnames.

You can handle multiple Phant Streams in one program with separate Phant variables and Configurations, like...
  JPhant jPhant0 = new JPhant(new JPhant_Config_0());
  JPhant jPhant1 = new JPhant(new JPhant_Config_1());

-------------------------------------------------------------------------
What isn't working yet...
  * It should support the entire UTF-8 Character set, but appears to only work for characters 0 through 127.
  * Has support for Paging when Data > 50K, but doesn't appear to be working as I expected.
  * I was going to add support for the Timestamp Field that Phant automatically returns.
  * AddData() only adds the first row of data passed to it, I was going to add Bulk Updates when multiple rows of data are passed in.

-------------------------------------------------------------------------
I hope that when this other project that is pulling me away is over, I can get back to this. In the mean time, feel free to develop it as you see fit.
