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
 * ��������� ���������� �� ������ ����.
 */
public interface DepoAccountService extends Service
{

	/**
	 * ����������� ������� �� ������������ � �����������
	 * @param client - ������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	void register(Client client) throws GateException, GateLogicException;

	/**
	 * ������ ������ ���� �������
	 * @param client - ������
	 * @return ������ ������ ����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= ClientCacheKeyComposer.class, name = "DepoAccount.getDepoAccounts")
	List<DepoAccount> getDepoAccounts(Client client) throws GateException, GateLogicException;

	/**
	 * ��������� ����� ���� �� ��������������
	 * @param depoAccountId - ������������� ����� ����
	 * @return < ������������� ����� ����, ���� ���� >
	 */
	@Cachable(keyResolver= DepoAccountIdCacheKeyComposer.class, name = "DepoAccount.getDepoAccount")
	GroupResult<String,DepoAccount> getDepoAccount(String... depoAccountId);

	/**
	 * ��������� ���������� � ������������� �� ����� ����
	 * @param depoAccount - ���� ����
	 * @return ���������� � �������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = DepoAccountCacheKeyComposer.class, name = "DepoAccount.depoDebtInfo")
	DepoDebtInfo getDepoDebtInfo(DepoAccount depoAccount) throws GateException, GateLogicException;

	/**
	 * ��������� ��������� ���������� � ������ ������������� �� ����� ����
	 * ��������� ���������� ��� ������ �������������
	 * @param depoAccount - ���� ����
	 * @param debtItem - ������ ������������� �� ����� ����
	 * @return < ������ ������������� �� ����� ����, ��������� ���������� �� ������>
	 */
	@Cachable(keyResolver = DepoDebtItemCacheKeyComposer.class,resolverParams = "1", name = "DepoAccount.depoDebtItemInfo")
	GroupResult<DepoDebtItem,DepoDebtItemInfo> getDepoDebtItemInfo(DepoAccount depoAccount, DepoDebtItem... debtItem);

	/**
	 * ��������� ���������� � ������� �� ����� ����
 	 * @param depoAccount - ����� ����
	 * @return depoAccountPosition
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = DepoAccountCacheKeyComposer.class, name = "DepoAccount.depoAccountPosition")
	DepoAccountPosition getDepoAccountPosition(DepoAccount depoAccount) throws GateException, GateLogicException;

	/**
	 * �������� ������ � ��������� ����� ����.
	 * @param depoAccount - ����� ����
	 * @return < ���� ����, ������>	 
	 */
	@Cachable(keyResolver = DepoAccountCacheKeyComposer.class, name = "DepoAccount.depoAccountOwner")
	GroupResult<DepoAccount, Client> getDepoAccountOwner(DepoAccount... depoAccount);
}
