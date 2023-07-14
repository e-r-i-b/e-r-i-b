package com.rssl.phizic.business.documents.exceptions;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author krenev
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class WrongDocumentTypeException extends DocumentException
{
	/**
	 * ����������� ���������� 
	 * @param document �������� � �������� �����
	 * @param expected ��������� ��� ���������
	 */
	public WrongDocumentTypeException(BusinessDocument document, Class expected)
	{
		super("�������� ��� ��������� "+document.getClass().getName()+". ��������� "+expected.getName());
	}
}
