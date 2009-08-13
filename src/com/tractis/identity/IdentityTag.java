package com.tractis.identity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * Abstract class for tags used to connect woth tractis identity verifications
 * @author dave
 *
 */
public abstract class IdentityTag implements Tag{

	protected Tag parent;
	protected PageContext context;
	protected JspWriter out;
	protected static Properties properties = null;
	
	protected void initProperties() throws JspException {
		try {
			InputStream resourceAsStream = this.getClass().getResourceAsStream("/tractis-settings.properties");
			properties = new Properties();
			properties.load(resourceAsStream);
		} catch (IOException e) {
			throw new JspException("Cannot recover Tractis service properties, please check that tractis-settings.properties is available on classpath");
		}
	}
	
	public Tag getParent() {
		return this.parent;
	}

	public void release() {
	}

	public void setPageContext(PageContext context) {
		this.context = context;
		this.out = this.context.getOut();
	}

	public void setParent(Tag parent) {
		this.parent = parent;
	}
	
	public void print(String message){
		try {
			this.out.print(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}
