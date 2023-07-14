package com.rssl.phizic.business.documents.metadata.checkers;

import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class DocumentStateChecker extends DocumentCheckerBase
{
	/**
	 * ��������� ��������
	 * @param doc ��������
	 * @return ��������� ��������
	 */
	public boolean check(BusinessDocument doc)
	{
		return doc.getState().getCode().equalsIgnoreCase(getParameter("state"));
	}
}
