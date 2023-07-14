package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.ClientByTemplateCacheKeyComposer;
import com.rssl.phizic.gate.cache.proxy.composers.ClientIDCacheKeyComposer;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * ѕолучение данных о клиентах из внешней системы
 *
 * @author Kidyaev
 * @ created 03.10.2005
 * @ $Author: bogdanov $
 * @ $Revision$
 */

public interface ClientService extends Service
{
    /**
     * ѕолное описание клиента
     *
     * @param id ID пользовател€
     * @return описание
     * @throws GateLogicException
     * @throws GateException
     */
    @Cachable(keyResolver= ClientIDCacheKeyComposer.class, name = "ClientById")
    Client getClientById(String id) throws GateLogicException,GateException;

    /**
     * ¬озврашает полные данные о пользователе
     * ћетод не кешируетс€, так как дл€ сбера возникает проблема: дл€ новых клиентов кешировалось по clientId,
     * cleintId заполн€лс€ cb_code. ¬ итоге, если заходит два клиента с одним cb_code (он един дл€ всех клиентов одного отделени€),
     * то второй клиент заполн€етс€ данными первого, что есть крайне некорректно (клиент видит чужие счета и карты)
     *  ешировать этот метод не надо, так как он вызываетс€ один раз за сессию.
     * @param client описание, содержащее ограниченный надор данных
     * @return полное описание
     * @throws GateLogicException
     * @throws GateException
     */
	Client fillFullInfo(Client client) throws GateLogicException, GateException;


	/**
	 * ѕоиск клиентов по ‘»ќ
	 * @param client - клиент
	 * @param office - офис, в котором искать
	 * @param firstResult пропустить первые firstResult - 1 элементов выборки
	 * @param maxResults максимальное количество возвращаемых данных
	 * @return список, содержащий краткое описание пользователей
	 * @throws GateLogicException
	 * @throws GateException
	 */
   @Cachable(keyResolver= ClientByTemplateCacheKeyComposer.class, name = "ClientByTemplate")
   List<Client> getClientsByTemplate(Client client, Office office, int firstResult, int maxResults) throws GateLogicException, GateException;

    /**
     * ¬озврашает полные данные о пользователе по номеру карты и
     *  офису, в котором заведена карта, по которой пользователь вошел в систему
     *
     * @param cardNumber - Ќомер карты, по которой проводилась идентификаци€.
     * @param rbTbBranchId - идентификатор офиса, в котором заведена карта, по которой пользователь вошел в систему.
     * @return описание клиента
     * @throws GateLogicException, GateException
     */
	Client getClientByCardNumber(String rbTbBranchId, String cardNumber) throws GateLogicException, GateException;

}
