package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.security.PermissionUtil;

/**
 * @author Dorzhinov
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class GenericWidgetAccessor implements WidgetAccessor
{
	public final boolean access(WidgetDefinition widgetDefinition) throws BusinessException, BusinessLogicException
	{
		boolean rc = widgetDefinition.isAvailability();
		rc = rc && impliesWidgetPermission(widgetDefinition);
		rc = rc && impliesMainPermission(widgetDefinition);
		rc = rc && checkData(widgetDefinition);
		return rc;
	}

	/**
	 * ��������� ������� ������� � ������-��������
	 * @param widgetDefinition - ������-���������
	 * @return true, ���� ���� ������ � ������-��������
	 */
	private boolean impliesWidgetPermission(WidgetDefinition widgetDefinition)
	{
		return PermissionUtil.impliesOperation(widgetDefinition.getOperation(), null);
	}

	/**
	 * ��������� ������� ������� � ����������������, ��������������� � �������
	 * @param widgetDefinition - ������-���������
	 * @return true, ���� ���� ������ � �������� ����������������
	 */
	protected boolean impliesMainPermission(WidgetDefinition widgetDefinition)
	{
		return true;
	}

	/**
	 * ��������� ������� �������� ��� �������
	 * @param widgetDefinition - ������-���������
	 * @return true, ���� ��� ������� ���� ����������� ������
	 */
	protected boolean checkData(WidgetDefinition widgetDefinition) throws BusinessException, BusinessLogicException
	{
		return true;
	}
}
