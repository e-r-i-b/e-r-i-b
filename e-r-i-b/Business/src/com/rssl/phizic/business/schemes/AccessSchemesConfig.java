package com.rssl.phizic.business.schemes;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;

/**
 * @author Roshka
 * @ created 26.02.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class AccessSchemesConfig extends Config
{
	protected AccessSchemesConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @param accessType тип доступа
	 * @return схема по умолчанию для типа доступа
	 */
	public abstract SharedAccessScheme getDefaultAccessScheme(AccessType accessType);

	/**
	 * @return все схемы доступа
	 */
	public abstract List<SharedAccessScheme> getSchemes();

	/**
	 * Поиск схемы по ее коду
	 * @param code код схемы
	 * @return схема
	 */
	public abstract SharedAccessScheme getByCode(String code);

	/**
	 * @return Схема доступа встроенной учетной записи администратора
	 */
	public abstract SharedAccessScheme getBuildinAdminAccessScheme();

	/**
	 * @return Схема доступа для анонимного клиента
	 */
	public abstract SharedAccessScheme getAnonymousClientAccessScheme();
}
