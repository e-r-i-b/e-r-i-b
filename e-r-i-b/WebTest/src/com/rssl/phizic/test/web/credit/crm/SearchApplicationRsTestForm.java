package com.rssl.phizic.test.web.credit.crm;

import org.apache.struts.action.ActionForm;

/**
 * User: Moshenko
 * Date: 14.06.15
 * Time: 13:24
 * Взаимодействие с "Розничным" CRM
 */
public class SearchApplicationRsTestForm extends ActionForm
{
	//XML-сообщение SearchApplicationRs
	private String SearchApplicationRsXML;

	public String getSearchApplicationRsXML()
	{
		return SearchApplicationRsXML;
	}

	public void setSearchApplicationRsXML(String searchApplicationRsXML)
	{
		SearchApplicationRsXML = searchApplicationRsXML;
	}
}
