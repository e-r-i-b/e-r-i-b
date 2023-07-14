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
	 * @return идентификатор удаляемой операции
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - идентификатор удаляемой операции
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
}
