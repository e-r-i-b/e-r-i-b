package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.config.MobilePlatformRemoveOperation;
import com.rssl.phizic.operations.config.MobilePlatformSettingsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Map;

/**
 * ��������� mAPI � ������� ��������
 * @author Jatsky
 * @ created 29.07.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatformConfigShowAction extends ListActionBase
{

	@Override protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(MobilePlatformSettingsOperation.class);
	}

	/**
	 * ������� ����� ����������.
	 * � ����������� �������(�� ����������� ENH00319) ����� ������������ frm.FILTER_FORM
	 * @param frm struts-�����
	 * @param operation ��������. ��������� �����(�������� ��������) ������������ ��������
	 * @return ����� ����������
	 */
	@Override protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return MobilePlatformConfigShowForm.FILTER_FORM;
	}

	/**
	 * ��������� ������ ����������� ��� ��������� ������.
	 * @param query ������
	 * @param filterParams ��������� �������.
	 */
	@Override protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		query.setParameter("platformName", StringHelper.getEmptyIfNull(filterParams.get("platformName")));
		query.setParameter("platformId", StringHelper.getEmptyIfNull(filterParams.get("platformId")));
		query.setParameter("scheme", StringHelper.isEmpty((String) filterParams.get("scheme")) ? null : Boolean.valueOf((String) filterParams.get("scheme")));
        query.setParameter("isSocial", false);
	}

	@Override protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(MobilePlatformRemoveOperation.class);
	}
}
