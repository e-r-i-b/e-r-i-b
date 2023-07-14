package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.web.common.client.ext.sbrf.payment.services.ListCategoryPaymentsForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 17.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCategoryPaymentsMobileForm extends ListCategoryPaymentsForm
{
	private static final String TYPE = "service";

	private int itemsPerPage;
	
	private List<Object> list = new ArrayList<Object>(); //объединяет groupServices и services из предка

	public String getType()
	{
		return TYPE;
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
}
