package com.rssl.phizic.task;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.module.Module;

/**
 * @author Erkin
 * @ created 03.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������.
 * ��������: 
 * - ��������� ������,
 * - ���������� ������,
 * - ���������, ������������ � ������.
 * �����������:
 * - ���������� ������ ������������� ����������� ��� ����������
 */
@Statefull
public interface Task
{
	/**
	 * ���������� ������, � ������� ����������� ������
	 * @param module - ������, � ������� ����������� ������ (never null)
	 */
	@MandatoryParameter
	void setModule(Module module);
}
