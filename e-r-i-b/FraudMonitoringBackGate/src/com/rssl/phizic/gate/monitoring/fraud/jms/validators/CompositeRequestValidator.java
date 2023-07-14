package com.rssl.phizic.gate.monitoring.fraud.jms.validators;

import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Композитный валидатор
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public class CompositeRequestValidator implements RequestValidator
{
	private List<RequestValidator> validators = new ArrayList<RequestValidator>();
	private RequestValidator enabledValidator;

	public CompositeRequestValidator(RequestValidator ... validators)
	{
		if (ArrayUtils.isNotEmpty(validators))
		{
			Collections.addAll(this.validators, validators);
		}
	}

	public boolean validate()
	{
		for (RequestValidator validator : validators)
		{
			if (!validator.validate())
			{
				enabledValidator = validator;
			}
		}
		return true;
	}

	public String getMessage()
	{
		if (enabledValidator != null)
		{
			return enabledValidator.getMessage();
		}

		throw new IllegalArgumentException();
	}
}
