package com.tractis.identity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * Implements identity verification callback
 * @author dave
 */
public class IdentityVerificationCheck extends  IdentityTag {

	public int doEndTag() throws JspException {
		
		HttpURLConnection connection = this.prepareConnection();
		ServletRequest request = this.context.getRequest();
		Map<String,String> filtered = filterAttributes(request.getParameterMap());
		
		//Attribute value checking vs remote Tractis service
		try {
			InputStream inputStream = connection.getInputStream();
		} catch (IOException e) {
			//If we arrived here is because authentication failed
			print("Authentication failed");
			this.clearSessionAttributes(filtered.keySet());
			this.afterVerification(false);
			return -1;
		}				
		
		//This code is only for visualization
		print("Authentication succeed for data ");
		this.addAttributesToSession(filtered);
		this.afterVerification(true);
		
		return 0;
	}
	
	private void addAttributesToSession(Map<String,String> attributes){
		for(String id : attributes.keySet()) this.context.getSession().setAttribute(id, attributes.get(id));
	}
	
	private void clearSessionAttributes(Collection<String> attributesIdentifiers){
		for(String id : attributesIdentifiers) this.context.getSession().removeAttribute(id);
	}
	
	private void afterVerification(boolean succeed){
		HttpServletResponse res = (HttpServletResponse) this.context.getResponse();
		res.setContentType("text/plain");
		res.setStatus(res.SC_MOVED_TEMPORARILY);
		
		String siteURL;
		if (succeed){
			siteURL = properties.getProperty("tractis.verificationokurl");
		}else {
			siteURL = properties.getProperty("tractis.verificationfailedurl");
		}
		
        res.setHeader("Location", siteURL);
	}
	
	private HttpURLConnection prepareConnection() throws JspException{
		try{ 
			
			if (properties == null) this.initProperties();
			
			//Must connect to remote endpoint passing it's params to check they're true
			URI tractisVerificationURL = URI.create(properties.getProperty("tractis.pasarelacheck"));
			HttpURLConnection connection = (HttpURLConnection) tractisVerificationURL.toURL().openConnection();
			connection.setRequestMethod("POST");
			
			connection.setDoOutput(true);
			
			ServletRequest request = this.context.getRequest();
			Map<String,String[]> parameterMap = new HashMap<String, String[]>(request.getParameterMap());
			//API key is added
			parameterMap.put("api_key", new String[]{properties.getProperty("tractis.apikey")});
			
			//Parameters are sent using post body
			this.addParameters(parameterMap, connection);
			
			return connection;
		} catch (IOException e) {
			throw new JspException(e);
		}
	}
	
	//TODO find a better way of sending post params using this kind of connection
	private void addParameters(Map<String,String[]> parameters, HttpURLConnection connection) throws IOException{
		boolean first = true;
		String data = "";
		for(String key : parameters.keySet()){
			String value = parameters.get(key)[0]; //Only first value will be used because no multi-valued parameters are used
			String param = URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8"); 
			
			if (first) {
				data = param;
				first = false;
			}
			else{
				data = data + "&" + param;
			}
		}
		OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
		wr.write(data);
		wr.flush();
	}
	
	private Map<String,String> filterAttributes(Map unfiltered){
		Map<String, String> result = new HashMap<String, String>();
		for(Object key : unfiltered.keySet()){
			if (key.toString().startsWith("tractis:attribute:")) {
				String value = ((String[])unfiltered.get(key))[0];
				result.put(key.toString(), value);
			}
		}
		return result;
	}
	
	public int doStartTag() throws JspException {
		return 0;
	}


}
