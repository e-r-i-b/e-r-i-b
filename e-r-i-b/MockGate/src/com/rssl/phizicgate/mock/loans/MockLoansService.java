package com.rssl.phizicgate.mock.loans;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizicgate.mock.clients.ClientImpl;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author hudyakov
 * @ created 26.08.2008
 * @ $Author$
 * @ $Revision$
 */
public class MockLoansService extends AbstractService implements LoansService
{
	public MockLoansService(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Список кредитов для клиента
	 *
	 * @param client клиент
	 * @return список кредитов
	 * @exception com.rssl.phizic.gate.exceptions.GateException
	 */
	public List<Loan> getLoans(Client client) throws GateException
	{
		List<Loan> loans = new ArrayList<Loan>();		
		loans.add(createLoan("132"));
		loans.add(createLoan("133"));
		return loans;
	}
	
	public ScheduleItem getNextScheduleItem(Loan loan, Money amount) throws GateException
	{
		return getNextScheduleItem(loan);
	}

	/**
    * Получить информацию о ближайшем платеже.
    * Может не совпадать с первой записью в getSchedule из-за учета штрафов и пр.
    *
    * @param loan Кредит
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
	public ScheduleItem getNextScheduleItem(Loan loan) throws GateException
	{
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		ScheduleItem scheduleItem = null;
		Currency currencyRUB = null;
		try
		{
			currencyRUB = currencyService.findByAlphabeticCode("RUB");


			scheduleItem = new ScheduleItemImpl(
					DateHelper.parseCalendar("19.09.2008"),
					new Money(new BigDecimal(4879.47),currencyRUB),
					new Money(new BigDecimal(1092.53),currencyRUB),
					new Money(new BigDecimal(0.00),currencyRUB),
					new Money(new BigDecimal(5972.00),currencyRUB),
					new Money(new BigDecimal(5972.00),currencyRUB),
					new Money(new BigDecimal(108376.00),currencyRUB),
					LoanPaymentState.paid,
					Long.valueOf(1),
					"qwer", getDebts(Long.valueOf(1))
			);
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
		return scheduleItem;
	}

    /**
    * Получить график погашения
    *
    * @param loans Список кредитов
    * @return график погашения
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
	public GroupResult<Loan,ScheduleAbstract> getSchedule(Long startNumber, Long count, Loan... loans)
	{
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		GroupResult<Loan,ScheduleAbstract> result = new GroupResult<Loan,ScheduleAbstract>();

		Random r = new Random();
		for(Loan loan : loans)
		{
			ScheduleAbstractImpl scheduleAbstract = new ScheduleAbstractImpl();
			List<ScheduleItem> schedule = new ArrayList<ScheduleItem>();

			int paymentDay = r.nextInt(25) + 1;
			long paymentNumber = startNumber;
			try
			{
				Currency currencyRUB = currencyService.findByAlphabeticCode("RUB");

				ScheduleItem firstScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".01.2008"),
						new Money(new BigDecimal(4356.95),currencyRUB),
						new Money(new BigDecimal(1615.05),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(150000.00),currencyRUB),
						LoanPaymentState.paid,
						Long.valueOf(-4),
						"qwer",
						getDebts(Long.valueOf(-4))
					);
				schedule.add(firstScheduleItem);

				ScheduleItem secondScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".02.2008"),
						new Money(new BigDecimal(4464.65),currencyRUB),
						new Money(new BigDecimal(1507.35),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(144028.00),currencyRUB),
						LoanPaymentState.paid,
						Long.valueOf(-3),
						"qwer",
						getDebts(Long.valueOf(-3))
				);
				schedule.add(secondScheduleItem);

				ScheduleItem thirdScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".03.2008"),
						new Money(new BigDecimal(4614.96),currencyRUB),
						new Money(new BigDecimal(1357.04),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(138056),currencyRUB),
						LoanPaymentState.paid,
						Long.valueOf(-2),
						"qwer",
						getDebts(Long.valueOf(-2))
				);
				schedule.add(thirdScheduleItem);

				ScheduleItem fourthScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".04.2008"),
						new Money(new BigDecimal(4580.01),currencyRUB),
						new Money(new BigDecimal(1391.99),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(132084.00),currencyRUB),
						LoanPaymentState.fail,
						Long.valueOf(-1),
						"qwer",
						getDebts(Long.valueOf(-1))
				);
				schedule.add(fourthScheduleItem);

				ScheduleItem fifthScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".05.2008"),
						new Money(new BigDecimal(4356.95),currencyRUB),
						new Money(new BigDecimal(1615.05),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(126112.00),currencyRUB),
						LoanPaymentState.fail,
						Long.valueOf(0),
						"qwer",
						getDebts(Long.valueOf(0))
				);
				schedule.add(fifthScheduleItem);

				ScheduleItem sixthScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".06.2008"),
						new Money(new BigDecimal(4738.25),currencyRUB),
						new Money(new BigDecimal(1233.75),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(126112.00),currencyRUB),
						LoanPaymentState.future,
						Long.valueOf(1),
						"qwer",
						getDebts(Long.valueOf(1))
				);
				schedule.add(sixthScheduleItem);

				ScheduleItem seventhScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".07.2008"),
						new Money(new BigDecimal(4796.50),currencyRUB),
						new Money(new BigDecimal(1175.50),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(120320.00),currencyRUB),
						LoanPaymentState.future,
						Long.valueOf(2),
						"qwer",
						getDebts(Long.valueOf(2))
				);
				schedule.add(seventhScheduleItem);

				ScheduleItem eighthScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".08.2008"),                        
						new Money(new BigDecimal(4818.26),currencyRUB),
						new Money(new BigDecimal(1153.74),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(114348.00),currencyRUB),
						LoanPaymentState.future,
						Long.valueOf(3),
						"qwer",
						getDebts(Long.valueOf(3))
				);
				schedule.add(eighthScheduleItem);

				ScheduleItem ninthScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".09.2008"),
						new Money(new BigDecimal(4879.47),currencyRUB),
						new Money(new BigDecimal(1092.53),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(108376.00),currencyRUB),
						LoanPaymentState.future,
						Long.valueOf(4),
						"qwer",
						getDebts(Long.valueOf(4))
				);
				schedule.add(ninthScheduleItem);

				ScheduleItem tenthScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".10.2008"),
						new Money(new BigDecimal(4908.22),currencyRUB),
						new Money(new BigDecimal(1063.78),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(102400.00),currencyRUB),
						LoanPaymentState.future,
						Long.valueOf(5),
						"qwer",
						getDebts(Long.valueOf(5))
				);
				schedule.add(tenthScheduleItem);

				ScheduleItem eleventhScheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".11.2008"),
						new Money(new BigDecimal(4908.22),currencyRUB),
						new Money(new BigDecimal(1063.78),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(102400.00),currencyRUB),
						LoanPaymentState.future,
						Long.valueOf(6),
						"qwer",
						getDebts(Long.valueOf(6))
				);
				schedule.add(eleventhScheduleItem);

				ScheduleItem scheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".12.2008"),
						new Money(new BigDecimal(4908.22),currencyRUB),
						new Money(new BigDecimal(1063.78),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(102400.00),currencyRUB),
						LoanPaymentState.future,
						Long.valueOf(7),
						"qwer",
						getDebts(Long.valueOf(7))
				);
				schedule.add(scheduleItem);

				scheduleItem = new ScheduleItemImpl(
						DateHelper.parseCalendar(paymentDay + ".12.2008"),
						new Money(new BigDecimal(4908.22),currencyRUB),
						new Money(new BigDecimal(1063.78),currencyRUB),
						new Money(new BigDecimal(0.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(5972.00),currencyRUB),
						new Money(new BigDecimal(102400.00),currencyRUB),
						LoanPaymentState.future,
						Long.valueOf(8),
						"qwer",
						getDebts(Long.valueOf(8))
				);
				schedule.add(scheduleItem);



				scheduleAbstract.setSchedules(schedule);
				scheduleAbstract.setPaymentCount(Long.valueOf(20));
				result.putResult(loan,scheduleAbstract);
			}
			catch (IKFLException e)
			{
				result.putException(loan, e);
			}
			catch (Exception e)
			{
				result.putException(loan, new SystemException(e));
			}
		}

		return result;
	}

   /**
    * Получить из гейта те ответы на вопросы анкеты, которые уже известны.
    *
    * @param clientId Внешний ID клиента (Domain: ExternalID)
    * @return список ответов
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
	public List<QuestionnaireAnswer> getQuestionarieAnswers(String clientId) throws GateException
	{
		throw new UnsupportedOperationException();
	}

   /**
    * Получить кредит по заявке.
    *
    * @param claim
    * @return кредит
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
	public Loan getLoanByClaim(LoanOpeningClaim claim) throws GateException
	{
		return createLoan("132");
	}

	/**
    * Получение кредита по идентификатору.
    *
    * @param loanIds Идентификаторы кредитов во внешней системе.
    * @return <идентификатор кредита, кредит>
    * @exception com.rssl.phizic.gate.exceptions.GateException
    */
	public GroupResult<String, Loan> getLoan(String... loanIds)
	{
		GroupResult<String, Loan> res = new GroupResult<String, Loan>();
		for(String loanId: loanIds)
			res.putResult(loanId,createLoan(loanId));
		return res;
	}

	/**
	 * Получить владельца кредита.
	 *
	 * @param loan - кредит
	 * @return владелец кредита
	 */
	public GroupResult<Loan,Client> getLoanOwner(Loan... loan)
	{
		//Здесь этот код работать не будет
/**
		final ClientService clientService = GateSingleton.getFactory().service(ClientService.class);

		CommonLogin login = AuthModule.getAuthModule().getPrincipal().getLogin();
		ActivePerson currentPerson = null;
		try
		{
			currentPerson = (new PersonService()).findByUserId(login.getUserId());
		}

		catch (Exception e)
		{
			new BusinessException(e);
		}

		return clientService.getClientById(currentPerson.getClientId());
 **/
		throw new UnsupportedOperationException();
	}

	public GroupResult<String, Loan> getLoanShortCut(String... loanIds) throws GateLogicException, GateException
	{
		GroupResult<String, Loan> res = new GroupResult<String, Loan>();
		for(String loanId: loanIds)
			res.putResult(loanId,createLoan(loanId));
		return res;
	}

	public Map<String, LoanPrivate> getLoanPrivate(List<Loan> loans) throws GateException, GateLogicException
	{
		Map<String, LoanPrivate> result = new HashMap<String, LoanPrivate>();
		for (Loan loan : loans)
		{
			LoanPrivateImpl loanPrivate = new LoanPrivateImpl();
			loanPrivate.setClosestPaymentDate(Calendar.getInstance());
			loanPrivate.setRecPaymentAmount(new BigDecimal(1000));
			result.put(loan.getProdType(), loanPrivate);
		}
		return result;
	}

	private Loan createLoan(String loanId)
	{
		LoanImpl loan = null;

		try
		{
		    CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

			Set<GuaranteeContract> guaranteeContracts = new HashSet<GuaranteeContract>();
			guaranteeContracts.add(new GuaranteeContractImpl(
					"1",
					ContractType.guarantee,
					ContractState.open,
					DateHelper.parseCalendar("17.12.2007"),
					DateHelper.parseCalendar("17.12.2009"),
					new Money(new BigDecimal("200.0"), currencyService.findByAlphabeticCode("RUB")),
					null
			));

			Map<PersonLoanRole, Client> persons = new HashMap<PersonLoanRole, Client>();
			ClientImpl client = new ClientImpl();
			client.setFullName("Тестов Тест Тестович");
			persons.put(PersonLoanRole.borrower, client);

			ClientImpl borrower = new ClientImpl();
			borrower.setFirstName("Иван");
			borrower.setPatrName("Иванович");
			borrower.setSurName("Иванов");
			borrower.setFullName(borrower.getSurName() + " " + borrower.getFirstName() + " " + borrower.getPatrName());
			Currency currencyRUB = currencyService.findByAlphabeticCode("RUB");
			if(loanId.equals("132"))
			{
				loan = new LoanImpl(
					loanId,
					LoanState.overdue ,
					"Доступные деньги",
					"1310/00052",
					DateHelper.parseCalendar("17.12.2007"),
					new DateSpan(2,0,0),
					DateHelper.parseCalendar("17.12.2009"),
					new Money(new BigDecimal(150000.00), currencyRUB),
					new BigDecimal(13),
					new Money(new BigDecimal(100000.00), currencyRUB),
					DateHelper.parseCalendar("18.05.2008"),
					new Money(new BigDecimal(5972.00), currencyRUB),
					DateHelper.parseCalendar("18.04.2008"),
					new Money(new BigDecimal(5972.00), currencyRUB),
					new Money(new BigDecimal(0.00), currencyRUB),
					12,
					PersonLoanRole.borrower,
					false,
					"12345678901234567890",
					borrower,
					new Money(new BigDecimal(100.00),currencyRUB),
					new Money(new BigDecimal(200.00),currencyRUB),
					new Money(new BigDecimal(300.00),currencyRUB),
					new BigDecimal(12),
					CommissionBase.loanRest,
					guaranteeContracts.iterator(),
					persons
				);
			}
			else
			{
				loan = new LoanImpl(
					loanId,
					LoanState.open,
					"Автокредит",
					"1310/00052",
					DateHelper.parseCalendar("17.12.2007"),
					new DateSpan(2,0,0),
					DateHelper.parseCalendar("17.12.2009"),
					new Money(new BigDecimal(550000.00), currencyRUB),
					new BigDecimal(13),
					new Money(new BigDecimal(100000.00), currencyRUB),
					DateHelper.parseCalendar("18.05.2008"),
					new Money(new BigDecimal(5972.00), currencyRUB),
					DateHelper.parseCalendar("18.04.2008"),
					new Money(new BigDecimal(5972.00), currencyRUB),
					new Money(new BigDecimal(0.00), currencyRUB),
					12,
					PersonLoanRole.borrower,
					false,
					"12345678901234567811",
					borrower,
					new Money(new BigDecimal(100.00),currencyRUB),
					new Money(new BigDecimal(200.00),currencyRUB),
					new Money(new BigDecimal(300.00),currencyRUB),
					new BigDecimal(12),
					CommissionBase.loanRest,
					guaranteeContracts.iterator(),
					persons
				);
			}
		}
		catch(Exception e)
		{
			new GateException(e);
		}
		return loan;
	}

	/**
	 * @return MAP с задолжностями по платежу
	 */
	private Map<PenaltyDateDebtItemType, DateDebtItem> getDebts(Long paymentNumber) throws GateException
	{
		final CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Map<PenaltyDateDebtItemType, DateDebtItem> debts = new HashMap<PenaltyDateDebtItemType, DateDebtItem>();

		Random rand = new Random();
		int i = rand.nextInt(5);// задолжности будут у каждого 5-го платежа
		//если платеж ещё предстоит, то задолжностей быть не должно
		if(paymentNumber < 0 && i!=1)
		{
			try{
				Currency currencyRUB = currencyService.findByAlphabeticCode("RUB");
				DateDebtItem debt = new DateDebtItemImpl(PenaltyDateDebtItemType.penaltyDelayDebtAmount,
						1L,	"1223",	null, "name", new Money(new BigDecimal(179.47),currencyRUB));
				debts.put(debt.getCode(),debt);
				debt = new DateDebtItemImpl(PenaltyDateDebtItemType.penaltyDelayPercentAmount,
						1L,	"1223",	null, "name", new Money(new BigDecimal(56.47),currencyRUB));
				debts.put(debt.getCode(),debt);
			}
			catch (Exception e)
			{
				throw new GateException(e);
			}
		}
		return debts;
	}
}
