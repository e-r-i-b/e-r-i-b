package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.task.Task;

/**
 * @author Erkin
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������, ������������� � ��������� �������
 */
public interface PersonTask extends Task
{
	/**
	 * ���������� ���-�������� �� �������
	 * @param person - ������
	 */
	@MandatoryParameter
	void setPerson(Person person);

}
