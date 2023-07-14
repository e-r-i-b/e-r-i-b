package com.rssl.phizic.business.dictionaries.providers;

import java.math.BigDecimal;

/**
 * ����� ��� ������ ����������� ������ ���������� �����������
 * @author lukina
 * @ created 20.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ServiceProviderShort
{
	private Long  id; //id ����������
	private String uuid;
	private String  code;
	private String  kind;
	private String  synchKey;
	private String  name;  // �������� ����������
	private String  nameService;  // �������� ������
	private String  codeService;  //��� ������
	private String  description; // ��������
	private Long    billing; // id ��������
	private Long    imageId; // id ��������
	private boolean autoPaymentSupported;  //�������� �� ����������
	private boolean autoPaymentSupportedInApi;
	private boolean autoPaymentSupportedInATM;

	private boolean availablePaymentsForAtmApi;      //�������� �� ������� ����� �� � atmApi
	private boolean availablePaymentsForInternetBank;//�������� �� ������� ����� �� � ��
	private boolean availablePaymentsForMApi;        //�������� �� ������� ����� �� � mApi
	private boolean availablePaymentsForSocialApi;   //�������� �� ������� ����� �� � socialApi
	private boolean availablePaymentsForErmb;        //�������� �� ������� ����� �� � ����
	private Integer versionAPI;      // ������ API, � ������� ������������ ��������� (� ������� ��� �����������: 500, 510, 520)

	private String mobilebankCode;
	private String commissionMessage;   //��������� � ��������
	private BigDecimal maxComissionAmount;
	private BigDecimal minComissionAmount;
	private BigDecimal comissionRate;

	private String  attrDelimiter;
	private String  attrValuesDelimiter;
	private boolean ground;
	private boolean templateSupported; //������������ �� �������� �������
	private boolean creditCardSupported;
	private AccountType accountType;
	private Long imageHelpId;       // ������������� ����������� ���������
	private boolean federal;
	private boolean bankDetails;  //���������� ���������� ����������
	private String formName; // ��� ��������� �����
	private ServiceProviderSubType subType;     // ������� ���������� (������� �����/�������� �������)

	private boolean editPaymentSupported;
	private ServiceProviderState state;

	private BigDecimal minimumSum;  //����������� ����������� ����� ��������.
	private BigDecimal maximumSum; //������������ ����������� ����� ��������.

	private Long  departmentId;
	private String  transitAccount;
	private boolean offlineAvailable; // �������� �� ��������� ��� ������ ��� ������������ ������� �������
	private String  INN;
	private String  account;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getUuid()
	{
		return uuid;
	}

	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getKind()
	{
		return kind;
	}

	public void setKind(String kind)
	{
		this.kind = kind;
	}

	public String getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(String synchKey)
	{
		this.synchKey = synchKey;
	}

	public String getNameService()
	{
		return nameService;
	}

	public void setNameService(String nameService)
	{
		this.nameService = nameService;
	}

	public String getCodeService()
	{
		return codeService;
	}

	public void setCodeService(String codeService)
	{
		this.codeService = codeService;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public boolean isAutoPaymentSupported()
	{
		return autoPaymentSupported;
	}

	public void setAutoPaymentSupported(boolean autoPaymentSupported)
	{
		this.autoPaymentSupported = autoPaymentSupported;
	}

	public boolean isAutoPaymentSupportedInApi()
	{
		return autoPaymentSupportedInApi;
	}

	public void setAutoPaymentSupportedInApi(boolean autoPaymentSupportedInApi)
	{
		this.autoPaymentSupportedInApi = autoPaymentSupportedInApi;
	}

	public boolean isAutoPaymentSupportedInATM()
	{
		return autoPaymentSupportedInATM;
	}

	public void setAutoPaymentSupportedInATM(boolean autoPaymentSupportedInATM)
	{
		this.autoPaymentSupportedInATM = autoPaymentSupportedInATM;
	}

	public boolean isAvailablePaymentsForAtmApi()
	{
		return availablePaymentsForAtmApi;
	}

	public void setAvailablePaymentsForAtmApi(boolean availablePaymentsForAtmApi)
	{
		this.availablePaymentsForAtmApi = availablePaymentsForAtmApi;
	}

	public boolean isAvailablePaymentsForInternetBank()
	{
		return availablePaymentsForInternetBank;
	}

	public void setAvailablePaymentsForInternetBank(boolean availablePaymentsForInternetBank)
	{
		this.availablePaymentsForInternetBank = availablePaymentsForInternetBank;
	}

	public boolean isAvailablePaymentsForMApi()
	{
		return availablePaymentsForMApi;
	}

	public void setAvailablePaymentsForMApi(boolean availablePaymentsForMApi)
	{
		this.availablePaymentsForMApi = availablePaymentsForMApi;
	}

	public boolean isAvailablePaymentsForSocialApi()
	{
		return availablePaymentsForSocialApi;
	}

	public void setAvailablePaymentsForSocialApi(boolean availablePaymentsForSocialApi)
	{
		this.availablePaymentsForSocialApi = availablePaymentsForSocialApi;
	}

	public boolean isAvailablePaymentsForErmb()
	{
		return availablePaymentsForErmb;
	}

	public void setAvailablePaymentsForErmb(boolean availablePaymentsForErmb)
	{
		this.availablePaymentsForErmb = availablePaymentsForErmb;
	}

	public Integer getVersionAPI()
	{
		return versionAPI;
	}

	public void setVersionAPI(Integer versionAPI)
	{
		this.versionAPI = versionAPI;
	}

	public String getMobilebankCode()
	{
		return mobilebankCode;
	}

	public void setMobilebankCode(String mobilebankCode)
	{
		this.mobilebankCode = mobilebankCode;
	}

	public String getCommissionMessage()
	{
		return commissionMessage;
	}

	public void setCommissionMessage(String commissionMessage)
	{
		this.commissionMessage = commissionMessage;
	}

	public BigDecimal getMaxComissionAmount()
	{
		return maxComissionAmount;
	}

	public void setMaxComissionAmount(BigDecimal maxComissionAmount)
	{
		this.maxComissionAmount = maxComissionAmount;
	}

	public BigDecimal getMinComissionAmount()
	{
		return minComissionAmount;
	}

	public void setMinComissionAmount(BigDecimal minComissionAmount)
	{
		this.minComissionAmount = minComissionAmount;
	}

	public BigDecimal getComissionRate()
	{
		return comissionRate;
	}

	public void setComissionRate(BigDecimal comissionRate)
	{
		this.comissionRate = comissionRate;
	}

	public String getAttrDelimiter()
	{
		return attrDelimiter;
	}

	public void setAttrDelimiter(String attrDelimiter)
	{
		this.attrDelimiter = attrDelimiter;
	}

	public String getAttrValuesDelimiter()
	{
		return attrValuesDelimiter;
	}

	public void setAttrValuesDelimiter(String attrValuesDelimiter)
	{
		this.attrValuesDelimiter = attrValuesDelimiter;
	}

	public boolean isGround()
	{
		return ground;
	}

	public void setGround(boolean ground)
	{
		this.ground = ground;
	}

	public boolean isTemplateSupported()
	{
		return templateSupported;
	}

	public void setTemplateSupported(boolean templateSupported)
	{
		this.templateSupported = templateSupported;
	}

	public boolean isCreditCardSupported()
	{
		return creditCardSupported;
	}

	public void setCreditCardSupported(boolean creditCardSupported)
	{
		this.creditCardSupported = creditCardSupported;
	}

	public boolean isEditPaymentSupported()
	{
		return editPaymentSupported;
	}

	public void setEditPaymentSupported(boolean editPaymentSupported)
	{
		this.editPaymentSupported = editPaymentSupported;
	}

	public ServiceProviderState getState()
	{
		return state;
	}

	public void setState(ServiceProviderState state)
	{
		this.state = state;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public Long getBilling()
	{
		return billing;
	}

	public void setBilling(Long billing)
	{
		this.billing = billing;
	}

	public AccountType getAccountType()
	{
		return accountType;
	}

	public void setAccountType(AccountType accountType)
	{
		this.accountType = accountType;
	}

	public Long getImageHelpId()
	{
		return imageHelpId;
	}

	public void setImageHelpId(Long imageHelpId)
	{
		this.imageHelpId = imageHelpId;
	}

	public boolean isFederal()
	{
		return federal;
	}

	public void setFederal(boolean federal)
	{
		this.federal = federal;
	}

	public boolean isBankDetails()
	{
		return bankDetails;
	}

	public void setBankDetails(boolean bankDetails)
	{
		this.bankDetails = bankDetails;
	}

	public String getFormName()
	{
		return formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public ServiceProviderSubType getSubType()
	{
		return subType;
	}

	public void setSubType(ServiceProviderSubType subType)
	{
		this.subType = subType;
	}

	public BigDecimal getMinimumSum()
	{
		return minimumSum;
	}

	public void setMinimumSum(BigDecimal minimumSum)
	{
		this.minimumSum = minimumSum;
	}

	public BigDecimal getMaximumSum()
	{
		return maximumSum;
	}

	public void setMaximumSum(BigDecimal maximumSum)
	{
		this.maximumSum = maximumSum;
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getTransitAccount()
	{
		return transitAccount;
	}

	public void setTransitAccount(String transitAccount)
	{
		this.transitAccount = transitAccount;
	}

	public boolean isOfflineAvailable()
	{
		return offlineAvailable;
	}

	public void setOfflineAvailable(boolean offlineAvailable)
	{
		this.offlineAvailable = offlineAvailable;
	}

	/**
	 * @return ���
	 */
	public String getINN()
	{
		return INN;
	}

	/**
	 * @param INN ���
	 */
	public void setINN(String INN)
	{
		this.INN = INN;
	}

	/**
	 * @return ����
	 */
	public String getAccount()
	{
		return account;
	}

	/**
	 * @param account ����
	 */
	public void setAccount(String account)
	{
		this.account = account;
	}
}
