package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.BusinessException;

import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ListEntitiesOperation;

/**
 * @author Kosyakov
 * @ created 12.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */
public class GetMetaPaymentListOperation extends OperationBase implements ListEntitiesOperation
{
	private Long     loginId;
	private Metadata metadata;

	/**
	 * @param formName имя формы
	 */
	public void initialize(String formName) throws BusinessException
	{
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		ActivePerson person = provider.getPersonData().getPerson();

		loginId = person.getLogin().getId();

		if(formName == null)
			throw new IllegalArgumentException("formName не может быть null");

		metadata = MetadataCache.getBasicMetadata(formName);

	}

	/**
	 * @return ID логина клиента
	 */
    public Long getLoginId ()
    {
        return loginId;
    }


	/**
	 * @return метаданные платежа
	 */
	public Metadata getMetadata()
	{
		return metadata;
	}

	/**
	 * @return имя формы
	 */
	public String getFormName()
	{
		return metadata.getName();
	}
}
