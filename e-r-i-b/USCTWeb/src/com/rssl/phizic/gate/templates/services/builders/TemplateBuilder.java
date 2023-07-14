package com.rssl.phizic.gate.templates.services.builders;

import com.rssl.phizic.gate.templates.impl.TemplateDocument;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;

/**
 * Билдер шаблонов документов
 *
 * @author khudyakov
 * @ created 17.10.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateBuilder<T extends TemplateDocument>
{
	/**
	 * Создать шаблон документа по вернушнимся из ЕСУШ данным
	 * @param generated шаблон из WS
	 * @return шаблон
	 */
	TemplateDocument build(GateTemplate generated) throws Exception;

	/**
	 * Создать шаблон документа по вернушнимся из ЕСУШ данным
	 * @param template текущий шадлон
	 * @param generated шаблон из WS
	 * @return шаблон
	 */
	public TemplateDocument build(T template, GateTemplate generated) throws Exception;
}
