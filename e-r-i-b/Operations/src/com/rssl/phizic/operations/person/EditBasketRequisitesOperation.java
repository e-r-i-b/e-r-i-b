package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.business.userDocuments.UserDocument;
import com.rssl.phizic.business.userDocuments.UserDocumentService;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.person.Person;

/**
 * �������������� ���������� ������� �������
 * @author miklyaev
 * @ created 04.06.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketRequisitesOperation extends PersonExtendedInfoOperationBase implements EditEntityOperation<UserDocument, UserRestriction>
{
	private static final ProfileService profileService = new ProfileService();
	private static final PersonService simplePersonService = new PersonService();
	private static final UserDocumentService userDocumentService = UserDocumentService.get();

	private static final String DRIVER_LICENSE_NAME = "������������ �������������";
	private static final String REGISTRATION_CERTIFICATE_NAME = "������������� � ����������� ������������� ��������";


	private UserDocument entityRC;
	private UserDocument entityDL;
	private ActivePerson person;
	private Profile profile;

	/**
	 * ������������� ��������
	 * @param personId = ������������� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long personId) throws BusinessException, BusinessLogicException
	{
		Person temp = simplePersonService.findById(personId);
		if (temp == null)
			throw new BusinessException("������ � �������� id �� ������");

		UserRestriction restriction = getRestriction();
		if (!restriction.accept(temp))
			throw new BusinessLogicException("�� �� ������ �������� � ������ ��������");

		this.person = (ActivePerson) temp;

		profile = profileService.findByLogin(person.getLogin());
		if (profile == null)
			throw new BusinessException("�� ���������� ������� ��� ������� �������");

		entityDL = userDocumentService.getUserDocumentByLoginAndType(person.getLogin().getId(), DocumentType.DL);
		if (entityDL == null)
		{
			entityDL = new UserDocument();
			entityDL.setLoginId(person.getLogin().getId());
			entityDL.setDocumentType(DocumentType.DL);
			entityDL.setName(DRIVER_LICENSE_NAME);
		}
		entityRC = userDocumentService.getUserDocumentByLoginAndType(person.getLogin().getId(), DocumentType.RC);
		if (entityRC == null)
		{
			entityRC = new UserDocument();
			entityRC.setLoginId(person.getLogin().getId());
			entityRC.setDocumentType(DocumentType.RC);
			entityRC.setName(REGISTRATION_CERTIFICATE_NAME);
		}
	}

	/**
	 * @return �������
	 */
	public Profile getProfile()
	{
		return profile;
	}

	/**
	 * @return �������
	 */
	public ActivePerson getPerson()
	{
		return person;
	}

	public void saveRC() throws BusinessException, BusinessLogicException
	{
		userDocumentService.addOrUpdate(entityRC);
	}

	public void saveDL() throws BusinessException, BusinessLogicException
	{
		userDocumentService.addOrUpdate(entityDL);
	}

	public UserDocument getEntityDL() throws BusinessException, BusinessLogicException
	{
		return entityDL;
	}

	public UserDocument getEntityRC() throws BusinessException, BusinessLogicException
	{
		return entityRC;
	}

	/**
	 * ������ �������� ��������
	 * @param entity - ������������ �������������
	 */
	public void setEntityDL(UserDocument entity)
	{
		this.entityDL = entity;
	}

	public void setEntityRC(UserDocument entity)
	{
		this.entityRC = entity;
	}

	/**
	 * ������� ������������� � ����������� ������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void removeRC() throws BusinessException, BusinessLogicException
	{
		userDocumentService.remove(entityRC);
		entityRC = new UserDocument();
		entityRC.setLoginId(person.getLogin().getId());
		entityRC.setDocumentType(DocumentType.RC);
		entityRC.setName(REGISTRATION_CERTIFICATE_NAME);
	}

	/**
	 * ������� ������������ �������������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	*/
	public void removeDL() throws BusinessException, BusinessLogicException
	{
		userDocumentService.remove(entityDL);
		entityDL = new UserDocument();
		entityDL.setLoginId(person.getLogin().getId());
		entityDL.setDocumentType(DocumentType.DL);
		entityDL.setName(DRIVER_LICENSE_NAME);
	}

	public UserDocument getEntity() throws BusinessException, BusinessLogicException
	{
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
