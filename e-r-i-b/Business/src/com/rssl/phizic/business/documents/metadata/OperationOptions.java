package com.rssl.phizic.business.documents.metadata;

import com.rssl.phizic.business.documents.payments.BusinessDocument;

import java.io.Serializable;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public interface OperationOptions extends Serializable
{
	/**
	 * @return ��� ����� ��������
	 */
	String getFormName();

	/**
	 * @return ���� ����������� ���������� ��������
	 * @param businessDocument
	 */
	 boolean canDo(BusinessDocument businessDocument);// throws DocumentException, DocumentLogicException;
}
