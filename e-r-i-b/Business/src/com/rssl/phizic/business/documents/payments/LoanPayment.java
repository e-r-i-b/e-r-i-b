package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AbstractLongOfferDocument;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.templates.TemplateHelper;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.loans.DateDebtItem;
import com.rssl.phizic.gate.loans.PenaltyDateDebtItemType;
import com.rssl.phizic.gate.loans.ScheduleItem;
import com.rssl.phizic.gate.payments.LoanTransfer;
import com.rssl.phizic.gate.payments.longoffer.LoanTransferLongOffer;
import com.rssl.phizic.utils.counter.DefaultNamingStrategy;
import org.w3c.dom.Document;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

/**
 * @author gladishev
 * @ created 05.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanPayment extends AbstractLongOfferDocument implements LoanTransfer, LoanTransferLongOffer
{
	public static final String LOAN_EXTERNAL_ID_ATTRIBUTE_NAME = "loan-external-id"; //externalId кредита
	public static final String LOAN_ACCOUNT_NUMBER_ATTRIBUTE_NAME = "loan-account-number"; //Номер ссудного счета
	public static final String AGREEMENT_NUMBER_ATTRIBUTE_NAME = "agreement-number"; //Номер кредитного договора
	public static final String PRINCIPAL_AMOUNT_ATTRIBUTE_NAME = "principal-amount"; //Сумма основного долга
	public static final String INTERESTS_AMOUNT_ATTRIBUTE_NAME = "interests-amount"; //Сумма выплат по процентам
	public static final String TOTAL_PAYMENT_ATTRIBUTE_NAME = "total-payment-amount"; //общая сумма
	public static final String LOAN_TYPE_ATTRIBUTE_NAME = "loan-type"; //тип кредита
	public static final String LOAN_NAME_ATTRIBUTE_NAME = "loan-name"; //название кредита
	public static final String LOAN_LINK_ID_ATTRIBUTE_NAME = "loan-link-id"; //LoanLink.id

	private String idSpacing;
	private Calendar spacingDate;

	public Class<? extends GateDocument> getType()
	{
		return isLongOffer() ? LoanTransferLongOffer.class : LoanTransfer.class;
	}

	public FormType getFormType()
	{
		return FormType.LOAN_PAYMENT;
	}

	public String getLoanExternalId()
	{
		return getNullSaveAttributeStringValue(LOAN_EXTERNAL_ID_ATTRIBUTE_NAME);
	}

	public String getAccountNumber()
	{
		return getNullSaveAttributeStringValue(LOAN_ACCOUNT_NUMBER_ATTRIBUTE_NAME);
	}

	public String getAgreementNumber()
	{
		return getNullSaveAttributeStringValue(AGREEMENT_NUMBER_ATTRIBUTE_NAME);
	}

	public String getLoanType()
	{
		return getNullSaveAttributeStringValue(LOAN_TYPE_ATTRIBUTE_NAME);
	}

	public String getLoanName()
	{
		return getNullSaveAttributeStringValue(LOAN_NAME_ATTRIBUTE_NAME);
	}

	public Long getLoanLinkId()
	{
		return (Long) getNullSaveAttributeValue(LOAN_LINK_ID_ATTRIBUTE_NAME);
	}

	public String getIdSpacing()
	{
		return idSpacing;
	}

	public void setIdSpacing(String idSpacing)
	{
		this.idSpacing = idSpacing;
	}

	public Calendar getSpacingDate()
	{
		return spacingDate;
	}

	public void setSpacingDate(Calendar spacingDate)
	{
		this.spacingDate = spacingDate;
	}

	public void setAuthorizeCode(String authorizeCode)
	{
	}

	public String getAuthorizeCode()
	{
		return null;
	}

	public Calendar getAuthorizeDate()
	{
		return null;
	}

	public void setAuthorizeDate(Calendar authorizeDate)
	{
	}

	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document, updateState, messageCollector);
		if (getChargeOffResourceType() == ResourceType.CARD)
		{
			try
			{
				if(isLongOffer())
				{
					setChargeOffCardExpireDate(getChargeOffCardExpireDate(ResourceType.CARD, messageCollector));
				}
				else
				{
					//Сохраняем инфу о сроке действия карты
					Card chargeOffCard = receiveChargeOffCard(messageCollector);
					setChargeOffCardExpireDate(chargeOffCard.getExpireDate());
				}
			}
			catch (DocumentLogicException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// при инициализации идем дальше, обновим нормальными данными в свое время
				log.warn(e);
			}
			catch (DocumentException e)
			{
				if(updateState != InnerUpdateState.INIT)
					throw e;
				// при инициализации идем дальше, обновим нормальными данными в свое время
				log.warn(e);
			}
		}
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}

	/**
	 * Сохранение разбивки платежа
	 * @param schedule - информация о выплатах по кредиту на указанную сумму
	 */
	public void addAmountSplitting(ScheduleItem schedule)
	{
		Map<PenaltyDateDebtItemType, DateDebtItem> debtsMap = schedule.getPenaltyDateDebtItemMap();
		addMoneyAttribute(PRINCIPAL_AMOUNT_ATTRIBUTE_NAME, schedule.getPrincipalAmount());
		addMoneyAttribute(INTERESTS_AMOUNT_ATTRIBUTE_NAME, schedule.getInterestsAmount());
		addMoneyAttribute(TOTAL_PAYMENT_ATTRIBUTE_NAME, schedule.getTotalPaymentAmount());
		for (PenaltyDateDebtItemType type : PenaltyDateDebtItemType.values())
		{
			addMoneyAttribute(type.name(), debtsMap.get(type));
		}
	}

	/**
	 * Записывает сумму задолженности
	 * @param name - название поля задолженности
	 * @param item - статья задолженности
	 */
	private void addMoneyAttribute(String name, DateDebtItem item)
	{
		if (item == null)
		{
			removeAttribute(name);
			return;
		}
		addMoneyAttribute(name, item.getAmount());
	}

	private void addMoneyAttribute(String name, Money value)
	{
		if (value == null)
		{
			removeAttribute(name);
			return;
		}

		setNullSaveAttributeDecimalValue(name,  value.getDecimal());
	}

	/**
	 * Вернуть ссылку на погашаемый кредит.
	 * Внимание: ссылка возвращается по текущему состоянию системы,
	 * т.е. может отсутствовать для старых документов (в данном случае возвращается null)
	 * @return ссылка или null, если линк удалён либо не указан
	 */
	public LoanLink getLoanLink() throws DocumentException
	{
		try
		{
			BusinessDocumentOwner documentOwner = getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			return externalResourceService.findInSystemLinkById(documentOwner.getLogin(), LoanLink.class, getLoanLinkId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException("Сбой при получении ссылки на погашаемый кредит", e);
		}
	}

	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		Set<ExternalResourceLink> links = super.getLinks();
		LoanLink loanLink = getLoanLink();
		if (loanLink != null)
		{
			links.add(loanLink);
		}
		return links;
	}

	public String getDefaultTemplateName() throws BusinessException, BusinessLogicException
	{
		if (isLongOffer())
		{
			return super.getDefaultTemplateName();
		}

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
