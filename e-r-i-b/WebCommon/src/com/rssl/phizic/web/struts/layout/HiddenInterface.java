package com.rssl.phizic.web.struts.layout;

/**
  * User: Zhuravleva
 * Date: 15.05.2009
 * Time: 13:09:53
  */
public interface HiddenInterface
{
	/**
	 * Добавляет возможность скрытия/раскрытия столбцов списка
	 * @param buffer буфер
	 * @param idHiddenLink номер столбца, который необходимо скрыть.
	*/
		void doColumnsHiddenItem(StringBuffer buffer, int idHiddenLink);
}
