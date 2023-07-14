package com.rssl.phizic.gate.documents;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.DebtServiceCacheKeyComposer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;

import java.util.List;

/**
 * @author akrenev
 * @ created 28.12.2009
 * @ $Author$
 * @ $Revision$
 */
public interface DebtService extends Service
{
	/**
	 * Получить список задолженностей для поставшика, по списку ключевых полей
	 * @param recipient поставщик
	 * @param keyFields ключевые поля
	 * @return список задолженностей
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= DebtServiceCacheKeyComposer.class, name = "Debt.getDebts")
	List<Debt> getDebts(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException;
}