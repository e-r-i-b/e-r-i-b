package com.rssl.phizic.business.services.groups.destenaion;

import com.rssl.phizic.business.services.groups.ServicesGroupInformation;

/**
 * ��������� ��� ��������� ����� ��������
 * @author komarov
 * @ created 08.05.2015
 * @ $Author$
 * @ $Revision$
 */
public interface GroupDestenation<T>
{
	/**
	 * ��������� �������
	 * @param information ServicesGroupInformation
	 */
	void add(ServicesGroupInformation information);

	/**
	 * ���������� ���������
	 * @return ���������
	 */
	T getResult();
}
