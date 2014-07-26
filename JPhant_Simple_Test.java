package jPhant_Test;

import jPhant.JPhant;
import jPhant.JPhant.Stats;

public class JPhant_Simple_Test {
	public static void main(String[] sArgs) {
		// Configure
		final JPhant jPhant1 = new JPhant(new JPhant_Config_1());
		
		System.out.println("---------------------------------");
		System.out.println("Test Clear & Stats");
		jPhant1.clear();
		System.out.println("\tStatus="+jPhant1.getStatus().toString());
		System.out.println("\tVerify PageCount==0 and Used==0 above...");
		System.out.println();

		System.out.println("---------------------------------");
		System.out.println("AddData and Verify");
		final String sID = "1";
		final String sDATA = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~'";
		//final String sDATA = "Blagh";
		final String[][] aasAddData = jPhant1.toStringArrayOfStringArrays(null);
		aasAddData[1] = new String[] { sDATA, sID };
		System.out.println("\tAddData: "+jPhant1.toStringArrayListOfStringArrayLists(aasAddData).toString());
		jPhant1.addData(aasAddData);
		final String[][] aasGetData = jPhant1.getData();
		System.out.println("\tGetData: "+jPhant1.toStringArrayListOfStringArrayLists(aasGetData).toString());
		System.out.println("\tVerify AddData & GetData are identical above (w/ addition of timstamp)...");
		System.out.println("\tData Identical="+(aasAddData[0][0].equals(aasGetData[0][0]) && aasAddData[0][1].equals(aasGetData[0][1]) && aasAddData[1][0].equals(aasGetData[1][0]) && aasAddData[1][1].equals(aasGetData[1][1])));
		//System.out.println("\tData Identical="+(aasAddData[0][0].equals(aasGetData[0][0])+", "+aasAddData[0][1].equals(aasGetData[0][1])+", "+aasAddData[1][0].equals(aasGetData[1][0])+", "+aasAddData[1][1].equals(aasGetData[1][1])));
		System.out.println("\tStatus="+jPhant1.getStatus().toString());
		System.out.println("\tVerify PageCount==1 and Used>0 above...");
		
		System.out.println("\tTest Get Rate Limits...");
		System.out.println("\t\tgetXRateLimitLimit()="+jPhant1.getXRateLimitLimit());
		System.out.println("\t\tgetXRateLimitRemaining()="+jPhant1.getXRateLimitRemaining());
		System.out.println("\t\tgetXRateLimitReset()="+jPhant1.getXRateLimitReset());
		System.out.println();
		
		for(String[] asRow : aasGetData) {
			for(String sField : asRow) {
				System.out.print(sField+", ");
			}
			System.out.println();
		}

		System.out.println("---------------------------------");
		System.out.println("AddData until PageCount==2 and Verify Page2");
		String sBigData = "";
		while(sBigData.length()<10000) {
			sBigData += sDATA;
		}
		int iID = 1;
		Stats sStats = jPhant1.getStatus();
		while(sStats.getPageCount()<2) {
			aasAddData[1] = new String[] { sBigData, ""+(iID+1) };
			System.out.println("\tAddBigData "+jPhant1.toStringArrayListOfStringArrayLists(aasAddData).toString());
			jPhant1.addData(aasAddData);
			sStats = jPhant1.getStatus();
			iID++;
		}
		final String[][] aasGetBigData = jPhant1.getData(2);
		System.out.println("\tGetBigData: "+jPhant1.toStringArrayListOfStringArrayLists(aasGetBigData).toString());
		System.out.println("\tVerify AddBigData & GetBigData are identical above (w/ addition of timstamp)...");
		System.out.println("\tData Identical="+(aasAddData[0][0].equals(aasGetBigData[0][0]) && aasAddData[0][1].equals(aasGetBigData[0][1]) && aasAddData[1][0].equals(aasGetBigData[1][0]) && aasAddData[1][1].equals(aasGetBigData[1][1])));
		System.out.println("\tData Identical="+(aasAddData[0][0].equals(aasGetBigData[0][0])+", "+aasAddData[0][1].equals(aasGetBigData[0][1])+", "+aasAddData[1][0].equals(aasGetBigData[1][0])+", "+aasAddData[1][1].equals(aasGetBigData[1][1])));
		System.out.println("\tStatus="+jPhant1.getStatus().toString());
		System.out.println("\tVerify PageCount==2 and Used>50000 above...");
		
		System.out.println("\tTest Get Rate Limits...");
		System.out.println("\t\tgetXRateLimitLimit()="+jPhant1.getXRateLimitLimit());
		System.out.println("\t\tgetXRateLimitRemaining()="+jPhant1.getXRateLimitRemaining());
		System.out.println("\t\tgetXRateLimitReset()="+jPhant1.getXRateLimitReset());
		System.out.println();

		System.out.println("---------------------------------");
		System.out.println("Clear & Stats");
		jPhant1.clear();
		System.out.println("\tStatus="+jPhant1.getStatus().toString());
		System.out.println("\tVerify PageCount==0 and Used==0 above...");
		System.out.println();
	}
}
