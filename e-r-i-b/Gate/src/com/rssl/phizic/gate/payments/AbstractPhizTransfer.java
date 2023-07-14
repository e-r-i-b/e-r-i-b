package com.rssl.phizic.gate.payments;

/**
 * ����������� ������� ���. ���� �� ����������
 *
 * @author khudyakov
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractPhizTransfer extends AbstractRUSPayment
{
	/**
	 * @return ������� ���������� �������
	 */
	String getReceiverSurName();

	 /**
	  * @return ��� ���������� �������
	  */
	String getReceiverFirstName();

	 /**
	  * @return �������� ���������� �������
	  */
	String getReceiverPatrName();

	/**
	 * ��� ����������
	 * @return name ����������, ���. �� ��� ����������� ��� �� svcActInfo
	 */
	String getReceiverName();
}
