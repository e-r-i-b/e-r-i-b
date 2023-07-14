package com.rssl.phizic.web.component;

import java.util.Map;

/**
 * @author akrenev
 * @ created 31.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * интерфейс компонента "фильтр по интервалу времени"
 */

public interface PeriodFilter
{
	/**
	 * Нормализует параметры фильтра
	 * Т.е. получает из относительных значений абсолютные:
	 * например, "неделя" -> [сейчас-неделя, сейчас]
	 * @return нормализованные параметры фильтра
	 */
	public PeriodFilter normalize();

	/**
	 * @return все параметры фильтра в виде мапа
	 */
	public Map<String, Object> getParameters();
}
