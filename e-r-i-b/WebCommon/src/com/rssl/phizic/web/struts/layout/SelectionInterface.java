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
	 * ������� HTML ��� ������ ������ �������� �� ������
	 * @param buf �����
	 * @param name ��� �������� ���� ��� ������
	 * @param itemId id �������� �����
	 * @param selected true == ������ ������
	 */
	void doPrintItemsSelectOne(StringBuffer buf, String name, String itemId,  String[] styleClass, boolean selected);

	/**
	 * ������� HTML ��� ������ ���������� ��������� �� ������
	 * @param buf �����
	 * @param name ��� �������� ���� ��� ������
	 * @param itemId id �������� �����
	 * @param selected true == ������ ������
	 */
	void doPrintItemsSelectMulti(StringBuffer buf, String name, String itemId, String[] styleClass, boolean selected);

	/**
	 * ������� HTML ��������� (������ ������ for example)
	 * @param buf �����
	 * @param name ��� �������� ���� ��� ������
	 * @param text �������
	 */
	void doPrintHeaderSelectOne(StringBuffer buf, String name, String text);

	/**
	 * ������� HTML ��� ������ (select all feature support)
	 * @param buf �����
	 * @param name ��� �������� ���� ��� ������
	 * @param text �������
	 */
	void doPrintHeaderSelectMulti(StringBuffer buf, String name, String text);
}
