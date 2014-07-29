package jPhant;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class JPhant {
	public enum Format { csv, json, jsonp };
	private static final String sINPUT = "input/";
	private static final String sCLEAR = "clear";
	private static final String sOUTPUT = "output/";
	private static final String sSTATS = "stats";
	//private static final String sSTREAMS = "streams/";
	private static final String sPRIVATE_KEY_EQUALS = "private_key=";
	private static final String sX_RATE_LIMIT_LIMIT = "X-Rate-Limit-Limit";
	private static final String sX_RATE_LIMIT_REMAINING = "X-Rate-Limit-Remaining";
	private static final String sX_RATE_LIMIT_RESET = "X-Rate-Limit-Reset";
	private static final String sPHANT_PRIVATE_KEY_HEADER = "Phant-Private-Key";
	private static final String sPAGE_EQUALS = "page=";
	private static final int iRETRY_COUNT = 5;
	private static final int iRETRY_DELAY_MS = 1000;
	private static final String sHTTPS_PROXY_HOST = "https://proxyHost";
	private static final String sHTTPS_PROXY_PORT = "https://proxyPort";

	// *** Configuration *** //
	private String sPublicKey = "";
	private String sPrivateKey = "";
	//private String sDeleteKey = "";
	private String[] asFields = null;
	private String sPhantURL = "";
	private String sProxyHostAndPort = "";
	private JPhantConfig.Method eMethod = JPhantConfig.Method.POST;

	private int iXRateLimitLimit = 0;
	private int iXRateLimitRemaining = 0;
	private double dXRateLimitReset = 0.0;
	public interface JPhantConfig {
		public Method getMethod();
		public String getBaseURL();
		public String getProxyHostAndPort();
		public String getPublicKey();
		public String getPrivateKey();
		//public String getDeleteKey();
		public String[] getFields();
		public enum Method { GET, POST };
	}
	enum StatsFields { cap,pageCount,remaining,used };
	public class Stats {
		private int iCap = 0;
		private int iPageCount = 0;
		private int iRemaining = 0;
		private int iUsed = 0;
		Stats(final int iCap, final int iPageCount, final int iRemaining, final int iUsed) {
			this.iCap = iCap;
			this.iPageCount = iPageCount;
			this.iRemaining = iRemaining;
			this.iUsed = iUsed;
		}
		public int getCap() { return iCap; }
		public int getPageCount() { return iPageCount; }
		public int getRemaining() { return iRemaining; }
		public int getUsed() { return iUsed; }
		@Override public String toString() { return "{ Cap="+iCap+", PageCount="+iPageCount+", Remaining="+iRemaining+", Used="+iUsed+" }"; }
	}
	public JPhant(final JPhantConfig jpcConfig) {
		this.sPhantURL = jpcConfig.getBaseURL();
		this.sProxyHostAndPort = jpcConfig.getProxyHostAndPort();
		this.sPublicKey = jpcConfig.getPublicKey();
		this.sPrivateKey = jpcConfig.getPrivateKey();
		//this.sDeleteKey = jpcConfig.getDeleteKey();
		this.asFields = jpcConfig.getFields();
		this.eMethod = jpcConfig.getMethod();
	}
	public String getsPublicKey() { return sPublicKey; }
	public String getsPrivateKey() { return sPrivateKey; }
	public String[] getAsFields() { return asFields; }
	public String getsPhantURL() { return sPhantURL; }
	public String getsProxyHostAndPort() { return sProxyHostAndPort; }
	public JPhantConfig.Method geteMethod() { return eMethod; }
	public boolean clear() {
		try {
			//	Clear All Data
			//	http://data.sparkfun.com/input/PUBLIC_KEY/clear?private_key=PRIVATE_KEY
			final String sClearURL = getsPhantURL()+sINPUT+getsPublicKey()+"/"+sCLEAR+"?"+sPRIVATE_KEY_EQUALS+getsPrivateKey();
			return getURL(sClearURL).substring(0, 1).equals("1");
		} catch (final Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return false;
		}
	}
	public final String[][] getData() {
		return getData(1);
	}
	public final String[][] getData(final int iPage) {
		final String sData = getData(iPage, Format.csv);
		//System.out.println("\t\tsData="+sData);
		return csvSplit(sData);
	}
	public final String getData(final Format eFormat) {
		return getData(0, eFormat);
	}
	public final String getData(final int iPage, final Format eFormat) {
		try {
			//	Get Data
			//	http://data.sparkfun.com/output/PUBLIC_KEY.FORMAT
			final String sGetDataURL = getsPhantURL()+sOUTPUT+getsPublicKey()+"."+eFormat.toString()+"?"+sPAGE_EQUALS+iPage;
			return getURL(sGetDataURL);
		} catch (final Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		}
	}
	public String getStatus(final Format eFormat) {
		try {
			//	Get Status
			//	http://data.sparkfun.com/output/PUBLIC_KEY/stats.FORMAT
			final String sStatsURL = getsPhantURL()+sOUTPUT+getsPublicKey()+"/"+sSTATS+"."+eFormat.toString();
			return getURL(sStatsURL);
		} catch (final Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		}
	}
	public Stats getStatus() {
		try {
			final String sStats = getStatus(Format.csv);
			final String[] asStats = sStats.split("\r\n");
			final ArrayList<String> alFields = new ArrayList<String>(Arrays.asList(asStats[0].split(",")));
			final ArrayList<String> alData = new ArrayList<String>(Arrays.asList(asStats[1].split(",")));
			//System.out.println("alFields="+alFields.toString());
			//System.out.println("remaining="+alFields.indexOf(StatsFields.remaining.toString()));
			//System.out.println("iCap='"+alData.get(alFields.indexOf(StatsFields.cap.toString()))+"'");
			final int iCap = Integer.parseInt(alData.get(alFields.indexOf(StatsFields.cap.toString())));
			final int iPageCount = Integer.parseInt(alData.get(alFields.indexOf(StatsFields.pageCount.toString())));
			final int iRemaining = Integer.parseInt(alData.get(alFields.indexOf(StatsFields.remaining.toString())));
			final int iUsed = Integer.parseInt(alData.get(alFields.indexOf(StatsFields.used.toString())));
			Stats stats = new Stats(iCap, iPageCount, iRemaining, iUsed);
			return stats;
		} catch (final Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return null;
		}
	}
	public boolean addData(final String[][]aasData) {
		try {
			// Note Escapes \r & \n
			// Verify aasData[0]===asFields
			if(aasData.length!=2) {
				throw new Exception("Array size Mismatch in addData: aasData.length="+aasData.length+" it must==2");
			} else if(aasData[0].length!=getAsFields().length) {
				throw new Exception("Data Field Mismatch in addData: aasData[0].length="+aasData[0].length+" not equal asFields.length="+getAsFields().length);
			} else if(aasData[1].length!=getAsFields().length) {
				throw new Exception("Data Field Mismatch in addData: aasData[1].length="+aasData[1].length+" not equal asFields.length="+getAsFields().length);
			} else for(int x=0 ; x<getAsFields().length ; x++) {
				if(aasData[0][x].equals(getAsFields()[x])==false) {
					throw new Exception("Data Field Mismatch in addData: aasData[0]["+x+"]='"+aasData[0][x]+"' not equal asFields[x]");
				}
			}
			String sAddData = "";
			//System.out.println("asFields.length="+asFields.length+", hmData.size()="+hmData.size());
			//System.out.println("lFields.toString()="+asFields.toString()+", hmData.toString()="+hmData.toString());
			for(int x=0 ; x<aasData[0].length ; x++) {
				//System.out.println("x="+x);
				sAddData += ((sAddData.length()>0)?"&":"")+aasData[0][x]+"="+URLEncoder.encode(aasData[1][x].replace("\r", "\\r").replace("\n", "\\n"), "UTF-8");
			}
			final String sAddDataPostURL = getsPhantURL()+sINPUT+getsPublicKey()+((this.geteMethod()==JPhantConfig.Method.GET)?"?"+sPRIVATE_KEY_EQUALS+getsPrivateKey()+"&"+sAddData:"");
			String sData = null;
			int iTries = iRETRY_COUNT;
			while(iTries>0 && sData==null) {
				try {
					//System.out.println("sAddDataURL="+sAddDataURL);
					final URL url = new URL(sAddDataPostURL);
					final URLConnection urlc = url.openConnection();
					if(geteMethod()==JPhantConfig.Method.POST) {
						urlc.setRequestProperty(sPHANT_PRIVATE_KEY_HEADER, getsPrivateKey());
						urlc.setDoInput(true);
						urlc.setDoOutput(true);
						OutputStreamWriter osw = new OutputStreamWriter(urlc.getOutputStream());
						osw.write(sAddData);
						osw.flush();
						osw.close();
					}
					final BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
					String sLine = br.readLine(); 
					while(sLine!=null) {
						sData += sLine+"\r\n";
						sLine = br.readLine();
					}
					for(int x=0 ; x<urlc.getHeaderFields().size() ; x++) {
						//System.out.println("\taddData\turlc.getHeaderFieldKey("+x+")="+urlc.getHeaderFieldKey(x)+":"+urlc.getHeaderField(x));
						if(urlc.getHeaderFieldKey(x)!=null) {
							if(urlc.getHeaderFieldKey(x).equals(sX_RATE_LIMIT_LIMIT)) {
								iXRateLimitLimit = Integer.parseInt(urlc.getHeaderField(x));
							} else if(urlc.getHeaderFieldKey(x).equals(sX_RATE_LIMIT_REMAINING)) {
								iXRateLimitRemaining = Integer.parseInt(urlc.getHeaderField(x));
							} else if(urlc.getHeaderFieldKey(x).equals(sX_RATE_LIMIT_RESET)) {
								dXRateLimitReset = Double.parseDouble(urlc.getHeaderField(x));
							}
						}
					}
					br.close();
				} catch (final Exception e) {
					System.err.println(e);
					e.printStackTrace();
					System.out.println(iTries+" Retrys left for "+geteMethod().toString()+":"+sAddDataPostURL+((geteMethod()==JPhantConfig.Method.POST)?sPHANT_PRIVATE_KEY_HEADER+getsPrivateKey()+"&"+sAddData:""));
					sData = null;
				} finally {
					iTries--;
				}
			}
			return (sData!=null && sData.substring(0, 1).equals("1"));
		} catch (final Exception e) {
			System.err.println(e);
			e.printStackTrace();
			return false;
		}
	}
	public int getXRateLimitLimit() { return iXRateLimitLimit; }
	public int getXRateLimitRemaining() { return iXRateLimitRemaining; }
	public double getXRateLimitReset() { return dXRateLimitReset; }
	public ArrayList<ArrayList<String>> toStringArrayListOfStringArrayLists(final String[][] aasData) {
		ArrayList<ArrayList<String>> alalData = new ArrayList<ArrayList<String>>();
		if(aasData==null) {
			ArrayList<String> alData = new ArrayList<String>();
			for(String sField : getAsFields()) {
				alData.add(sField);
			}
			alalData.add(alData);
		} else {
			for(String[] asRow : aasData) {
				ArrayList<String> alData = new ArrayList<String>();
				for(String sCol : asRow) {
					alData.add(sCol);
				}
				alalData.add(alData);
			}
		}
		return alalData;
	}
	public String[][] toStringArrayOfStringArrays(final ArrayList<ArrayList<String>> alalData) {
		String[][]aas = null;
		if(alalData==null) {
			aas = new String[2][];
			aas[0] = getAsFields().clone();
			return aas;
		}
		aas = new String[alalData.size()][];
		for(int x=0 ; x<alalData.size() ; x++) {
			aas[x] = new String[alalData.get(x).size()];
			for(int y=0 ; y<alalData.get(x).size() ; y++) {
				aas[x][y] = alalData.get(x).get(y);
			}
		}
		return aas;
	}
	private String getURL(final String sURL) {
		String sData = "";
		int iTries = iRETRY_COUNT;
		while(iTries>0 && sData.equals("")) {
			try {
				final URL url = new URL(sURL);
				String sProxyHost = "";
				String sProxyPort  = "";
				if(this.getsProxyHostAndPort().length()>0) {
					final String[] asProxyHostAndPort = this.getsProxyHostAndPort().split("\\:");
					if(asProxyHostAndPort!=null && asProxyHostAndPort.length>=1 && asProxyHostAndPort[0]!=null && asProxyHostAndPort[0].length()>0) {
						sProxyHost = asProxyHostAndPort[0];
						if(asProxyHostAndPort.length>=2 && asProxyHostAndPort[1]!=null && asProxyHostAndPort[1].length()>0) {
							sProxyPort = asProxyHostAndPort[1];
						} else {
							sProxyPort = "80";
						}
					}
				}
				//System.out.println("\t\t\tProxyHost:Port="+sProxyHost+":"+sProxyPort);
				final URLConnection urlc = url.openConnection();
				if(sProxyHost.length()>0) {
					final Properties pSystemProperties = System.getProperties();
					pSystemProperties.setProperty(sHTTPS_PROXY_HOST, sProxyHost);
					pSystemProperties.setProperty(sHTTPS_PROXY_PORT, sProxyPort);
				}
				final BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
				String sLine = br.readLine(); 
				//System.out.println("\t\t\tsLine="+sLine);
				while(sLine!=null) {
					sData += sLine+"\r\n";
					sLine = br.readLine();
					//System.out.println("\t\t\tsLine="+sLine);
				}
				br.close();
				return sData;
			} catch (final Exception e) {
				System.err.println(e);
				e.printStackTrace();
				System.out.println(iTries+" Retrys left for:"+sURL);
				sData = "";
			} finally {
				iTries--;
			}
			try { Thread.sleep(iRETRY_DELAY_MS); } catch(final Exception e) { ; }
		}
		return sData;
	}
	// The Phant CSV Rules seem to be...
	//		If it contains a quote or a comma, Field will be quoted.
	//		If it starts w/ a quote, it will end w/ a quote.
	//		If it contains a quote, it will be double quoted.
	//		If first char is quote, it will begin w/ 3 quotes.
	//		If last char is quote, it will end w/ 3 quotes.
	//		Otherwise it is unquoted.
	//		CR & LF Characters will be stripped out & replaced w/ spaces
	//			(Escaped in addData, Unescaped here).

	//Data,timestamp
	//"""Leading Quote",2014-07-20T15:51:31.004Z
	//"Imbebbed "" Quote",2014-07-20T15:51:31.184Z
	//"Trailing Quote""",2014-07-20T15:09:48.523Z
	//No Quote,2014-07-20T15:09:48.202Z
	private String[][] csvSplit(final String sData) {
		//System.out.println("\t\tsData="+sData);
		final String sQuote = "\"";
		final String sComma = ",";
		final String sQuote2 = sQuote+sQuote;
		final String sQuote3 = sQuote2+sQuote;
		final String sQuoteComma = sQuote+sComma;
		ArrayList<ArrayList<String>> alalData = new ArrayList<ArrayList<String>>();
		for(String sRow : sData.split("\r\n")) {
			ArrayList<String> alCurrentRow = new ArrayList<String>();
			int x = 0;
			while(x<sRow.length()) {
				if(sRow.substring(x).startsWith(sQuote)==true || sRow.substring(x).startsWith(sQuote3)==true) {
					x += ((sRow.substring(x).startsWith(sQuote3)==true)?2:1);
					final int iTokenStart = x;
					while(x<sRow.length() && sRow.substring(x).startsWith(sQuoteComma)==false) {
						x += ((sRow.substring(x).startsWith(sQuote2))?2:1);
					}
					final int iTokenEnd = x;
					alCurrentRow.add(sRow.substring(iTokenStart, iTokenEnd).replace(sQuote2, sQuote).replace("\\r", "\r").replace("\\n", "\n"));
					x++;
				} else {
					final int iTokenStart = x;
					while(x<sRow.length() && sRow.substring(x).startsWith(sComma)==false) {
						x += ((sRow.substring(x).startsWith(sQuote2))?2:1);
					}
					final int iTokenEnd = x;
					alCurrentRow.add(sRow.substring(iTokenStart, iTokenEnd).replace(sQuote2, sQuote).replace("\\r", "\r").replace("\\n", "\n"));
				}
				//System.out.println("\t\talCurrentRow="+alCurrentRow);
				x++;
			}
			alalData.add(alCurrentRow);
			//System.out.println("\t\talalData="+alalData);
		}
		return this.toStringArrayOfStringArrays(alalData);
	}
}
