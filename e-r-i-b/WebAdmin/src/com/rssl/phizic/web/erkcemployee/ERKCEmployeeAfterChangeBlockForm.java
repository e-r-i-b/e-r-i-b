package com.rssl.phizic.web.erkcemployee;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author akrenev
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� ������ ����������� ���� �������������� ����� ����� �����
 */

public class ERKCEmployeeAfterChangeBlockForm extends ListFormBase<ActivePerson>
{
	private Long id;

	/**
	 * @return ������������� ���������� �������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� ���������� �������
	 * @param id ������������� �������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
}
