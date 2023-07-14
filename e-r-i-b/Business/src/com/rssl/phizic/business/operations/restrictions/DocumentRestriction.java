package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;

/**
 * ѕроверка проходит ли документ по ограничению
 * @author niculichev
 * @ created 20.10.2011
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentRestriction extends Restriction
{
	public boolean accept(BusinessDocument document) throws BusinessException;
}
