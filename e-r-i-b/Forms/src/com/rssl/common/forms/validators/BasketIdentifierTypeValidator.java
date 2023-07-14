package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import org.dom4j.DocumentType;

/**
 * @author shapin
 * @ created 04.03.15
 * @ $Author$
 * @ $Revision$
 */
public class BasketIdentifierTypeValidator extends FieldValidatorBase
{
	private static final String RC = "RC";
	private static final String DL = "DL";
	private static final String INN = "INN";
	private static final String MESSAGE = "Неверное значение поля \"Системный ID\". Допустимые значения: DL, INN, RC.";

	public BasketIdentifierTypeValidator()
	{
		super();
		setMessage(MESSAGE);
	}


	public boolean validate(String value) throws TemporalDocumentException
	{
		return value.equals(DL) || value.equals(INN) || value.equals(RC);
	}
}
