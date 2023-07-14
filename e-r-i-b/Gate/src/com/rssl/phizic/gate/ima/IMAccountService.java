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
 * Информационный сервис для работы со счетами ОМС.
 */
public interface IMAccountService extends Service
{
   /**
    * Список ОМС клиента.
    *
    * @param client Клиент, чьи счета необходимо получить.
    * @return Если клиент не найден - бросается GateExceptio, Если нет ОМС - пустой список
    * @exception GateException
    * @exception GateLogicException
    */
   @Cachable(keyResolver = ClientCacheKeyComposer.class, name = "IMAccount.clientIMAccounts")
   List<IMAccount> getClientIMAccounts(Client client) throws GateException, GateLogicException;
   /**
    * Получение информации по счету ОМС по его идентификатору во внешней системе.
    *
    * @param externalId Номер во внешней системе
    */
   @Cachable(keyResolver = IMAccountIDCacheKeyComposer.class, name = "IMAccount.getIMAccount")
   GroupResult<String, IMAccount> getIMAccount(String... externalId);
	/**
    * Вернуть офис, в котором заведен ОМС
    *
    * @param imAccount Счет ОМС
    * @return  офис, в котором заведен ОМС
    */
   @Cachable(keyResolver = IMAccountCacheKeyComposer.class, name = "IMAccount.leadOffice")
   GroupResult<IMAccount, Office> getLeadOffice(IMAccount... imAccount);
   /**
    * Получить данные о владельце счета.
    * Данные должны быть идентичны возвращаемым методом getClientById сервиса ClientService
    *
    * @param imAccount Счет ОМС
    * @return данные о владельце счета.
    */
   @Cachable(keyResolver = IMAccountCacheKeyComposer.class, name = "IMAccount.ownerInfo")
   GroupResult<IMAccount, Client> getOwnerInfo(IMAccount... imAccount);
   /**
    * Выписка по счету ОМС за период
    *
    * @param imAccount Счет ОМС.
    * @param fromDate Начальная дата (включая ее)
    * @param toDate Конечная дата (включая ее)
    * @return Выписка по счету ОМС за период
    * @exception GateLogicException
    * @exception GateException
    */
   @Cachable(keyResolver = FullAbstractCacheKeyComposer.class, linkable = false, name = "IMAccount.abstract")
   IMAccountAbstract getAbstract(IMAccount imAccount, Calendar fromDate, Calendar toDate) throws GateLogicException, GateException;
   /**
    * Получение последних операций по счету.
    *
    * @param count Кол-во операций, которые необходимо вернуть.
    * @param imAccount - Массив ОМС клиента
    * @return Последние операции по счету
    */
   @Cachable(keyResolver = NumberAbstractCacheKeyComposer.class,linkable = false,resolverParams="1", name = "IMAccount.abstracts")
   GroupResult<IMAccount,IMAccountAbstract> getAbstract(Long count, IMAccount... imAccount);

	/**
	 * Получить ОМС по номеру
	 * @param client клиент - владелец ОМС
	 * @param imAccountNumbers номер ОМС
	 * @return групповая информаци об ОМС
	 */
	@Cachable(keyResolver = IMAccountByNumberCacheKeyComposer.class, resolverParams="1", name = "IMAccountByNumber")
    GroupResult<String, IMAccount> getIMAccountByNumber(Client client, String... imAccountNumbers);
}