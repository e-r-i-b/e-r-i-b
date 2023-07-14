package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.web.common.client.ext.sbrf.payment.services.ListServicesPaymentForm;

import java.util.List;
import java.util.ArrayList;

/**
 * список подуслуг услуги или поставщиков услуги
 * @author Dorzhinov
 * @ created 17.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListServicesPaymentMobileForm extends ListServicesPaymentForm
{
	private String type;

	private int itemsPerPage; //Кол-во элементов на странице. Используется для переопределения константы из браузерной версии.
	private boolean autoPaymentOnly; //показать только ПУ, поддерживающих автоплатежи
	private boolean includeServices; //признак, отвечающий за необходимость получения подкатегорий

	private List<Object> list = new ArrayList<Object>(); //объединяет services и providers из предка

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public int getItemsPerPage()
	{
		return itemsPerPage;
	}

	public void setItemsPerPage(int itemsPerPage)
	{
		this.itemsPerPage = itemsPerPage;
	}

	public List<Object> getList()
	{
		return list;
	}

	public void setList(List<Object> list)
	{
		this.list = list;
	}

	public boolean isAutoPaymentOnly()
	{
		return autoPaymentOnly;
	}

	public void setAutoPaymentOnly(boolean autoPaymentOnly)
	{
		this.autoPaymentOnly = autoPaymentOnly;
	}

	public boolean isIncludeServices()
	{
		return includeServices;
	}

	public void setIncludeServices(boolean includeServices)
	{
		this.includeServices = includeServices;
	}
}
