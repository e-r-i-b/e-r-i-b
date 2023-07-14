package com.rssl.common.forms.xslt;

import com.rssl.common.forms.FormException;

import javax.xml.transform.Source;
import java.util.Map;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 31.01.2006
 * @ $Author: omeliyanchuk $
 * @ $Revision: 8314 $
 */

public interface FilteredEntityListSource extends Serializable
{
	/**
	 * @param params параметры отбора (фильтра).
	 * ключ - имя поля фильтра, значение - значение фильтра
	 * @param selectedKeys выбранные ключи
	 * @return XML документ содержащий выбранные документы
	 */
	Source getList(Map<String, Object> params, String[] selectedKeys) throws FormException;

	/**
	 * @param params параметры отбора (фильтра).
	 * ключ - имя поля фильтра, значение - значение фильтра
	 * @return XML документ содержащий условия отбора
	 */
	Source getFilter(Map<String, Object> params);

}
