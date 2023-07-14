package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.depo.DeliveryType;
import com.rssl.phizic.gate.depo.TransferOperation;
import com.rssl.phizic.gate.depo.TransferSubOperation;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.counter.DefaultNamingStrategy;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author mihaylov
 * @ created 26.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class SecuritiesTransferClaim extends AbstractDepoAccountClaim implements com.rssl.phizic.gate.claims.SecuritiesTransferClaim
{
	public static final String OPER_TYPE_ATTRIBUTE_NAME = "operation-type";
	public static final String OPER_SUB_TYPE_ATTRIBUTE_NAME = "operation-sub-type";
	public static final String OPERATION_DESC_ATTRIBUTE_NAME = "operation-desc";
	public static final String DIVISION_TYPE_ATTRIBUTE_NAME = "division-type";
	public static final String DIVISION_NUMBER_ATTRIBUTE_NAME = "division-number";
	public static final String INSIDE_CODE_ATTRIBUTE_NAME = "inside-code";
	public static final String SECURITY_COUNT_ATTRIBUTE_NAME = "security-count";
	public static final String SECURITY_NAME_ATTRIBUTE_NAME = "security-name";
	public static final String SECURITY_NOMINAL_ATTRIBUTE_NAME = "nominal-amount";
	public static final String SECURITY_CURRENCY_CODE_ATTRIBUTE_NAME = "currency-code";
	public static final String OPERATION_REASON_ATTRIBUTE_NAME = "operation-reason";
	public static final String CORR_DEPOSITARY_ATTRIBUTE_NAME = "corr-depositary";
	public static final String CORR_DEPOSITARY_ACCOUNT_ATTRIBUTE_NAME = "corr-depo-account";
	public static final String CORR_DEPOSITARY_ACCOUNT_OWNER_ATTRIBUTE_NAME = "corr-depo-account-owner";
	public static final String ADDITIONAL_INFO_ATTRIBUTE_NAME = "additional-info";
	public static final String DELIVERY_TYPE_ATTRIBUTE_NAME = "delivery-type";
	public static final String REGISTRATION_NUMBER_ATTRIBUTE_NAME = "registration-number";


	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.SecuritiesTransferClaim.class;
	}

	public FormType getFormType()
	{
		return FormType.SECURITIES_TRANSFER_CLAIM;
	}

	public TransferOperation getOperType()
	{
		return getNullSaveAttributeEnumValue(TransferOperation.class, OPER_TYPE_ATTRIBUTE_NAME);
	}

	public TransferSubOperation getOperationSubType()
	{
		return getNullSaveAttributeEnumValue(TransferSubOperation.class, OPER_SUB_TYPE_ATTRIBUTE_NAME);
	}

	public String getOperationDesc()
	{
		return getNullSaveAttributeStringValue(OPERATION_DESC_ATTRIBUTE_NAME);
	}

	public String getDivisionType()
	{
		return getNullSaveAttributeStringValue(DIVISION_TYPE_ATTRIBUTE_NAME);
	}

	public String getDivisionNumber()
	{
		return getNullSaveAttributeStringValue(DIVISION_NUMBER_ATTRIBUTE_NAME);
	}

	public String getInsideCode()
	{
		return getNullSaveAttributeStringValue(INSIDE_CODE_ATTRIBUTE_NAME);
	}

	public String getSecurityName()
	{
		return getNullSaveAttributeStringValue(SECURITY_NAME_ATTRIBUTE_NAME);
	}

	public String getTransferAmount()
	{
		String nominalString = getNullSaveAttributeStringValue(SECURITY_NOMINAL_ATTRIBUTE_NAME);
		Long securityCount = getSecurityCount();
		if(nominalString == null || securityCount == null )
		{
			return null;
		}
		Double nominal = Double.parseDouble(nominalString);
		Double transferAmount = nominal * securityCount;
		return transferAmount.toString();
	}

	public String getTransferAmountCurrencyCode()
	{
		return getNullSaveAttributeStringValue(SECURITY_CURRENCY_CODE_ATTRIBUTE_NAME);
	}

	public Long getSecurityCount()
	{
		String securityCount = getNullSaveAttributeStringValue(SECURITY_COUNT_ATTRIBUTE_NAME);
		return StringHelper.isEmpty(securityCount) ? null :
		       Long.valueOf(securityCount);
	}

	public String getOperationReason()
	{
		return getNullSaveAttributeStringValue(OPERATION_REASON_ATTRIBUTE_NAME);
	}

	public String getCorrDepositary()
	{
		return getNullSaveAttributeStringValue(CORR_DEPOSITARY_ATTRIBUTE_NAME);
	}

	public String getCorrDepoAccount()
	{
		return getNullSaveAttributeStringValue(CORR_DEPOSITARY_ACCOUNT_ATTRIBUTE_NAME);
	}

	public String getCorrDepoAccountOwner()
	{
		return getNullSaveAttributeStringValue(CORR_DEPOSITARY_ACCOUNT_OWNER_ATTRIBUTE_NAME);
	}

	public String getAdditionalInfo()
	{
		return getNullSaveAttributeStringValue(ADDITIONAL_INFO_ATTRIBUTE_NAME);
	}

	public DeliveryType getDeliveryType()
	{
		return getNullSaveAttributeEnumValue(DeliveryType.class, DELIVERY_TYPE_ATTRIBUTE_NAME);
	}

	public String getRegistrationNumber()
	{
		return getNullSaveAttributeStringValue(REGISTRATION_NUMBER_ATTRIBUTE_NAME);  
	}

	public boolean equalsKeyEssentials(TemplateDocument template)
	{
		if (template == null)
		{
			return false;
		}
		return ObjectUtils.equals(getOperType(), template.getOperType())
			&& ObjectUtils.equals(getOperationSubType(), template.getOperationSubType())
			&& ObjectUtils.equals(getOperationDesc(), template.getOperationDesc())
			&& ObjectUtils.equals(getDepoAccountNumber(), template.getDepoAccountNumber())
			&& ObjectUtils.equals(getDivisionType(), template.getDivisionType())
			&& ObjectUtils.equals(getDivisionNumber(), template.getDivisionNumber())
			&& ObjectUtils.equals(getInsideCode(), template.getInsideCode())
			&& ObjectUtils.equals(getRegistrationNumber(), template.getRegistrationNumber())
			&& ObjectUtils.equals(getSecurityCount(), template.getSecurityCount())
			&& ObjectUtils.equals(getOperationReason(), template.getOperationReason())
			&& ObjectUtils.equals(getCorrDepositary(), template.getCorrDepositary())
			&& ObjectUtils.equals(getCorrDepoAccount(), template.getCorrDepoAccount())
			&& ObjectUtils.equals(getCorrDepoAccountOwner(), template.getCorrDepoAccountOwner())
			&& ObjectUtils.equals(getAdditionalInfo(), template.getAdditionalInfo())
			&& ObjectUtils.equals(getDeliveryType(), template.getDeliveryType());
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}

	public String getDefaultTemplateName() throws BusinessException, BusinessLogicException
	{
		try
		{
			Metadata metadata = MetadataCache.getBasicMetadata(getFormName());
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			return TemplateHelper.addCounter(new ClientImpl(documentOwner.getPerson()), getDefaultTemplateName(metadata), new DefaultNamingStrategy());
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}
}
