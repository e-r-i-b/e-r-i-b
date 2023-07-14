package com.rssl.phizicgate.esberibgate.loans;

import com.rssl.phizic.common.types.*;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.DocumentTypeComparator;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.gate.utils.StoredResourcesService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClientConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.AbstractService;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;
import com.rssl.phizicgate.esberibgate.messaging.ClientRequestHelperBase;
import com.rssl.phizicgate.esberibgate.messaging.LoanResponseSerializer;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.types.loans.LoanImpl;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.utils.LoanCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateLoanDetailsRs;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateLoanListRs;

import java.util.*;

/**
 * @author gladishev
 * @ created 13.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoansServiceImpl extends AbstractService implements LoansService
{
	private LoanResponseSerializer serializer = new LoanResponseSerializer();
	private LoanRequestHelper requestHelper = new LoanRequestHelper(getFactory());
	private ClientRequestHelperBase clientRequestHelper = new ClientRequestHelperBase(getFactory());
    ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
    private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	public LoansServiceImpl(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public List<Loan> getLoans(Client client) throws GateException, GateLogicException
	{
		ProductContainer productContainer = clientRequestHelper.createBankAcctInqRq(client, getClientDocument(client), BankProductType.Credit);
		if (productContainer.getIfxRq_type() == null)
			throw new InactiveExternalSystemException(productContainer.getProductError(BankProductType.Credit));

		IFXRs_Type ifxRs = getRequest(productContainer.getIfxRq_type());
		return serializer.fillLoans(ifxRs, client.getInternalOwnerId());
	}

	public ScheduleItem getNextScheduleItem(Loan loan) throws GateException, GateLogicException
	{
		return getNextScheduleItem(loan, null);
	}

	public ScheduleItem getNextScheduleItem(Loan loan, Money amount) throws GateException, GateLogicException
	{
        log.debug("[:|||:] (3) запрос задолжностей по кредиту " + loan.getProdType());
        Calendar currentDate = DateHelper.getCurrentDate(); //устанавливаем текущую дату

		LoanCompositeId composite = EntityIdHelper.getLoanCompositeId(loan);
		String branchId = loan.getOffice().getCode().getFields().get("office");
		IFXRq_Type ifxRq = requestHelper.createLoanInqRq(composite, branchId, currentDate, amount);

		IFXRs_Type ifxRs = getRequest(ifxRq);
        ScheduleItem item = serializer.getSchedule(ifxRq, ifxRs.getLoanInqRs(), currentDate, amount);
        log.debug("[:|||:] (e3) запрос задолжностей по кредиту " + loan.getProdType() + " получена");
		return item;
	}

	public GroupResult<Loan, ScheduleAbstract> getSchedule(Long startNumber, Long count, Loan... loans)
	{
		GroupResult<Loan, ScheduleAbstract> result = new GroupResult<Loan, ScheduleAbstract>();
		for (Loan loan : loans)
		{
			try
			{
                ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
                if (clientConfig.isJmsForLoanEnabled())
                {
                    LoanPaymentJMSRequestSender sender = new LoanPaymentJMSRequestSender(getFactory());
                    RequestHelperBase helper = new RequestHelperBase(getFactory());
                    result.putResult(loan, serializer.getScheduleAbstract(sender.send(loan, helper.getRbTbBrch(EntityIdHelper.getLoanCompositeId(loan))), loan));
                }
                else
                {
                    LoanCompositeId composite = EntityIdHelper.getLoanCompositeId(loan);
                    IFXRq_Type ifxRq = requestHelper.createLoanPaymentRq(composite, startNumber, count, Long.valueOf(0));
                    IFXRs_Type ifxRs = getRequest(ifxRq);
                    result.putResult(loan, serializer.getScheduleAbstract(ifxRq, ifxRs.getLoanPaymentRs(), loan));
                }

			}
			catch (IKFLException e)
			{
				result.putException(loan, e);
			}
		}

		return result;
	}

	/**
	 * Возвращает детальную информацию по кредитам
	 * @param loanIds идентификаторы кредитов вида 123...321|123456^SYSTEM_ID^054321^41 (номер ссудного счета|Идентификатор
	 *             банковского продукта^идентификатор системы-источника продукта^номер ОСБ где ведется ссудный счет^loginId владельца)
	 * @return GroupResult
	 */
	public GroupResult<String, Loan> getLoan(String... loanIds)
	{
		GroupResult<String, Loan> groupResult = new GroupResult<String, Loan>();

		Long loginId = new LoanCompositeId(loanIds[0]).getLoginId();
		try
		{
			GroupResult<String, Loan> loanShortCut = getLoanShortCut(loanIds);

			if (!loanShortCut.getResults().isEmpty() && clientConfig.isJmsForLoanEnabled())
			{
				RequestHelperBase helper = new RequestHelperBase(getFactory());
				StoredResourcesService storedResourcesService = GateSingleton.getFactory().service(StoredResourcesService.class);

				Collection<Loan> values = loanShortCut.getResults().values();
				String rbTbBrch = helper.getRbTbBrch(EntityIdHelper.getLoanCompositeId((Loan) values.toArray()[0]));

				for (Loan loanShort : values)
				{
					boolean contains = false;
					for (String loanId : loanIds)
					{
						if (loanId.equals(loanShort.getId()))
							contains = true;
					}
					if (!contains)
						continue;
					LoanImpl loan = (LoanImpl) loanShort;
					LoanPrivateDetailsJMSRequestSender detailsJMSRequestSender = new LoanPrivateDetailsJMSRequestSender(getFactory());
					GetPrivateLoanDetailsRs detailResponse = detailsJMSRequestSender.send(loan, rbTbBrch);
					serializer.getLoanFromPrivateLoanDetailsRs(detailResponse, loan);

					storedResourcesService.updateStoredLoan(EntityIdHelper.getLoanCompositeId(loan.getId()).getLoginId(), loan);
					groupResult.putResult(loan.getId(), loan);
				}
			}
		}
		catch (GateLogicException e)
		{
			groupResult.putException(String.valueOf(loginId), e);
		}
		catch (GateException e)
		{
			groupResult.putException(String.valueOf(loginId), e);
		}

		return groupResult;
	}

	public GroupResult<String, Loan> getLoanShortCut(String... loanIds) throws GateLogicException, GateException
	{

		log.debug("[:|||:] (2) детальная информация по кредитам " + loanIds);
		Map<Long, List<Loan>> loansGFLByLogin = new HashMap<Long, List<Loan>>();
		GroupResult<String, Loan> groupResult = new GroupResult<String, Loan>();

		Long loginId = new LoanCompositeId(loanIds[0]).getLoginId();
		List<Loan> loansGFL = loansGFLByLogin.get(loginId);
		try
		{
			if (loansGFL == null)
			{
				BackRefClientService backRefClientService = GateSingleton.getFactory().service(BackRefClientService.class);
				loansGFL = getLoans(backRefClientService.getClientById(loginId));
				loansGFLByLogin.put(loginId, loansGFL);
			}
		}
		catch (IKFLException e)
		{
			groupResult.putException(String.valueOf(loginId), e);
		}

		if (!loansGFL.isEmpty())
		{
			Map<String, LoanPrivate> loanPrivateMap = new HashMap<String, LoanPrivate>();
			loanPrivateMap = GateSingleton.getFactory().service(LoansService.class).getLoanPrivate(loansGFL);
			for (String loanId : loanIds)
			{
				try
				{
					LoanImpl loan = null;

					for (Loan value : loansGFL)
					{
						if (loanId.equals(value.getId()))
						{
							loan = (LoanImpl) value;
							break;
						}
					}

					if (loan == null)
					{
						groupResult.putException(loanId, new GateLogicException("Нет информации по кредиту с id=" + loanId));
						continue;
					}

					StoredResourcesService storedResourcesService = GateSingleton.getFactory().service(StoredResourcesService.class);

					if (clientConfig.isJmsForLoanEnabled())
					{
						LoanPrivate loanPrivate = loanPrivateMap.get(loan.getProdType());
						loan.setClosestPaymentDate(loanPrivate.getClosestPaymentDate());
						loan.setRecPayment(new Money(loanPrivate.getRecPaymentAmount(), loan.getLoanAmount().getCurrency()));
					}
					else
					{
						ScheduleItem scheduleItem = getNextScheduleItem(loan);
						loan.setBalanceAmount(scheduleItem.getEarlyPaymentAmount());
						if (loan.getNextPaymentDate() == null)
						{
							loan.setNextPaymentDate(scheduleItem.getDate());
						}

						if (loan.getNextPaymentAmount() == null)
						{
							loan.setNextPaymentAmount(scheduleItem.getTotalPaymentAmount());
						}
					}

					storedResourcesService.updateStoredLoan(EntityIdHelper.getLoanCompositeId(loanId).getLoginId(), loan);
					groupResult.putResult(loanId, loan);
				}
				catch (IKFLException e)
				{
					groupResult.putException(loanId, e);
				}
			}
		}
		log.debug("[:|||:] (e2) детальная информация по кредитам " + loanIds + " получена");
		return groupResult;
	}

	public Map<String, LoanPrivate> getLoanPrivate(List<Loan> loans) throws GateException, GateLogicException
	{
		String rbTbBrch = new RequestHelperBase(getFactory()).getRbTbBrch(EntityIdHelper.getLoanCompositeId(loans.get(0)));
		LoanPrivateListJMSRequestSender listJMSRequestSender = new LoanPrivateListJMSRequestSender(getFactory());
		GetPrivateLoanListRs response = listJMSRequestSender.send(loans, rbTbBrch);

		Map<String, LoanPrivate> result = new HashMap<String, LoanPrivate>();
		for (GetPrivateLoanListRs.LoanRec rec : response.getLoanRecs())
		{
			LoanPrivateImpl loanPrivate = new LoanPrivateImpl();
			loanPrivate.setClosestPaymentDate(XMLDatatypeHelper.parseDateTime(rec.getLoanInfo().getPaymentDate()));
			loanPrivate.setRecPaymentAmount(rec.getLoanInfo().getRecPayment());
			result.put(rec.getLoanInfo().getProdId(), loanPrivate);
		}
		return result;
	}

	public Loan getLoanByClaim(LoanOpeningClaim claim) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public GroupResult<Loan, Client> getLoanOwner(Loan... loans)
	{
		throw new UnsupportedOperationException();
	}

	public List<QuestionnaireAnswer> getQuestionarieAnswers(String clientId) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Возвращает паспорт клиента
	 * если его нет, то первый из списка
	 * @param client - клиент
	 * @return документ
	 */
	public ClientDocument getClientDocument(Client client)
	{
		List<? extends ClientDocument> documents = client.getDocuments();
		if (documents == null || documents.isEmpty())
			return null;

		Collections.sort(documents, new DocumentTypeComparator());
		return documents.get(0);
	}
}
