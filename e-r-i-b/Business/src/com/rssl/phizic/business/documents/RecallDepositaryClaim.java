package com.rssl.phizic.business.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Document;

/**
 * @author mihaylov
 * @ created 15.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class RecallDepositaryClaim extends GateExecutableDocument implements com.rssl.phizic.gate.claims.RecallDepositaryClaim
{
	private static BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	public static final String CLAIM_EXTERNAL_ID_ATTRIBUTE_NAME = "claim-external-id";
	public static final String REVOKE_PURPOSE_ATTRIBUTE_NAME = "revoke-purpose";
	public static final String DOC_ID_ATTRIBUTE_NAME = "recall-document-id";

	public static final String DOC_FORM_NAME_ATTRIBUTE_NAME = "document-form-name";
	public static final String DOC_TYPE_ATTRIBUTE_NAME = "document-type";
	public static final String DOC_NUMBER_ATTRIBUTE_NAME = "recall-document-number";
	public static final String DOC_DATE_ATTRIBUTE_NAME = "recall-document-date";
	public static final String DOC_DEPO_ACCOUNT_ATTRIBUTE_NAME = "depo-account";
	public static final String DOC_DEPO_EXTERNAL_ID_ATTRIBUTE_NAME = "depo-external-id";
	public static final String DEPONENT_FIO_ATTRIBUTE_NAME = "deponent-fio";

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.RecallDepositaryClaim.class;
	}

	public String getDocNumber()
	{
		return getNullSaveAttributeStringValue(CLAIM_EXTERNAL_ID_ATTRIBUTE_NAME);
	}

	public String getRevokePurpose()
	{
		return getNullSaveAttributeStringValue(REVOKE_PURPOSE_ATTRIBUTE_NAME);
	}

	public Long getRecallDocumentID()
	{
		String recallDocumentId = getNullSaveAttributeStringValue(DOC_ID_ATTRIBUTE_NAME);
		return StringHelper.isEmpty(recallDocumentId) ? null : Long.parseLong( recallDocumentId );
	}

	/**
	 * @return внешний id счета депо
	 */
	public String getDepoExternalId()
	{
		return getNullSaveAttributeStringValue(DOC_DEPO_EXTERNAL_ID_ATTRIBUTE_NAME);
	}
	/**
	 * @return номер отзываемого документа
	 */
	public String getRecallDocNumber()
	{
		return getNullSaveAttributeStringValue(DOC_NUMBER_ATTRIBUTE_NAME);
	}

	/**
	 * @return счет депо
	 */
	public String getRecallDepoAccount()
	{
		return getNullSaveAttributeStringValue(DOC_DEPO_ACCOUNT_ATTRIBUTE_NAME);
	}
	
	/**
	 * @return тип отзываемого документа
	 */
	public String getRecallDocumentType()
	{
		return getNullSaveAttributeStringValue(DOC_TYPE_ATTRIBUTE_NAME );
	}

	public void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException
	{
		super.initialize(document, messageCollector);
		AbstractDepoAccountClaim recallDocument = getRecallDocument();
		setNullSaveAttributeStringValue(DOC_FORM_NAME_ATTRIBUTE_NAME, recallDocument.getFormName());
		setNullSaveAttributeStringValue(DOC_NUMBER_ATTRIBUTE_NAME, recallDocument.getDocumentNumber());
		setNullSaveAttributeStringValue(CLAIM_EXTERNAL_ID_ATTRIBUTE_NAME, recallDocument.getExternalId());
		setNullSaveAttributeStringValue(DOC_DATE_ATTRIBUTE_NAME, getDateFormat().format(DateHelper.toDate(recallDocument.getDocumentDate())));
		setNullSaveAttributeStringValue(DOC_DEPO_ACCOUNT_ATTRIBUTE_NAME, recallDocument.getDepoAccountNumber());
		setNullSaveAttributeStringValue(DEPONENT_FIO_ATTRIBUTE_NAME, recallDocument.getDepositor());
		setNullSaveAttributeStringValue(DOC_DEPO_EXTERNAL_ID_ATTRIBUTE_NAME, recallDocument.getDepoExternalId());
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}

	private AbstractDepoAccountClaim getRecallDocument()
	{
		String recallDocumentId = getNullSaveAttributeStringValue(DOC_ID_ATTRIBUTE_NAME);
		if (StringHelper.isEmpty(recallDocumentId))
			throw new RuntimeException("Не задан " + DOC_ID_ATTRIBUTE_NAME);
		try
		{
			BusinessDocument document = businessDocumentService.findById(Long.parseLong(recallDocumentId));
			CommonLogin login = AuthModule.getAuthModule().getPrincipal().getLogin();

			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			if(!login.equals(documentOwner.getLogin()))//проверяем, что отзываем свой документ
			{
				throw new AccessException("Ошибка доступа. У вас нет прав для просмотра объекта.");
			}
			if (!(document instanceof AbstractDepoAccountClaim))
			{
				throw new RuntimeException("Неверный тип отзываемого документа " + document.getClass() +
						". Данный документ служит для отзыва только документов по депозитарию");
			}
			if (!document.getState().equals(new State("DISPATCHED")))
			{
				throw new RuntimeException("Неверный статус отзываемого документа: " + document.getState());
			}
			return (AbstractDepoAccountClaim) document;
		}
		catch (BusinessException e)
		{
			throw new RuntimeException("Не найден документ id=" + recallDocumentId, e);
		}
	}
}
