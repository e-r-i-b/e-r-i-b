package com.rssl.phizic.web.struts.layout;

/**
 * @author Krenev
 * @ created 16.02.2009
 * @ $Author$
 * @ $Revision$
 */
public interface PaginationRenderer
{
	/**
	 * Вывести пагинацию
	 * @param buf  буфер отрисовки
	 * @param paginationOffset смещение данных
	 * @param paginationSize запрошенный размер данных.
	 * @param realDataSize реальный размер выборки.
	 * @param offsetFieldName имя поля для смещения
	 * @param sizeFieldName имя поля для размера выборки
	 */
	void doPrintPagination(StringBuffer buf, int paginationOffset, int paginationSize, int realDataSize, String offsetFieldName, String sizeFieldName, String paginationType);
}
