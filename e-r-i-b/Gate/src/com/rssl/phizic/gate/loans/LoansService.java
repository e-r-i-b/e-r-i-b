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
 * ������ ��� ��������� ������ �� �������� ��������
 */
public interface LoansService extends Service
{
   /**
    * ������ �������� ��� �������
    *
    * @param client ������
    * @return ������ ��������
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   @Cachable(keyResolver= ClientCacheKeyComposer.class, name = "Loans.getLoans")
   List<Loan> getLoans(Client client) throws GateException, GateLogicException;
   /**
    * �������� ���������� � ��������� �������.
    * ����� �� ��������� � ������ ������� � getSchedule ��-�� ����� ������� � ��.
    *
    * @param loan ������
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   @Cachable(keyResolver= LoanCacheKeyComposer.class, name = "Loans.nextScheduleItem")
   ScheduleItem getNextScheduleItem(Loan loan) throws GateException, GateLogicException;
	/**
    * �������� ���������� � ��������� �������.
	* ��������� ����� ��������������� �������(������������ ������) ��� �������������� �������� �������
    *
    * @param loan ������
    * @param amount �����
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
	@Cachable(keyResolver= LoanMoneyCacheKeyComposer.class, name = "Loans.nextSheduleItemWithSumm")
	ScheduleItem getNextScheduleItem(Loan loan, Money amount) throws GateException, GateLogicException;

	/**
    * �������� �� ����� �� ������ �� ������� ������, ������� ��� ��������.
    *
    * @param clientId ������� ID ������� (Domain: ExternalID)
    * @return ������ �������
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
   List<QuestionnaireAnswer> getQuestionarieAnswers(String clientId) throws GateException, GateLogicException;

	/**
	 * �������� ������ ���������
	 *
	 * @param startNumber - ����� �������, ������� � �������� ����� ������
	 * @param count - ���-�� �������� � �������
	 * @param loans �������
	 * @return <������, ������ ���������>
	 */
	@Cachable(keyResolver= LoanScheduleCacheKeyComposer.class,resolverParams="2", name = "Loans.shedule")
	GroupResult<Loan, ScheduleAbstract> getSchedule(Long startNumber, Long count, Loan... loans);

	/**
    * �������� ������ �� ������.
    *
    * @param claim
    * @return ������
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */

   Loan getLoanByClaim(LoanOpeningClaim claim) throws GateException, GateLogicException;

   /**
    * ��������� ������� �� ��������������.
    *
    * @param loanIds �������������� �������� �� ������� �������.
    * @return <ID �������, ������>
    */
    @Cachable(keyResolver= LoanIdCacheKeyComposer.class, name = "Loans.getLoan")
    GroupResult<String, Loan> getLoan(String... loanIds);

	/**
	 * �������� ���������� ��������.
	 *
	 * @param loan - ������ ��������
	 * @return <������, �������� �������>
	 */
	@Cachable(keyResolver= LoanCacheKeyComposer.class, name = "Loans.loanOwner")
	GroupResult<Loan,Client> getLoanOwner(Loan... loan);

	/**
	 * ������ ������� ���������� � ��������
	 * @param loanIds �������������� �������� �� ������� �������.
	 * @return <ID �������, ������>
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= LoanIdCacheKeyComposer.class, name = "Loans.getLoanShortCut")
	GroupResult<String, Loan> getLoanShortCut(String... loanIds)  throws GateLogicException, GateException;

	/**
	 * ������ GetPrivateLoanListRq
	 * @param loans �������
	 * @return <prodID �������, ��������� GetPrivateLoanListRq>
	 * @throws GateException
	 * @throws GateLogicException
	 */
	@Cachable(keyResolver= LoanPrivateCacheKeyComposer.class, name = "Loans.getLoanPrivate")
	public Map<String, LoanPrivate> getLoanPrivate(List<Loan> loans) throws GateException, GateLogicException;
}
