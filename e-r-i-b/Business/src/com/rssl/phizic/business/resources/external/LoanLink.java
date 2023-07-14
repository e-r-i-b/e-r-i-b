package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.loans.mock.MockLoan;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ResourceTypeKey;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.loans.LoansService;
import com.rssl.phizic.gate.loans.PersonLoanRole;
import com.rssl.phizic.gate.utils.OfflineExternalSystemException;
import com.rssl.phizic.utils.GroupResultHelper;

/**
 * @author gladishev
 * @ created 12.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanLink extends ErmbProductLink
{
	public static final String CODE_PREFIX = "loan";

	private String number; //����� �������� �����
	private PersonLoanRole personRole; //���� �������
	private Boolean closedState; //������� ������������� ������ ��������� � �������� �������

	public String getCodePrefix()
	{
		return CODE_PREFIX;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public int compareTo(Object o)
	{
		LoanLink link = (LoanLink)o;
		return this.getNumber().compareTo(link.getNumber());
	}

	public Object getValue()
	{
		return getLoan();
	}

	private Loan toLoanFromDb()
	{
		MockLoan loan = new MockLoan();
		loan.setId(getExternalId());
		loan.setAccountNumber(getNumber());

		return loan;
	}
	
	public void reset() throws BusinessLogicException, BusinessException
	{
		try
		{
			GateSingleton.getFactory().service(CacheService.class).clearLoanCache(toLoanFromDb());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	public Loan getLoan()
	{
		try
		{
            log.debug("[:|||:] (1)  ��������� ������� ����� " + getExternalId());
			Loan loan = GroupResultHelper.getOneResult(getLoansService().getLoan(getExternalId()));
			if (loan==null)
			{
				log.error("������ ��� ��������� ������� �" + getNumber());
				return new MockLoan();
			}
            log.debug("[:|||:] (e1)  ������ ����� " + getExternalId() + " �������");
			return loan;
		}
		catch (InactiveExternalSystemException e)
	    {
		    return getStoredLoan(e);
	    }
		catch (OfflineExternalSystemException e)
		{
			return getStoredLoan(e);
		}
		catch (Exception e)
	    {
		    //�� ������ ������� � jsp
		    log.error("������ ��� ��������� ������� �" + getNumber(), e);
		    return new MockLoan();
	    }
	}

	public Loan getLoanShortCut()
	{
		try
		{
			Loan loan = GroupResultHelper.getOneResult(getLoansService().getLoanShortCut(getExternalId()));
			if (loan==null)
			{
				return new MockLoan();
			}
			return loan;
		}
		catch (InactiveExternalSystemException e)
		{
			return getStoredLoan(e);
		}
		catch (OfflineExternalSystemException e)
		{
			return getStoredLoan(e);
		}
		catch (Exception e)
		{
			//�� ������ ������� � jsp
			return new MockLoan();
		}
	}

	private Loan getStoredLoan(Exception e)
	{
		AbstractStoredResource storedResource = StoredResourceHelper.findStoredResource(this, e);
		if (storedResource == null)
		{
			log.error("������ ��� ��������� ������� �" + getNumber(), e);
			return new MockLoan();
		}

		if (!getStoredResourceConfig().isExpired(ResourceTypeKey.LOAN_TYPE_KEY, storedResource.getEntityUpdateTime()))
		{
			return (Loan) storedResource;
		}
		else
		{
			// ���� ������ �������� ������������, ���������� ������
			StoredLoan result = (StoredLoan) storedResource;
			result.setLoanAmount(null);
			result.setBalanceAmount(null);
			result.setLastPaymentAmount(null);
			result.setNextPaymentAmount(null);
			result.setPastDueAmount(null);
			return result;
		}
	}

	public String toString()
    {
        return "������ �" + getNumber();
    }

	private static LoansService getLoansService()
    {
        return GateSingleton.getFactory().service(LoansService.class);
    }

	/**
	 * ��������� ���� ������� ��� �������
	 * @return PersonLoanRole
	 */
	public PersonLoanRole getPersonRole()
	{
		return personRole;
	}

	/**
	 * ������������� ���� ������� ��� �������� �������
	 * @param personRole - ���� �������
	 */
	public void setPersonRole(PersonLoanRole personRole)
	{
		this.personRole = personRole;
	}

	public ResourceType getResourceType()
	{
		return ResourceType.LOAN;
	}

	public String getPatternForFavouriteLink()
	{
		return "$$loanName:" + this.getId();
	}

	@Override
	public Class getStoredResourceType()
	{
		return StoredLoan.class;
	}

	/**
	 * @return ������� ������������� ������ ��������� � �������� �������
	 */
	public Boolean getClosedState()
	{
		return closedState;
	}

	/**
	 * ������ ������� ������������� ������ ��������� � �������� �������
	 * @param closedState - �������
	 */
	public void setClosedState(Boolean closedState)
	{
		this.closedState = closedState;
	}
}
