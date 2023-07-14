package com.rssl.phizic.business.fields;

import com.rssl.phizgate.common.documents.payments.fields.FieldValidationParameter;

/**
 * @author hudyakov
 * @ created 14.01.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class FieldValidationParameterBase implements FieldValidationParameter
{
	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
