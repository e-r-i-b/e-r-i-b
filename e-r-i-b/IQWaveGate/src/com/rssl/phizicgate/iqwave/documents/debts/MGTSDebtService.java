package com.rssl.phizicgate.iqwave.documents.debts;

import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

import java.util.List;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 * Оплата МГТС
 * Код сервиса (маршрута)
 * Символьная кодировка	Цифровая кодировка
 *      Q	                17
 * Запрос задолженности:
 * Элемент	            Тип	        Комментарий                       Кратность
 * <Route>          RouteCode   Код сервиса (маршрута)	                [1]
 * <DebitCard>	    CardInf     Информация по карте списания	        [1]
 * <RecIdentifier>	Requisite   Реквизит, идентифицирующий плательщика	[1]
 * <FlatNumber>	    FlatNumb    Номер квартиры	                        [0..1]
 *
 * Ответ о задолженности:
 * Элемент	            Тип	        Комментарий	                        Кратность
 * <SummCanEdit>	    Bool        Клиент может изменить сумму платежа	    [1]
 * <DebtsRoute>	        RouteCode   Код сервиса (маршрута)	                [1]
 * <DebtsCurrCode>	    IsoCode     Валюта задолженности	                [1]
 * <DebtsSumma>	        DebSumType  Сумма задолженности	                    [1]
 */
public class MGTSDebtService extends AbstractIQWaveDebtService
{
	public MGTSDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_DEBTS_MGTS_REQUEST);
		DebtsHelper.fillDebtRequest(message, recipient, keyFields);
		//к общей части необходимо добавить номер квартиры(при необходимости)
		Field flatNumber = BillingPaymentHelper.getFieldById(keyFields, Constants.FLAT_NUMBER_FIELD_NAME);
		if (flatNumber != null && flatNumber.getValue()!=null)
		{
			message.addParameter(Constants.FLAT_NUMBER_FIELD_NAME, flatNumber.getValue());
		}

		Document response = serviceFacade.sendOnlineMessage(message, null);
		return DebtsHelper.parseMGTSFNSDebtList(response);
	}
}
