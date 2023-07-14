package com.rssl.phizic.config.loanclaim;

import com.rssl.phizic.config.BeanConfigBase;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Moshenko
 * @ created 24.12.2013
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ���������� ��������
 */
public class LoanClaimConfig extends BeanConfigBase<LoanClaimConfigBean>
{
	private static final String CODENAME = "LoanClaimConfig";

	private static final String ESB_CREDIT_BACK_QUEUE_NAME = "jms/etsm/LoanClaimStatusQueue";

	private static final String ESB_CREDIT_BACK_QCF_NAME = "jms/etsm/LoanClaimStatusQCF";

	private static final int EXTENDED_LOAN_CLAIM_LIFETIME_DAYS = 60;

	/**
	 * ������������ �� ����� ������ ���������
	 */
	private boolean useNewAlgorithm;
	/**
	 * ���� � �������� �������� ������� �� �������� �������������� ��������� ����.
	 */
	private String unloadCardOfferReportPath;

	/**
	 * ������, � �������, �� ������� ����� ������������ ����������� ������ �� ������ � ����� "������� ������""
	 */
	private int periodRequestLoanStatus;

	/**
	 * ���������� "���������� �� ��������� ������������ ������"
	 */
	private List<AgreeOnProcessPersonData> agreesOnProcessPersonData;

    /**
     * ����� ������������ � ����� �������� ��������������� ���������� �����������
     */
    private List<String> profitPreapprovedCredit;

    /**
     *
     */
    private AgreeOnProcessPersonData latestLoanOffersTerm;

	private ETSMMockConfig etsmMockConfig;

	/**
	 * ����������� � csv-������, ��� �������� ������������ ��������� ����������
	 */
	private String loanClaimDictionaryCSVDelimiter;

	/**
	 * ����������� ���� � csv-������ (������ ����� ������)
	 */
	private String dictionarySearchWordSeparators;

	/**
	 * ��+���+���, � ������� ��������� �������� ������� ��� ������ �� ������
	 */
	private String guestLoanDepartmentTb;
	private String guestLoanDepartmentOsb;
	private String guestLoanDepartmentVsp;

	/**
	 * ������������ ������ � ������
	 */
	private boolean useQuestionnaire;

	/**
	 * �������� ����� ������, �� ������� ��������� ��������� ��������� ������
	 */
	private List<String> allowedRefusalCodes;

	/**
	 * ���� ������� ������� ��������� ������
	 */
	private int creationRestrictedDays;

	/**
	 * �������� ����� ��������� ������ � ��������� ������
	 */
	private boolean callAvailable;

	/**
	 * ������������ ���� �������� ������ ��
	 */
	private int waitTmResponseDays;

	/**
	 * ������������ ���� � ������� ��������
	 */
	private int historyAvailableDays;

	/**
	 * ����� ��������� ������ ������
	 */
	private int lengthShortNumberClaim;

	/**
	 * ������������ ���� � ����, �� ������� ��������� ������ �� ������ ��� ������������� � ��� ������ ��������� ������
	 */
	private int confirmAvailableDays;

	/**
	 * �������� �� �������� �������� ��������� ������ � CRM
	 */
	private boolean sendUpdStatusAvailable;
	/**
	 * ������� "������������ ������ xsd ������ 16"
	 */
	private boolean useXSDRelease16Version;
	/**
	 * ������ "��������� �������� ���� �� ����� ���������".
	 */
	private String detailedDescriptionOfCardsLink;
	/**
	 * �������� ������� ��� ����� � �������
	 */
	private boolean getCreditAtLogon;
	/**
	 * �������� ������������� �������� �������� �� ����� ���������� ������� ����� ����
	 */
	private boolean needConfirmDebitOperationERKC;
	/**
	 *  ����������� ����� �������� �� ����� ���������� ������� ��� ������������� � ���� � ������
	 */
	private BigDecimal minRUBSumDebitOperationERKC;
	/**
	 *  ����������� ����� �������� �� ����� ���������� ������� ��� ������������� � ���� � ��������
	 */
	private BigDecimal minUSDSumDebitOperationERKC;
	/**
	 *  ����������� ����� �������� �� ����� ���������� ������� ��� ������������� � ���� � ����
	 */
	private BigDecimal minEURSumDebitOperationERKC;
	/**
	 *  �������� ���������� �������� �������� �� ����� ���������� �������
	 */
	private boolean lockOperationDebit;
	/**
	 *  ������ �������� ����������� �� �������� �������� �� ����� ���������� �������
	 */
	private int periodLockedOperationDebit;
	/**
	 *  ������������ ����� ��� ������ ����������� �� �����, ���������� � �������� ��������� ���������� ������� � ������
	 */
	private int maxRUBSumUnlockRestriction;
	/**
	 *  ������������ ����� ��� ������ ����������� �� �����, ���������� � �������� ��������� ���������� ������� � ��������
	 */
	private int maxUSDSumUnlockRestriction;
	/**
	 *  ������������ ����� ��� ������ ����������� �� �����, ���������� � �������� ��������� ���������� ������� � ����
	 */
	private int maxEURSumUnlockRestriction;

	/**
	 * true - � ������ �� ������ � ���� "�������������� � ���������" �������� (������������) ��� ������ �������� "������"
	 */
	private boolean availableOtherSbrfRelationType;

	/**
	 * ������������ xsd 19��� ������
	 */
	private boolean useXSDRelease19Version;

	/**
	 * ���������� ����� �������������� ������, ������� ����� ������� � ������ ����� ��������� ������
	 */
	private int newAccountsCount;

	/**
	 * depositType ��� �������� ������ ����.�����
	 */
	private String newOpeningAccountDepositType;

	/**
	 * depositGroup ��� �������� ������ ����.����� ��� ������� ���������
	 */
	private String newOpeningAccountDepositGroupOld;

	/**
	 * depositGroup ��� �������� ������ ����.����� ��� ��������� ���_���
	 */
	private String newOpeningAccountDepositGroupCasNSI;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param reader
	 */
    public LoanClaimConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return ��� ������� ��� ������ �������� �� ��� �� �������� (��� � ���)
	 */
	public String getEsbCreditBackQueueName()
	{
		return ESB_CREDIT_BACK_QUEUE_NAME;
	}

	/**
	 * @return ��� ��������-������� �������� ��� ������ �������� �� ��� �� �������� (��� � ���)
	 */
	public String getEsbCreditBackQCFName()
	{
		return ESB_CREDIT_BACK_QCF_NAME;
	}

	/**
		 * @return ������� ���� ��� ������������ ����� �������� ������ � ���������.
	 */
	public boolean isUseNewAlgorithm()
	{
		return useNewAlgorithm;
	}

	protected String getCodename()
	{
		return CODENAME;
	}

	protected Class<LoanClaimConfigBean> getConfigDataClass()
	{
		return LoanClaimConfigBean.class;
	}

	@Override
	protected <T> T doSave()
	{
		LoanClaimConfigBean configBean = getConfigData();
		configBean.setUseQuestionnaire(useQuestionnaire);
		configBean.setCallAvailable(callAvailable);
		configBean.setGetCreditAtLogon(getCreditAtLogon);
		configBean.setNeedConfirmDebitOperationERKC(needConfirmDebitOperationERKC);
		configBean.setMinRUBSumDebitOperationERKC(minRUBSumDebitOperationERKC);
		configBean.setMinUSDSumDebitOperationERKC(minUSDSumDebitOperationERKC);
		configBean.setMinEURSumDebitOperationERKC(minEURSumDebitOperationERKC);
		configBean.setLockOperationDebit(lockOperationDebit);
		configBean.setPeriodLockedOperationDebit(periodLockedOperationDebit);
		configBean.setMaxRUBSumUnlockRestriction(maxRUBSumUnlockRestriction);
		configBean.setMaxUSDSumUnlockRestriction(maxUSDSumUnlockRestriction);
		configBean.setMaxEURSumUnlockRestriction(maxEURSumUnlockRestriction);
		return (T) configBean;
	}

	protected void doRefresh() throws ConfigurationException
	{
		LoanClaimConfigBean configBean = getConfigData();
		useNewAlgorithm = configBean.isUseNewAlgorithm();
		unloadCardOfferReportPath = configBean.getUnloadCardOfferReportPath();
        profitPreapprovedCredit = configBean.getProfitPreapprovedCredit();
		agreesOnProcessPersonData = getAgreesOnProcessPersonDataFromIdNameBean(configBean.getAgreeOnProcessPersonDataBeans());
        latestLoanOffersTerm = getLatestLoanOffersTerms(configBean.getAgreeOnProcessPersonDataBeans());
		periodRequestLoanStatus = configBean.getPeriodRequestLoanStatus();
		etsmMockConfig = configBean.getEtsmMockConfig();
		loanClaimDictionaryCSVDelimiter = configBean.getLoanClaimDictionaryDelimiter();
		if (etsmMockConfig == null)
			etsmMockConfig = new ETSMMockConfig();
		dictionarySearchWordSeparators = configBean.getDictionarySearchWordSeparators();
		guestLoanDepartmentTb = configBean.getGuestLoanDepartmentTb();
		guestLoanDepartmentOsb = configBean.getGuestLoanDepartmentOsb();
		guestLoanDepartmentVsp = configBean.getGuestLoanDepartmentVsp();
		useQuestionnaire = configBean.isUseQuestionnaire();
		allowedRefusalCodes = configBean.getAllowedRefusalCodes();
		creationRestrictedDays = configBean.getCreationRestrictedDays();
		callAvailable = configBean.isCallAvailable();
		waitTmResponseDays = configBean.getWaitTmResponseDays();
		historyAvailableDays = configBean.getHistoryAvailableDays();
		confirmAvailableDays = configBean.getConfirmAvailableDays();
		lengthShortNumberClaim = configBean.getLengthShortNumberClaim();
		sendUpdStatusAvailable = configBean.isSendUpdStatusAvailable();
		useXSDRelease16Version = configBean.isUseXSDRelease16Scheme();
		useXSDRelease19Version = configBean.isUseXSDRelease19Scheme();
		detailedDescriptionOfCardsLink = configBean.getDetailedDescriptionOfCardsLink();
		getCreditAtLogon = configBean.isGetCreditAtLogon();
		needConfirmDebitOperationERKC = configBean.isNeedConfirmDebitOperationERKC();
		minRUBSumDebitOperationERKC = configBean.getMinRUBSumDebitOperationERKC();
		minUSDSumDebitOperationERKC = configBean.getMinUSDSumDebitOperationERKC();
		minEURSumDebitOperationERKC = configBean.getMinEURSumDebitOperationERKC();
		lockOperationDebit = configBean.isLockOperationDebit();
		periodLockedOperationDebit = configBean.getPeriodLockedOperationDebit();
		maxRUBSumUnlockRestriction = configBean.getMaxRUBSumUnlockRestriction();
		maxUSDSumUnlockRestriction = configBean.getMaxUSDSumUnlockRestriction();
		maxEURSumUnlockRestriction = configBean.getMaxEURSumUnlockRestriction();
		availableOtherSbrfRelationType = configBean.isAvailableOtherSbrfRelationType();
		newAccountsCount = configBean.getNewAccountCount();
		newOpeningAccountDepositType = configBean.getNewOpeningAccountDepositType();
		newOpeningAccountDepositGroupOld = configBean.getNewOpeningAccountDepositGroupOld();
		newOpeningAccountDepositGroupCasNSI = configBean.getNewOpeningAccountDepositGroupCasNsi();
	}

	/**
	 * �������� ��� �������� �����������  "���������� �� ��������� ������������ ������"
	 * @return ������ ��������
	 */
	public List<AgreeOnProcessPersonData> getAgreesOnProcessPersonData()
	{
		return Collections.unmodifiableList(agreesOnProcessPersonData);
	}

    public List<String> getProfitPreapprovedCredit()
    {
	    return profitPreapprovedCredit == null ? new ArrayList<String>(): profitPreapprovedCredit;
    }

    public AgreeOnProcessPersonData getLatestLoanOffersTerm()
    {
        return latestLoanOffersTerm;
    }

	/**
	 * ��������� ������� ����������� ������ � �������
	 * @return ���������� �������
	 */
	public int getPeriodRequestLoanStatus()
	{
		return periodRequestLoanStatus;
	}

	/**
	 * @return ���� � �������� �������� ������ �� �������� �������������� ����������� �� ������.
	 */
	public String getUnloadCardOfferReportPath()
	{
		return unloadCardOfferReportPath;
	}

	/**
	 * @return ������ ������������ ����������� ����������� ������ �� ������ (never null)
	 */
	public ETSMMockConfig getETSMMockConfig()
	{
		return etsmMockConfig;
	}

	/**
	 * @return ���������� ����, � ������� ������� ����������� ��������� ������ ��������� �� ����������
	 *
	 * �������� ������ ��� ����, ����� ���������� ����� ���������� �� ���� "� �������"
	 * ��. ������ BUG070774: �������� ������������� ����������� ������ �� ������ �� �������� "�������������� ������".
	 */
	public int getExtendedLoanClaimLifetimeDays()
	{
		return EXTENDED_LOAN_CLAIM_LIFETIME_DAYS;
	}

	private List<AgreeOnProcessPersonData> getAgreesOnProcessPersonDataFromIdNameBean(List<AgreeOnProcessPersonDataBean> input)
	{
		List<AgreeOnProcessPersonData> result = new ArrayList<AgreeOnProcessPersonData>(input.size());
		for(AgreeOnProcessPersonDataBean bean : input)
		{
			AgreeOnProcessPersonData agreeOnProcessPersonData = new AgreeOnProcessPersonData();
			agreeOnProcessPersonData.setId(bean.getId());
			agreeOnProcessPersonData.setEffectiveDate(bean.getEffectiveDate());
			agreeOnProcessPersonData.setConditionsText(bean.getConditionsText());
			result.add(agreeOnProcessPersonData);
		}
		return result;
	}

	/**
	 * @param input ������ ��������
	 * @return ������� �������(���� ������ �������� ������ ���� ������ �������  ����, � ������������ ����� �� ������ ������ ������� ����������� ������ ��������)
	 */
    private AgreeOnProcessPersonData getLatestLoanOffersTerms(List<AgreeOnProcessPersonDataBean> input)
    {
        if (input == null || input.size() == 0)
        {
            return null;
        }

	    AgreeOnProcessPersonDataBean currentBean = null;

	    for(AgreeOnProcessPersonDataBean bean:input)
	    {
		    Calendar effectiveDate = bean.getEffectiveDate();
		    int currentDateCompare = effectiveDate.compareTo(Calendar.getInstance());
		    if (currentBean == null && currentDateCompare <= 0 ||
			    currentBean != null && currentDateCompare <= 0 && effectiveDate.compareTo(currentBean.getEffectiveDate()) > 0)
		        currentBean = bean;
	    }
	    //���� ��� ������� � ����� ������ ���� ������ ������� �� ���������� ������� ���.
	    if (currentBean == null)
		   return null;

        AgreeOnProcessPersonData agreeOnProcessPersonData = new AgreeOnProcessPersonData();
        agreeOnProcessPersonData.setId(currentBean.getId());
        agreeOnProcessPersonData.setEffectiveDate(currentBean.getEffectiveDate());
        agreeOnProcessPersonData.setConditionsText(currentBean.getConditionsText());
        return agreeOnProcessPersonData;
    }

	public String getLoanClaimDictionaryCSVDelimiter()
	{
		return loanClaimDictionaryCSVDelimiter;
	}

	public String getDictionarySearchWordSeparators()
	{
		return dictionarySearchWordSeparators;
	}

	public String getGuestLoanDepartmentTb()
	{
		return guestLoanDepartmentTb;
	}

	public String getGuestLoanDepartmentOsb()
	{
		return guestLoanDepartmentOsb;
	}

	public String getGuestLoanDepartmentVsp()
	{
		return guestLoanDepartmentVsp;
	}

	public boolean isUseQuestionnaire()
	{
		return useQuestionnaire;
	}

	public void setUseQuestionnaire(boolean useQuestionnaire)
	{
		this.useQuestionnaire = useQuestionnaire;
	}

	public List<String> getAllowedRefusalCodes()
	{
		return allowedRefusalCodes == null ? new ArrayList<String>(): allowedRefusalCodes;
	}

	public void setAllowedRefusalCodes(List<String> allowedRefusalCodes)
	{
		this.allowedRefusalCodes = allowedRefusalCodes;
	}

	public int getCreationRestrictedDays()
	{
		return creationRestrictedDays;
	}

	public void setCreationRestrictedDays(int creationRestrictedDays)
	{
		this.creationRestrictedDays = creationRestrictedDays;
	}

	public boolean isCallAvailable()
	{
		return callAvailable;
	}

	public void setCallAvailable(boolean callAvailable)
	{
		this.callAvailable = callAvailable;
	}

	public int getWaitTmResponseDays()
	{
		return waitTmResponseDays;
	}

	public void setWaitTmResponseDays(int waitTmResponseDays)
	{
		this.waitTmResponseDays = waitTmResponseDays;
	}

	public int getHistoryAvailableDays()
	{
		return historyAvailableDays;
	}

	public void setHistoryAvailableDays(int historyAvailableDays)
	{
		this.historyAvailableDays = historyAvailableDays;
	}

	public int getLengthShortNumberClaim()
	{
		return lengthShortNumberClaim;
	}

	public int getConfirmAvailableDays()
	{
		return confirmAvailableDays;
	}

	public void setConfirmAvailableDays(int confirmAvailableDays)
	{
		this.confirmAvailableDays = confirmAvailableDays;
	}

	public boolean isSendUpdStatusAvailable()
	{
		return sendUpdStatusAvailable;
	}

	public void setSendUpdStatusAvailable(boolean sendUpdStatusAvailable)
	{
		this.sendUpdStatusAvailable = sendUpdStatusAvailable;
	}

	public boolean isUseXSDRelease16Version()
	{
		return useXSDRelease16Version;
	}

	public String getDetailedDescriptionOfCardsLink()
	{
		return detailedDescriptionOfCardsLink;
	}

	public boolean isGetCreditAtLogon()
	{
		return getCreditAtLogon;
	}

	public void setGetCreditAtLogon(boolean getCreditAtLogon)
	{
		this.getCreditAtLogon = getCreditAtLogon;
	}

	public boolean isNeedConfirmDebitOperationERKC()
	{
		return needConfirmDebitOperationERKC;
	}

	public void setNeedConfirmDebitOperationERKC(boolean needConfirmDebitOperationERKC)
	{
		this.needConfirmDebitOperationERKC = needConfirmDebitOperationERKC;
	}

	public BigDecimal getMinRUBSumDebitOperationERKC()
	{
		return minRUBSumDebitOperationERKC;
	}

	public void setMinRUBSumDebitOperationERKC(BigDecimal minRUBSumDebitOperationERKC)
	{
		this.minRUBSumDebitOperationERKC = minRUBSumDebitOperationERKC;
	}

	public BigDecimal getMinUSDSumDebitOperationERKC()
	{
		return minUSDSumDebitOperationERKC;
	}

	public void setMinUSDSumDebitOperationERKC(BigDecimal minUSDSumDebitOperationERKC)
	{
		this.minUSDSumDebitOperationERKC = minUSDSumDebitOperationERKC;
	}

	public BigDecimal getMinEURSumDebitOperationERKC()
	{
		return minEURSumDebitOperationERKC;
	}

	public void setMinEURSumDebitOperationERKC(BigDecimal minEURSumDebitOperationERKC)
	{
		this.minEURSumDebitOperationERKC = minEURSumDebitOperationERKC;
	}

	public boolean isLockOperationDebit()
	{
		return lockOperationDebit;
	}

	public void setLockOperationDebit(boolean lockOperationDebit)
	{
		this.lockOperationDebit = lockOperationDebit;
	}

	public int getPeriodLockedOperationDebit()
	{
		return periodLockedOperationDebit;
	}

	public void setPeriodLockedOperationDebit(int periodLockedOperationDebit)
	{
		this.periodLockedOperationDebit = periodLockedOperationDebit;
	}

	public int getMaxRUBSumUnlockRestriction()
	{
		return maxRUBSumUnlockRestriction;
	}

	public void setMaxRUBSumUnlockRestriction(int maxRUBSumUnlockRestriction)
	{
		this.maxRUBSumUnlockRestriction = maxRUBSumUnlockRestriction;
	}

	public int getMaxUSDSumUnlockRestriction()
	{
		return maxUSDSumUnlockRestriction;
	}

	public void setMaxUSDSumUnlockRestriction(int maxUSDSumUnlockRestriction)
	{
		this.maxUSDSumUnlockRestriction = maxUSDSumUnlockRestriction;
	}

	public int getMaxEURSumUnlockRestriction()
	{
		return maxEURSumUnlockRestriction;
	}

	public void setMaxEURSumUnlockRestriction(int maxEURSumUnlockRestriction)
	{
		this.maxEURSumUnlockRestriction = maxEURSumUnlockRestriction;
	}

	/**
	 * @return true - � ������ �� ������ � ���� "�������������� � ���������" �������� (������������) ��� ������ �������� "������"
	 */
	public boolean isAvailableOtherSbrfRelationType()
	{
		return availableOtherSbrfRelationType;
	}

	public boolean isUseXSDRelease19Version()
	{
		return useXSDRelease19Version;
	}

	public XSDReleaseVersion getXSDReleaseVersion()
	{
		if (useXSDRelease16Version)
		{
			return XSDReleaseVersion.VERSION_16;
		}
		else if (useXSDRelease19Version)
		{
			return XSDReleaseVersion.VERSION_19;
		}
		else
		{
			return XSDReleaseVersion.VERSION_13;
		}
	}

	public int getNewAccountsCount()
	{
		return newAccountsCount;
	}

	public String getNewOpeningAccountDepositType()
	{
		return newOpeningAccountDepositType;
	}

	public String getNewOpeningAccountDepositGroupOld()
	{
		return newOpeningAccountDepositGroupOld;
	}

	public String getNewOpeningAccountDepositGroupCasNSI()
	{
		return newOpeningAccountDepositGroupCasNSI;
	}
}