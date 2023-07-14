package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.exceptions.WrongDocumentTypeException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentSystemIdLink;

import java.util.List;

/**
 * @author krenev
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 * Сорц документа в адрес оплаты персоонального поставщика.
 * Происходит установка идентифкатора клиента в биллинге.
 */
public class PersonalProviderNewDocumentSource extends NewDocumentSource
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	public PersonalProviderNewDocumentSource(String formName, FieldValuesSource initialValuesSource, CreationType type, CreationSourceType creationSourceType) throws BusinessException, BusinessLogicException, DocumentException, DocumentLogicException
	{
		super(formName, initialValuesSource, type, creationSourceType);

		BusinessDocument document = getDocument();
		if (!(document instanceof JurPayment))
		{
			throw new WrongDocumentTypeException(document, JurPayment.class);
		}
		JurPayment payment = (JurPayment) document;
		payment.setBillingClientId(getBillingClientCode(document));
	}

	/**
	 * Получить идентификатор клиента владельца документа в биллинге
	 * @param document документ
	 * @return идентификатор
	 * @throws DocumentException кидается в случаее ниепредвидинных проблем или незаданном для клиента идентификаторе
	 */
	private String getBillingClientCode(BusinessDocument document) throws DocumentException, DocumentLogicException
	{
		try
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			CommonLogin commonLogin = documentOwner.getLogin();
			List<PaymentSystemIdLink> list = externalResourceService.getLinks(commonLogin, PaymentSystemIdLink.class);
			if (list.isEmpty())
			{
				throw new DocumentException("Для клиента " + commonLogin.getUserId() + " не задан идентфикатор в биллинге");
			}
			if (list.size() > 1)
			{
				throw new DocumentException("Для клиента " + commonLogin.getUserId() + " задано несколько идентфикатор в биллинге");
			}
			return list.get(0).getValue();
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}
}
