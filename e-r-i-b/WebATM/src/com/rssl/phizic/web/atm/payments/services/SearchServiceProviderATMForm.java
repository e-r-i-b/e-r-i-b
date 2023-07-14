package com.rssl.phizic.web.atm.payments.services;

import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;
import com.rssl.phizic.web.actions.payments.catalog.ApiSearchCatalogFormBase;
import com.rssl.phizic.web.actions.payments.forms.ContainRegionGuidActionFormInterface;

import java.util.Collections;
import java.util.List;

/**
 @author Pankin
 @ created 19.11.2010
 @ $Author$
 @ $Revision$
 */
public class SearchServiceProviderATMForm extends ApiSearchCatalogFormBase implements ContainRegionGuidActionFormInterface
{
	//in
	private String search;
	private Long regionId;
	private boolean autoPaymentOnly; //�������, ���������� �� ����� �� �����������, �������������� �������� �����������.
	private boolean includeServices; //�������, ����������� �� ��������� � ����� ����� �����
	private String regionGuid;       //����������� ������������� �������

	public Long getRegionId()
	{
		return regionId;
	}

	public void setRegionId(Long regionId)
	{
		this.regionId = regionId;
	}

	public boolean isIncludeServices()
	{
		return includeServices;
	}

	public void setIncludeServices(boolean includeServices)
	{
		this.includeServices = includeServices;
	}

	public String getRegionGuid()
	{
		return regionGuid;
	}

	public void setRegionGuid(String regionGuid)
	{
		this.regionGuid = regionGuid;
	}
}
