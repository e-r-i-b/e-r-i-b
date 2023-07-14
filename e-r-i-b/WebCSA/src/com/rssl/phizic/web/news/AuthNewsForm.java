package com.rssl.phizic.web.news;
import org.apache.struts.action.ActionForm;

/**
 * @author basharin
 * @ created 20.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class AuthNewsForm extends ActionForm
{
	private String jsonString;

	public String getJsonString()
	{
		return jsonString;
	}

	public void setJsonString(String jsonString)
	{
		this.jsonString = jsonString;
	}
}
