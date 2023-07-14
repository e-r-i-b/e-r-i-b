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
	 * ���������� ��������� ����� � �������� ��� ���������� ������ �� ��������
	 * @param buffer - �����
	 * @param parameter - ��������, �� �������� ����� ������������� ����������
	 * @param orderParameters - ������ ����������, �� ������� ������������ ���������� � ������� ������
	 */
	void doPrintSortColumn(StringBuffer buffer, String parameter, List<OrderParameter> orderParameters);

	/**
	 * ���������� ��������� ������ ��������, �� ������� ���� ����������� ����������
	 * @param buf - �����
	 * @param orderParameters - ������ ����������, �� ������� ������������ ���������� � ������� ������
	 * @param headers - �������� ����� �������
	 * @param sortProperties - MAP �������� ������� �� ������� ����� ������������� ����������
	 */
	void doPrintSortedElements(StringBuffer buf, List<OrderParameter> orderParameters, ArrayList headers, Map<Integer, String> sortProperties);
}
