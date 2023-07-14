package com.rssl.common.forms.validators.strategy;

import com.rssl.common.forms.processing.ValidationStrategy;

/**
 * @author krenev
 * @ created 16.09.2010
 * @ $Author$
 * @ $Revision$
 * ��������� ��������� ������� ����������:��������� ������������, ���� � �������� m�de ���� �������� document 
 */
public class DocumentValidationStrategy extends AbstractModeBasedValidatorStrategy
{
	public static final String MODE = "document";
	private static ValidationStrategy instance = new DocumentValidationStrategy();

	private DocumentValidationStrategy()
	{
		super(MODE);
	}

	public static ValidationStrategy getInstance()
	{
		return instance;
	}
}
