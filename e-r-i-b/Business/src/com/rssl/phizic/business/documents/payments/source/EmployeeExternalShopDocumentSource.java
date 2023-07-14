package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.exceptions.NotOwnDocumentException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.validators.DocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.IsOwnDocumentValidator;
import com.rssl.phizic.business.documents.payments.validators.NullDocumentValidator;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.person.Person;
import org.apache.commons.lang.ObjectUtils;

/**
 * —орс дл€ платежей по технологии e-invoicing через сотрудника
 * @author niculichev
 * @ created 21.08.15
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeExternalShopDocumentSource extends ExternalShopDocumentSource
{
	private static final PersonService personService = new PersonService();

	/**
	 * ctor
	 * @param orderUUID - UUID заказа
	 * @param initialValuesSource  »сточник значеий полей дл€ работы с формами
	 */
	public EmployeeExternalShopDocumentSource(Long personId, String orderUUID, FieldValuesSource initialValuesSource) throws BusinessException, BusinessLogicException
	{
		super(personId, orderUUID, initialValuesSource, CreationType.internet, CreationSourceType.ordinary);
	}

	protected DocumentSource createNewDelegateSource(String formName, FieldValuesSource valuesSource, CreationType type, CreationSourceType creationSourceType) throws BusinessLogicException, BusinessException
	{
		return new NewDocumentSource(formName, valuesSource, type, creationSourceType)
		{
			protected Long getNodeTemporaryNumber()
			{
				return null;
			}

			protected Department getDepartment() throws DocumentException
			{
				try
				{
					return personService.getDepartmentByPersonId(getPersonId());
				}
				catch (BusinessException e)
				{
					throw new DocumentException(e);
				}
			}

			protected LoginType getLoginType()
			{
				return null;
			}
		};
	}

	protected DocumentSource createExistDelegateSource(JurPayment document) throws BusinessLogicException, BusinessException
	{
		return new ExistingSource(document, getExistDocumentValidator());
	}

	private DocumentValidator getExistDocumentValidator()
	{
		return new DocumentValidator()
		{
			public void validate(BusinessDocument document) throws BusinessException, BusinessLogicException
			{
				BusinessDocumentOwner owner = document.getOwner();
				if(owner == null)
					return;

				if (!ObjectUtils.equals(owner.getPerson().getId(), getPersonId()))
					throw new NotOwnDocumentException("” пользовател€ c id = " + getPersonId() + " нет прав на просмотр документа с id=" + document.getId());
			}
		};
	}
}
