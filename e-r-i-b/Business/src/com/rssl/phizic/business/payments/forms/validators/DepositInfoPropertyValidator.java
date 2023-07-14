package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Krenev
 * @ created 28.07.2008
 * @ $Author$
 * @ $Revision$
 */
// У DepositInfo валидируемого депозита получается свойство, указанное в property,
// и сравнивается со значением в expected-value
public class DepositInfoPropertyValidator extends FieldValidatorBase
{
	private static final String PARAMETER_PROPERTY = "property";
	private static final String PARAMETER_EXPECTED_VALUE = "expected-value";
	private String property;
	private String expectedValue;

	public void setParameter(String name, String value)
	{
		if (PARAMETER_PROPERTY.equals(name))
		{
			property = value;
		}
		else if (PARAMETER_EXPECTED_VALUE.equals(name))
		{
			expectedValue = value;
		}
	}

	public String getParameter(String name)
	{
		if (PARAMETER_PROPERTY.equals(name))
		{
			return property;
		}
		if (PARAMETER_EXPECTED_VALUE.equals(name))
		{
			return expectedValue;
		}
		return null;
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		String property = getParameter(PARAMETER_PROPERTY);
		String expectedValue = getParameter(PARAMETER_EXPECTED_VALUE);
		if (isValueEmpty(value))
			return true;

		try
		{

			PersonData data = PersonContext.getPersonDataProvider().getPersonData();
			DepositLink depositLink = data.getDeposit(Long.valueOf(value));

			if (depositLink == null)
				return false;

			String propertyValue = BeanUtils.getProperty(depositLink.getDepositInfo(), property);
			return expectedValue.equals(propertyValue);
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new RuntimeException(e);
		}
	}
}
