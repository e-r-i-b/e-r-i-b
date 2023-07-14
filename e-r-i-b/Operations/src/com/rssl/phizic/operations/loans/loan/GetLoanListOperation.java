package com.rssl.phizic.operations.loans.loan;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.resources.external.StoredLoan;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoanState;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.link.GetExternalResourceLinkOperation;
import com.rssl.phizic.utils.MockHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gladishev
 * @ created 26.03.2008
 * @ $Author$
 * @ $Revision$
 */

public class GetLoanListOperation extends GetExternalResourceLinkOperation
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static ExternalResourceService resourceService = new ExternalResourceService();

	private final LoansService loanService = GateSingleton.getFactory().service(LoansService.class);

	//���� �� ��������� ������
	private boolean isBackError = false;

	public boolean isBackError()
	{
		return isBackError;
	}

	/**
	 * ���������� ������ ���� �������� �������, ������������ � �������
	 * @return ������ ��������
	 */
	public List<LoanLink> getLoans()
	{
		try
		{
			return getPersonData().getLoans();
		}
		catch (BusinessException e)
		{
			log.error("������ ��������� ������ ���������", e);
			isBackError = true;
			return new ArrayList<LoanLink>();
		}
		catch (BusinessLogicException e)
		{
			log.error("������ ��������� ������ ���������", e);
			isBackError = true;
			return new ArrayList<LoanLink>();
		}
	}

	/**
	 * ��������� ������ ��������
	 * @param onlyShowInMain ���� ���������� �� ������������ ��������� ������ ��� ���������, ������� ����� ���������� �� ������� ��������
	 * @return
	 */
	private List<LoanLink> getLoans(boolean onlyShowInMain)
	{
		List<LoanLink> loans = new ArrayList<LoanLink>();

		for (LoanLink loanLink : getLoans())
		{
			// ���� �� ����� ���������� ���������� �� ����� �����, ���� �� �������� �������� ��
			if (loanLink.getShowInMain() || !onlyShowInMain)
			{
				Loan loan = loanLink.getLoan();
				if (MockHelper.isMockObject(loan))
					isBackError = true;

				if (!isUseStoredResource())
				{
					setUseStoredResource(loan instanceof StoredLoan);
				}

				loans.add(loanLink);
			}
		}
		return loans;
	}

	/**
	 * ���������� ������ ��������, ������������ �� ������� ��������
	 * @return ������ ��������
	 */
	public List<LoanLink> getShowInMainLoans()
	{
		List<LoanLink> loans = getLoans();
		return getShowInMainLinks(loans);
	}

	public ScheduleItem getNextScheduleItem(Loan loan) throws BusinessException, BusinessLogicException
	{
		try
		{
			return loanService.getNextScheduleItem(loan);
		}
		catch(GateException e)
		{
			throw new BusinessException("������ ��� ��������� ���������� " +
					"� ��������� ������� �� ������� c id=" + loan.getId(), e);
		}
		catch(GateLogicException e)
		{
			throw new BusinessLogicException("������ ��� ��������� ���������� " +
					"� ��������� ������� �� ������� c id=" + loan.getId(), e);
		}
	}

	public List<LoanLink> getAllLoans()
	{
		return getLoans(false);
	}

	/**
	 * ��������� ������ ��������, ��������� � ���-������ (�� ��������)
	 * @return ������ ��������
	 */
	public List<LoanLink> getErmbActiveLoans() throws BusinessException, BusinessLogicException
	{
		List<LoanLink> loans = new ArrayList<LoanLink>();
		for (LoanLink loanLink: getPersonData().getLoansAll())
		{
			if (loanLink.getShowInSystem() && loanLink.getShowInSms() && loanLink.getLoan().getState() != LoanState.closed)
				loans.add(loanLink);
		}
		return loans;
	}

	/**
	 * ������ ������ ��������� � �������� ��� �������� "��������"
	 * @param loanNumber - ����� �������
	 */
	public void setLoanLinkFalseClosedState(String loanNumber)
	{
		try
		{
			resourceService.setLoanLinksFalseClosedState(loanNumber);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ���������� ������� ������ ��������� � �������� ��������", e);
		}
	}
}
