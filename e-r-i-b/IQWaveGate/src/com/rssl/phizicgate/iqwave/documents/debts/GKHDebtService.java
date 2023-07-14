package com.rssl.phizicgate.iqwave.documents.debts;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizicgate.iqwave.documents.RequestHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import org.w3c.dom.Document;

import java.util.List;

/**
 * @author krenev
 * @ created 14.05.2010
 * @ $Author$
 * @ $Revision$
 * получем задолженность по оплате улуг ЖКХ
 * Код сервиса (маршрута)
 * Символьная кодировка	Цифровая кодировка
 *      BC                  75
 * Запрос задолженности:
 * Элемент	         Тип	        Комментарий	                        Кратность
 * <Route>	        RouteCode       Код сервиса (маршрута)                  [1]
 * <DebitCard>	    CardInf         Информация по карте списания	        [1]
 * <RecIdentifier>	Requisite       Реквизит, идентифицирующий плательщика	[1]
 * <Period>	        PayPeriod       Период мес/год	                        [1]
 *
 *  Ответ о задолженности:
 * Элемент	                    Тип	            Комментарий	                                                             Кратность
 * <SummCanEdit>	            Bool        Клиент может изменить сумму платежа.                                        [1]
 *                                  (всегда равен «false», менять сумму нельзя).
 * <PayDebtsList>	 Список задолженностей. Пустой список означает, что у клиента нет текущей задолженности.	        [0..n]
 *      <DebtsRoute>	        RouteCode   Код сервиса (маршрута)	                                                    [1]
 *  	<DebtsNumber>	        Long        Номер задолженности	                                                        [1]
 *      <DebtsPeriod>	        PayPeriod   Период задолженности	                                                    [1]
 *  	<DebtsCurrCode>	        IsoCode     Валюта задолженности	                                                    [1]
 *      <DebtsSumma>	        DebSumType  Сумма задолженности, должна быть положительной, переплата не допускается.	[1]
 *  	<DebtsComission>	    Money       Сумма комиссии, должна быть положительной	                                [1]
 *  	<DebtsName>	            C-50        Описание задолженности	                                                    [1]
 *  	<DebtsCases>    Варианты оплаты	                                                                                [0…2]
 *  		<CaseNumber>	    Long        Номер тега спецификации E-pay для поля суммы варианта оплаты.	            [1]
 *  		<CaseDesc>	        C-50        Описание варианта оплаты	                                                [1]
 *          <CaseSumma>	        DebSumType  Сумма задолженности варианта оплаты, должна быть положительной,
 *                                          переплата не допускается.	                                                [1]
 *           <CaseComission>    MoneyСумма комиссии варианта оплаты, должна быть положительной	                        [1]
 *
 * Примечание: Если <DebtsCases> не содержит дочерних элементов,
 * сумма задолженности и комиссия берется из полей <DebtsSumma> и <DebtsComission>.
 * Если <DebtsCases> содержит 1 или 2 варианта оплаты,
 * сумма задолженности и комиссия берется из полей <CaseSumma> и <CaseComission>.
 */

public class GKHDebtService extends AbstractIQWaveDebtService implements DebtService
{
	public GKHDebtService(GateFactory factory)
	{
		super(factory);
	}

	public List<Debt> getDebts(Recipient recipient,List<Field> keyFields) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.PAYMENT_DEBTS_GKH_REQUEST);
		DebtsHelper.fillDebtRequest(message, recipient, keyFields);
		//к общей части необходимо добавить период мес/год
		Field period = BillingPaymentHelper.getFieldById(keyFields, Constants.PERIOD_FIELD_NAME);
		RequestHelper.appendPeriod(message, Constants.PERIOD_FIELD_NAME, (String) period.getValue());

		Document response = serviceFacade.sendOnlineMessage(message, null);
		return DebtsHelper.parseGKHDebts(response);
	}
}