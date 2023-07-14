package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.owners.person.Profile;

/**
 * Интерфейс шаблона документа
 *
 * @author khudyakov
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateDocument extends GateTemplate
{
	/**
	 * @return профиль владельца документа
	 */
	Profile getProfile();

	/**
	 * Установить владельца шаблона документа
	 * @param profile владелец
	 */
	void setProfile(Profile profile);
}
