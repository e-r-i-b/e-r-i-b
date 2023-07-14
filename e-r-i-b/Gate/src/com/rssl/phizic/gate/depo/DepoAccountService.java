package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.ClientCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.DepoAccountIdCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.DepoAccountCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.DepoDebtItemCacheKeyComposer;

import java.util.List;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Получение информации по счетам ДЕПО.
 */
public interface DepoAccountService extends Service
{

	/**
	 * Регистрация клиента на обслуживание в Депозитарии
	 * @param client - клиент
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void register(Client client) throws GateException, GateLogicException;

	/**
	 * Список счетов ДЕПО клиента
	 * @param client - клиент
	 * @return список счетов ДЕПО
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= ClientCacheKeyComposer.class, name = "DepoAccount.getDepoAccounts")
	List<DepoAccount> getDepoAccounts(Client client) throws GateException, GateLogicException;

	/**
	 * Получение счета ДЕПО по идентификатору
	 * @param depoAccountId - идентификатор счета ДЕПО
	 * @return < идентификатор счета ДЕПО, счет ДЕПО >
	 */
	@Cachable(keyResolver= DepoAccountIdCacheKeyComposer.class, name = "DepoAccount.getDepoAccount")
	GroupResult<String,DepoAccount> getDepoAccount(String... depoAccountId);

	/**
	 * Получение информации о задолженности по счету ДЕПО
	 * @param depoAccount - счет ДЕПО
	 * @return информация о задолженности
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = DepoAccountCacheKeyComposer.class, name = "DepoAccount.depoDebtInfo")
	DepoDebtInfo getDepoDebtInfo(DepoAccount depoAccount) throws GateException, GateLogicException;

	/**
	 * Получение детальной информации о статье задолженности по счету ДЕПО
	 * Получение реквизитов для оплаты задолженности
	 * @param depoAccount - счет депо
	 * @param debtItem - статья задолженности по счету ДЕПО
	 * @return < статья задолженности по счету депо, детальная информация по статье>
	 */
	@Cachable(keyResolver = DepoDebtItemCacheKeyComposer.class,resolverParams = "1", name = "DepoAccount.depoDebtItemInfo")
	GroupResult<DepoDebtItem,DepoDebtItemInfo> getDepoDebtItemInfo(DepoAccount depoAccount, DepoDebtItem... debtItem);

	/**
	 * Получение информации о позиции по счету ДЕПО
 	 * @param depoAccount - счета ДЕПО
	 * @return depoAccountPosition
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = DepoAccountCacheKeyComposer.class, name = "DepoAccount.depoAccountPosition")
	DepoAccountPosition getDepoAccountPosition(DepoAccount depoAccount) throws GateException, GateLogicException;

	/**
	 * Получить данные о владельце счета депо.
	 * @param depoAccount - счета ДЕПО
	 * @return < счет депо, клиент>	 
	 */
	@Cachable(keyResolver = DepoAccountCacheKeyComposer.class, name = "DepoAccount.depoAccountOwner")
	GroupResult<DepoAccount, Client> getDepoAccountOwner(DepoAccount... depoAccount);
}
