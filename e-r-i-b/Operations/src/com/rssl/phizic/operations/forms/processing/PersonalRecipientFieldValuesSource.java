package com.rssl.phizic.operations.forms.processing;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.dictionaries.GetPersonalPaymentFieldValuesOperation;

import java.util.Collections;
import java.util.Map;

/**
 * @author Erkin
 * @ created 10.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class PersonalRecipientFieldValuesSource extends FieldValuesSourceBase
{
	private final Map<String, String> fieldValues;

	/**
	 * ctor
	 * @param operationFactory
	 * @param providerId
	 */
	public PersonalRecipientFieldValuesSource(OperationFactory operationFactory, Long providerId)
			throws BusinessException, BusinessLogicException
	{
		super(operationFactory);

		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();

		GetPersonalPaymentFieldValuesOperation operation
				= operationFactory.create(GetPersonalPaymentFieldValuesOperation.class);
		if (person.getTrustingPersonId() != null)
			operation.initialize(person.getTrustingPersonId(), providerId);
		else operation.initialize(person.getId(), providerId);

		fieldValues = operation.getFieldValues();
	}
	

	public String getValue(String name)
	{
		return fieldValues.get(name);
	}

	public Map<String, String> getAllValues()
	{
		return Collections.unmodifiableMap(fieldValues);
	}

	public boolean isChanged(String name)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return fieldValues.isEmpty();
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}
