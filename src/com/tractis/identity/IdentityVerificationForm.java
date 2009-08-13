package com.tractis.identity;

import java.io.IOException;

import javax.servlet.jsp.JspException;

/**
 * Creates a new form enabling connection with Tractis identity services
 * @author dave
 *
 */
public class IdentityVerificationForm extends IdentityTag {
	
	public int doEndTag() throws JspException {
		try {
			
			if (properties == null) this.initProperties();
			
			this.out.print("<form method='post' action='" + properties.getProperty("tractis.pasarelaurl")+ "'>");
			this.out.print("<input type='hidden' value='" + properties.getProperty("tractis.apikey") + "' name='api_key'/>");
			this.out.print("<input type='hidden' value='" + properties.getProperty("tractis.callbackurl") + "' name='notification_callback'/>");
			this.out.print("<input type='submit' value='" + properties.getProperty("tractis.submitbutton") + "' name='commit'/>");
			this.out.print("<input type='hidden' value='" + properties.getProperty("tractis.isverificationpublic") + "' name='public_verification'/>");
			this.out.print("</form>");
		} catch (IOException e) {
			throw new JspException(e);
		}
		
		return 0;
	}

	public int doStartTag() throws JspException {
		return 0;
	}

	
}
