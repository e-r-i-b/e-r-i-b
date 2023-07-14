package com.rssl.phizic.web.configure.exceptions;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.business.exception.ExceptionEntryService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;

/**
 * ���������, ����������� ������������� ������ � �������� ��������������� � �����������.
 * @author mihaylov
 * @ created 15.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class ExistExceptionEntryValidator extends FieldValidatorBase
{
	private static final String VALIDATION_MESSAGE = "��������� ���� ������������� ������ �� ������. ������� ���������� �������������.";
	private static final ExceptionEntryService exceptionEntryService = new ExceptionEntryService();

	/**
	 * �����������
	 */
	public ExistExceptionEntryValidator()
	{
		setMessage(VALIDATION_MESSAGE);
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		if(StringHelper.isEmpty(value))
			return true;

		try
		{
			return exceptionEntryService.getById(Long.valueOf(value)) != null;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException("�� ������� ��������� ������������� ������",e);
		}
	}
}
