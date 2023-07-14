package com.rssl.phizic.business.documents.templates.validators;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * Интерфейс валидаторов шаблона
 *
 * @author khudyakov
 * @ created 16.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateValidator
{
	/**
	 * Проверить шаблон
	 * @param template шаблон
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	void validate(TemplateDocument template) throws BusinessException, BusinessLogicException;
}
