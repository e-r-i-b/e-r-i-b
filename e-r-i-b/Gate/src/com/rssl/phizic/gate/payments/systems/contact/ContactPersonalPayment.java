package com.rssl.phizic.gate.payments.systems.contact;

import java.util.Calendar;

/**
 * User: novikov_a
 * Date: 24.09.2009
 * Time: 16:10:23
 */
public interface ContactPersonalPayment extends ContactPayment
{
	/**
	 *
	 * @return ��� ����������
	 */
	String getReceiverFirstName();

	/**
	 *
	 * @return �������� ����������
	 */
	String getReceiverPatrName();

    /**
	 *
	 * @return ���� ��������  ����������
	 */
	Calendar getReceiverBornDate();
}
