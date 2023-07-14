package com.rssl.phizic.business.documents.metadata.checkers;

import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class FalseDocumentChecker extends DocumentCheckerBase
{
	/**
	 * Проверить документ
	 * @param doc документ
	 * @return результат проверки
	 */
	public boolean check(BusinessDocument doc)
	{
		return false;
	}
}
