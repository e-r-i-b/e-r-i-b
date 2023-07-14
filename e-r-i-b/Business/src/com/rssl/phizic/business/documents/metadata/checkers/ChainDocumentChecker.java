package com.rssl.phizic.business.documents.metadata.checkers;

import com.rssl.phizic.business.documents.payments.BusinessDocument;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class ChainDocumentChecker extends DocumentCheckerBase
{
	private List<DocumentChecker> checkers = new ArrayList<DocumentChecker>();

	/**
	 * Проверить документ
	 * @param doc документ
	 * @return результат проверки
	 */
	public boolean check(BusinessDocument doc)
	{
		for(DocumentChecker documentChecker:checkers){
			if (!documentChecker.check(doc)){
				 return false;
			}
		}
		return true;
	}
	/**
	 * Добавить в цепочку checker
	 * @param checker "проверятель"
	 */
	public void addChecker(DocumentChecker checker)
	{
		checkers.add(checker);
	}


}
