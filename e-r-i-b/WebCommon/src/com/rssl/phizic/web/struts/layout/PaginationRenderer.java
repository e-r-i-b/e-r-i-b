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
	 * ������� ���������
	 * @param buf  ����� ���������
	 * @param paginationOffset �������� ������
	 * @param paginationSize ����������� ������ ������.
	 * @param realDataSize �������� ������ �������.
	 * @param offsetFieldName ��� ���� ��� ��������
	 * @param sizeFieldName ��� ���� ��� ������� �������
	 */
	void doPrintPagination(StringBuffer buf, int paginationOffset, int paginationSize, int realDataSize, String offsetFieldName, String sizeFieldName, String paginationType);
}
