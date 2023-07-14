package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.web.Widget;
import com.rssl.phizic.common.types.annotation.Stateless;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ������������� �������
 * �����! ��������� ������������ � ��������� �������� ������� 
 */
@Stateless
public interface WidgetInitializer<TWidget extends Widget>
{
	/**
	 * ���������������� ������
	 * @param widget - ������, ������� ����� ���������
	 */
	void init(TWidget widget) throws BusinessException, BusinessLogicException;
}
