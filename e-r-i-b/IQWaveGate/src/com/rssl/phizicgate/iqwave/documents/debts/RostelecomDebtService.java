package com.rssl.phizicgate.iqwave.documents.debts;

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
 * @author hudyakov
 * @ created 12.08.2010
 * @ $Author$
 * @ $Revision$
 *
 * Оплата Ростелеком
 *
 * Запрос задолженности:
 * Элемент	            Тип         Комментарий                         Кратность
 *	<Route> 	        RouteCode   Код сервиса (маршрута)	                [1]
 *	<DebitCard>	        CardInf     Информация по карте списания	        [1]
 *	<RecIdentifier>	    Requisite   Реквизит, идентифицирующий плательщика	[1]
 *
 * Ответ задолженности:
 * Элемент	            Тип	        Комментарий	                        Кратность
 *	<SummCanEdit>       Bool        Клиент может изменить сумму платежа. (всегда равен «false», менять сумму нельзя).	    [1]
 *	<PayDebtsList>		            Список задолженностей. Пустой список означает, что у клиента нет текущей задолженности.	[0..n]
 *	    <DebtsRoute>	RouteCode   Код сервиса (маршрута)	                [1]
 *	    <DebtsNumber>	Long        Номер задолженности	                    [1]
 *	    <DebtsPeriod>	PayPeriod   Период задолженности	                [1]
 *	    <DebtsCurrCode>	IsoCode     Валюта задолженности	                [1]
 *	    <DebtsSumma>	DebSumType  Сумма задолженности, должна быть положительной, переплата не допускается.	            [1]
 *	    <DebtsName>	    C-50        Описание задолженности	                [1]
 *
 */
public class RostelecomDebtService extends AbstractIQWaveDebtService
{
	public RostelecomDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field> fields) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_DEBTS_ROSTELECOM_REQUEST);
		DebtsHelper.fillDebtRequest(message, recipient, fields);
		Document response = serviceFacade.sendOnlineMessage(message, null);
		return DebtsHelper.parseRostelecomDebts(response);
	}
}
