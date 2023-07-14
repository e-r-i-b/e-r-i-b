package com.rssl.phizic.locale.dynamic.resources;

import java.io.Serializable;

/**
 * интерфейс для динамических мультиязычных текстовок
 * @author komarov
 * @ created 02.10.2014
 * @ $Author$
 * @ $Revision$
 */
public interface LanguageResources<T> extends Serializable
{
	/**
	 * @return идентификатор
	 */
	T getId();

	/**
	 * @return идентификатор локали
	 */
	String getLocaleId();
}
