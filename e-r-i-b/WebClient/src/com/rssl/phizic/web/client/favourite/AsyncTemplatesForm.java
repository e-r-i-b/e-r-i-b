package com.rssl.phizic.web.client.favourite;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ����� ��������� ������ ������� ����������� �������� ����� ����
 * @ author gorshkov
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AsyncTemplatesForm extends ActionFormBase
{
	private Long[] sortTemplates;

	/**
	 * @return ������ id �������� � ������� ����������
	 */
	public Long[] getSortTemplates()
	{
		return sortTemplates;
	}

	/**
	 * @param sortTemplates - ������ id �������� � ������� ����������
	 */
	public void setSortTemplates(Long[] sortTemplates)
	{
		this.sortTemplates = sortTemplates;
	}
}
