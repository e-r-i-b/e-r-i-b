package com.rssl.phizic.business.loans;

import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.LoanCardOfferClaim;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.loanclaim.type.LoanRate;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.business.loans.products.LoanProductBase;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 @author Pankin
 @ created 24.09.2010
 @ $Author$
 @ $Revision$
 */
public class LoansUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final LoanKindService loanKindService = new LoanKindService();
	private static final ModifiableLoanProductService loanProductService = new ModifiableLoanProductService();

    private static final String FIRST_PART = "������ �";
    private static final String LAST_PART = "�";

	/**
	 * ����������, ���� �� � ������� ������������ ������� � ������������������ �������� ���������
	 * ������� ��� ����������� ������� ������ "�������� ������" �� �������� ���������� �� ��������
	 * @return true - ����, false - ���
	 */
	public static boolean isOpenedDifLoan()
	{
		Boolean isHasOpenedDifLoan = PersonContext.getPersonDataProvider().getPersonData().isHasOpenedDifLoan();
		return  isHasOpenedDifLoan == null ? false : isHasOpenedDifLoan;
	}

	/**
	 * ����� ������� �� ������ �������� �����
	 * @param number - ����� �������� �����
	 * @return LoanLink
	 */
	public static LoanLink getLoanLink(String number)
	{
		if (StringHelper.isEmpty(number))
			return null;
		try
		{
			PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
			LoanLink link = personData.findLoan(number);
			if (link == null)
				log.warn("�� ������ LoanLink. ����� �������� �����=" + number);

			return link;
		}
		catch (Exception ex)
		{
			log.error("������ ������ �������", ex);
			return null;
		}
	}

/**
	 * ���� LoanProduct �� ��������������
	 * @param id - �������������
	 * @return
	 */
	public static ModifiableLoanProduct getLoanProductById(Long id)
	{
		try
		{
			return loanProductService.findById(id);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ���������� �������� �� id", e);
			return null;
		}
	}

	/**
	 * ����������� ��������� ������ �� ������ ���� LoanProductBase � ModifiableLoanProduct
	 * ����� ������������� ��� ���� jsp
	 * @param product -  ��������� �������
	 * @return ��� �� ��������� �������, �� ����������� � ������� ����
	 */
	public static ModifiableLoanProduct toModifiableLoanProduct(LoanProductBase product)
	{
		if (product instanceof  ModifiableLoanProduct)
			return (ModifiableLoanProduct) product;

		return null;
	}

	/**
	 * �������� ������ ���� ����� ��������
	 * @return ������ ���� ����� ��������
	 */
	public static List<LoanKind> getAllLoanKinds()
	{
		try
		{
			return loanKindService.getAll();
		}
		catch (BusinessException e)
		{
			log.error("������ ��������� ������ ���� ����� ��������", e);
			return new ArrayList<LoanKind>();
		}
	}

	/**
	 * �������� ���������� ���� ����� ��������
	 * @return ���������� ���� ����� ��������
	 */
	public static int getCountAllLoanKinds()
	{
		List<LoanKind> loanKinds = getAllLoanKinds();
		if (loanKinds == null)
			return 0;
		return loanKinds.size();
	}

	/**
	 * �������� ������ ���� ��������� ���������
	 * @return ������ ���� ��������� ���������
	 */
	public static List<ModifiableLoanProduct> getAllLoanProducts()
	{
		try
		{
			EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
			return loanProductService.getAllProducts(employeeData.isAllTbAccess(),employeeData.getEmployee().getLogin().getId());
		}
		catch (BusinessException e)
		{
			log.error("������ ��������� ������ ���� ��������� ���������", e);
			return new ArrayList<ModifiableLoanProduct>();
		}
	}

	/**
	 * ���������� ���� �� �������������� ��������� ��������� ��������
	 * @return true/false
	 */
	public static Boolean isPublishedProductsExists()
	{
		try
		{
			return loanProductService.isPublishedProductsExists();
		}
		catch (BusinessException e)
		{
			log.error("������ ����������� ������� ��������������  ��������� ���������", e);
			return false;
		}
	}

	/**
	 * @param offerIdAsString - ������������� �����������
	 * @return ��� �����������
	 */
	public static LoanCardOfferType getLoanCardOfferType(String offerIdAsString)
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		OfferId offerId = OfferId.fromString(offerIdAsString);
		try
		{
			LoanCardOffer offer = personData.findLoanCardOfferById(offerId);
			if (offer != null)
				return offer.getOfferType();

			log.error("����������� � id=" + offerIdAsString + " �� �������.");
			return null;
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� ���� �����������.", e);
			return null;
		}
	}

    /**
     * ��������� ���������� �� ��� �����������, �� �������� ���� ��������� ������ �� ����. �����
     * @param document ������ �� �������������� ����. �����
     * @return true - ����������� ��� ����������, false - ����������� ��� �������
     */
    public static boolean existCreditCardOffer(BusinessDocument document)
    {
        try
        {
            if (!(document instanceof LoanCardOfferClaim))
                return true;
            
            LoanCardOfferClaim claim = (LoanCardOfferClaim) document;
	        PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
	        if (StringHelper.isNotEmpty(claim.getLoan()))
	            return personData.existCreditCardOffer(Long.valueOf(claim.getLoan()));
	        else
		        return false;
        }
        catch (BusinessException e)
        {
            log.error("������ ��� �������� ������� ��� ������������ � ���� ������", e);
            return false;
        }
    }

	/**
	 * ���������� ������ �� ������� � ������� n% ��� n1 - n2%
	 * @param loanRate
	 * @return
	 */
	public static String formatLoanRate(LoanRate loanRate)
	{
		try
		{
			BigDecimal minLoanRate = loanRate.getMinLoanRate();
			BigDecimal maxLoanRate = loanRate.getMaxLoanRate();

			DecimalFormat df = new DecimalFormat("0.##");

			if (minLoanRate.compareTo(maxLoanRate) == 0)
				return df.format(minLoanRate.stripTrailingZeros()) + "%";

			return df.format(minLoanRate.stripTrailingZeros()) +  " - " + df.format(maxLoanRate.stripTrailingZeros()) + "%";
		} catch (Exception e)
		{
			log.error("������ ��� �������������� ���������� ������ �� �������.", e);
			return "";
		}
	}

	/**
	 * ���������� ������ �� ������� � ������� n%
	 * @param percentRate
	 * @return
	 */
	public static String formatPercentRate(BigDecimal percentRate)
	{
		try
		{
			DecimalFormat df = new DecimalFormat("0.##");
			return df.format(percentRate.stripTrailingZeros()) + "%";

		} catch (Exception e)
		{
			log.error("������ ��� �������������� ���������� ������ �� �������.", e);
			return "";
		}
	}

    /**
     * ���������� �������� �������� �������
     * @param name
     * @return
     */
    public static String buildFullLoanTitle(String name) {
        StringBuilder builder = new StringBuilder(FIRST_PART);
        builder.append(name);
        builder.append(LAST_PART);
        return builder.toString();
	}
}
