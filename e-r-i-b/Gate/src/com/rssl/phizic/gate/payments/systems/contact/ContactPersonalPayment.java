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
	 * @return Имя получателя
	 */
	String getReceiverFirstName();

	/**
	 *
	 * @return Отчество получателя
	 */
	String getReceiverPatrName();

    /**
	 *
	 * @return Дата рождения  получателя
	 */
	Calendar getReceiverBornDate();
}
