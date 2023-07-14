package com.rssl.common.forms.doc;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.state.StateObject;

import java.io.Serializable;
import java.util.Map;

/**
 * Интерфейс фильтров машины состояний
 *
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public interface StateMachineFilter<SO extends StateObject> extends Serializable
{
	/**
	 * Проверка на активность фильтра
	 * @param stateObject объект машины состояний
	 * @return true - данный фильтр активен
	 */
	boolean isEnabled(SO stateObject) throws DocumentException, DocumentLogicException;

	/**
	 * Вернуть параметры фильтра
	 * @return параметры фильтра
	 */
	Map<String, String> getParameters();

	/**
	 * Установить параметры фильтра
	 * @param parameters параметры
	 */
	void setParameters(Map<String, String> parameters);
}
