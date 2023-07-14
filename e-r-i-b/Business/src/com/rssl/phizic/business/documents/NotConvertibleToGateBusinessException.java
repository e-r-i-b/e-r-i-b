package com.rssl.phizic.business.documents;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * ����������, ��������� ���� ������ �������� �� ������������ �������������� � ��������� ����
 *
 * @author khudyakov
 * @ created 17.03.15
 * @ $Author$
 * @ $Revision$
 */
public class NotConvertibleToGateBusinessException extends BusinessException
{
	private static final String MESSAGE = "�������� className = %s �� ��������� ��������� ConvertableToGateDocument";

	public NotConvertibleToGateBusinessException(BusinessDocument document)
	{
		super(String.format(MESSAGE, document.getClass().getName()));
	}
}
