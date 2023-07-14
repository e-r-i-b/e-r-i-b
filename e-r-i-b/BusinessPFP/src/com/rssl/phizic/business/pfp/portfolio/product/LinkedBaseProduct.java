package com.rssl.phizic.business.pfp.portfolio.product;

/**
 * @author mihaylov
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 * ��������� ��� ������� ��������� � ��������, ��� ������� �������������� ���������� � ���������� � ������ ���������
 */
public interface LinkedBaseProduct
{
	/**
	 * @return ������������� ��������
	 */
	Long getId();

	/**
	 * ���������� ������������� ���������� �������� � ������ ��������
	 * @param id - ������������� ���������� ��������
	 */
	void setLinkedProductId(Long id);

	/**
	 * @return ������������� ���������� �������� � ������ ��������
	 */
	Long getLinkedProductId();
}
