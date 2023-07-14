package com.rssl.phizic.web.struts.layout;

/**
 * @author Evgrafov
 * @ created 26.07.2007
 * @ $Author: krenev $
 * @ $Revision: 9528 $
 */

public interface SelectionInterface
{
	/**
	 * Выводит HTML для выбора одного элемента из списка
	 * @param buf буфер
	 * @param name имя парамета поля для вывода
	 * @param itemId id текущего итема
	 * @param selected true == выбран выбран
	 */
	void doPrintItemsSelectOne(StringBuffer buf, String name, String itemId,  String[] styleClass, boolean selected);

	/**
	 * Выводит HTML для выбора нескольких элементов из списка
	 * @param buf буфер
	 * @param name имя парамета поля для вывода
	 * @param itemId id текущего итема
	 * @param selected true == выбран выбран
	 */
	void doPrintItemsSelectMulti(StringBuffer buf, String name, String itemId, String[] styleClass, boolean selected);

	/**
	 * Выводит HTML заголовок (отмена выбора for example)
	 * @param buf буфер
	 * @param name имя парамета поля для вывода
	 * @param text подпись
	 */
	void doPrintHeaderSelectOne(StringBuffer buf, String name, String text);

	/**
	 * Выводит HTML для выбора (select all feature support)
	 * @param buf буфер
	 * @param name имя парамета поля для вывода
	 * @param text подпись
	 */
	void doPrintHeaderSelectMulti(StringBuffer buf, String name, String text);
}
