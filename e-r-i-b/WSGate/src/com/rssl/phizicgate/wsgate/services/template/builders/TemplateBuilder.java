package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate;

/**
 * Билдер шаблонов документов
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateBuilder
{
	/**
	 * Создать шаблон документа по вернушнимся из ЕСУШ данным
	 * @param generated шаблон из WS
	 * @return шаблон
	 */
	TemplateDocument build(GateTemplate generated) throws GateException, GateLogicException;
}
