package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.RequisiteType;
import org.apache.commons.lang.StringUtils;

/**
 * @author osminin
 * @ created 05.12.2012
 * @ $Author$
 * @ $Revision$
 * проверяем, соответствуют ли пришедшие значения значениям RequisiteType
 */
public class FieldRequisiteTypeValidator extends FieldValidatorBase
{
	private static final String SEPARATOR = ",";

	public FieldRequisiteTypeValidator(String message)
	{
		setMessage(message);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (StringUtils.isNotEmpty(value))
		{
			String [] requisiteTypes = value.split(SEPARATOR);
			for (int i = 0; i < requisiteTypes.length; i++)
			{
				if (!RequisiteType.validate(requisiteTypes[i]))
					return false;
			}
		}
		return true;
	}
}
