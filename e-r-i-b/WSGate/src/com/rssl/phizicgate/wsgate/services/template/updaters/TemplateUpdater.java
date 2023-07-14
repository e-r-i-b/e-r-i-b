package com.rssl.phizicgate.wsgate.services.template.updaters;

import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Апдейтер шаблонов документов
 *
 * @author khudyakov
 * @ created 05.08.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateUpdater
{
	/**
	 * Обновить шаблон документа
	 * @param template Gate представление шаблона
	 * @throws GateException
	 */
	void update(GateTemplate template) throws GateException;
}
