package com.rssl.phizic.web.common.client.ext.sbrf.payment.services;

import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;

import java.util.List;
import java.util.Map;

/**
 * @author lukina
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListServicesPaymentForm extends ListPaymentServiceFormBase
{
	//список услуг
	private List<Object> services;
	//список поставщиков
	private List<Object> providers;
	//иерархия услуг
	private List<Object> paymentServices;
	//список родительских услуг вида id->name
	private Map<String, String> parentServices;
	//список id родительских услуг
	private String parentIds;

	public List<Object> getProviders()
	{
		return providers;
	}

	public void setProviders(List<Object> providers)
	{
		this.providers = providers;
	}

	public List<Object> getServices()
	{
		return services;
	}

	public void setServices(List<Object> services)
	{
		this.services = services;
	}

	public List<Object> getPaymentServices()
	{
		return paymentServices;
	}

	public void setPaymentServices(List<Object> paymentServices)
	{
		this.paymentServices = paymentServices;
	}

	public Map<String, String> getParentServices()
	{
		return parentServices;
	}

	public void setParentServices(Map<String, String> parentServices)
	{
		this.parentServices = parentServices;
	}

	public String getParentIds()
	{
		return parentIds;
	}

	public void setParentIds(String parentIds)
	{
		this.parentIds = parentIds;
	}
}
