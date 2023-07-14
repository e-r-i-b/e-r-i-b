package com.rssl.phizic.gate.payments;

/**
 * Абстрактный перевод физ. лицу по реквизитам
 *
 * @author khudyakov
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractPhizTransfer extends AbstractRUSPayment
{
	/**
	 * @return Фамилия получателя платежа
	 */
	String getReceiverSurName();

	 /**
	  * @return имя получателя платежа
	  */
	String getReceiverFirstName();

	 /**
	  * @return отчество получателя платежа
	  */
	String getReceiverPatrName();

	/**
	 * Имя получателя
	 * @return name получателя, ФИО. То что присылается нам из svcActInfo
	 */
	String getReceiverName();
}
