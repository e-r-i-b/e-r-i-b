package com.rssl.phizic.operations.ermb.person.identity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.ErmbHelper;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.persons.oldIdentity.DuplicationIdentityException;
import com.rssl.phizic.business.persons.oldIdentity.PersonIdentityService;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.person.PersonDocumentType;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.List;

/**
 * User: moshenko
 * Date: 22.03.2013
 * Time: 13:38:08
 * �������������� ������� ��������� ����������������� ������ �������
 */
public class PersonIdentityHistoryEditOperation extends PersonIdentityHistoryOperationBase implements EditEntityOperation
{
	private static final PersonIdentityService PERSON_IDENTITY_SERVICE = new PersonIdentityService();
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private PersonOldIdentity personOldIdentity;
	private PersonDocumentType documentType;
	private String documentNumber;
	private String documentSeries;
	private boolean isSavePerson;

	/**
	 * @param personOldIdentityId �� ����������������� ������
	 * @param personId �� ������� �������
	 * @param isSavePerson ���� ���� ��� ��. ������������ ��� ���������� ������ �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long personOldIdentityId, Long personId,boolean isSavePerson) throws BusinessException, BusinessLogicException
	{
		super.initialize(personId);
		this.isSavePerson = isSavePerson;
		if (personOldIdentityId != null)
			personOldIdentity = PERSON_IDENTITY_SERVICE.getIdentityById(personOldIdentityId);
		else
			personOldIdentity = new PersonOldIdentity();

		Person person = getPerson();
		personOldIdentity.setPerson(person);
		personOldIdentity.setRegion(PersonHelper.getPersonTb(person));
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		boolean isMatch = is�oincidence();
		if(!isMatch)
			try
			{
				PERSON_IDENTITY_SERVICE.addOrUpdate(personOldIdentity);
			}
			catch (DuplicationIdentityException e)
			{
				throw new BusinessLogicException("����� ������ ��� ���� � �������. ���������� ����������", e);
			}
		else if (isMatch && !isSavePerson)
			throw new BusinessLogicException("�������� ������ ��������� � �������� ������� �������.");
	}

	/**
	 * @return true - ���� ��� ��� (�������) �� �������� ��������� � �������� ��� ������������� �������
	 */
	public boolean is�oincidence() throws BusinessException
	{
		personOldIdentity.setDocType(documentType);
		personOldIdentity.setDocNumber(documentNumber);
		personOldIdentity.setDocSeries(documentSeries);

		//�������� �� �� ��� �������� ������ �� ��������� � ��������
		Person activePerson = getPerson();
		String aName = activePerson.getFirstName();
		String aSurname = activePerson.getSurName();
		String aPatr = activePerson.getPatrName();
		Calendar aBirthDay = activePerson.getBirthDay();

		String hName = personOldIdentity.getFirstName();
		String hSurname = personOldIdentity.getSurName();
		String hPatr = personOldIdentity.getPatrName();
		Calendar hBirthDay = personOldIdentity.getBirthDay();
		String hSeriesAndNumber = StringHelper.getEmptyIfNull(personOldIdentity.getDocSeries()) + StringHelper.getEmptyIfNull(personOldIdentity.getDocNumber());

		PersonDocument doc = PersonHelper.getMainPersonDocument(activePerson);
		if (doc == null)
			return false;
		String aSeriesAndNumber = StringHelper.getEmptyIfNull(doc.getDocumentSeries()) + StringHelper.getEmptyIfNull(doc.getDocumentNumber());

		if  (StringHelper.equalsIgnoreCaseStrip(hSeriesAndNumber, aSeriesAndNumber) &&
				ErmbHelper.isSameFIOAndBirthday(aName, aSurname, aPatr, aBirthDay, hName, hSurname, hPatr, hBirthDay))
			return true;

		//��������� ������������ ������ �� ������� ����������
		for (PersonOldIdentity oldIdentity: PERSON_IDENTITY_SERVICE.getListByProfile(activePerson))
		{
			String oldName = oldIdentity.getFirstName();
			String oldSurname = oldIdentity.getSurName();
			String oldPatr = oldIdentity.getPatrName();
			Calendar oldBirthDay = oldIdentity.getBirthDay();
			String oldSeriesAndNumber = StringHelper.getEmptyIfNull(oldIdentity.getDocSeries()) + StringHelper.getEmptyIfNull(oldIdentity.getDocNumber());
			 if (StringHelper.equalsIgnoreCaseStrip(hSeriesAndNumber, oldSeriesAndNumber) &&
				ErmbHelper.isSameFIOAndBirthday(oldName, oldSurname, oldPatr, oldBirthDay, hName, hSurname, hPatr, hBirthDay))
			return true;
		}
		return false;
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return personOldIdentity;
	}

	/**
	 * @return �� ��������
	 */
	public Long getIdentityId()
	{
		return personOldIdentity.getId();
	}

	/**
	 * @return ������ ���� ����� ���������� �������
	 */
	public List<String> getDocumentTypes()
	{
		return PersonHelper.getDocumentStringTypes();
	}

	/**
	 * ���������� �������� �� save ���� ����������� ����� ���������
	 * @param documentType ���
	 * @param documentNumber �����
	 * @param documentSeries �����
	 */
	public void setDocument(String documentType, String documentNumber, String documentSeries)
	{
		this.documentType = PersonDocumentType.valueOf(documentType);
		this.documentNumber = documentNumber;
		this.documentSeries = documentSeries;
	}

	//�������� ���� ������� �������� �������
	private ErmbProfileImpl getErmbProfile() throws BusinessLogicException, BusinessException
	{
		ErmbProfileImpl ermbProfile = profileService.findByUser(getPerson());

		if (ermbProfile == null)
			throw new BusinessLogicException("������� ���� �� ������. ���������� �������� ������� ���������");

		return ermbProfile;
	}
}
