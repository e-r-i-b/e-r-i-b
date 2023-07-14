package com.rssl.phizic.operations.documents.templates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.source.ExistingTemplateSource;
import com.rssl.phizic.business.documents.templates.source.TemplateDocumentSource;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author Evgrafov
 * @ created 14.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 58855 $
 */

public interface RemoveTemplateOperation<E> extends RemoveEntityOperation
{
	/**
	 * ������������� ��������
	 * @param source ������
	 * @throws BusinessException
	 */
	void initialize(TemplateDocumentSource source) throws BusinessException, BusinessLogicException;
}
