package com.rssl.auth.csa.back.servises.operations;

import com.rssl.phizic.common.types.csa.IdentificationType;

/**
 * @author tisov
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 * Интерфейс контекста идентификации для логирования
 */
public interface LogProfileIdentificationContext
{
	/**
	 * Параметр идентификации
	 * @return
	 */
	public String getIdentificationParam();

	/**
	 * Тип идентификации
	 * @return
	 */
	public IdentificationType getIdentificationType();

}
