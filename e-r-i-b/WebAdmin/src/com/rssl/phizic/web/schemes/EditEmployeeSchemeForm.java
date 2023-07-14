package com.rssl.phizic.web.schemes;

import com.rssl.phizic.business.services.groups.ServicesGroupIterator;

/**
 * @author akrenev
 * @ created 22.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������������� ����� ���� ����������
 */

public class EditEmployeeSchemeForm extends EditSchemeForm
{
	private ServicesGroupIterator groups;

	/**
	 * @return ������ ��������
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public ServicesGroupIterator getGroups()
	{
		groups.refresh();
		return groups;
	}

	/**
	 * ������ ������ ��������
	 * @param groups ������
	 */
	public void setGroups(ServicesGroupIterator groups)
	{
		this.groups = groups;
	}
}
