package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 13.03.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Информация о кредите
 */
public interface Loan extends Serializable
{
   /**
    * Внешний ID кредита
    * Domain: ExternalID
    *
    * @return ID
    */
   String getId();
   /**
    * Текущее состояние
    *
    *
    * @return состояние
    */
   LoanState getState();
   /**
    * Описание (Автокредит, Потребительский etc)
    *
    * Domain: Text
    *
    * @return текст
    */
   String getDescription();
   /**
    * Номер договора
    *
    * Domain: Text
    *
    * @return текст
    */
   String getAgreementNumber();
	/**
    * Номер ссудного счета
    *
    * Domain: Text
    *
    * @return текст
    */
   String getAccountNumber();
   /**
    * Дата открытия договора
    *
    * Domain: Date
    *
    * @return дата
    */
   Calendar getTermStart();
   /**
    * Дата окончания договора
    *
    * Domain: Date
    *
    * @return дата
    */
   Calendar getTermEnd();
   /**
    * Срок кредита
    *
    *
    * @return срок
    */
   DateSpan getTermDuration();
   /**
    * Сумма кредита по договору
    *
    *
    * @return сумма
    */
   Money getLoanAmount();
   /**
    * % ставка (% годовых)
    *
    * Domain: Percent
    *
    * @return
    */
   BigDecimal getRate();
   /**
    * Сумма остатка ссудной задолженности
    *
    *
    * @return сумма
    */
   Money getBalanceAmount();
   /**
    * Дата последнего платежа
    *
    * Domain: DateTime
    *
    * @return дата
    */
   Calendar getLastPaymentDate();
   /**
    * Сумма последнего платежа
    *
    *
    * @return сумма
    */
   Money getLastPaymentAmount();
   /**
    * Дата следующего платежа
    *
    * Domain: DateTime
    *
    * @return дата
    */
   Calendar getNextPaymentDate();
    /**
    * Рекомендуемая сумма следующего платежа
    *
    *
    * @return сумма
    */
	Money getNextPaymentAmount();

   /**
    * Сумма просроченной задолженности
    *
    *
    * @return сумма
    */
   Money getPastDueAmount();

	/**
    * Роль клиента в кредитном договоре
	*
    * @return роль клиента
    */
    PersonLoanRole getPersonRole();

	/**
    * Аннуитетный платеж
	*
    * @return true - аннуитетный платеж, false - дифференцированный платеж
    */
    boolean getIsAnnuity();

	/**
    * Заемщик кредита
	*
    * @return заемщик
    */
    Client getBorrower();

	/**
    * Подразделение, в котором открыт кредит
    *
    * @return Подразделение, в котором открыт кредит или null, если нельзя получить эту информацию
    */
    Office getOffice();

	/**
	 * Сумма штрафа за 1ю просрочку
	 *
	 * @return сумма
	 */
	public Money getFirstDelayPenalty();

	/**
	 * Сумма штрафа за 2ю просрочку
	 *
	 * @return сумма
	 */
	public Money getSecondDelayPenalty();

	/**
	 * Сумма штрафа за 3ю просрочку
	 *
	 * @return сумма
	 */
	public Money getThirdDelayPenalty();

	/**
	 * Ставка ежемесячной комиссии
	 *
	 * @return ставка комиссии
	 */
	public BigDecimal getCommissionRate();

	/**
	 * База ежемесячной комиссии
	 *
	 * @return база комиссии
	 */
	public CommissionBase getCommissionBase();

	/**
	 * Договоры обеспечения
	 *
	 * @return договоры
	 */
	public Iterator<GuaranteeContract> getGuaranteeContractIterator();

	/**
	 * Мар клиентов, принимающих участие в кредите(заемщик, созаемщик, залогадатель, поручитель)
	 *
	 * @return Мар клиентов
	 */
	public Map<PersonLoanRole, Client> getLoanPersons();

    /**
     * Идентификатор банковского продукта
     * <ProdType> из GFL
     * @return Идентификатор банковского продукта
     */
    public String getProdType();

    /**
     * Процентная ставка по кредиту
     * <CreditingRate> GetPrivateLoanDetailsRs
     * @return процентная ставка
     */
    public BigDecimal getCreditingRate();

    /**
     * тип кредита
     * <LoanType> GetPrivateLoanDetailsRs
     * @return тип кредита
     */
    public String getLoanType();

    /**
     * осталось погасить
     * <FullRepaymentAmount> GetPrivateLoanDetailsRs
     * @return осталось погасить
     */
    public Money getFullRepaymentAmount();

    /**
     * Дата ближайшего платежа
     * <PaymentDate> GetPrivateLoanListRs
     * @return Дата ближайшего платежа
     */
    public Calendar getClosestPaymentDate();

    /**
     * Сумма ближайшего платежа
     * <RecPayment> GetPrivateLoanListRs
     * @return Сумма ближайшего платежа
     */
    public Money getRecPayment();

    /**
     * Основной долг
     * <AcctBalOnDate><NextAmt> GetPrivateLoanListRs <BalType> = КРЕДИТ
     * @return Сумма основного долга
     */
    public Money getMainDebt();

    /**
     * Выплаты по процентам
     * <AcctBalOnDate><NextAmt> GetPrivateLoanListRs <BalType> = BAKS_EXT_PRC | BAKS_EXT_PEN
     * @return Выплаты по процентам
     */
    public Money getInterestPayments();

    /**
     * Просроченная задолжность по основному долгу
     * <AcctBalOnDate><NextAmt> GetPrivateLoanListRs <BalType> = BAKS_EXT_CRED
     * @return Просроченная задолжность по основному долгу
     */
    public Money getOverdueMainDebts();

    /**
     * Просроченная задолжность по процентам
     * <AcctBalOnDate><NextAmt> GetPrivateLoanListRs <BalType> = BAKS_EXT_OVE
     * @return Просроченная задолжность по процентам
     */
    public Money getOverdueInterestDebts();

    /**
     * Средства для погашения
     * <NextAmt> GetPrivateLoanDetailsRs
     * @return Средства для погашения
     */
    public List<String> getAccount();

    /**
     * Сумма основного долга
     * <AcctBalOnDate><CurAmt> GetPrivateLoanListRs <BalType> = КРЕДИТ | BAKS_EXT_CRED
     * @return Сумма основного долга
     */
    public Money getMainDebtAmount();

    /**
     * Сумма выплат по процентам
     * <AcctBalOnDate><CurAmt> GetPrivateLoanListRs <BalType> = BAKS_EXT_OVE | BAKS_EXT_PRC | BAKS_EXT_PEN
     * @return Сумма выплат по процентам
     */
    public Money getInterestPaymentsAmount();

    /**
     * Отделение обслуживания кредита
     * <AgencyAddress> GetPrivateLoanDetailsRs
     * @return Отделение обслуживания кредита
     */
    public String getAgencyAddress();

    /**
     * Заемщик
     * <PersonInfo> GetPrivateLoanDetailsRs
     * @return Заемщик
     */
    public List<String> getNewBorrower();

    /**
     * Созаемщики
     * <PersonInfo> GetPrivateLoanDetailsRs
     * @return Созаемщики
     */
    public List<String> getNewCoBorrower();

    /**
     * Поручитель
     * <PersonInfo> GetPrivateLoanDetailsRs
     * @return Поручитель
     */
    public List<String> getGuarantor();

    /**
     * Статус кредита
     * <LoanStatus> GetPrivateLoanDetailsRs
     * @return Статус кредита
     */
    public Long getLoanStatus();

    /**
     * Остаток основного долга по кредиту
     * <PrincipalBalance> GetPrivateLoanDetailsRs
     * @return Остаток основного долга по кредиту
     */
    public BigDecimal getPrincipalBalance();

    /**
     * Признак просрочки
     * <Overdue> GetPrivateLoanDetailsRs
     * @return Признак просрочки
     */
    public boolean isOverdue();

	/**
	 * <BAKS> GetPrivateLoanDetailsRs
	 * @return Признак БАКС
	 */
	public boolean isBAKS();

	/**
	 * <RepaymentChannel> GetPrivateLoanDetailsRs
	 * @return Зарегистрированное досрочные погашения
	 */
	public List<EarlyRepayment> getEarlyRepayments();

	public boolean isAutoGranted();

	public String getApplicationNumber();

}