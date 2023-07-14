package com.rssl.phizic.web.struts.layout;

import com.rssl.phizic.dataaccess.query.OrderParameter;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 01.08.2011
 * @ $Author$
 * @ $Revision$
 */

public interface SortedInterface
{
	/**
	 * Производит рендеринг блока с кнопками для сортировки данных по столбцам
	 * @param buffer - буфер
	 * @param parameter - параметр, по которому будет производиться сортировка
	 * @param orderParameters - список параметров, по которым производится сортировка в текущий момент
	 */
	void doPrintSortColumn(StringBuffer buffer, String parameter, List<OrderParameter> orderParameters);

	/**
	 * Производит рендеринг списка столбцов, по которым была произведена сортировка
	 * @param buf - буфер
	 * @param orderParameters - список параметров, по которым производится сортировка в текущий момент
	 * @param headers - элементы шапки таблицы
	 * @param sortProperties - MAP столбцов таблицы по которым может производиться сортировка
	 */
	void doPrintSortedElements(StringBuffer buf, List<OrderParameter> orderParameters, ArrayList headers, Map<Integer, String> sortProperties);
}
