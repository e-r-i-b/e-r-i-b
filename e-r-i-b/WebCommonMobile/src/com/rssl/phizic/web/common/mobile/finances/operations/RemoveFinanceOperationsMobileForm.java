package com.rssl.phizic.web.common.mobile.finances.operations;

import org.apache.struts.action.ActionForm;

/**
 * @author lepihina
 * @ created 05.03.14
 * $Author$
 * $Revision$
 */
public class RemoveFinanceOperationsMobileForm extends ActionForm
{
	private Long id;

	/**
	 * @return ������������� ��������� ��������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - ������������� ��������� ��������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
}
