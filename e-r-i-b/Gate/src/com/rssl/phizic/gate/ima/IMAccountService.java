package com.rssl.phizic.gate.ima;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;
import java.util.List;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
/**
 * �������������� ������ ��� ������ �� ������� ���.
 */
public interface IMAccountService extends Service
{
   /**
    * ������ ��� �������.
    *
    * @param client ������, ��� ����� ���������� ��������.
    * @return ���� ������ �� ������ - ��������� GateExceptio, ���� ��� ��� - ������ ������
    * @exception GateException
    * @exception GateLogicException
    */
   @Cachable(keyResolver = ClientCacheKeyComposer.class, name = "IMAccount.clientIMAccounts")
   List<IMAccount> getClientIMAccounts(Client client) throws GateException, GateLogicException;
   /**
    * ��������� ���������� �� ����� ��� �� ��� �������������� �� ������� �������.
    *
    * @param externalId ����� �� ������� �������
    */
   @Cachable(keyResolver = IMAccountIDCacheKeyComposer.class, name = "IMAccount.getIMAccount")
   GroupResult<String, IMAccount> getIMAccount(String... externalId);
	/**
    * ������� ����, � ������� ������� ���
    *
    * @param imAccount ���� ���
    * @return  ����, � ������� ������� ���
    */
   @Cachable(keyResolver = IMAccountCacheKeyComposer.class, name = "IMAccount.leadOffice")
   GroupResult<IMAccount, Office> getLeadOffice(IMAccount... imAccount);
   /**
    * �������� ������ � ��������� �����.
    * ������ ������ ���� ��������� ������������ ������� getClientById ������� ClientService
    *
    * @param imAccount ���� ���
    * @return ������ � ��������� �����.
    */
   @Cachable(keyResolver = IMAccountCacheKeyComposer.class, name = "IMAccount.ownerInfo")
   GroupResult<IMAccount, Client> getOwnerInfo(IMAccount... imAccount);
   /**
    * ������� �� ����� ��� �� ������
    *
    * @param imAccount ���� ���.
    * @param fromDate ��������� ���� (������� ��)
    * @param toDate �������� ���� (������� ��)
    * @return ������� �� ����� ��� �� ������
    * @exception GateLogicException
    * @exception GateException
    */
   @Cachable(keyResolver = FullAbstractCacheKeyComposer.class, linkable = false, name = "IMAccount.abstract")
   IMAccountAbstract getAbstract(IMAccount imAccount, Calendar fromDate, Calendar toDate) throws GateLogicException, GateException;
   /**
    * ��������� ��������� �������� �� �����.
    *
    * @param count ���-�� ��������, ������� ���������� �������.
    * @param imAccount - ������ ��� �������
    * @return ��������� �������� �� �����
    */
   @Cachable(keyResolver = NumberAbstractCacheKeyComposer.class,linkable = false,resolverParams="1", name = "IMAccount.abstracts")
   GroupResult<IMAccount,IMAccountAbstract> getAbstract(Long count, IMAccount... imAccount);

	/**
	 * �������� ��� �� ������
	 * @param client ������ - �������� ���
	 * @param imAccountNumbers ����� ���
	 * @return ��������� ��������� �� ���
	 */
	@Cachable(keyResolver = IMAccountByNumberCacheKeyComposer.class, resolverParams="1", name = "IMAccountByNumber")
    GroupResult<String, IMAccount> getIMAccountByNumber(Client client, String... imAccountNumbers);
}