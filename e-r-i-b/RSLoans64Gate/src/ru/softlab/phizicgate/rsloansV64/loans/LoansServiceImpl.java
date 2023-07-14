package ru.softlab.phizicgate.rsloansV64.loans;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.utils.GroupResultHelper;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import ru.softlab.phizicgate.rsloansV64.data.RSLoansV64Executor;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Danilov
 * @ created 13.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoansServiceImpl extends AbstractService implements LoansService
{

	public LoansServiceImpl(GateFactory factory)
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
	public List<Loan> getLoans(final Client client) throws GateException
	{
		try
		{
			List<Loan> loans = RSLoansV64Executor.getInstance(true).execute(new HibernateAction<List<Loan>>()
			{
				public List<Loan> run(Session session) throws Exception
				{
					org.hibernate.Query query = createReadonlyQuery(session, "GetLoans")
							.setParameter("clientId", Long.decode(client.getId()));

					return (List<Loan>) query.list();
				}
			});
			for (Loan loan : loans)
			{
				LoanImpl loanImpl = (LoanImpl) loan;
				loanImpl.setTermDuration(new DateSpan(0,loanImpl.getTermDurationMonths(),0));
			}
			return loans;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получить информацию о ближайшем платеже.
	 * Может не совпадать с первой записью в getSchedule из-за учета штрафов и пр.
	 *
	 * @param loan Кредит
	 */
	public ScheduleItem getNextScheduleItem(Loan loan, Money amount) throws GateException
	{
		return getNextScheduleItem(loan);
	}

	/**
	 * Получить информацию о ближайшем платеже.
	 * Может не совпадать с первой записью в getSchedule из-за учета штрафов и пр.
	 *
	 * @param loan Кредит
	 */
	public ScheduleItem getNextScheduleItem(Loan loan) throws GateException
	{
		try{
			ScheduleAbstract scheduleAbstract = GroupResultHelper.getOneResult(getSchedule(null,null,loan));
			List<ScheduleItem> list = scheduleAbstract.getSchedules();
			for(ScheduleItem item : list)
			{
				if (item.getDate().equals(loan.getNextPaymentDate()))
				{
					return item;
				}
			}
			return null;
		}
		catch(IKFLException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получить график погашения
	 *
	 * @param loans Список кредитов
	 * @return <кредит, график погашения>
	 */
	public GroupResult<Loan, ScheduleAbstract> getSchedule(Long startNumber, Long count ,final Loan... loans)
	{
		GroupResult<Loan, ScheduleAbstract> result = new GroupResult<Loan, ScheduleAbstract>();
		for(final Loan loan: loans)
		{
			try
			{
				Money amount = new Money(BigDecimal.ZERO, loan.getLoanAmount().getCurrency());
				String paymentCommission = RSLoansV64Executor.getInstance(true).execute(new HibernateAction<String>()
					{
						public String run(Session session) throws Exception
						{
							Query query = session.getNamedQuery("ru.softlab.phizicgate.rsloansV64.loans.getEarlyPaymentCommission");
							query.setParameter("creditNumber", loan.getId());
							return (String) query.uniqueResult();
						}
				});

				if (paymentCommission == null) paymentCommission = "0";
				Money earlyPaymentCommission = new Money(new BigDecimal(paymentCommission), loan.getLoanAmount().getCurrency());

				List<ScheduleItem> list = RSLoansV64Executor.getInstance(true).execute(new HibernateAction<List<ScheduleItem>>()
				{
					public List<ScheduleItem> run(Session session) throws Exception
					{
						org.hibernate.Query query = createReadonlyQuery(session, "GetSchedule")
								.setParameter("creditNumber", loan.getId());

						return (List<ScheduleItem>) query.list();
					}
				});
				List<ScheduleItem> listComm = RSLoansV64Executor.getInstance(true).execute(new HibernateAction<List<ScheduleItem>>()
				{
					public List<ScheduleItem> run(Session session) throws Exception
					{
						org.hibernate.Query query = createReadonlyQuery(session, "GetScheduleComm")
								.setParameter("creditNumber", loan.getId());

						return (List<ScheduleItem>) query.list();
					}
				});
				for (ScheduleItem item : list)
				{
					for (ScheduleItem itemComm : listComm)
						if( item.getDate().equals(itemComm.getDate()) )
						{
							((ScheduleItemImpl)item).setCommissionAmount(itemComm.getCommissionAmount());
							break;
						}
					((ScheduleItemImpl)item).setTotalAmount(
							item.getPrincipalAmount()
							.add(item.getInterestsAmount()
							.add(item.getCommissionAmount()
					)));
					((ScheduleItemImpl)item).setTotalPaymentAmount(item.getTotalAmount());

					amount = amount.add(item.getPrincipalAmount());

					((ScheduleItemImpl)item).setEarlyPaymentAmount(
							loan.getLoanAmount()
							.sub(amount)
							.add(item.getTotalAmount())
							.add(earlyPaymentCommission));
				}
				ScheduleAbstractImpl schedule = new ScheduleAbstractImpl();
				schedule.setSchedules(list);
				result.putResult(loan,schedule);
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
	 */
	public List<QuestionnaireAnswer> getQuestionarieAnswers(String clientId)
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * Получить кредит по заявке.
	 *
	 * @param claim
	 */
	public Loan getLoanByClaim(LoanOpeningClaim claim) throws GateException
	{
		final String claimId = claim.getExternalId();
		try
		{
			Loan loan = RSLoansV64Executor.getInstance(true).execute(new HibernateAction<Loan>()
			{
				public Loan run(Session session) throws Exception
				{
					org.hibernate.Query query = createReadonlyQuery(session, "GetLoanByClaim")
							.setParameter("claimId", claimId);

					return (Loan)query.uniqueResult();
				}
			});
			LoanImpl loanImpl = (LoanImpl) loan;
			loanImpl.setTermDuration(new DateSpan(0,loanImpl.getTermDurationMonths(),0));
			return loan;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @return фабрика, создавшая сервис
	 */
	public GateFactory getFactory()
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	private org.hibernate.Query createReadonlyQuery(Session session, String queryName)
	{
		org.hibernate.Query query = session.getNamedQuery(queryName);
		query.setReadOnly(true).setFlushMode(FlushMode.NEVER);

		return query;
	}

	public GroupResult<String, Loan> getLoan(final String... loanIds)
	{
		GroupResult<String, Loan> res = new GroupResult<String, Loan>();
		for(final String loanId: loanIds)
		{
			try
			{
				Loan loan = RSLoansV64Executor.getInstance(true).execute(new HibernateAction<Loan>()
				{
					public Loan run(Session session) throws Exception
					{
						org.hibernate.Query query = createReadonlyQuery(session, "GetLoanById")
								.setParameter("id", loanId);

						return (Loan) query.uniqueResult();
					}
				});
				LoanImpl loanImpl = (LoanImpl) loan;
				loanImpl.setTermDuration(new DateSpan(0,loanImpl.getTermDurationMonths(),0));
				res.putResult(loanId,loan);
			}
			catch (IKFLException e)
			{
				res.putException(loanId, e);
			}
			catch (Exception e)
			{
				res.putException(loanId, new SystemException(e));
			}
		}
		return res;
	}

	/**
	 * Получение дополнительной информации по кредиту
	 * @param loans - список кредитов
	 * @return информация по кредиту
	 * @throws GateException
	 */
	public GroupResult<Loan, LoanInfo> getLoanInfo(Loan... loans)
	{
		GroupResult<Loan, LoanInfo> res = new GroupResult<Loan, LoanInfo>();
		for(Loan loan: loans)
		{
			final String loanId = loan.getId();
			try
			{
				LoanInfo loanInfo = RSLoansV64Executor.getInstance(true).execute(new HibernateAction<LoanInfo>()
				{
					public LoanInfo run(Session session) throws Exception
					{
						org.hibernate.Query query = createReadonlyQuery(session, "GetLoanInfo")
								.setParameter("loanId", loanId);

						return (LoanInfo) query.uniqueResult();
					}
				});

				List<GuaranteeContract> contracts = RSLoansV64Executor.getInstance(true).execute(new HibernateAction<List<GuaranteeContract>>()
				{
					public List<GuaranteeContract> run(Session session) throws Exception
					{
						org.hibernate.Query query = createReadonlyQuery(session, "GetGuaranteeContracts")
								.setParameter("loanId", loanId);

						return (List<GuaranteeContract>) query.list();
					}
				});

				for (GuaranteeContract currentContract : contracts)
				{
					final String number = currentContract.getNumber();
					Long guaranteeId = RSLoansV64Executor.getInstance().execute(new HibernateAction<Long>()
					{
						public Long run(Session session) throws Exception
						{
							Query query = createReadonlyQuery(session, "getGuaranteeIdByContractId")
									.setParameter("contractId", number);

							return (Long) query.uniqueResult();
						}
					});
					ClientService service = GateSingleton.getFactory().service(ClientService.class);
					Client guarantee = service.getClientById(guaranteeId.toString());

					GuaranteeContractImpl contractImpl = (GuaranteeContractImpl) currentContract;
					contractImpl.setGuarantee(guarantee);
				}

				LoanInfoImpl infoImpl = (LoanInfoImpl) loanInfo;
				infoImpl.setGuaranteeContractIterator(contracts.iterator());
				res.putResult(loan,loanInfo);
			}
			catch (IKFLException e)
			{
				res.putException(loan, e);
			}
			catch (Exception e)
			{
				res.putException(loan, new SystemException(e));
			}
		}
		return res;
	}

	public GroupResult<Loan,Client> getLoanOwner(Loan... loans)
	{
		GroupResult<Loan, Client> res = new GroupResult<Loan, Client>();

		for(Loan loan: loans)
		{
			final Long loanId = Long.valueOf(loan.getId());

			try
			{
				Long clientId = RSLoansV64Executor.getInstance().execute(new HibernateAction<Long>()
				{
					public Long run(Session session) throws Exception
					{
						Query query = createReadonlyQuery(session, "GetLoanOwnerId")
								.setParameter("loanId", loanId);

						return (Long) query.uniqueResult();
					}
				});
				ClientService service = GateSingleton.getFactory().service(ClientService.class);
				Client client = service.getClientById(clientId.toString());
				res.putResult(loan,client);
			}
			catch (IKFLException e)
			{
				res.putException(loan, e);
			}
			catch (Exception e)
			{
				res.putException(loan, new SystemException(e));
			}
		}

		return res;
	}
}