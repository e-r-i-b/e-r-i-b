package com.rssl.phizic.web.common.client.ext.sbrf.payment.services;

import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;

import java.util.List;

/**
 * @author lukina
 * @ created 09.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListCategoryPaymentsForm extends ListPaymentServiceFormBase
{
	//������ ������������ ����� � �����������
	private List<Object> groupServices;
	//������ ����� � ���������������� ������������
	private List<Object> services;

	public List<Object> getGroupServices()
	{
		return groupServices;
	}

	public void setGroupServices(List<Object> groupServices)
	{
		this.groupServices = groupServices;
	}

	public List<Object> getServices()
	{
		return services;
	}

	public void setServices(List<Object> services)
	{
		this.services = services;
	}
}
