package com.rssl.phizic.web.struts.layout;

/**
  * User: Zhuravleva
 * Date: 15.05.2009
 * Time: 13:09:53
  */
public interface HiddenInterface
{
	/**
	 * ��������� ����������� �������/��������� �������� ������
	 * @param buffer �����
	 * @param idHiddenLink ����� �������, ������� ���������� ������.
	*/
		void doColumnsHiddenItem(StringBuffer buffer, int idHiddenLink);
}
