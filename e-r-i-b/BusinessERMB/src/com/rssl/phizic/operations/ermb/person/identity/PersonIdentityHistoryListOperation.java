package com.rssl.phizic.operations.ermb.person.identity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.common.type.PersonOldIdentityStatus;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.person.PersonDocument;

import java.util.LinkedList;
import java.util.List;

/**
 * User: moshenko
 * Date: 23.03.2013
 * Time: 23:36:32
 */
public class PersonIdentityHistoryListOperation extends PersonIdentityHistoryOperationBase implements ListEntitiesOperation
{
	public static final long CURRENT_IDENTITY_MOCK_ID = -1L;

	/**
	 * @param curentList список старых идентификационных данных
	 * @return список старых идентификационных данных + текущие, если клиент не найден то пустой список
	 * @throws BusinessException
	 */
	public List<PersonOldIdentity> appendCurrentIdentity(List<PersonOldIdentity> curentList) throws BusinessException
	{
		LinkedList<PersonOldIdentity> oldIdentities = new LinkedList<PersonOldIdentity>();
		ActivePerson person = getPerson();
		if (person != null)
		{
			oldIdentities = new LinkedList<PersonOldIdentity>(curentList);

			//добавляем в список текущие данные клиента 
			PersonOldIdentity currentIdentity = new PersonOldIdentity();
			currentIdentity.setPerson(person);
			currentIdentity.setRegion(PersonHelper.getPersonTb(person));
			currentIdentity.setFirstName(person.getFirstName());
			currentIdentity.setSurName(person.getSurName());
			currentIdentity.setPatrName(person.getPatrName());
			currentIdentity.setBirthDay(person.getBirthDay());
			PersonDocument mainDocument = PersonHelper.getMainPersonDocument(person);
			currentIdentity.setDocType(mainDocument.getDocumentType());
			currentIdentity.setDocName(mainDocument.getDocumentName());
			currentIdentity.setDocNumber(mainDocument.getDocumentNumber());
			currentIdentity.setDocSeries(mainDocument.getDocumentSeries());
			currentIdentity.setDocIssueDate(mainDocument.getDocumentIssueDate());
			currentIdentity.setDocIssueBy(mainDocument.getDocumentIssueBy());
			currentIdentity.setDocIssueByCode(mainDocument.getDocumentIssueByCode());
			currentIdentity.setDocMain(mainDocument.getDocumentMain());
			currentIdentity.setDocTimeUpDate(mainDocument.getDocumentTimeUpDate());
			currentIdentity.setDocIdentify(mainDocument.isDocumentIdentify());
			currentIdentity.setStatus(PersonOldIdentityStatus.ACTIVE);
			currentIdentity.setDateChange(mainDocument.getDocumentTimeUpDate());
			currentIdentity.setId(CURRENT_IDENTITY_MOCK_ID);//-1 будем понимать что это текущие данные
			oldIdentities.addFirst(currentIdentity);
		}
		return oldIdentities;
	}
}
