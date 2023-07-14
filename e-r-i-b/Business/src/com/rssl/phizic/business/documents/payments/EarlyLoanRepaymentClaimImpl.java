package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentsUtils;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.loans.*;
import com.rssl.phizic.gate.payments.loan.EarlyLoanRepaymentClaim;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Класс заявки  на досрочное погашение кредита
 * User: petuhov
 * Date: 14.05.15
 * Time: 12:13 
 */
public class EarlyLoanRepaymentClaimImpl extends AbstractPaymentDocument implements EarlyLoanRepaymentClaim
{
	private static final String OPER_UID_NAME = "oper-uid";
	private static final String LOAN_IDENTIFIER = "loan-identifier";
	private static final String PARTIAL = "partial";
	private static final String LOAN_LINK_ID = "loanLinkId";
	private static final String SELECTED_RESOURCE_REST_ATTRIBUTE_NAME = "selectedResourceRest";
	private LoanLink loanLink;

	public Class<? extends GateDocument> getType()
	{
		return EarlyLoanRepaymentClaim.class;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	public boolean isPartial()
	{
		return getNullSaveAttributeBooleanValue(PARTIAL);
	}

	public Calendar getRepaymentDate()
	{
		return getDocumentDate();
	}

	public String getLoanIdentifier()
	{
		return getNullSaveAttributeStringValue(LOAN_IDENTIFIER);
	}

	/**
	 * Просетить идентификатор погашаемого кредита в системе УКО
	 * @param identifier - идентификатор
	 */
	private void setLoanIdentifier(String identifier)
	{
		setNullSaveAttributeStringValue(LOAN_IDENTIFIER, identifier);
	}

	public Money getRepaymentAmount()
	{
		return this.getChargeOffAmount();
	}

	public void setOperUID(String currentOperUID)
	{
		setNullSaveAttributeStringValue(OPER_UID_NAME, currentOperUID);
	}

	public String getOperUID()
	{
		return getNullSaveAttributeStringValue(OPER_UID_NAME);
	}

	@Override
	public Set<ExternalResourceLink> getLinks() throws DocumentException
	{
		Set<ExternalResourceLink> links = super.getLinks();
		links.add(getLoanLink());
		return links;
	}

	@Override
	protected void readFromDom(Document document, InnerUpdateState updateState, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.readFromDom(document,updateState,messageCollector);
		setLoanIdentifier(getLoanLink().getLoan().getProdType());
	}

	@Override
	public Document convertToDom() throws DocumentException
	{
		Document document = super.convertToDom();
		Element root = document.getDocumentElement();
		appendNullSaveString(root, SELECTED_RESOURCE_REST_ATTRIBUTE_NAME, (this.getChargeOffResourceLink()!=null)?this.getChargeOffResourceLink().getRest().getDecimal().toString():"");
		return document;
	}

	/**
	 * получить идентификатор линка на кредит
	 * @return идентификатор
	 */
	public Long getLoanLinkId()
	{
		return getNullSaveAttributeLongValue(LOAN_LINK_ID);
	}

	/**
	 * Получает линк на кредит(загружает при необходимости)
	 * @return Линк на кредит
	 * @throws DocumentException
	 */
	public LoanLink getLoanLink() throws DocumentException
	{
		if (loanLink==null)
		{
			try
			{
				BusinessDocumentOwner documentOwner = getOwner();
				if (documentOwner.isGuest())
					throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
				loanLink = PersonContext.getPersonDataProvider().getPersonData().getLoan(getLoanLinkId());
			}
			catch (BusinessException e)
			{
				throw new DocumentException("Сбой при получении линка-на-кредит", e);
			}
			catch(BusinessLogicException e){
				throw new DocumentException("Сбой при получении линка-на-кредит", e);
			}
		}
		return loanLink;
	}

	/**
	 *  Получить код ТБ
	 *  @return код ТБ
	 */
	public String getTB() throws BusinessException
	{
		return DepartmentsUtils.getTbByDepartmentId(this.getOwner().getPerson().getDepartmentId());
	}

}
