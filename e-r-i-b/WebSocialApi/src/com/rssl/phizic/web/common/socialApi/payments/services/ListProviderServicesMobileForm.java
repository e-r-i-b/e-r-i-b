package com.rssl.phizic.web.common.socialApi.payments.services;

import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 20.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListProviderServicesMobileForm extends ActionFormBase
{
	//TODO ��� ����������� BUG078963 � ��� socialAPI, ������������� �� ListProviderServicesAPIFormBase
	private Long id; //���������� id ����������
	private List<BillingServiceProvider> serviders = new ArrayList<BillingServiceProvider>(); //������ ������-����������� ��� ����������-�����.

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public List<BillingServiceProvider> getServiders()
	{
		return serviders;
	}

	public void setServiders(List<BillingServiceProvider> serviders)
	{
		this.serviders = serviders;
	}
}
