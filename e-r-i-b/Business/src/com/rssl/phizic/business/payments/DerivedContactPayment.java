package com.rssl.phizic.business.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.documents.WithdrawMode;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.common.types.Money;
import org.w3c.dom.Document;

/**
 * @author Kosyakova
 * @ created 23.01.2007
 * @ $Author$
 * @ $Revision$
 */
//TODO Разобраться с этим классом! по идее это редактирование платежа. но ведетсебя как отзыв.
public class DerivedContactPayment extends GateExecutableDocument implements WithdrawDocument
{
	BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private void addAttribute(String name, String value)
	{
		setNullSaveAttributeStringValue(name, value);
	}

	private String getParentAttributeValue(String name, BusinessDocumentBase parent)
	{
		ExtendedAttribute attribute = parent.getAttribute(name);
		return attribute == null ? "" : attribute.getStringValue();
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		AbstractPaymentDocument parent = (AbstractPaymentDocument) getParent();
		addAttribute("recalled-document-date", DateHelper.toString(DateHelper.toDate(parent.getClientCreationDate())));
		addAttribute("recalled-document-number", parent.getDocumentNumber());
		addAttribute("payer-account", parent.getChargeOffAccount());
		addAttribute("amount", parent.getChargeOffAmount().getDecimal().toString());
		addAttribute("currency", getParentAttributeValue("currency", parent));
		addAttribute("receiver-sur-name", getParentAttributeValue("receiver-sur-name", parent));
		addAttribute("receiver-first-name", getParentAttributeValue("receiver-first-name", parent));
		addAttribute("receiver-patr-name", getParentAttributeValue("receiver-patr-name", parent));
		addAttribute("receiver-bank-code", getParentAttributeValue("receiver-bank-code", parent));
		addAttribute("receiver-bank-name", getParentAttributeValue("receiver-bank-name", parent));
		addAttribute("receiver-bank-phone", getParentAttributeValue("receiver-bank-phone", parent));
		addAttribute("receiver-bank-city", getParentAttributeValue("receiver-bank-city", parent));
		addAttribute("receiver-bank-address", getParentAttributeValue("receiver-bank-address", parent));
		addAttribute("add-information", getParentAttributeValue("add-information", parent));
		addAttribute("parent-id", parent.getId().toString());
	}

	private GateExecutableDocument getParent() throws DocumentException
	{
		ExtendedAttribute parentIdAttr = getAttribute("parent-id");
		if (parentIdAttr == null)
			throw new DocumentException("Не задан parentId");
		try
		{
			return (GateExecutableDocument) businessDocumentService.findById(Long.parseLong(parentIdAttr.getStringValue()));
		}
		catch (BusinessException e)
		{
			throw new DocumentException("не найден платеж id=" + parentIdAttr.getStringValue(), e);
		}
	}

	protected boolean ownRestCheck()
	{
		return true;
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		ExtendedAttribute surNameAttribute = getAttribute("receiver-sur-name");
		ExtendedAttribute firstNameAttribute = getAttribute("receiver-first-name");
		ExtendedAttribute patrNameAttribute = getAttribute("receiver-patr-name");
//		setReceiverName(surNameAttribute.getStringValue() + " " + firstNameAttribute.getStringValue() + " " + patrNameAttribute.getStringValue());
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.EXTERNAL_PAYMENT_OPERATION;
	}

	/**
	 * Фактичский тип документа
	 *
	 * @return фактичский тип документа
	 */
	public Class<? extends GateDocument> getType()
	{
		return WithdrawDocument.class;
	}

	/**
	 * Внешний ID отзываемого документа
	 * Domain: ExternalID
	 *
	 * @return id
	 */
	public String getWithdrawExternalId()
	{
		try
		{
			return getParent().getExternalId();
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void setWithdrawExternalId(String withdrawExternalId)
	{
		try
		{
			getParent().setExternalId(withdrawExternalId);
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Внутренний ID отзываемого документа
	 * Domain: InternalID
	 *
	 * @return id
	 */
	public Long getWithdrawInternalId()
	{
		try
		{
			return getParent().getId();
		}
		catch (DocumentException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Фактический тип отзываемого документа
	 *
	 * @return фактичский тип отзываемого документа
	 */
	public Class<? extends GateDocument> getWithdrawType()
	{
		return com.rssl.phizic.gate.payments.systems.contact.ContactPayment.class;
	}

	public WithdrawMode getWithdrawMode()
	{
		return null;
	}

	public GateDocument getTransferPayment()
	{
		try
		{
			return getParent();
		}
		catch (DocumentException ignore)
		{
			return null;
		}
	}

	public Money getChargeOffAmount()
	{
		return null;
	}

	public String getIdFromPaymentSystem()
	{
		return null;
	}

	public void setIdFromPaymentSystem(String id)
	{
	}

	public void setAuthorizeCode(String authorizeCode)
	{
	}

	public String getAuthorizeCode()
	{
		return null;
	}
}
