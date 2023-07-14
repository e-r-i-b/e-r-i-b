package com.rssl.phizic.business.persons.dbserialize;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.MultiInstanceSecurityService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.MultiInstanceAccessPolicyService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.MultiInstanceSimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.departments.MultiInstanceDepartmentService;
import com.rssl.phizic.business.dictionaries.MultiInstancePaymentReceiverService;
import com.rssl.phizic.business.operations.OperationDescriptorService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.MultiInstancePersonService;
import com.rssl.phizic.business.resources.ResourceService;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.business.resources.own.MultiInstanceSchemeOwnService;
import com.rssl.phizic.business.schemes.MultiInstanceAccessSchemeService;
import com.rssl.phizic.business.services.MultiInstanceServiceService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.password.UserPassword;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 09.07.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� �������, � ��� �������� �� ������� �� ��������� �� ������ ��������� ��
 */
public class CopyPersonHelper
{
	private static final MultiInstanceAccessSchemeService accessSchemeService = new MultiInstanceAccessSchemeService();
	private static final MultiInstancePersonService personService = new MultiInstancePersonService();
	private static final MultiInstanceSecurityService securityService = new MultiInstanceSecurityService();
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();
	private static final MultiInstanceSimpleService simpleService = new MultiInstanceSimpleService();
	private static final MultiInstanceDepartmentService departmentService = new MultiInstanceDepartmentService();
	private static final MultiInstanceSchemeOwnService schemeOwnService = new MultiInstanceSchemeOwnService();
	private static final MultiInstancePaymentReceiverService paymentReceiverService = new MultiInstancePaymentReceiverService();
	private static final MultiInstanceAccessPolicyService accessPolicyService = new MultiInstanceAccessPolicyService();
	private static final OperationDescriptorService operationDescriptorService = new OperationDescriptorService();
	private static final MultiInstanceServiceService serviceService             = new MultiInstanceServiceService();
	private static final ResourceService resourceService            = new ResourceService();

	/**
	 * ������� ������ ����� ������� � ��������� � ��� ��������� �� �������� �� �� ������ ���������
	 * � �� ���������� ������� ������ ������ ��������������.
	 * @param to ��� ���������� ���������, �� ������ ���� null ��� ��� ��������� ����������
	 * @param personCopy ������������ ��� �����������
	 * @throws BusinessException ������ ��� �����������
	 */
	public static void copy(final ActivePerson personCopy,final String from,final String to) throws BusinessException
	{
		//todo linkPINOperation
		//todo ServiceExistValidator
		//todo AssignPersonAccessOperation - ������������ ����� �� ���������� �� personOperationBase
		//todo PINService
		//todo RegisterClientOperation
	    try
	    {
		    //�������� ��, ��� � ������
		    final ActivePerson person = (ActivePerson)personService.findById(personCopy.getId(), from);
		    final CommonLogin login = securityService.findById(person.getLogin().getId(),from);
		    final UserPassword userPassword = securityService.getPassword(person.getLogin(), from);
		    
		    HibernateExecutor.getInstance(to).execute(new HibernateAction<Void>()
		    {
			    public Void run(Session session) throws Exception
			    {				    
				    boolean isEmpowered = (person.getTrustingPersonId()!=null);

				    if(!isEmpowered)
				    {
						Department department = departmentService.findById(person.getDepartmentId(), from);
					    replicateDepartment(department, to);
				    }

					try
					{
						securityService.replicate(person.getLogin(),to);
						simpleService.replicate(userPassword, to);
					}
					catch(SecurityDbException ex)
					{
						throw new BusinessException(ex);
					}

				    //������� � ����������� �������� � �����, ���� �������� BUG011371
				    ActivePerson personClone = new ActivePerson(person);
					simpleService.replicate(personClone,to);
					externalResourceService.replicate(person.getLogin(), from, to);

				    //�������� ������ � ���. ��,  ��� ��� � �������� �������, ��� ��� ���������
				    if(to!=null && !isEmpowered)
				    {
					    resourceService.replicateAll(to);
						operationDescriptorService.replicateAll(to);
						serviceService.replicateAll(to,from);
					    accessSchemeService.replicateAll(to, from);
				    }
					schemeOwnService.replicate(login, AccessType.simple, from, to);
					schemeOwnService.replicate(login, AccessType.secure, from, to);
				    schemeOwnService.replicate(login, AccessType.smsBanking, from, to);
				    try
					{
						accessPolicyService.replicateAccessPolicySetting(login,AccessType.simple,from,to);
						accessPolicyService.replicateAccessPolicySetting(login,AccessType.secure,from,to);
						accessPolicyService.replicateAccessPolicySetting(login,AccessType.smsBanking, from, to);
					}
					catch(SecurityDbException ex)
					{
						throw new BusinessException(ex);
					}
				    if(!isEmpowered)
				    {
						paymentReceiverService.replicate(person.getLogin(),from, to);

						List<ActivePerson> empoweredPersons = personService.getEmpoweredPersons(person, from);
						for (ActivePerson empoweredPerson : empoweredPersons)
						{
							if(Person.ACTIVE.equals(empoweredPerson.getStatus()))
								CopyPersonHelper.copy(empoweredPerson, from, to);
						}
				    }

				    return null;
			    }
		    });
	    }
	    catch (Exception e)
	    {
		    throw new BusinessException(e);
	    }
	}

	//���������� ����������� ���� ���������.
	private static void replicateDepartment(Department department, String to) throws BusinessException {
		if (department==null){
			return;
		}
		replicateDepartment(new DepartmentService().getParent(department), to);
		departmentService.replicate(department, to);
	}

	/**
	 * ������� ����� �������
	 * @param instanceName ��������� �� ��� �������
	 * @param person ������, ������� ������ ���� ������
	 * @throws BusinessException
	 */
	public static void deleteCopy(final String instanceName,final Person person) throws BusinessException
	{
		//������ ��� � ����� ����������
	    try
	    {
		    HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
			    public Void run(Session session) throws Exception
			    {
				    if(Person.DELETED.equals( person.getStatus()))
				    {
					    simpleService.remove(person, instanceName);
					    return null;
				    }
					ActivePerson instancePerson = (ActivePerson)personService.findById(person.getId(),instanceName);
					externalResourceService.removeAll(instancePerson.getLogin(), instanceName);
					schemeOwnService.removeAllSchemes(instancePerson.getLogin(), instanceName);
				    securityService.removePassword(instancePerson.getLogin(), instanceName);
					try
					{
						accessPolicyService.removeAllLoginSettings(instancePerson.getLogin(), instanceName);
					}
					catch(SecurityDbException ex)
					{
						throw new BusinessException(ex);
					}				    
				    if(instancePerson.getTrustingPersonId()==null)
				    {
						List<Person> empoweredPersons = personService.getAllEmpoweredPersons(instancePerson, instanceName);
						for (Person empoweredPerson : empoweredPersons)
						{
							CopyPersonHelper.deleteCopy(instanceName, empoweredPerson);
						}
						paymentReceiverService.removeAll(instancePerson.getLogin(), instanceName);
				    }
				    // ������� ������� ��� ������������ ����������, ����� ����� ���� ������� ����� �������
				    Calendar blockUntil = new GregorianCalendar();

				    simpleService.removeList(securityService.getDelayedBlocks(instanceName, blockUntil.getTime()), instanceName);
				    personService.delete(instancePerson, instanceName);
				    securityService.removeLastBlockers(instancePerson.getLogin(), instanceName);

				    return null;
			    }
		    });
	    }
	    catch (Exception e)
	    {
		    throw new BusinessException(e);
	    }
	}

	public static boolean isCopyExists(ActivePerson person, String instanceName) throws BusinessException
	{
		return personService.findById(person.getId(),instanceName) != null;
	}
}
