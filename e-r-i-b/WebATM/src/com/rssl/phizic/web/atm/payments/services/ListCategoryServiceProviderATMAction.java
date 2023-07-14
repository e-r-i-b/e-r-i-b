package com.rssl.phizic.web.atm.payments.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticATMPersonData;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.web.actions.payments.catalog.ApiCatalogActionBase;
import com.rssl.phizic.web.actions.payments.IndexForm;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;

/**
 * ��������� ������ ��������� �����
 * ��� �������� �� ������ �������� ������ (������ �����) ���� ������ ����������� ������.
 * @author Dorzhinov
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 * ������: /private/dictionary/servicesPayments.do
 * ������� ���������	���	�����������	���������
 * id	string	������������� ��������� ��� ������	[0-1]
 * region	integer	������������� �������	[0-1]
 * regionGuid	string	����������� ������������� �������		[0-1]
 *      �������������� �������� ����� �������� ��������� ���������� ��������. ���� ��� ��������� �����������, ����� ������������ � ������ �������������� ������� ������������.
 * autoPaymentOnly	boolean	�������, ���������� �� ����� �����������, �������������� �������� �����������. ���� true � ���������� ������ �� ���������, ������, ����������, ������� ��������� ������� ����������, ���� false ���� �� ��������, ���������� ����.	[0-1]
 * paginationSize	integer	������ �������������� �������. ��� ���������� ������� ��������� �������� ��� ���������� ��������������� �������.	[0-1]
 * paginationOffset	integer	�������� ������������ ������ �������. �� ��������� ����� ����.	[0-1]
 */
public class ListCategoryServiceProviderATMAction extends ApiCatalogActionBase
{

	protected boolean getFinalDescendants(IndexForm frm)
	{
		return !ConfigFactory.getConfig(AtmApiConfig.class).isShowServices();
	}

	protected String getChanel()
	{
		return "ATMAPI";
	}

	protected Long getRegionId(ListPaymentServiceFormBase form) throws BusinessException
	{
		ListCategoryATMForm frm = (ListCategoryATMForm) form;
		//TODO ������� ��� BUG082486: [ISUP] ��� : ����� ����������. ������������ � ID ������� ��� ��������� c���������� �����������.
		// ������ � ��������� ������ � ��������������� ����� �� ATM, ������� ������ ��� "��������� ������ ����� ����������".
		Long regionId = ListATMActionBase.getRegionIdWithGuid(frm);
		if (PersonContext.isAvailable() &&
			(PersonContext.getPersonDataProvider().getPersonData() instanceof StaticATMPersonData))
		{
			StaticATMPersonData personData = (StaticATMPersonData)PersonContext.getPersonDataProvider().getPersonData();
			personData.setServProvRegionID(regionId);
		}

		return regionId;
	}
}
