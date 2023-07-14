package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.persons.*;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.person.Person;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.09.2005
 * Time: 15:50:27
 */
public class RemovePersonOperation extends RemovePersonOperationBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	@Transactional
	public void remove() throws BusinessException, BusinessLogicException
    {
	    final ActivePerson personToRemove = getEntity();
	    if ( personToRemove == null )
            throw new IllegalStateException("�� ����������� ���� 'person' � ������� ������ 'RemovePersonOperation'");

	    String personStatus = personToRemove.getStatus();
	    if(personStatus.equals(Person.DELETED))
	        throw new InvalidPersonStateException(personToRemove, "���������� ������� ����������� �� �������� '������' ");

	    try
	    {
			unsubscribeAccounts();
		    unsubscribeSubscriptions(personToRemove.getLogin());

			if(personStatus.equals(Person.ACTIVE))
			{
				if(getNeedAgrementCancellation() && !isAgreementBroken(personToRemove))//���� ��������� ������ ��������.
				{
					throw new InvalidPersonStateException(personToRemove, "����� ��������� ���������� ����������� ��������");
				}

				certDemandMarkDeleted();
				certOwnMarkDeleted();

				personService.markDeleted(personToRemove);
				additionalClientsService.cleanClientIdsConnection(personToRemove.getClientId());
				recallByOwner(personToRemove.getLogin());
				return;//��������� ������� �� �������, ������ �������� ��� ����������.
			}

			HibernateExecutor.getInstance().execute(new HibernateAction<Object>()
			{
				private SecurityService securityService = new SecurityService();

				public Object run(Session session) throws Exception
				{
					DeleteCommonLoginQueryBuilder builder = new DeleteCommonLoginQueryBuilder();

					Query query   = session.getNamedQuery("com.rssl.phizic.operations.person.GetEmpoweredPersonsListOperation.list");
					query.setParameter("trustingPersonId", personToRemove.getId());
					//noinspection unchecked
					List<ActivePerson> empoweredPersons = (List<ActivePerson>) query.list();


					// ������� ���������� ���
					for ( ActivePerson empoweredPerson : empoweredPersons )
					{
						removePerson(builder, empoweredPerson);
						securityService.markDeleted(empoweredPerson.getLogin());
					}

					// ������� ������� �� ��
					removePerson(builder, personToRemove);
					securityService.markDeleted(personToRemove.getLogin());

					return null;
				}
			});
	    }
	    catch(BusinessLogicException e)
	    {
		    throw e;
	    }
	    catch (GateException e)
	    {
		    throw new BusinessException(e);
	    }
	    catch (GateLogicException e)
	    {
		    throw new BusinessLogicException(e);
	    }
	    catch (Exception e)
	    {
		    throw new BusinessException(e);
	    }
    }

	public ActivePerson getEntity()
	{
		return getPerson();
	}
}
