package com.rssl.phizic.bankroll;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.annotation.NonThreadSafe;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ћенеджер клиента по банковским продуктам в разрезе текущего модул€ (канала)
 */
@NonThreadSafe
public interface PersonBankrollManager
{
	/**
	 * ѕерезагрузить продукты клиента данными из внешней системы
	 */
	void reloadProducts();

	/**
	 * ¬озвращает список счетов клиента
	 * @return список счетов клиента (never null)
	 */
	Collection<? extends BankrollProductLink> getAccounts();

	/**
	 * ¬озвращает список карт клиента
	 * @return список карт клиента (never null)
	 */
	Collection<? extends BankrollProductLink> getCards();

	/**
	 * ¬озвращает список кредитов клиента
	 * @return список кредитов клиента (never null)
	 */
	Collection<? extends BankrollProductLink> getLoans();

	/**
	 * »щет банкролл-продукт по его —ћ—-алиасу
	 * @param smsAlias - —ћ—-алиас (never null)
	 * @param searchClasses - классы продуктов, по которым нужно искать; null - искать по всем доступным
	 * @return линк-на-банкролл-продукт или null, если не найден
	 */
	BankrollProductLink findProductBySmsAlias(String smsAlias, Class<? extends BankrollProductLink>... searchClasses);

	/**
	 * ѕоиск рублевой валюты
	 * @return валюта
	 */
	Currency getRURCurrency();

	/**
	 * ѕоиск приоритетной карты дл€ списани€, на которой есть достаточный лимит доступных средств с учетом приоритетной карты, определенной пользователем
	 * @param amount - сумма списани€ (never null)
	 * @return приоритетна€ карта, not null
	 */
	BankrollProductLink getPriorityCardForChargeOff(Money amount);

	/**
	 * ѕоиск приоритетной карты дл€ оплаты
	 * @return приоритетна€ карта, not null
	 */
	BankrollProductLink getPriorityCard();

	/**
	 * ¬ернуть количество транзакций, которые необходимо выводить в мини-выписке по карте и счету. »спользуетс€ в команде "»стори€"
	 * @return количество транзакций
	 */
	Long getNumberOfTransactions();
}
