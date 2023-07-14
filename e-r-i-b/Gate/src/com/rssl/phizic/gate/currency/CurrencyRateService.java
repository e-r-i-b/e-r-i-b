package com.rssl.phizic.gate.currency;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * ѕолучение курсов валют
 *
 * @author Kosyakov
 * @ created 17.10.2006
 * @ $Author: egorovaav $
 * @ $Revision: 66619 $
 */
public interface CurrencyRateService extends Service
{
	/**
	 * ѕолучить текущий курс
	 * „итаетс€ как <from> <type> за <to> 
	 * @param from валюта, из которой конвертируют
	 * @param to валюта, в которую конвертируют
	 * @param type тип курса
	 * @param office - ќфис, из которого необходимо получить курс
	 * @param tarifPlanCodeType - тип тарифного плана, дл€ которого действует курс
	 * @return курс конверсии, при этом:
	 *           getFromValue - сумма в валюте from
	 *           getFromCurrency Ц переданный from
	 *           getToValue - сумма в валюте to
	 *           getToCurrency Ц переданный to
	 *           getFactor ЦToValue/FromValue
	 *           getReverseFactor ЦFromValue/ToValue
	 *           getType Ц  переданный тип type
	 * @throws GateException, GateLogicException
	 */
	CurrencyRate getRate (Currency from, Currency to, CurrencyRateType type, Office office,
	                      String tarifPlanCodeType) throws GateException, GateLogicException;

	/**
	 * ѕолучить курс конверсии дл€ суммы from в валюте from за валюту to
	 * @param from сумма продажи (сумма списани€)
	 * @param to валюта покупки (валюта зачислени€)
	 * @param office - ќфис, из которого необходимо получить курс.
	 * @param tarifPlanCodeType - тип тарифного плана, дл€ которого действует курс
	 * @return курс конверсии, при этом:
	 *           getFromValue - переданна€ сумма
	 *           getToValue - сумма, котора€ будет зачислена, т.е. переданна€ сумма, умноженна€ на курс операции
	 *           getFactor ЦToValue/FromValue
	 *           getReverseFactor ЦFromValue/ToValue
	 *           getType Ц  null
	 * @throws GateException, GateLogicException
	 */
	CurrencyRate convert(Money from, Currency to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException;

	/**
	 * ѕолучить курс конверсии валюты from на сумму to в валюте to
	 * @param from валюта дл€ продажи (валюта списани€)
	 * @param to сумма покупки (сумма зачислени€)
	 * @param office - ќфис, из которого необходимо получить курс.
	 * @param tarifPlanCodeType - тип тарифного плана, дл€ которого действует курс
	 * @return курс конверсии, при этом:
	 *           getFromValue - сумма, котора€ будет списана, т.е. переданна€ сумма, поделенна€ на курс операции
	 *           getToValue Ц переданна€ сумма
	 *           getFactor ЦToValue/FromValue
	 *           getReverseFactor ЦFromValue/ToValue
	 *           getType Ц  null
	 * @throws GateException, GateLogicException 
	 */
	CurrencyRate convert(Currency from, Money to, Office office, String tarifPlanCodeType) throws GateException, GateLogicException;
}
