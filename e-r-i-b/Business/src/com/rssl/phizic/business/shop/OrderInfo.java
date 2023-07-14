package com.rssl.phizic.business.shop;

/**
 * @author Mescheryakova
 * @ created 06.12.2010
 * @ $Author$
 * @ $Revision$
 */

/*
��������� ������ ������������� ��� ���� ������� ������� (���)
 */
public interface OrderInfo
{
	/**
	 * �������� �������� ���� ������
	 */
	Order getOrder();

	/**
	 * ������ �������� ���� ������
	 * @return
	 */
	void setOrder(Order order);

	/*
	 * �������� id ������
	 */
	Long getId();
}