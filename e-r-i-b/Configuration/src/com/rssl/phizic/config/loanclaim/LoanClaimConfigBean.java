package com.rssl.phizic.config.loanclaim;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author Moshenko
 * @ created 24.12.2013
 * @ $Author$
 * @ $Revision$
 */
@XmlRootElement(name="config")
@XmlType(name = "Config")
@XmlAccessorType(XmlAccessType.NONE)
@PlainOldJavaObject
@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField"})
class LoanClaimConfigBean
{
	@XmlElement(name = "use-new-algorithm", required = true)
	private boolean useNewAlgorithm;

	@XmlElement(name = "unload-cardOffer-report-path", required = true)
	private String unloadCardOfferReportPath;

	@XmlElement(name = "period-request-loan-status", required = true)
	private int periodRequestLoanStatus;

	@XmlElementWrapper(name = "list-agree-on-process-person-data", required = true)
	@XmlElement(name = "agree-on-process-person-data", required = true)
	private List<AgreeOnProcessPersonDataBean> agreeOnProcessPersonDataBeans;

    @XmlElementWrapper(name = "Profit_preapproved_credit")
    @XmlElement(name = "loan-offer-detail-item", type = String.class)
    private List<String> profitPreapprovedCredit;

	@XmlElement(name = "etsm-mock-config")
	private ETSMMockConfig etsmMockConfig;

	@XmlElement(name = "loan-claim-dictionary-delimiter", required = true)
	private String loanClaimDictionaryDelimiter;

	@XmlElement(name = "dictionary-search-word-separators", required = true)
	private String dictionarySearchWordSeparators;

	@XmlElement(name = "guest-loan-department-tb", required = true)
	private String guestLoanDepartmentTb;

	@XmlElement(name = "guest-loan-department-osb", required = true)
	private String guestLoanDepartmentOsb;

	@XmlElement(name = "guest-loan-department-vsp", required = true)
	private String guestLoanDepartmentVsp;

	@XmlElement(name = "use-questionnaire", required = true)
	private boolean useQuestionnaire;

	@XmlElementWrapper(name = "allowed-refusal-codes")
	@XmlElement(name = "allowed-refusal-code", type = String.class)
	private List<String> allowedRefusalCodes;

	@XmlElement(name = "creation-restricted-days", required = true)
	private int creationRestrictedDays;

	@XmlElement(name = "call-available", required = true)
	private boolean callAvailable;

	@XmlElement(name = "wait-tm-response-days", required = true)
	private int waitTmResponseDays;

	@XmlElement(name = "history-available-days", required = true)
	private int historyAvailableDays;

	@XmlElement(name = "length-short-number-claim", required = true)
	private int lengthShortNumberClaim;

	@XmlElement(name = "confirm-available-days", required = true)
	private int confirmAvailableDays;

	@XmlElement(name = "send-update-status-available", required = true)
	private boolean sendUpdStatusAvailable;

	@XmlElement(name = "use-xsd-release-16-scheme", required = true)
	private boolean useXSDRelease16Scheme;

	@XmlElement(name = "use-xsd-release-19-scheme", required = true)
	private boolean useXSDRelease19Scheme;

	@XmlElement(name = "detailed-description-of-cards-link", required = true)
	private String detailedDescriptionOfCardsLink;

	@XmlElement(name = "get-credit-at-logon", required = true)
	private boolean getCreditAtLogon;

	@XmlElement(name = "need-confirm-debit-operation-ERKC", required = true)
	private boolean needConfirmDebitOperationERKC;

	@XmlElement(name = "min-rub-sum-debit-operation-ERKC", required = true)
	private BigDecimal minRUBSumDebitOperationERKC;

	@XmlElement(name = "min-usd-sum-debit-operation-ERKC", required = true)
	private BigDecimal minUSDSumDebitOperationERKC;

	@XmlElement(name = "min-eur-sum-debit-operation-ERKC", required = true)
	private BigDecimal minEURSumDebitOperationERKC;

	@XmlElement(name = "lock-operation-debit", required = true)
	private boolean lockOperationDebit;

	@XmlElement(name = "period-locked-operation-debit", required = true)
	private int periodLockedOperationDebit;

	@XmlElement(name = "max-rub-sum-unlock-restriction", required = true)
	private int maxRUBSumUnlockRestriction;

	@XmlElement(name = "max-usd-sum-unlock-restriction", required = true)
	private int maxUSDSumUnlockRestriction;

	@XmlElement(name = "max-eur-sum-unlock-restriction", required = true)
	private int maxEURSumUnlockRestriction;

	@XmlElement(name = "available-other-sbrf-relation-type", required = true)
	private boolean availableOtherSbrfRelationType;

	@XmlElement(name = "new-account-count", required = true)
	private int newAccountCount;

	@XmlElement(name = "new-opening-account-deposit-type", required = true)
	private String newOpeningAccountDepositType;

	@XmlElement(name = "new-opening-account-deposit-group-old", required = true)
	private String newOpeningAccountDepositGroupOld;

	@XmlElement(name = "new-opening-account-deposit-group-casnsi", required = true)
	private String newOpeningAccountDepositGroupCasNsi;

	boolean isUseNewAlgorithm()
	{
		return useNewAlgorithm;
	}

	void setUseNewAlgorithm(boolean useNewAlgorithm)
	{
		this.useNewAlgorithm = useNewAlgorithm;
	}

	List<AgreeOnProcessPersonDataBean> getAgreeOnProcessPersonDataBeans()
	{
		return agreeOnProcessPersonDataBeans;
	}

	void setAgreeOnProcessPersonDataBeans(List<AgreeOnProcessPersonDataBean> agreeOnProcessPersonDataBeans)
	{
		this.agreeOnProcessPersonDataBeans = agreeOnProcessPersonDataBeans;
	}

    List<String> getProfitPreapprovedCredit()
    {
        return profitPreapprovedCredit;
    }

    void setProfitPreapprovedCredit(List<String> profitPreapprovedCredit)
    {
        this.profitPreapprovedCredit = profitPreapprovedCredit;
    }


	int getPeriodRequestLoanStatus()
	{
		return periodRequestLoanStatus;
	}

	void setPeriodRequestLoanStatus(int periodRequestLoanStatus)
	{
		this.periodRequestLoanStatus = periodRequestLoanStatus;
	}

	public String getUnloadCardOfferReportPath()
	{
		return unloadCardOfferReportPath;
	}

	public void setUnloadCardOfferReportPath(String unloadCardOfferReportPath)
	{
		this.unloadCardOfferReportPath = unloadCardOfferReportPath;
	}

	ETSMMockConfig getEtsmMockConfig()
	{
		return etsmMockConfig;
	}

	String getLoanClaimDictionaryDelimiter()
	{
		return loanClaimDictionaryDelimiter;
	}

	void setLoanClaimDictionaryDelimiter(String loanClaimDictionaryDelimiter)
	{
		this.loanClaimDictionaryDelimiter = loanClaimDictionaryDelimiter;
	}

	String getDictionarySearchWordSeparators()
	{
		return dictionarySearchWordSeparators;
	}

	void setDictionarySearchWordSeparators(String dictionarySearchWordSeparators)
	{
		this.dictionarySearchWordSeparators = dictionarySearchWordSeparators;
	}

	String getGuestLoanDepartmentTb()
	{
		return guestLoanDepartmentTb;
	}

	void setGuestLoanDepartmentTb(String guestLoanDepartmentTb)
	{
		this.guestLoanDepartmentTb = guestLoanDepartmentTb;
	}

	String getGuestLoanDepartmentOsb()
	{
		return guestLoanDepartmentOsb;
	}

	void setGuestLoanDepartmentOsb(String guestLoanDepartmentOsb)
	{
		this.guestLoanDepartmentOsb = guestLoanDepartmentOsb;
	}

	String getGuestLoanDepartmentVsp()
	{
		return guestLoanDepartmentVsp;
	}

	void setGuestLoanDepartmentVsp(String guestLoanDepartmentVsp)
	{
		this.guestLoanDepartmentVsp = guestLoanDepartmentVsp;
	}

	boolean isUseQuestionnaire()
	{
		return useQuestionnaire;
	}

	void setUseQuestionnaire(boolean useQuestionnaire)
	{
		this.useQuestionnaire = useQuestionnaire;
	}

	List<String> getAllowedRefusalCodes()
	{
		return allowedRefusalCodes;
	}

	void setAllowedRefusalCodes(List<String> allowedRefusalCodes)
	{
		this.allowedRefusalCodes = allowedRefusalCodes;
	}

	int getCreationRestrictedDays()
	{
		return creationRestrictedDays;
	}

	void setCreationRestrictedDays(int creationRestrictedDays)
	{
		this.creationRestrictedDays = creationRestrictedDays;
	}

	boolean isCallAvailable()
	{
		return callAvailable;
	}

	void setCallAvailable(boolean callAvailable)
	{
		this.callAvailable = callAvailable;
	}

	int getWaitTmResponseDays()
	{
		return waitTmResponseDays;
	}

	void setWaitTmResponseDays(int waitTmResponseDays)
	{
		this.waitTmResponseDays = waitTmResponseDays;
	}

	int getHistoryAvailableDays()
	{
		return historyAvailableDays;
	}

	void setHistoryAvailableDays(int historyAvailableDays)
	{
		this.historyAvailableDays = historyAvailableDays;
	}

	int getLengthShortNumberClaim()
	{
		return lengthShortNumberClaim;
	}

	void setLengthShortNumberClaim(int lengthShortNumberClaim)
	{
		this.lengthShortNumberClaim = lengthShortNumberClaim;
	}

	int getConfirmAvailableDays()
	{
		return confirmAvailableDays;
	}

	void setConfirmAvailableDays(int confirmAvailableDays)
	{
		this.confirmAvailableDays = confirmAvailableDays;
	}

	boolean isSendUpdStatusAvailable()
	{
		return sendUpdStatusAvailable;
	}

	void setSendUpdStatusAvailable(boolean sendUpdStatusAvailable)
	{
		this.sendUpdStatusAvailable = sendUpdStatusAvailable;
	}

	boolean isUseXSDRelease16Scheme()
	{
		return useXSDRelease16Scheme;
	}

	void setUseXSDRelease16Scheme(boolean useXSDRelease16Scheme)
	{
		this.useXSDRelease16Scheme = useXSDRelease16Scheme;
	}

	String getDetailedDescriptionOfCardsLink()
	{
		return detailedDescriptionOfCardsLink;
	}

	void setDetailedDescriptionOfCardsLink(String detailedDescriptionOfCardsLink)
	{
		this.detailedDescriptionOfCardsLink = detailedDescriptionOfCardsLink;
	}

	boolean isGetCreditAtLogon()
	{
		return getCreditAtLogon;
	}

	void setGetCreditAtLogon(boolean getCreditAtLogon)
	{
		this.getCreditAtLogon = getCreditAtLogon;
	}

	boolean isNeedConfirmDebitOperationERKC()
	{
		return needConfirmDebitOperationERKC;
	}

	void setNeedConfirmDebitOperationERKC(boolean needConfirmDebitOperationERKC)
	{
		this.needConfirmDebitOperationERKC = needConfirmDebitOperationERKC;
	}

	BigDecimal getMinRUBSumDebitOperationERKC()
	{
		return minRUBSumDebitOperationERKC;
	}

	void setMinRUBSumDebitOperationERKC(BigDecimal minRUBSumDebitOperationERKC)
	{
		this.minRUBSumDebitOperationERKC = minRUBSumDebitOperationERKC;
	}

	BigDecimal getMinUSDSumDebitOperationERKC()
	{
		return minUSDSumDebitOperationERKC;
	}

	void setMinUSDSumDebitOperationERKC(BigDecimal minUSDSumDebitOperationERKC)
	{
		this.minUSDSumDebitOperationERKC = minUSDSumDebitOperationERKC;
	}

	BigDecimal getMinEURSumDebitOperationERKC()
	{
		return minEURSumDebitOperationERKC;
	}

	void setMinEURSumDebitOperationERKC(BigDecimal minEURSumDebitOperationERKC)
	{
		this.minEURSumDebitOperationERKC = minEURSumDebitOperationERKC;
	}

	boolean isLockOperationDebit()
	{
		return lockOperationDebit;
	}

	void setLockOperationDebit(boolean lockOperationDebit)
	{
		this.lockOperationDebit = lockOperationDebit;
	}

	int getPeriodLockedOperationDebit()
	{
		return periodLockedOperationDebit;
	}

	void setPeriodLockedOperationDebit(int periodLockedOperationDebit)
	{
		this.periodLockedOperationDebit = periodLockedOperationDebit;
	}

	int getMaxRUBSumUnlockRestriction()
	{
		return maxRUBSumUnlockRestriction;
	}

	void setMaxRUBSumUnlockRestriction(int maxRUBSumUnlockRestriction)
	{
		this.maxRUBSumUnlockRestriction = maxRUBSumUnlockRestriction;
	}

	int getMaxUSDSumUnlockRestriction()
	{
		return maxUSDSumUnlockRestriction;
	}

	void setMaxUSDSumUnlockRestriction(int maxUSDSumUnlockRestriction)
	{
		this.maxUSDSumUnlockRestriction = maxUSDSumUnlockRestriction;
	}

	int getMaxEURSumUnlockRestriction()
	{
		return maxEURSumUnlockRestriction;
	}

	void setMaxEURSumUnlockRestriction(int maxEURSumUnlockRestriction)
	{
		this.maxEURSumUnlockRestriction = maxEURSumUnlockRestriction;
	}

	boolean isAvailableOtherSbrfRelationType()
	{
		return availableOtherSbrfRelationType;
	}

	void setAvailableOtherSbrfRelationType(boolean availableOtherSbrfRelationType)
	{
		this.availableOtherSbrfRelationType = availableOtherSbrfRelationType;
	}

	public boolean isUseXSDRelease19Scheme()
	{
		return useXSDRelease19Scheme;
	}

	public void setUseXSDRelease19Scheme(boolean useXSDRelease19Scheme)
	{
		this.useXSDRelease19Scheme = useXSDRelease19Scheme;
	}

	int getNewAccountCount()
	{
		return newAccountCount;
	}

	void setNewAccountCount(int newAccountCount)
	{
		this.newAccountCount = newAccountCount;
	}

	String getNewOpeningAccountDepositType()
	{
		return newOpeningAccountDepositType;
	}

	void setNewOpeningAccountDepositType(String newOpeningAccountDepositType)
	{
		this.newOpeningAccountDepositType = newOpeningAccountDepositType;
	}

	String getNewOpeningAccountDepositGroupOld()
	{
		return newOpeningAccountDepositGroupOld;
	}

	void setNewOpeningAccountDepositGroupOld(String newOpeningAccountDepositGroupOld)
	{
		this.newOpeningAccountDepositGroupOld = newOpeningAccountDepositGroupOld;
	}

	String getNewOpeningAccountDepositGroupCasNsi()
	{
		return newOpeningAccountDepositGroupCasNsi;
	}

	void setNewOpeningAccountDepositGroupCasNsi(String newOpeningAccountDepositGroupCasNsi)
	{
		this.newOpeningAccountDepositGroupCasNsi = newOpeningAccountDepositGroupCasNsi;
	}
}
