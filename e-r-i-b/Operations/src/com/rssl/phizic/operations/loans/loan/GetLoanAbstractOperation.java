package com.rssl.phizic.operations.loans.loan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loans.LoanPaymentComparator;
import com.rssl.phizic.business.loans.LoanServiceHelper;
import com.rssl.phizic.business.loans.ScheduleAbstractImpl;
import com.rssl.phizic.business.operations.restrictions.LoanRestriction;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * @author mihaylov
 * @ created 21.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class GetLoanAbstractOperation extends OperationBase<LoanRestriction>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public static final Long MAX_COUNT_OF_TRANSACTIONS_RECV = 10L;

	private static LoansService loansService = GateSingleton.getFactory().service(LoansService.class);
	private List<LoanLink> loanLinks;
	private LoanLink loanLink;
	private boolean isError = false;
	//��������� ��������� �� ������ ��� ����������� ������������ ��� ��������� ������� ��������
	private final Map<LoanLink, String> loanAbstractMsgErrorMap = new HashMap<LoanLink, String>();

	private boolean isUseStoredResource;

	public void initialize(List<LoanLink> loanLinks)
	{
		this.loanLinks = new  ArrayList<LoanLink>();
		for(LoanLink link : loanLinks)
		{
			 if(check(link))
			      this.loanLinks.add(link);
		}
	}


	public void initialize(LoanLink loanLink)
	{
		 if(check(loanLink))
		{
			this.loanLink = loanLink;
			loanLinks = new ArrayList<LoanLink>();
			loanLinks.add(loanLink);
		}
	}

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
	    PersonDataProvider provider = PersonContext.getPersonDataProvider();
	    LoanLink loanLink = provider.getPersonData().getLoan(id);
	    if(check(loanLink))
		{
			this.loanLink = loanLink;
			loanLinks = new ArrayList<LoanLink>();
			loanLinks.add(loanLink);
		}
	}

	private boolean check(LoanLink loanLink)
	{
		Loan loan = loanLink.getLoanShortCut();
		isUseStoredResource = !isUseStoredResource ? (loan instanceof AbstractStoredResource) : isUseStoredResource;
		return getRestriction().accept(loan);
	}

	public LoanLink getLoanLink()
	{
		return loanLink;
	}

	/**
	 * ���������� ������� �������� ��� ������������������ ��������, ���� ���� �������� ������, �� ������
	 * @param startNumber - ��������� �����
	 * @param count - ���������� �������
	 * @param backSortSchedules - true ���������� ������ �������� �� ����� � �������� �������
	 * @return ���� 1) Map<LoanLink, ScheduleAbstract> - ������ �������� �� ��������   2) Map<LoanLink, String> ������ ������ �� ��������
	 */
	public Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>> getScheduleAbstract(Long startNumber, Long count, boolean backSortSchedules) throws BusinessException
	{
		return 	getScheduleAbstract(startNumber, count,  backSortSchedules, true);
	}

	/**
	 * ���������� ������� �������� ��� ������������������ ��������
	 * @param startNumber - ��������� �����
	 * @param count - ���������� �������
	 * @param backSortSchedules - true ���������� ������ �������� �� ����� � �������� �������
	 * @param isClosed - ������ �� ��� �������� ��������
	 * @return ���� 1) Map<LoanLink, ScheduleAbstract> - ������ �������� �� ��������   2) Map<LoanLink, String> ������ ������ �� ��������
	 */
	public Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>> getScheduleAbstract(Long startNumber, Long count, boolean backSortSchedules, boolean isClosed) throws BusinessException
	{
		Loan[] loans = getLoans(isClosed);
		if(ArrayUtils.isEmpty(loans))
			return new Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>>(Collections.<LoanLink, ScheduleAbstract>emptyMap(), Collections.<LoanLink, String>emptyMap());

		if (isUseStoredResource())
		{
			isError = true;
			for (LoanLink link : loanLinks)
			{
				loanAbstractMsgErrorMap.put(link, StoredResourceMessages.getUnreachableStatement());
			}
			return new Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>>(Collections.<LoanLink, ScheduleAbstract>emptyMap(), Collections.<LoanLink, String>emptyMap());
		}
		
		GroupResult<Loan, ScheduleAbstract> results = loansService.getSchedule(startNumber, count, loans);

		Map<Loan, ScheduleAbstract> resultsMap = results.getResults();
		Map<Loan, IKFLException> exceptionsMap = results.getExceptions();

		Map<LoanLink, ScheduleAbstract> scheduleAbstractMap = new HashMap<LoanLink, ScheduleAbstract>();
		Map<LoanLink, String> errors = new HashMap<LoanLink, String>();

		LoanLink currentLoanLink;
		for (Map.Entry<Loan, ScheduleAbstract> resultEntry : resultsMap.entrySet())
		{
			Loan loan = resultEntry.getKey();
			currentLoanLink = LoanServiceHelper.findLoanLinkByNumber(loanLinks,loan.getAccountNumber());
			if(currentLoanLink == null)
			{
				log.error("�������� ���������� �� �� ���� �������. ������� ���� �" + loan.getAccountNumber());
				continue;
			}

			ScheduleAbstract scheduleAbstract = resultEntry.getValue();
			if (backSortSchedules && scheduleAbstract != null)
			{
				List<ScheduleItem> scheduleItems = getSortScheduleItems(scheduleAbstract.getSchedules());
				ScheduleAbstractImpl abstractImpl = new ScheduleAbstractImpl(scheduleAbstract);
				abstractImpl.setSchedules(scheduleItems);
				scheduleAbstractMap.put(currentLoanLink,abstractImpl);
			}
			else
				scheduleAbstractMap.put(currentLoanLink,scheduleAbstract);
		}

		for(Map.Entry<Loan, IKFLException> exceptionEntry : exceptionsMap.entrySet())
		{
			Loan loan = exceptionEntry.getKey();
			currentLoanLink = LoanServiceHelper.findLoanLinkByNumber(loanLinks,loan.getAccountNumber());

			IKFLException ikflException = exceptionsMap.get(loan);
			if(currentLoanLink != null)
			{
				errors.put(currentLoanLink, ikflException.getMessage());
				if (ikflException instanceof GateLogicException)
					loanAbstractMsgErrorMap.put(currentLoanLink,ikflException.getMessage());
			}

			log.error("��������� ������ ��� ��������� ������� �� �������. ������� ���� �" + loan.getAccountNumber(), ikflException);
		}

		//����� ������ ������ ���� �� ���� �������� ������ ������
	    if (MapUtils.isEmpty(resultsMap) && !MapUtils.isEmpty(exceptionsMap))
		    isError = true;

		return new Pair<Map<LoanLink, ScheduleAbstract>, Map<LoanLink, String>>(scheduleAbstractMap, errors);
	}

	/**
	 * ���������� ������ �������� ��������������� �� ����� � �������� �������
	 * @param items - ������ ��������
	 * @return List<ScheduleItem>
	 */
	private List<ScheduleItem> getSortScheduleItems(List<ScheduleItem> items)
	{
		if (items == null || items.isEmpty())
			return new ArrayList<ScheduleItem>();

		List<ScheduleItem> result = new ArrayList<ScheduleItem>(items);
		Collections.sort(result, new LoanPaymentComparator());
		Collections.reverse(result);
		return result;
	}

	public boolean isError()
	{
		return isError;
	}

	/**
	 * ���������, �������� �� ������ ��������
	 * @return true-  ��, false - ���
	 */
	private boolean isLoanClosed(Loan loan)
	{
		return loan.getState() == LoanState.closed;
	}


	/**
	 * @param isClosed - ����� �� ������ ���������� ��� �������� ��������
	 * @return ������� ��� ��������� �������
	 */
	private Loan[] getLoans(boolean isClosed) throws BusinessException
	{
		List<Loan> loans = new ArrayList<Loan>();
		for(LoanLink link : loanLinks)
		{
			Loan loan = link.getLoanShortCut();
			if (isClosed && isLoanClosed(loan))
				throw new BusinessException("�� ��������� ������� ������ �������� ��������� ���������� � ������ ��������");

			loans.add(link.getLoanShortCut());
		}
		return loans.toArray(new Loan[loans.size()]);
	}

	public Map<LoanLink, String> getLoanAbstractMsgErrorMap()
	{
		return Collections.unmodifiableMap(loanAbstractMsgErrorMap);
	}

	public boolean isUseStoredResource()
	{
		return isUseStoredResource;
	}

    /**
     * �������� ������ �������� �� ������� this.loanLink �� ������
     * @param from ������ �������
     * @param to ����� �������
     * @return ������ ��������
     * @throws BusinessException
     */
    public ScheduleAbstract getLoanAbstract(Calendar from, Calendar to) throws BusinessException
    {
        Calendar nextPaymentDate = loanLink.getLoan().getNextPaymentDate();
        Pair<Long, Long> countRec = LoanServiceHelper.getPaymentCountByDates(nextPaymentDate, from, to);
        long startNumber = countRec.getFirst(); // ����� ������� � �������, ������� � �������� ������ ������
        long count = countRec.getSecond();       // ���������� �������� � �������

        Map<LoanLink, ScheduleAbstract> scheduleAbstract = getScheduleAbstract(startNumber, count, false).getFirst();
        return scheduleAbstract.get(loanLink);
    }

    /**
     * �������� ������ �������� �� ������� this.loanLink �� ������
     * @param from ������ �������
     * @param to ����� �������
     * @return ������ ��������
     * @throws BusinessException
     */
    public ScheduleAbstract getLoanAbstract(Date from, Date to) throws BusinessException
    {
        return getLoanAbstract(DateHelper.toCalendar(from), DateHelper.toCalendar(to));
    }

    /**
     * �������� ������ �������� �� ������� this.loanLink � ��������� ����������� ��������
     * @param count ���������� ��������
     * @return ������ ��������
     * @throws BusinessException
     */
    public ScheduleAbstract getLoanAbstract(Long count) throws BusinessException
    {
        Calendar fromDate = DateHelper.getCurrentDate();
        fromDate.add(Calendar.MONTH, -1);

        Calendar toDate = DateHelper.getCurrentDate();
        toDate.add(Calendar.MONTH, count.intValue() - 1);

        return getLoanAbstract(fromDate, toDate);
    }

    /**
     * �������� ������ �������� �� ������� this.loanLink �� ������ �� ���������
     * @return ������ ��������
     * @throws BusinessException
     */
    public ScheduleAbstract getDefaultLoanAbstract() throws BusinessException
    {
        Calendar fromDate = DateHelper.getCurrentDate();
        fromDate.add(Calendar.MONTH, -1);
        fromDate.set(Calendar.DAY_OF_MONTH, 1);

        Calendar toDate = DateHelper.getCurrentDate();
        toDate.add(Calendar.MONTH, 11);
        toDate.set(Calendar.DAY_OF_MONTH, 1);
        toDate.add(Calendar.DAY_OF_MONTH, -1);

        return getLoanAbstract(fromDate, toDate);
    }
}
