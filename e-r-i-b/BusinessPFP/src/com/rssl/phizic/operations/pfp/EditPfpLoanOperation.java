package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProductService;
import com.rssl.phizic.business.dictionaries.pfp.targets.Target;
import com.rssl.phizic.business.dictionaries.pfp.targets.TargetService;
import com.rssl.phizic.business.pfp.PersonLoan;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 22.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditPfpLoanOperation extends NotCheckStateEditPfpOperationBase
{
	private static final LoanKindProductService loanKindProductService = new LoanKindProductService();
	private static final TargetService targetService = new TargetService();
	private static final BigDecimal QUARTERS_IN_YEAR = BigDecimal.valueOf(4);

	private PersonLoan personLoan;
	private List<PersonTarget> personTargets;

	public void initialize(Long profileId, Long personLoanId) throws BusinessException, BusinessLogicException
	{
		initializeProfile(profileId);
		for(PersonLoan loan : personalFinanceProfile.getPersonLoans())
			if(loan.getId().equals(personLoanId))
				personLoan = loan;
		personTargets = personalFinanceProfile.getPersonTargets();
	}

	public void initializeNew(Long profileId) throws BusinessException, BusinessLogicException
	{
		initializeProfile(profileId);
		personLoan = new PersonLoan();
		personalFinanceProfile.addPersonLoan(personLoan);
		personTargets = personalFinanceProfile.getPersonTargets();
	}

	private void initializeProfile(Long profileId) throws BusinessException, BusinessLogicException
	{
		initializePerson();
		personalFinanceProfile = pfpService.getProfileById(profileId);

		if (personalFinanceProfile == null)
			throw new BusinessLogicException("Планирование с id=" + profileId + " не найдено.");

		if (!person.getLogin().getId().equals(personalFinanceProfile.getOwner().getId()))
			throw new AccessException("Клиент с id = " + person.getId() + " не имеет доступа к ПФП с id = " + personalFinanceProfile.getId());
	}

	public Calendar getPersonBirthDay()
	{
		return person.getBirthDay();
	}

	public PersonLoan getLoan()
	{
		return personLoan;
	}

	/**
	 * Сохранить изменения
	 * @throws BusinessException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		checkLoan();
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	private void checkLoan() throws BusinessException, BusinessLogicException
	{
		if(DateHelper.yearsDiff(getPersonBirthDay(), personLoan.getStartDate()) >= 60)
		{
			throw new BusinessLogicException("Дата оформления кредита должна быть меньше даты достижения возраста 60 лет.");
		}

		if(DateHelper.yearsDiff(getPersonBirthDay(), personLoan.getEndDate()) >= 65)
		{
			throw new BusinessLogicException("Дата последнего платежа по кредиту должна быть меньше даты, когда вам исполнится 65 лет.");
		}

		for(PersonTarget target : personTargets)
		{
			Target dTarget = targetService.getById(target.getDictionaryTarget(), null);
			if(dTarget.isLaterLoans() && target.getPlannedDate().compareTo(personLoan.getEndDate()) < 0)
				throw new BusinessLogicException("Вам необходимо осуществить все выплаты по кредиту до выхода на пенсию. Пожалуйста, уменьшите срок выбранного Вами кредитного продукта, чтобы окончание выплат по кредиту было до Вашего планируемого выхода на пенсию.");
		}

		PersonPortfolio portfolio = personalFinanceProfile.getPortfolioByType(PortfolioType.QUARTERLY_INVEST);
		BigDecimal quarterlyInvestAmount = portfolio.getProductSum().getDecimal();
		BigDecimal quarterlyInvestQuarterIncome = portfolio.getIncome().divide(QUARTERS_IN_YEAR).divide(BigDecimal.valueOf(100));
		Map<Integer, BigDecimal> loanQuarterPaymentList = createLoanQuarterPaymentMap();
		Map<Integer, BigDecimal> quarterlyInvestAmountList = new HashMap<Integer, BigDecimal>();
		quarterlyInvestAmountList.put(0, BigDecimal.ZERO);
        //Максимум из конца выплат по кредиту и даты достижения последней цели
        Calendar endOfInterval = null;
        if (personLoan.getEndDate().before(personalFinanceProfile.getLastTargetDate()))
            endOfInterval =(Calendar) personalFinanceProfile.getLastTargetDate().clone();
        else
            endOfInterval = (Calendar) personLoan.getEndDate().clone();
		int quarterCount = DateHelper.getDiffInQuarters(personalFinanceProfile.getStartPlaningDate(), endOfInterval);

		for(int i=1; i <= quarterCount; i++)
        {
	        BigDecimal quarterlyInvestPrevQuarterAmount = quarterlyInvestAmountList.get(i-1);
            BigDecimal quarterlyInvestQuarterAmount = quarterlyInvestAmount.add(quarterlyInvestPrevQuarterAmount.multiply(quarterlyInvestQuarterIncome.add(BigDecimal.ONE)));

	        BigDecimal loanQuarterPayment = loanQuarterPaymentList.get(i) != null ? loanQuarterPaymentList.get(i) :  BigDecimal.ZERO;

	        if(quarterlyInvestAmount.compareTo(loanQuarterPayment) < 0)
		        throw new BusinessLogicException("Вы не можете воспользоваться кредитом на указанную сумму, так как у Вас не хватит средств для оплаты квартального платежа.");
	        quarterlyInvestAmountList.put(i, quarterlyInvestQuarterAmount.subtract(loanQuarterPayment));
        }
	}

	private Map<Integer, BigDecimal> createLoanQuarterPaymentMap()
	{
		List<PersonLoan> loans = personalFinanceProfile.getPersonLoans();
		Map<Integer, BigDecimal> loanQuarterPaymentList = new HashMap<Integer, BigDecimal>();
		Calendar startDate = personalFinanceProfile.getStartPlaningDate();
		for(PersonLoan loan : loans)
        {
	        Calendar quarter = DateHelper.getNextQuarter(loan.getStartDate());
	        Calendar endQuarter = DateHelper.getNextQuarter(loan.getEndDate());
	        while(quarter.before(endQuarter))
            {
	            Integer quarters = DateHelper.getDiffInQuarters(startDate, quarter);
	            BigDecimal loanQuarterPayment = loanQuarterPaymentList.get(quarters);
	            if(loanQuarterPayment != null)
		            loanQuarterPaymentList.put(quarters, loanQuarterPayment.add(PfpMathUtils.calcQuarterPayment(loan)));
	            else
		            loanQuarterPaymentList.put(quarters, PfpMathUtils.calcQuarterPayment(loan));
	            quarter = DateHelper.getNextQuarter(quarter);
            }
        }
		return loanQuarterPaymentList;
	}


	/**
	 * Удалить кредит
	 * @throws BusinessException
	 */
	public void removeLoan() throws BusinessException
	{
		List<PersonLoan> personLonList = personalFinanceProfile.getPersonLoans();
		if(CollectionUtils.isNotEmpty(personLonList))
			personLonList.remove(personLoan);
		pfpService.addOrUpdateProfile(personalFinanceProfile);
	}

	/**
	 * @return Справочник типов кредитов
	 * @throws BusinessException
	 */
	public List<LoanKindProduct> getLoanKindDictionary() throws BusinessException
	{
		return loanKindProductService.getAll();
	}

}
