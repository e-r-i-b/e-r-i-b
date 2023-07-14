package com.rssl.phizic.test.web.credit.crm;

import org.apache.struts.action.ActionForm;

/* Форма запроса согласия на оферту
* @ author: Moshenko
* @ created: 5.06.15
* @ $Author$
* @ $Revision$
*/
public class InitiateConsumerProductOfferRqTestForm extends ActionForm
{
	private String initiateConsumerProductOfferRqXML;

	public String getInitiateConsumerProductOfferRqXML()
	{
		return initiateConsumerProductOfferRqXML;
	}

	public void setInitiateConsumerProductOfferRqXML(String initiateConsumerProductOfferRqXML)
	{
		this.initiateConsumerProductOfferRqXML = initiateConsumerProductOfferRqXML;
	}
}
