package com.rssl.phizgate.common.payments;

import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.config.ConfigFactory;

/**
 * @author khudyakov
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 * Хелпер для работы с платежами
 */
public class PaymentHelper
{
	/**
	 * Получить название получателя платежа
	 * @param rusPayment перевод по реквизитам
	 * @return имя получателя платежа
	 */
	public static String getReceiverName(AbstractRUSPayment rusPayment) throws GateException
	{
		if (rusPayment instanceof AbstractJurTransfer)
		{
			return ((AbstractJurTransfer) rusPayment).getReceiverName();
		}

		if (rusPayment instanceof AbstractPhizTransfer)
		{
			AbstractPhizTransfer phizTransfer = (AbstractPhizTransfer) rusPayment;
			return phizTransfer.getReceiverSurName() + " " + phizTransfer.getReceiverFirstName() + " " + phizTransfer.getReceiverPatrName();
		}
		throw new GateException("Некорректный тип документа, определение названия получателя платежа невожможно.");
	}

	/**
	 * Является ли банк получателя платежа банком корреспондентом
	 * @param payment платеж
	 * @return true - является
	 * @throws GateException
	 */
	public static boolean isCorrespondentBank(AbstractRUSPayment payment) throws GateException
	{
		ResidentBank residentBank = payment.getReceiverBank();
		if (residentBank == null)
			throw new GateException("Не задан банк получателя платежа.");

		PaymentsConfig paymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);
		return (paymentsConfig.getBanksList().contains(residentBank.getBIC()));
	}

    /**
     * Получить имя получателя платежа
     * @param payment перевод по реквизитам
     * @return имя получателя платежа
     */
    public static String getReceiverFirstName(AbstractTransfer payment) throws GateException
    {
        if (payment instanceof AccountIntraBankPayment)
            return ((AccountIntraBankPayment) payment).getReceiverFirstName();

        throw new GateException("Некорректный тип документа, определение имя получателя платежа невожможно.");
    }

    /**
     * Получить фамилию получателя платежа
     * @param payment перевод по реквизитам
     * @return имя получателя платежа
     */
    public static String getReceiverSecondName(AbstractTransfer payment) throws GateException
    {
        if (payment instanceof AccountIntraBankPayment)
            return ((AccountIntraBankPayment) payment).getReceiverPatrName();
        throw new GateException("Некорректный тип документа, определение имя получателя платежа невожможно.");
    }

    /**
     * Получить отчество получателя платежа
     * @param payment перевод по реквизитам
     * @return имя получателя платежа
     */
    public static String getReceiverSurName(AbstractTransfer payment) throws GateException
    {
        if (payment instanceof AccountIntraBankPayment)
            return ((AccountIntraBankPayment) payment).getReceiverSurName();
        throw new GateException("Некорректный тип документа, определение имя получателя платежа невожможно.");
	}
}
