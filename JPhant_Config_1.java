package jPhant_Test;

import jPhant.JPhant.JPhantConfig;

public class JPhant_Config_1 implements JPhantConfig {
	// *** Enter Public/Private/Delete Keys below *** //
	private static final String sPHANT_PUBLIC_KEY = "8ddVyNJN8QcO0gW3dlj6";
	private static final String sPHANT_PRIVATE_KEY = "pzz29b7bgqtzn18m92G0";
	//private static final String sPHANT_DELETE_KEY = "y33z18M8AjS5yz3vJnLY";

	// *** Enter your Field Names below (Case Sensitive!) in SAME ORDER as Phant Returns *** //
	// *** For Example: public enum Fields { Id, Temperature, Pressure, Humidity, WindSpeed };
	public enum Fields { DATA, ID };

	// *** If running your own Phant Server, enter URL below *** //
	private static final String sPHANT_BASE_URL = "https://data.sparkfun.com/";

	// *** getMethod can return GET or POST (POST is recommended so PrivateKey & Data are encrypted in transit) *** //
	public Method getMethod() { return Method.POST; }

	// *** *** *** *** Don't touch anything below this Line *** *** *** //
	public String getBaseURL() { return sPHANT_BASE_URL; }
	public String getPublicKey() { return sPHANT_PUBLIC_KEY; }
	public String getPrivateKey() { return sPHANT_PRIVATE_KEY; }
	//public String getDeleteKey() { return sPHANT_DELETE_KEY; }
	public String[] getFields() { String[] asFields = new String[Fields.values().length]; for(int x=0 ; x<Fields.values().length ; x++) { asFields[x] = Fields.values()[x].toString(); } return asFields; }
}
