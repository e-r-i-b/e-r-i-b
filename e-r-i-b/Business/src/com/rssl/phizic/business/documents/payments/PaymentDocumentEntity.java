package com.rssl.phizic.business.documents.payments;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author Rtischeva
 * @ created 06.04.15
 * @ $Author$
 * @ $Revision$
 */
public class PaymentDocumentEntity
{
	private Long documentId;
	private Long departmentId;
	private String stateCode;
	private String stateDescription;
	private Long formId;
	private String kind;
	private Calendar dateCreated;
	private Boolean longOffer;
	private String chargeOffAccount;
	private Long loginId;
	private String receiverAccount;
	private String documentNumber;
	private String systemName;
	private BigDecimal commission;
	private String commissionCurrency;
	private Boolean archive;
	private String creationType;
	private String clientOperationChannel;
	private String refusingReason;
	private Calendar operationDate;
	private Calendar admissionDate;
	private Calendar executionDate;
	private Calendar documentDate;
	private String stateMachineName;
	private Long templateId;
	private String creationSourceType;
	private String confirmStrategyType;
	private String operationUID;
	private String sessionId;
	private String promoCode;
	private Long createdEmployeeLoginId;
	private Long confirmedEmployeeLoginId;
	private String codeATM;
	private String additionalOperationChannel;
	private String loginType;
	private String payerName;
	private String externalId;
	private String externalOfficeId;
	private String externalOwnerId;
	private String payerAccount;
	private String confirmEmployee;
	private BigDecimal chargeOffAmount;
	private String chargeOffCurrency;
	private BigDecimal destinationAmount;
	private String destinationCurrency;
	private String summType;
	private String ground;
	private String receiverName;
	private String providerExternalId;
	private Long receiverInternalId;
	private String billingDocumentNumber;
	private Long countError;
	private String autoPaySchemeAsString;
	private String extendedFields;

	public Long getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(Long documentId)
	{
		this.documentId = documentId;
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getStateCode()
	{
		return stateCode;
	}

	public void setStateCode(String stateCode)
	{
		this.stateCode = stateCode;
	}

	public String getStateDescription()
	{
		return stateDescription;
	}

	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	public Long getFormId()
	{
		return formId;
	}

	public void setFormId(Long formId)
	{
		this.formId = formId;
	}

	public String getKind()
	{
		return kind;
	}

	public void setKind(String kind)
	{
		this.kind = kind;
	}

	public Calendar getDateCreated()
	{
		return dateCreated;
	}

	public void setDateCreated(Calendar dateCreated)
	{
		this.dateCreated = dateCreated;
	}

	public Boolean getLongOffer()
	{
		return longOffer;
	}

	public void setLongOffer(Boolean longOffer)
	{
		this.longOffer = longOffer;
	}

	public String getChargeOffAccount()
	{
		return chargeOffAccount;
	}

	public void setChargeOffAccount(String chargeOffAccount)
	{
		this.chargeOffAccount = chargeOffAccount;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public String getReceiverAccount()
	{
		return receiverAccount;
	}

	public void setReceiverAccount(String receiverAccount)
	{
		this.receiverAccount = receiverAccount;
	}

	public String getDocumentNumber()
	{
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber)
	{
		this.documentNumber = documentNumber;
	}

	public String getSystemName()
	{
		return systemName;
	}

	public void setSystemName(String systemName)
	{
		this.systemName = systemName;
	}

	public BigDecimal getCommission()
	{
		return commission;
	}

	public void setCommission(BigDecimal commission)
	{
		this.commission = commission;
	}

	public String getCommissionCurrency()
	{
		return commissionCurrency;
	}

	public void setCommissionCurrency(String commissionCurrency)
	{
		this.commissionCurrency = commissionCurrency;
	}

	public Boolean getArchive()
	{
		return archive;
	}

	public void setArchive(Boolean archive)
	{
		this.archive = archive;
	}

	public String getCreationType()
	{
		return creationType;
	}

	public void setCreationType(String creationType)
	{
		this.creationType = creationType;
	}

	public String getClientOperationChannel()
	{
		return clientOperationChannel;
	}

	public void setClientOperationChannel(String clientOperationChannel)
	{
		this.clientOperationChannel = clientOperationChannel;
	}

	public String getRefusingReason()
	{
		return refusingReason;
	}

	public void setRefusingReason(String refusingReason)
	{
		this.refusingReason = refusingReason;
	}

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public Calendar getAdmissionDate()
	{
		return admissionDate;
	}

	public void setAdmissionDate(Calendar admissionDate)
	{
		this.admissionDate = admissionDate;
	}

	public Calendar getExecutionDate()
	{
		return executionDate;
	}

	public void setExecutionDate(Calendar executionDate)
	{
		this.executionDate = executionDate;
	}

	public Calendar getDocumentDate()
	{
		return documentDate;
	}

	public void setDocumentDate(Calendar documentDate)
	{
		this.documentDate = documentDate;
	}

	public String getStateMachineName()
	{
		return stateMachineName;
	}

	public void setStateMachineName(String stateMachineName)
	{
		this.stateMachineName = stateMachineName;
	}

	public Long getTemplateId()
	{
		return templateId;
	}

	public void setTemplateId(Long templateId)
	{
		this.templateId = templateId;
	}

	public String getCreationSourceType()
	{
		return creationSourceType;
	}

	public void setCreationSourceType(String creationSourceType)
	{
		this.creationSourceType = creationSourceType;
	}

	public String getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	public void setConfirmStrategyType(String confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	public String getOperationUID()
	{
		return operationUID;
	}

	public void setOperationUID(String operationUID)
	{
		this.operationUID = operationUID;
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public String getPromoCode()
	{
		return promoCode;
	}

	public void setPromoCode(String promoCode)
	{
		this.promoCode = promoCode;
	}

	public Long getCreatedEmployeeLoginId()
	{
		return createdEmployeeLoginId;
	}

	public void setCreatedEmployeeLoginId(Long createdEmployeeLoginId)
	{
		this.createdEmployeeLoginId = createdEmployeeLoginId;
	}

	public Long getConfirmedEmployeeLoginId()
	{
		return confirmedEmployeeLoginId;
	}

	public void setConfirmedEmployeeLoginId(Long confirmedEmployeeLoginId)
	{
		this.confirmedEmployeeLoginId = confirmedEmployeeLoginId;
	}

	public String getCodeATM()
	{
		return codeATM;
	}

	public void setCodeATM(String codeATM)
	{
		this.codeATM = codeATM;
	}

	public String getAdditionalOperationChannel()
	{
		return additionalOperationChannel;
	}

	public void setAdditionalOperationChannel(String additionalOperationChannel)
	{
		this.additionalOperationChannel = additionalOperationChannel;
	}

	public String getLoginType()
	{
		return loginType;
	}

	public void setLoginType(String loginType)
	{
		this.loginType = loginType;
	}

	public String getPayerName()
	{
		return payerName;
	}

	public void setPayerName(String payerName)
	{
		this.payerName = payerName;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getExternalOfficeId()
	{
		return externalOfficeId;
	}

	public void setExternalOfficeId(String externalOfficeId)
	{
		this.externalOfficeId = externalOfficeId;
	}

	public String getExternalOwnerId()
	{
		return externalOwnerId;
	}

	public void setExternalOwnerId(String externalOwnerId)
	{
		this.externalOwnerId = externalOwnerId;
	}

	public String getPayerAccount()
	{
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount)
	{
		this.payerAccount = payerAccount;
	}

	public String getConfirmEmployee()
	{
		return confirmEmployee;
	}

	public void setConfirmEmployee(String confirmEmployee)
	{
		this.confirmEmployee = confirmEmployee;
	}

	public BigDecimal getChargeOffAmount()
	{
		return chargeOffAmount;
	}

	public void setChargeOffAmount(BigDecimal chargeOffAmount)
	{
		this.chargeOffAmount = chargeOffAmount;
	}

	public String getChargeOffCurrency()
	{
		return chargeOffCurrency;
	}

	public void setChargeOffCurrency(String chargeOffCurrency)
	{
		this.chargeOffCurrency = chargeOffCurrency;
	}

	public BigDecimal getDestinationAmount()
	{
		return destinationAmount;
	}

	public void setDestinationAmount(BigDecimal destinationAmount)
	{
		this.destinationAmount = destinationAmount;
	}

	public String getDestinationCurrency()
	{
		return destinationCurrency;
	}

	public void setDestinationCurrency(String destinationCurrency)
	{
		this.destinationCurrency = destinationCurrency;
	}

	public String getSummType()
	{
		return summType;
	}

	public void setSummType(String summType)
	{
		this.summType = summType;
	}

	public String getGround()
	{
		return ground;
	}

	public void setGround(String ground)
	{
		this.ground = ground;
	}

	public String getReceiverName()
	{
		return receiverName;
	}

	public void setReceiverName(String receiverName)
	{
		this.receiverName = receiverName;
	}

	public String getProviderExternalId()
	{
		return providerExternalId;
	}

	public void setProviderExternalId(String providerExternalId)
	{
		this.providerExternalId = providerExternalId;
	}

	public Long getReceiverInternalId()
	{
		return receiverInternalId;
	}

	public void setReceiverInternalId(Long receiverInternalId)
	{
		this.receiverInternalId = receiverInternalId;
	}

	public String getBillingDocumentNumber()
	{
		return billingDocumentNumber;
	}

	public void setBillingDocumentNumber(String billingDocumentNumber)
	{
		this.billingDocumentNumber = billingDocumentNumber;
	}

	public Long getCountError()
	{
		return countError;
	}

	public void setCountError(Long countError)
	{
		this.countError = countError;
	}

	public String getAutoPaySchemeAsString()
	{
		return autoPaySchemeAsString;
	}

	public void setAutoPaySchemeAsString(String autoPaySchemeAsString)
	{
		this.autoPaySchemeAsString = autoPaySchemeAsString;
	}

	public String getExtendedFields()
	{
		return extendedFields;
	}

	public void setExtendedFields(String extendedFields)
	{
		this.extendedFields = extendedFields;
	}
}
