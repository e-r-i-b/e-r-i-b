package com.rssl.phizic.gate.clients;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.OTPRestriction;
import com.rssl.phizic.gate.ProductPermission;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.ClientProductsCacheComposer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;

/**
 * @ author: filimonova
 * @ created: 09.09.2010
 * @ $Author$
 * @ $Revision$
 */
public interface ClientProductsService extends Service
{
   /**
    * Список продуктов клиента
    *
    * @param client - клиент
    * @return список продуктов
    */
    //todo Кеш работает только для счетов, ОМС и карт. Остальные решено пока не делать, из-за не совпадения данных в GFL и детальной информации. см. BUG030707: Оптимизация входа пользователя в систему - частичное кэширование GFL
   //todo При изменении интерфейса смотри CacheCallback, есть закладки на текущий интерфейс
   //очистка кеша ведется через связанный, т.е. при очистке кеша по счетам и омс
	@Cachable(keyResolver= ClientProductsCacheComposer.class, name = "ClientProducts.getProducts")
	public GroupResult<Class, List<Pair<Object, AdditionalProductData>>> getClientProducts(Client client, Class... clazz);

	/**
	 * Список карт клиента по клиенту с неполной информацией (ФИО, ТБ, дата рождения, документ). Не кешируется.
	 * @param client - клиент
	 * @return - список карт
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public List<Pair<Card, AdditionalProductData>> getLightClientCards(Client client) throws GateLogicException, GateException;

	/**
	 * Изменение видимости продуктов
	 * На данный момент реализовано только для счетов через шину
	 * @param client - клиент
	 * @param pairs - список пар<продукт, видимость>
	 * @return результат изменения видимости для каждого продукта
	 */
	GroupResult<Object, Boolean> updateProductPermission(Client client, List<Pair<Object, ProductPermission>> pairs) throws GateException, GateLogicException;

	/**
	 * Установка(снятие) ограничения по печати и использованию одноразовых паролей 
	 * @param client - клиент
	 * @param pairs - список пар<продукт, видимость>
	 * @return результат изменения ограничений для каждого продукта
	 */
	GroupResult<Object, Boolean> updateOTPRestriction(Client client, List<Pair<Object, OTPRestriction>> pairs) throws GateException, GateLogicException;
}
