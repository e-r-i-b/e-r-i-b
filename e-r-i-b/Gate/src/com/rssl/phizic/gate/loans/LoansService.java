package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.cache.proxy.Cachable;
import com.rssl.phizic.gate.cache.proxy.composers.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 13.03.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Сервис для получения данных по кредитам клиентов
 */
public interface LoansService extends Service
{
   /**
    * Список кредитов для клиента
    *
    * @param client клиент
    * @return список кредитов
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   @Cachable(keyResolver= ClientCacheKeyComposer.class, name = "Loans.getLoans")
   List<Loan> getLoans(Client client) throws GateException, GateLogicException;
   /**
    * Получить информацию о ближайшем платеже.
    * Может не совпадать с первой записью в getSchedule из-за учета штрафов и пр.
    *
    * @param loan Кредит
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   @Cachable(keyResolver= LoanCacheKeyComposer.class, name = "Loans.nextScheduleItem")
   ScheduleItem getNextScheduleItem(Loan loan) throws GateException, GateLogicException;
	/**
    * Получить информацию о ближайшем платеже.
	* Учитывает сумму предполагаемого платежа(передаваемую суммму) для предоставления разбивки платежа
    *
    * @param loan Кредит
    * @param amount Сумма
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
	@Cachable(keyResolver= LoanMoneyCacheKeyComposer.class, name = "Loans.nextSheduleItemWithSumm")
	ScheduleItem getNextScheduleItem(Loan loan, Money amount) throws GateException, GateLogicException;

	/**
    * Получить из гейта те ответы на вопросы анкеты, которые уже известны.
    *
    * @param clientId Внешний ID клиента (Domain: ExternalID)
    * @return список ответов
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   List<QuestionnaireAnswer> getQuestionarieAnswers(String clientId) throws GateException, GateLogicException;

	/**
	 * Получить график погашения
	 *
	 * @param startNumber - номер платежа, начиная с которого нужен график
	 * @param count - кол-во платежей в графике
	 * @param loans Кредиты
	 * @return <кредит, график погашения>
	 */
	@Cachable(keyResolver= LoanScheduleCacheKeyComposer.class,resolverParams="2", name = "Loans.shedule")
	GroupResult<Loan, ScheduleAbstract> getSchedule(Long startNumber, Long count, Loan... loans);

	/**
    * Получить кредит по заявке.
    *
    * @param claim
    * @return кредит
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */

   Loan getLoanByClaim(LoanOpeningClaim claim) throws GateException, GateLogicException;

   /**
    * Получение кредита по идентификатору.
    *
    * @param loanIds Идентификаторы кредитов во внешней системе.
    * @return <ID кредита, кредит>
    */
    @Cachable(keyResolver= LoanIdCacheKeyComposer.class, name = "Loans.getLoan")
    GroupResult<String, Loan> getLoan(String... loanIds);

	/**
	 * Получить владельцев кредитов.
	 *
	 * @param loan - список кредитов
	 * @return <кредит, владелец кредита>
	 */
	@Cachable(keyResolver= LoanCacheKeyComposer.class, name = "Loans.loanOwner")
	GroupResult<Loan,Client> getLoanOwner(Loan... loan);

	/**
	 * Запрос краткой информации о кредитах
	 * @param loanIds Идентификаторы кредитов во внешней системе.
	 * @return <ID кредита, кредит>
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= LoanIdCacheKeyComposer.class, name = "Loans.getLoanShortCut")
	GroupResult<String, Loan> getLoanShortCut(String... loanIds)  throws GateLogicException, GateException;

	/**
	 * Запрос GetPrivateLoanListRq
	 * @param loans кредиты
	 * @return <prodID кредита, результат GetPrivateLoanListRq>
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= LoanPrivateCacheKeyComposer.class, name = "Loans.getLoanPrivate")
	public Map<String, LoanPrivate> getLoanPrivate(List<Loan> loans) throws GateException, GateLogicException;
}
