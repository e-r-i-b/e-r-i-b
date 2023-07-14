package com.rssl.phizic.operations.payment.support;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 12.01.2006
 * @ $Author: khudyakov $
 * @ $Revision: 51397 $
 */

public interface DocumentTarget extends Serializable
{
	/**
	 * Сохранить документа в БД
	 * @param document документ
	 * @throws BusinessException
	 */
	void save(BusinessDocument document) throws BusinessException;
}
