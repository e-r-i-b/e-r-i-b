package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Dorzhinov
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������� � �������
 * �����! ��������� ������������ � ��������� �������� �������
 */
public interface WidgetAccessor
{
	/**
	 * ���������� ����������� �������� ��������� ���������
	 * @param widgetDefinition - ������-���������
	 * @return true - widgetDefinition �������� �������
	 */
	boolean access(WidgetDefinition widgetDefinition) throws BusinessException, BusinessLogicException;
}
