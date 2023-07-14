package com.rssl.phizic.business.operations.restrictions.defaults;

import com.rssl.phizic.business.operations.restrictions.DocumentRestriction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author Dorzhinov
 * @ created 02.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class NullDocumentRestriction implements DocumentRestriction
{
	public static final NullDocumentRestriction INSTANCE = new NullDocumentRestriction();
	
	public boolean accept(BusinessDocument document) throws BusinessException
	{
		return true;
	}
}
