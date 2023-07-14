package com.rssl.phizic.web.common;

/**
 * @author Krenev
 * @ created 31.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditFormBase extends FilterActionForm
{
	protected Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
