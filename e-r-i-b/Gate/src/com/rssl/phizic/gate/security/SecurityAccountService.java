package com.rssl.phizic.gate.security;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.ClientCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.SecurityAccountIdCacheKeyComposer;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @author lukina
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */
/**
 * ��������� ���������� �� �������������� ������������
 */
public interface SecurityAccountService  extends Service
{
	/**
	 * ������ �������������� ������������ �������
	 * @param client - ������, ��� �������� �������� ��������
	 * @return  ������ �������������� ������������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver = ClientCacheKeyComposer.class, name = "SecurityAccount.getSecurityAccountList")
	List<SecurityAccount> getSecurityAccountList(Client client) throws GateException, GateLogicException;

	/**
	 * ��������� ���������� � �������������� �����������
	 * @param externalId  ������� ID ��������
	 * @return ��������� ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= SecurityAccountIdCacheKeyComposer.class, name = "SecurityAccount.getSecurityAccount")
	SecurityAccount getSecurityAccount(String externalId)throws GateException, GateLogicException;
}
