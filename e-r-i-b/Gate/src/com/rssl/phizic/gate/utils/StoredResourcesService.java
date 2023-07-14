package com.rssl.phizic.gate.utils;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.security.SecurityAccount;

/**
 * Сервис для обновления информации о сохраняемых оффлайн продуктах
 * @author Pankin
 * @ created 26.02.14
 * @ $Author$
 * @ $Revision$
 */
public interface StoredResourcesService extends Service
{

	/**
	 * Обновление сохраненной информации о счете
	 * @param loginId идентификатор логина клиента
	 * @param account счет
	 */
	void updateStoredAccount(Long loginId, Account account);

	/**
	 * Обновление сохраненной информации о карте
	 * @param loginId идентификатор логина клиента
	 * @param card карта
	 */
	void updateStoredCard(Long loginId, Card card);

	/**
	 * Обновление сохраненной информации о кредите
	 * @param loginId идентификатор логина клиента
	 * @param loan кредит
	 */
	void updateStoredLoan(Long loginId, Loan loan);

	/**
	 * Обновление сохраненной информации о счете ДЕПО
	 * @param loginId идентификатор логина клиента
	 * @param depoAccount счет ДЕПО
	 */
	void updateStoredDepoAccount(Long loginId, DepoAccount depoAccount);

	/**
	 * Обновление сохраненной информации об ОМС
	 * @param loginId идентификатор логина клиента
	 * @param imAccount ОМС
	 */
	void updateStoredIMAccount(Long loginId, IMAccount imAccount);

	/**
	 * Обновление сохраненной информации о длительных поручениях
	 * @param loginId идентификатор логина клиента
	 * @param longOffer длительное поручение
	 */
	void updateStoredLongOffer(Long loginId, LongOffer longOffer);

	/**
	 * Обновление сохраненной информации о сберегательных сертификатах
	 * @param loginId
	 * @param securityAccount
	 */
	void updateStoredSecurityAccount(Long loginId, SecurityAccount securityAccount);

}
