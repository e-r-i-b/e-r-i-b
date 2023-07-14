package com.rssl.phizic.business.loanclaim;

import com.google.gson.Gson;
import com.rssl.common.forms.FormConstants;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.etsm.offer.service.OfferEribService;
import com.rssl.phizic.business.loanOffer.LoanOfferHelper;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductCondition;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CreditProductConditionService;
import com.rssl.phizic.business.loanclaim.creditProduct.condition.CurrencyCreditProductCondition;
import com.rssl.phizic.business.loanclaim.dictionary.LoanClaimDictionaryService;
import com.rssl.phizic.business.loans.products.YearMonth;
import com.rssl.phizic.business.login.GuestHelper;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.earlyloanrepayment.EarlyLoanRepaymentConfig;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.loanclaim.dictionary.LoanIssueMethod;
import com.rssl.phizic.gate.loans.EarlyRepayment;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.json.NoPrettyPrintingGsonSingleton;
import org.hibernate.Query;
import org.hibernate.Session;
import sun.util.calendar.CalendarUtils;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Balovtsev
 * @since 17.03.14
 */
public class LoanClaimHelper
{
	public  static final int MONTH_IN_YEAR = 12;
	public static final String LAST_CLAIM_TIME_USER_PROPERTY_KEY = "com.rssl.phizic.userSettings.earlyRepayment.lastClaimTime";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final CreditProductConditionService conditionService = new CreditProductConditionService();
	private static final BusinessDocumentService       documentService  = new BusinessDocumentService();
	private static final LoanClaimDictionaryService loanClaimDictionaryService = new LoanClaimDictionaryService();
	private static final OfferEribService offerEribService = new OfferEribService();

	private static String ERROR_PERIOD = "�� ����������� ������� ���� �������. ����������, ������� ����, ������� ����� ��������������� �������� ������ �������";
	private static String ERROR_AMOUNT = "�� ����������� ������� ����� �������. ����������, ������� �����, ������� ����� ��������������� �������� ������ �������";

	public final static Comparator<com.rssl.phizic.business.offers.LoanOffer> LOAN_OFFER_COMPARATOR = new Comparator<com.rssl.phizic.business.offers.LoanOffer>()
	{
		public int compare(com.rssl.phizic.business.offers.LoanOffer o1, com.rssl.phizic.business.offers.LoanOffer o2)
		{
			Money  o1MaxLimit = o1.getMaxLimit();
			Money  o2MaxLimit = o2.getMaxLimit();
			String o1currCode = o1MaxLimit.getCurrency().getCode();
			String o2currCode = o2MaxLimit.getCurrency().getCode();

			if (!o1currCode.equals(o2currCode))
			{
				if ("RUB".equals(o1currCode))
				{
					return -1;
				}

				if ("RUB".equals(o2currCode))
				{
					return 1;
				}

				if ("EUR".equals(o1currCode))
				{
					return 1;
				}

				if ("EUR".equals(o2currCode))
				{
					return -1;
				}
			}
			int compareRes = o2MaxLimit.getDecimal().compareTo(o1MaxLimit.getDecimal());
			//��� �� ��� ������ ����� ������ �� ���������� �� ������ loanOffers
			if (compareRes == 0)
			{
				return -1;
			}

			return compareRes;
		}
	};

	/**
	 *
	 * ������ �������.
	 *
	 * @param conditionId ������������� ���������� �������. ���� �� ����������� ������������ ������� ��� ��������������� �����������.
	 * @return List&lt;String&gt;
	 */
	public static List<String> getLoanClaimTerms(final Long conditionId)
	{
		if (conditionId == null || conditionId == 0)
		{
			return LoanOfferHelper.getProfitPreapprovedCredit();
		}
		else
		{
			List<String> terms = new ArrayList<String>();

			try
			{
				CreditProductCondition condition = conditionService.findeById(conditionId);
				if (condition.getCreditProduct().isEnsuring())
				{
					terms.add("��������� �����������");
				}
				else
				{
					terms.add("����������� �� ���������");
				}

				StringBuilder buffer = new StringBuilder();

				Integer minDuration = condition.getMinDuration().getDuration();
				if (minDuration > 0)
				{
					buffer.append("�� ");

					Integer minYear  = minDuration / MONTH_IN_YEAR;
					Integer minMonth = minDuration % MONTH_IN_YEAR;

					if (minYear > 0)
					{
						buffer.append(minYear);
						buffer.append(minYear == 1 ? " ����" : " ���");
					}

					if (minMonth > 0)
					{
						buffer.append(" ");
						buffer.append(minMonth);
						buffer.append(minMonth == 1 ? " ������" : " �������");
					}
				}

				Integer maxDuration = condition.getMaxDuration().getDuration() - (condition.isMaxRangeInclude() ? 0 : 1);
				if (maxDuration > 0)
				{
					buffer.append(" �� ");

					Integer maxYear  = maxDuration / MONTH_IN_YEAR;
					Integer maxMonth = maxDuration % MONTH_IN_YEAR;

					if (maxYear > 0)
					{
						buffer.append(maxYear);
						buffer.append(maxYear == 1 ? " ����" : " ���");
					}

					if (maxMonth > 0)
					{
						if (maxYear > 0)
						{
							buffer.append(" � ");
						}

						buffer.append(maxMonth);
						buffer.append(maxMonth == 1 ? " ������" : " �������");
					}
				}

				terms.add(buffer.toString());

				if (StringHelper.isNotEmpty( condition.getAdditionalConditions() ))
				{
					terms.add(condition.getAdditionalConditions());
				}
			}
			catch (BusinessException e)
			{
				log.error("������ ��� ������������ ������ ������� �� �������", e);
			}

			return terms;
		}
	}

	/**
	 * @param condition ������� �� ���������� ��������
	 * @return ���� �� �������� ������� �� �������
	 */
	public static boolean hasActiveCurrencyCondition(CreditProductCondition condition)
	{
		List<CurrencyCreditProductCondition> allActiveCurrCondition = conditionService.getClientAvailableCurrConditions(condition);
	   	if (allActiveCurrCondition != null && !allActiveCurrCondition.isEmpty())
		    return true;
		return false;
	}

	/**
	 * �������� �� ��������� �������
	 * @param conditionId ���������� ������������� �������
	 * @param currencyId ���������� ������������� ������ �������
	 * @param jsonOldCondition ������� � ��������������� ����
	 * @param jsonOldCurrency ������ ������� � ��������������� ����
	 * @return true - ����������, false - ���
	 */
	public static boolean hasConditionChange(Long conditionId, Long currencyId, String jsonOldCondition, String jsonOldCurrency) throws BusinessException
	{
		CreditProductCondition condition = conditionService.findeById(conditionId);
		if (condition == null)
			return true;
		if (!StringHelper.equals(getJsonLoanField(condition), jsonOldCondition))
			return true;
		CurrencyCreditProductCondition currency = findCurrency(condition, currencyId);
		if (currency == null)
			return true;
		if (!StringHelper.equals(getJsonLoanField(currency), jsonOldCurrency))
			return true;
		return false;
	}

	private static CurrencyCreditProductCondition findCurrency(CreditProductCondition condition, Long currencyId)
	{
		CurrencyCreditProductCondition temp = null;
		for (CurrencyCreditProductCondition currency : condition.getCurrConditions())
			if (currency.getId().equals(currencyId))
			{
				temp = currency;
				break;
			}
		if (temp == null)
			return null;
		return conditionService.getActiveCurrCondition(condition, temp.getCurrency().getCode());
	}

	/**
	 * @param object �������� ��� ����������� � json
	 * @return ��������������� ��������
	 */
	public static String getJsonLoanField(Object object)
	{
		Gson gson = NoPrettyPrintingGsonSingleton.getGson();
		return gson.toJson(object);
	}

	/**
	 *
	 * @param loanOfferId Id ��������������� �����������
	 * @param loanOfferAmount ����� ��������� ��������
	 * @param loanPeriod ������ � �������
	 * @return ������ ��������� ���� null
	 */
	public static String validateLoanOffer(OfferId loanOfferId, BigDecimal loanOfferAmount, Integer loanPeriod) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		LoanOffer loanOffer = personData.findLoanOfferById(loanOfferId);
		if (loanOffer == null)
			throw new BusinessException("�� ������� ����� �������������� ��������� ������� � id = " + loanOfferId);

		BigDecimal maxLimit = loanOffer.getMaxLimit().getDecimal();
		if (loanOfferAmount.compareTo(maxLimit) == 1)
			return ERROR_AMOUNT;

		if (loanPeriod > loanOffer.getDuration())
			return  ERROR_PERIOD;

		return null;
	}

	/**
	 *
	 * @param loanPeriod ������ � �������
	 * @param condCurrId Id �� ��������� �������
	 * @param condId Id ���������� �������
	 * @param loanAmount ����� ��������� ��������
	 * @return ������ ��������� ���� null
	 * @throws BusinessException
	 */
	public static String validateCreditProductCondition(Integer loanPeriod,Long condCurrId,Long condId, BigDecimal loanAmount) throws BusinessException
	{
		CreditProductConditionService creditConditionService = new CreditProductConditionService();

		CreditProductCondition creditProductCondition = creditConditionService.findeById(condId);
		if (creditProductCondition == null)
			throw new BusinessException("�� ������� ����� ��������� ������� � id = " + condId);

		if (!creditProductCondition.isPublished())
			throw new BusinessException("��������� ������� � id = " + condId + " �� �������� �������.");


		CurrencyCreditProductCondition currencyCreditProductCondition = creditConditionService.findCurrCondById(condCurrId);
		if (currencyCreditProductCondition == null)
			throw new BusinessException("�� ������� ����� ���������� ��������� ������� � id = " + condCurrId);

		YearMonth minDuration = creditProductCondition.getMinDuration();
		YearMonth maxDuration = creditProductCondition.getMaxDuration();
		boolean maxDurationInclude = creditProductCondition.isMaxRangeInclude();

		if (minDuration != null && loanPeriod < minDuration.getDuration())
			return ERROR_PERIOD;

		if( (maxDuration != null && maxDurationInclude && loanPeriod > maxDuration.getDuration())||
				(maxDuration != null && !maxDurationInclude && loanPeriod >= maxDuration.getDuration()))
			return ERROR_PERIOD;

		Money minLimit = currencyCreditProductCondition.getMinLimitAmount();
		Money maxLimit = currencyCreditProductCondition.getMaxLimitAmount();
		Boolean maxLimitInclude = currencyCreditProductCondition.isMaxLimitInclude();
		Boolean maxLimitPercentUse = currencyCreditProductCondition.isMaxLimitPercentUse();

		//���� �������� ����� ��
		if ((minLimit != null) && loanAmount.compareTo(minLimit.getDecimal()) == -1)
			return ERROR_AMOUNT;

		//���� �������� ����� ��, � ������� ���� ��� ������������ ������� �� ��������� ������� �� ������
		if (maxLimit != null && !maxLimitPercentUse)
		{
			int res = loanAmount.compareTo(maxLimit.getDecimal());
			//���� ������ ������������ , ��� ����� �� �� �� ������������ ������� ������� �����.
			if (res == 1 || res == 0 && !maxLimitInclude)
				return ERROR_AMOUNT;
		}

		return  null;
	}

	/**
	 *
	 * ��������� ����������� �������� ����������� ������ �� ������
	 *
	 * @return true - �������� ������ ���������, � ��������� ������ false
	 * @throws BusinessException
	 */
	public static Boolean isExtendedLoanClaimAvailable() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return documentService.isExtendedLoanClaimAvailable(personData.getLogin(), personData.getPerson());
	}

	/**
	 * ��������� ����������� ����������� ��������������� ����������� �� �������
	 * @return true - ����������� ���������, ����� - false
	 */
	public static Boolean isPreapprovedLoanClaimAvailable() throws BusinessException
	{
		if (!PersonContext.isAvailable() || PersonHelper.isGuest())
			return false;
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		//���� ��� ������� ��� �����, �� ����������� ����� ����������
		return CollectionUtils.isEmpty(offerEribService.getOfferEribPrior(personData.getLogin().getId()));
	}

	/**
	 * ��������������� �� �������� ������, ����������� ������ �� ������, ��� �����
	 * @param paymentFormName - �������� ��������� �����
	 * @return true - ���������������, false - ���
	 */
	public static boolean isUnregisteredClientClaim(String paymentFormName)
	{
		return (FormConstants.EXTENDED_LOAN_CLAIM.equals(paymentFormName) || FormConstants.LOAN_CARD_CLAIM.equals(paymentFormName))
				&& PersonHelper.isGuest()
				&& !GuestHelper.hasGuestAccount();
	}

	/**
	 * ��������� ����������� ������ ��������� ������
	 * @param document ������ �� ������
	 * @return true - �������� �������� ������, false - ���
	 */
	public static boolean isLoanClaimNeedCall(BusinessDocumentBase document)
	{
		if (!(document instanceof ExtendedLoanClaim))
			return false;
		ExtendedLoanClaim claim = (ExtendedLoanClaim) document;
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		return ("INITIAL3".equals(claim.getState().getCode())
				|| "INITIAL4".equals(claim.getState().getCode())
				|| "INITIAL5".equals(claim.getState().getCode())
				|| "INITIAL6".equals(claim.getState().getCode())
				|| "INITIAL7".equals(claim.getState().getCode())
				|| "INITIAL8".equals(claim.getState().getCode())
				|| "SAVED".equals(claim.getState().getCode()))
			&& !claim.isInWaitTM()
			&& config.isCallAvailable();
	}

	/**
	 * @return true - � ������ �� ������ � ���� "�������������� � ���������" �������� (������������) ��� ������ �������� "������"
	 */
	public boolean isAvailableOtherSbrfRelationType()
	{
		return ConfigFactory.getConfig(LoanClaimConfig.class).isAvailableOtherSbrfRelationType();
	}

	/**
	 * �������� ������� �� ����������� �������� ������ �� ��������� ��������� � ������ ������
	 * @param loan ������
	 * @return ����������� ������� ������ �� ��������� ������� � ������ ������ � ������ ��� ������������ ������
	 */
	public static boolean isEarlyLoanRepaymentAvailable(Loan loan)
	{
		// ������� ���� && ������ ����������� && ��� ������ � ������� �������
		if(isEarlyLoanRepaymentPossible(loan))
		{
			if(loan.getEarlyRepayments() == null)
				return false;
			for(EarlyRepayment repayment : loan.getEarlyRepayments())
			{
				if(repayment.getStatus().equals("�������"))
				{
					return false;
				}
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * �������� ������� �� ����������� �������� ������ �� ��������� ���������
	 * @param loan ������
	 * @return ����������� ������� ������ �� ��������� ��������� ������� ��� ����� ��� ������������ ������
	 */
	public static boolean isEarlyLoanRepaymentPossible(Loan loan)
	{
		return ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).isAllowed() && loan.isBAKS() && loan.getIsAnnuity();
	}

	/**
	 * �������� ���������� ���������� ����� �������� �� ��������� ���������
	 * @param loan ������
	 * @return true ���� � ������ ������ ����� �������� ������ ��� ��������� �����������
	 */
	public static boolean isEarlyLoanRepaymentTimeLimitationFulfilled(Loan loan)
	{
		String lastClaimTimeString = PersonContext.getPersonDataProvider().getPersonData().getUserProperties().getProperty(LAST_CLAIM_TIME_USER_PROPERTY_KEY);
		if(lastClaimTimeString == null)
		{
			return true;
		}
		else
		{
			try
			{
				Calendar lastClaimTime = DateHelper.toCalendar(SimpleDateFormat.getInstance().parse(lastClaimTimeString));
				lastClaimTime.add(Calendar.HOUR_OF_DAY, ConfigFactory.getConfig(EarlyLoanRepaymentConfig.class).getTimeLimitation());
				return lastClaimTime.before(Calendar.getInstance());
			}
			catch (ParseException ignored)
			{
				return false;
			}
		}
	}

	/**
	 * ���������� ����� �������������� ������, ������� ����� ������� � ����� ������ �� ������
	 * @return
	 */
	public static int getNewAccountsCount()
	{
		return ConfigFactory.getConfig(LoanClaimConfig.class).getNewAccountsCount();
	}

	/**
	 * �������� �� ������������� �������� �������� �� ����� ���������� ������� ����� ����
	 * @return
	 */
	public static boolean isNeedConfirmDebitOperationERKC()
	{
		return ConfigFactory.getConfig(LoanClaimConfig.class).isNeedConfirmDebitOperationERKC();
	}

	/**
	 *  �������� �� ���������� �������� �������� �� ����� ���������� �������
	 */
	public static boolean isLockOperationDebit()
	{
		return ConfigFactory.getConfig(LoanClaimConfig.class).isLockOperationDebit();
	}

	/**
	 * ���������� �� ������, ���������� �� ������
	 * true, ���� �� ���� ���. ���������� �� ���� ������/������ ������ "����������/����������"
	 * @param extendedLoanClaim
	 * @return
	 */
	public static boolean isSalary(ExtendedLoanClaim extendedLoanClaim)
	{
		Collection<String> salaryCards = extendedLoanClaim.getApplicantSalaryCards();
		Collection<String> salaryAccounts= extendedLoanClaim.getApplicantSalaryDeposits();
		Collection<String> pensionCards = extendedLoanClaim.getApplicantPensionCards();
		Collection<String> pensionAccounts = extendedLoanClaim.getApplicantPensionDeposits();
		return (!salaryCards.isEmpty() || !salaryAccounts.isEmpty()) && pensionCards.isEmpty() && pensionAccounts.isEmpty();
	}

	/**
	 * ���������� �� ������, ��������� �� ������
	 * true, ���� �� ���� ���. ���������� ���� �� �� ������ �����/����� ������ �������� "����������"
	 * @param extendedLoanClaim
	 * @return
	 */
	public static boolean isPensioner(ExtendedLoanClaim extendedLoanClaim)
	{
		Collection<String> pensionCards = extendedLoanClaim.getApplicantPensionCards();
		Collection<String> pensionAccounts = extendedLoanClaim.getApplicantPensionDeposits();
		return !pensionCards.isEmpty() || !pensionAccounts.isEmpty();
	}

	/**
	 * ���������� �� ������, ��������� ��������� ������ ��� ���
	 * @param extendedLoanClaim
	 * @return
	 */
	public static boolean isSberEmployee(ExtendedLoanClaim extendedLoanClaim)
	{
		return extendedLoanClaim.getApplicantAsSBEmployee() != null;
	}

	public static String getWorkTimeInDepartment(Department department)
	{
		StringBuilder sb = new StringBuilder();
		if (department.getWeekOperationTimeBegin()!=null)
		{
			sb.append("��.-");
			if (department.getFridayOperationTimeBegin() != null && department.getFridayOperationTimeEnd()!=null
					&& !department.getFridayOperationTimeBegin().equals(department.getWeekOperationTimeBegin())
					&& !department.getFridayOperationTimeEnd().equals(department.getWeekOperationTimeEnd()))
			{
				sb.append("��. "+department.getWeekOperationTimeBegin()+"-"+department.getWeekOperationTimeEnd());
				sb.append("��. "+department.getFridayOperationTimeBegin() + "-" + department.getFridayOperationTimeEnd());
			} else {
				sb.append("��. "+department.getWeekOperationTimeBegin()+"-"+department.getWeekOperationTimeEnd());
			}
		}

		return sb.toString();
	}

	public static boolean isNewCardLoanIssueMetodAvailable()
	{
		try
		{
			List<LoanIssueMethod> loanIssueMethodOlds = loanClaimDictionaryService.getLoanIssueMethodAvailable();
			for (LoanIssueMethod loanIssueMethodOld : loanIssueMethodOlds)
			{
				if (loanIssueMethodOld.getProductForLoan().equals(LoanIssueMethod.LoanProductType.CARD) && loanIssueMethodOld.isNewProductForLoan())
					return true;
			}
			return false;
		}
		catch (BusinessException e)
		{
			log.error(e);
			return false;
		}
	}
	public static boolean isCurrentCardLoanIssueMetodAvailable()
	{
		try
		{
			List<LoanIssueMethod> loanIssueMethodOlds = loanClaimDictionaryService.getLoanIssueMethodAvailable();
			for (LoanIssueMethod loanIssueMethodOld : loanIssueMethodOlds)
			{
				if (loanIssueMethodOld.getProductForLoan().equals(LoanIssueMethod.LoanProductType.CARD) && !loanIssueMethodOld.isNewProductForLoan())
					return true;
			}
			return false;
		}
		catch (BusinessException e)
		{
			log.error(e);
			return false;
		}
	}
	public static boolean isNewAccountLoanIssueMetodAvailable()
	{
		try
		{
			List<LoanIssueMethod> loanIssueMethodOlds = loanClaimDictionaryService.getLoanIssueMethodAvailable();
			for (LoanIssueMethod loanIssueMethodOld : loanIssueMethodOlds)
			{
				if (loanIssueMethodOld.getProductForLoan().equals(LoanIssueMethod.LoanProductType.CURRENT_ACCOUNT) && loanIssueMethodOld.isNewProductForLoan())
					return true;
			}
			return false;
		}
		catch (BusinessException e)
		{
			log.error(e);
			return false;
		}
	}
	public static boolean isCurrentAccountLoanIssueMetodAvailable()
	{
		try
		{
			List<LoanIssueMethod> loanIssueMethodOlds = loanClaimDictionaryService.getLoanIssueMethodAvailable();
			for (LoanIssueMethod loanIssueMethodOld : loanIssueMethodOlds)
			{
				if (loanIssueMethodOld.getProductForLoan().equals(LoanIssueMethod.LoanProductType.CURRENT_ACCOUNT) && !loanIssueMethodOld.isNewProductForLoan())
					return true;
			}
			return false;
		}
		catch (BusinessException e)
		{
			log.error(e);
			return false;
		}
	}
}
