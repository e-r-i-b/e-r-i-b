package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.documents.WithdrawMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Krenev
 * @ created 15.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class RecallDocument extends GateExecutableDocument implements WithdrawDocument
{
	public static final String PARENT_ID_ATTRIBUTE_NAME = "parent-id";
	public static final String AMOUNT_ATTRIBUTE_NAME = "amount";//сумма возврата

	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private GateExecutableDocument parent;
	private Money amount;
	private String billingDocumentNumber;

	public Money getChargeOffAmount()
	{
		return amount;
	}

	public String getBillingDocumentNumber()
	{
		return billingDocumentNumber;
	}

	public void setBillingDocumentNumber(String billingDocumentNumber)
	{
		this.billingDocumentNumber = billingDocumentNumber;
	}

	public String getIdFromPaymentSystem()
	{
		return getBillingDocumentNumber();
	}

	public void setIdFromPaymentSystem(String id)
	{
		setBillingDocumentNumber(id);
	}

	public void setAuthorizeCode(String authorizeCode)
	{
		setNullSaveAttributeStringValue(AUTHORIZE_CODE_ATTRIBUTE_NAME, authorizeCode);
	}

	public String getAuthorizeCode()
	{
		return getNullSaveAttributeStringValue(AUTHORIZE_CODE_ATTRIBUTE_NAME);
	}

	public void setChargeOffAmount(Money amount)
	{
		this.amount = amount;
	}

	public Class<? extends GateDocument> getType()
	{
		return WithdrawDocument.class;
	}

	public String getWithdrawExternalId()
	{
		return getParent().getExternalId();
	}

	public void setWithdrawExternalId(String withdrawExternalId)
	{
		 getParent().setExternalId(withdrawExternalId);
	}

	public Long getWithdrawInternalId()
	{
		return getParent().getId();
	}

	public Class<? extends GateDocument> getWithdrawType()
	{
		return getParent().getType();
	}

	public WithdrawMode getWithdrawMode()
	{
		return WithdrawMode.Full;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		GateExecutableDocument parent = getParent();
		try
		{
			BusinessDocumentOwner parentDocumentOwner = parent.getOwner();
			if (parentDocumentOwner != null)
				setExternalOwnerId(parentDocumentOwner.getPerson().getClientId());

			setNullSaveAttributeStringValue("recalled-document-number", parent.getDocumentNumber());
			setNullSaveAttributeStringValue("recalled-document-form-name", parent.getFormName());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}

//TODO ОЧЧЕЕЕНЬ плохо!!! подумать как формализовать
		if (!(parent instanceof AbstractPaymentDocument))
			return;
		AbstractPaymentDocument paymentDocument = (AbstractPaymentDocument) parent;
		setNullSaveAttributeStringValue("recalled-document-payer-account", paymentDocument.getChargeOffAccount());
		Money money = paymentDocument.getChargeOffAmount();
		if (money != null)
		{
			setNullSaveAttributeStringValue("recalled-document-amount", paymentDocument.getChargeOffAccount());
			setNullSaveAttributeStringValue("recalled-document-amount-currency", paymentDocument.getChargeOffAccount());
		}
	}

	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		if (getChargeOffAmount() != null)
		{
			Element root = document.getDocumentElement();
			appendNullSaveMoney(root, AMOUNT_ATTRIBUTE_NAME, getChargeOffAmount());
		}
		return document;
	}

	private GateExecutableDocument getParent()
	{
		if (parent != null)
			return parent;

		String parentIdAttr = getNullSaveAttributeStringValue(PARENT_ID_ATTRIBUTE_NAME);
		if (parentIdAttr == null)
			throw new RuntimeException("Не задан " + PARENT_ID_ATTRIBUTE_NAME);
		try
		{
			BusinessDocument document = businessDocumentService.findById(Long.parseLong(parentIdAttr));
			if (!(document instanceof GateExecutableDocument))
			{
				throw new RuntimeException("Неверный тип отзываемого документа " + document.getClass() + ". Должен быть GateExecutableDocument");
			}
			parent = (GateExecutableDocument) document;
			return parent;
		}
		catch (BusinessException e)
		{
			throw new RuntimeException("Не найден платеж id=" + parentIdAttr, e);
		}
	}

	public GateDocument getTransferPayment()
	{
		return getParent();
	}
}