package com.rssl.phizic.business.pfp;

import com.rssl.common.forms.state.StateMachineInfo;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.state.Type;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.personData.ApartmentCount;
import com.rssl.phizic.business.dictionaries.pfp.personData.ChildCount;
import com.rssl.phizic.business.dictionaries.pfp.personData.LoanCount;
import com.rssl.phizic.business.dictionaries.pfp.personData.MaritalStatus;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.risk.profile.PersonRiskProfile;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.State;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 01.03.2012
 * @ $Author$
 * @ $Revision$
 */

/*������ ����������� ������������� ����������� ������������*/
public class PersonalFinanceProfile implements Serializable, StateObject
{
	private static final String COMPLETE_STATE_CODE = "COMPLITE";
	private static final String OLD_COMPLETE_STATE_CODE = "COMPLITE_OLD";

	private Long id;
	private State state;
	private Login owner;
	private Calendar creationDate;
	private Calendar executionDate;// ���� ��������� ���������� ���
	private Calendar startPlaningDate;// ���� ������ ������������
	private Calendar planDate;//���� �� ������� ��������� �������� �����

	private MaritalStatus maritalStatus; //�������� ���������
	private ChildCount childCount;       //���������� �����
	private List<PersonTarget> personTargets; //���� �������
	private List<PersonLoan> personLoans;//������� �������
	private Money planMoney;            //�����, ������� ��������� ��������

	//������ �������
	private Money shortTermAssetsSBRF; //����� ������������� ������� � ���������
	private Money shortTermAssetsOtherBanks; //����� ������������� ������� � ������ ������
	private Money shortTermAssetsCash; //����� ������������� �������: ��������
	private Money mediumTermAssetsFunds; //����� ������������� �������: ���
	private Money mediumTermAssetsIMA; //����� ������������� �������: ���
	private Money mediumTermAssetsOther; //����� ������������� �������: ������ �������������� ��������
	private ApartmentCount apartmentCount; //���������� �������� ������������ � �������������

	//������� �������(�������� �������������)
	private LoanCount mortgageCount;      // �������
	private LoanCount consumerLoanCount;  // ��������������� ������
	private LoanCount autoLoanCount;      // ����������
	private LoanCount creditCardCount;    // ��������� �����
	private String creditCardType;

	//������ �������
	private Money incomeMoney;      // ����������� �����
	private Money outcomeMoney;     // ����������� �������

	private Integer riskProfileWeight;  // ���������� ������, ��������� �������� ��� ����������� ���� �������.
	private RiskProfile defaultRiskProfile;    // ���� ������� �� ���������
	private PersonRiskProfile personRiskProfile; // ���� ������� �������
	private Map<Long, Long> questions;  // ���� �������� ����������� ����-������� (<id �������, id ���������� ������>)

	private List<PersonPortfolio> portfolioList; //������ ��������� �������

	//������ �� ����������, ������������ ���
	private String managerId;// ������������� ���������� ������������ ���
	private String employeeFIO;// ��� ���������� ������������ ���
	private String managerOSB;// ��� ���������
	private String managerVSP;// ��� ���������
	private Long cardId;// ������������� ��������� �����
	private Long channelId;// ������������� ������

	private boolean useAccount = true;//�������� ������ �����: ����� �����
	private BigDecimal accountValue;//�������� ������ �����: �������� ��������� �� ������
	private boolean useThanks = true;//�������� ������ �����: ����� �������
	private BigDecimal thanksValue;//�������� ��������� �� �������

	public PersonalFinanceProfile()
	{
	}

	public PersonalFinanceProfile(Login owner)
	{
		this.owner = owner;
		state = new State("INITIAL","��������� ��������� ����������� ������������");
		creationDate = Calendar.getInstance();
	}

	public Long getId()
	{
		return id;
	}

	public StateMachineInfo getStateMachineInfo()
	{
		return new StateMachineInfo(getStateMachineName(), Type.PFP_DOCUMENT);
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Login getOwner()
	{
		return owner;
	}

	public void setOwner(Login owner)
	{
		this.owner = owner;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public Calendar getExecutionDate()
	{
		return executionDate;
	}

	public void setExecutionDate(Calendar executionDate)
	{
		this.executionDate = executionDate;
	}

	public Calendar getStartPlaningDate()
	{
		return startPlaningDate;
	}

	public void setStartPlaningDate(Calendar startPlaningDate)
	{
		this.startPlaningDate = startPlaningDate;
	}

	public Calendar getPlanDate()
	{
		return planDate;
	}

	public void setPlanDate(Calendar planDate)
	{
		this.planDate = planDate;
	}

	public MaritalStatus getMaritalStatus()
	{
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus)
	{
		this.maritalStatus = maritalStatus;
	}

	public ChildCount getChildCount()
	{
		return childCount;
	}

	public void setChildCount(ChildCount childCount)
	{
		this.childCount = childCount;
	}

	public List<PersonTarget> getPersonTargets()
	{
		return personTargets;
	}

	public void setPersonTargets(List<PersonTarget> personTargets)
	{
		this.personTargets = personTargets;
	}

	/**
	 * �������� ���� � ������������
	 * @param target - ���� ��� ����������
	 */
	public void addPersonTarget(PersonTarget target)
	{
		if(personTargets == null)
			personTargets = new ArrayList<PersonTarget>();
		personTargets.add(target);
	}

	/**
	 * ������� ���� �� ������������
	 * @param target - ���� ��� ��������
	 */
	public void removePersonTarget(PersonTarget target)
	{
		if(CollectionUtils.isNotEmpty(personTargets))
			personTargets.remove(target);
	}

	public List<PersonLoan> getPersonLoans()
	{
		return personLoans;
	}	

	/**
	 * �������� ������ � ������������
	 * @param personLoan - ������ ��� ����������
	 */
	public void addPersonLoan(PersonLoan personLoan)
	{
		if(personLoans == null)
			personLoans = new ArrayList<PersonLoan>();
		personLoans.add(personLoan);
	}

	/**
	 * ������� ������ �� ������������
	 * @param loan - ������ ��� ��������
	 */
	public void removePersonLoan(PersonLoan loan)
	{
		if(CollectionUtils.isNotEmpty(personLoans))
			personLoans.remove(loan);
	}

	public Money getPlanMoney()
	{
		return planMoney;
	}

	public void setPlanMoney(Money planMoney)
	{
		this.planMoney = planMoney;
	}

	public Money getShortTermAssets() throws BusinessException
	{

		Money sum = new Money(BigDecimal.ZERO,MoneyUtil.getNationalCurrency());
		if (shortTermAssetsSBRF != null)
		{
			sum = sum.add(shortTermAssetsSBRF);
		}
		if (shortTermAssetsOtherBanks != null)
		{
			sum = sum.add(shortTermAssetsOtherBanks);
		}
		if (shortTermAssetsCash!=null)
		{
			sum = sum.add(shortTermAssetsCash);
		}
		return sum;
	}

	public Money getMediumTermAssets() throws BusinessException
	{
		Money sum = new Money(BigDecimal.ZERO,MoneyUtil.getNationalCurrency());
		if (mediumTermAssetsFunds != null)
			sum = sum.add(mediumTermAssetsFunds);
		if (mediumTermAssetsIMA != null)
			sum = sum.add(mediumTermAssetsIMA);
		if (mediumTermAssetsOther != null)
			sum = sum.add(mediumTermAssetsOther);
		return sum;
	}

	public ApartmentCount getApartmentCount()
	{
		return apartmentCount;
	}

	public void setApartmentCount(ApartmentCount apartmentCount)
	{
		this.apartmentCount = apartmentCount;
	}

	public LoanCount getMortgageCount()
	{
		return mortgageCount;
	}

	public void setMortgageCount(LoanCount mortgageCount)
	{
		this.mortgageCount = mortgageCount;
	}

	public LoanCount getConsumerLoanCount()
	{
		return consumerLoanCount;
	}

	public void setConsumerLoanCount(LoanCount consumerLoanCount)
	{
		this.consumerLoanCount = consumerLoanCount;
	}

	public LoanCount getAutoLoanCount()
	{
		return autoLoanCount;
	}

	public void setAutoLoanCount(LoanCount autoLoanCount)
	{
		this.autoLoanCount = autoLoanCount;
	}

	public LoanCount getCreditCardCount()
	{
		return creditCardCount;
	}

	public void setCreditCardCount(LoanCount creditCardCount)
	{
		this.creditCardCount = creditCardCount;
	}

	/**
	 * @return ������� ��������� ����� � ���������
	 */
	public String getCreditCardType()
	{
		return creditCardType;
	}

	/**
	 * ������ ������� ��������� ����� � ���������
	 * @param creditCardType ������� ��������� ����� � ���������
	 */
	public void setCreditCardType(String creditCardType)
	{
		this.creditCardType = creditCardType;
	}

	public Money getIncomeMoney()
	{
		return incomeMoney;
	}

	public void setIncomeMoney(Money incomeMoney)
	{
		this.incomeMoney = incomeMoney;
	}

	public Money getOutcomeMoney()
	{
		return outcomeMoney;
	}

	public void setOutcomeMoney(Money outcomeMoney)
	{
		this.outcomeMoney = outcomeMoney;
	}

	/**
	 * @return ����������� ��������� �������� �������
	 */
	public Money getFreeMoney()
	{
		return incomeMoney.sub(outcomeMoney);
	}

	public Integer getRiskProfileWeight()
	{
		return riskProfileWeight;
	}

	public void setRiskProfileWeight(Integer riskProfileWeight)
	{
		this.riskProfileWeight = riskProfileWeight;
	}

	public RiskProfile getDefaultRiskProfile()
	{
		return defaultRiskProfile;
	}

	public void setDefaultRiskProfile(RiskProfile defaultRiskProfile)
	{
		this.defaultRiskProfile = defaultRiskProfile;
	}

	/**
	 * @return ���� ������� ������������� ��������
	 */
	public PersonRiskProfile getPersonRiskProfile()
	{
		return personRiskProfile;
	}

	/**
	 * @param personRiskProfile ���� ������� ������������� ��������
	 */
	public void setPersonRiskProfile(PersonRiskProfile personRiskProfile)
	{
		this.personRiskProfile = personRiskProfile;
	}

	public Map<Long, Long> getQuestions()
	{
		return questions;
	}

	public void setQuestions(Map<Long, Long> questions)
	{
		this.questions = questions;
	}

	public List<PersonPortfolio> getPortfolioList()
	{
		return portfolioList;
	}

	public void setPortfolioList(List<PersonPortfolio> portfolioList)
	{
		this.portfolioList = portfolioList;
	}

	public void clearPortfolioList()
	{
		if(!CollectionUtils.isEmpty(portfolioList))
			portfolioList.clear();
	}

	public void addPortfolio(PersonPortfolio portfolio)
	{
		if(CollectionUtils.isEmpty(portfolioList))
			portfolioList = new ArrayList<PersonPortfolio>();
		portfolioList.add(portfolio);
	}

	public String getManagerId()
	{
		return managerId;
	}

	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}

	public String getEmployeeFIO()
	{
		return employeeFIO;
	}

	public void setEmployeeFIO(String employeeFIO)
	{
		this.employeeFIO = employeeFIO;
	}

	/**
	 * @return ��� ���������
	 */
	public String getManagerOSB()
	{
		return managerOSB;
	}

	/**
	 * ������ ��� ���������
	 * @param managerOSB ��� ���������
	 */
	public void setManagerOSB(String managerOSB)
	{
		this.managerOSB = managerOSB;
	}

	/**
	 * @return ��� ���������
	 */
	public String getManagerVSP()
	{
		return managerVSP;
	}

	/**
	 * ������ ��� ���������
	 * @param managerVSP ��� ���������
	 */
	public void setManagerVSP(String managerVSP)
	{
		this.managerVSP = managerVSP;
	}

	public String getStateMachineName()
	{
		return "PfpStateMachine";
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	/**
	 * @return ������������� ��������� ����� ��������� ��������
	 */
	public Long getCardId()
	{
		return cardId;
	}

	/**
	 * @return ������������� ������
	 */
	public Long getChannelId()
	{
		return channelId;
	}

	/**
	 * @param channelId ������������� ������
	 */
	public void setChannelId(Long channelId)
	{
		this.channelId = channelId;
	}

	/**
	 * @param cardId ������������� ��������� ����� ��������� ��������
	 */
	public void setCardId(Long cardId)
	{
		this.cardId = cardId;
	}

	public void setSystemName(String name)
	{
		//������ �� ������, ��� ��� ��� ������� ����������� ��� ������ ��� �� �����
	}

	/**
	 * @param type - ��� ��������
	 * @return ���������� �������� ������� �� ����, ���� � ������� ��� �������� ��������� ����, �� null
	 */
	public PersonPortfolio getPortfolioByType(PortfolioType type)
	{
		for (PersonPortfolio portfolio : portfolioList)
		{
			if (portfolio.getType() == type)
				return portfolio;
		}
		return null;
	}

	/**
	 * @return ��������� �� ������������.
	 */
	public boolean isCompleted()
	{
		String code = state.getCode();
		return code.equals(COMPLETE_STATE_CODE) || code.equals(OLD_COMPLETE_STATE_CODE);
	}

	/**
	 * @return ���� ���������� ��������� ����
	 */
	public Calendar getLastTargetDate()
	{
		Calendar lastTargetDate = null;
		for(PersonTarget personTarget : personTargets)
			if(lastTargetDate == null || personTarget.getPlannedDate().after(lastTargetDate))
				lastTargetDate = personTarget.getPlannedDate();
		return lastTargetDate;
	}

	/**
	 * @return ���� ���������� ���������� �������
	 */
	public Calendar getLastLoanDate()
	{
		Calendar lastLoanDate = null;
		for(PersonLoan personLoan : personLoans)
			if(lastLoanDate == null || personLoan.getEndDate().after(lastLoanDate))
				lastLoanDate = personLoan.getEndDate();
		return lastLoanDate;
	}

	public Money getShortTermAssetsSBRF()
	{
		return shortTermAssetsSBRF;
	}

	public void setShortTermAssetsSBRF(Money shortTermAssetsSBRF)
	{
		this.shortTermAssetsSBRF = shortTermAssetsSBRF;
	}

	public Money getShortTermAssetsOtherBanks()
	{
		return shortTermAssetsOtherBanks;
	}

	public void setShortTermAssetsOtherBanks(Money shortTermAssetsOtherBanks)
	{
		this.shortTermAssetsOtherBanks = shortTermAssetsOtherBanks;
	}

	public Money getMediumTermAssetsOther()
	{
		return mediumTermAssetsOther;
	}

	public void setMediumTermAssetsOther(Money mediumTermAssetsOther)
	{
		this.mediumTermAssetsOther = mediumTermAssetsOther;
	}

	public Money getShortTermAssetsCash()
	{
		return shortTermAssetsCash;
	}

	public void setShortTermAssetsCash(Money shortTermAssetsCash)
	{
		this.shortTermAssetsCash = shortTermAssetsCash;
	}

	public Money getMediumTermAssetsFunds()
	{
		return mediumTermAssetsFunds;
	}

	public void setMediumTermAssetsFunds(Money mediumTermAssetsFunds)
	{
		this.mediumTermAssetsFunds = mediumTermAssetsFunds;
	}

	public Money getMediumTermAssetsIMA()
	{
		return mediumTermAssetsIMA;
	}

	public void setMediumTermAssetsIMA(Money mediumTermAssetsIMA)
	{
		this.mediumTermAssetsIMA = mediumTermAssetsIMA;
	}

	public boolean getUseAccount()
	{
		return useAccount;
	}

	public void setUseAccount(boolean account)
	{
		this.useAccount = account;
	}

	public BigDecimal getAccountValue()
	{
		return accountValue;
	}

	public void setAccountValue(BigDecimal accountValue)
	{
		this.accountValue = accountValue;
	}

	public boolean getUseThanks()
	{
		return useThanks;
	}

	public void setUseThanks(boolean thanks)
	{
		this.useThanks = thanks;
	}

	public BigDecimal getThanksValue()
	{
		return thanksValue;
	}

	public void setThanksValue(BigDecimal thanksValue)
	{
		this.thanksValue = thanksValue;
	}

	public String getNextState()
	{
		throw new UnsupportedOperationException();
	}
}
