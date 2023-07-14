package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.fields.FieldDescription;

/**
 * @author niculichev
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoPaymentForm extends EditPaymentForm
{
	private String serviceName; // название сервиса
	private FieldDescription requisite; // ключевой реквизит

	public String getServiceName()
	{
		return serviceName;
	}

	public void setServiceName(String serviceName)
	{
		this.serviceName = serviceName;
	}

	public FieldDescription getRequisite()
	{
		return requisite;
	}

	public void setRequisite(FieldDescription requisite)
	{
		this.requisite = requisite;
	}
}
