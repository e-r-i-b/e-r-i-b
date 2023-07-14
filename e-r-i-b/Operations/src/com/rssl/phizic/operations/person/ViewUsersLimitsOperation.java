package com.rssl.phizic.operations.person;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.strategies.ClientAccumulateLimitsInfo;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.GroupRiskService;
import com.rssl.phizic.common.types.limits.ChannelType;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * @author vagin
 * @ created 26.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class ViewUsersLimitsOperation extends OperationBase implements ListEntitiesOperation
{
	private Person person;
	private ClientAccumulateLimitsInfo limitsInfo;
	private static final PersonService personService = new PersonService();
	private static final GroupRiskService groupRiskService = new GroupRiskService();
	private static final DepartmentService departmentService = new DepartmentService();

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	public void initialize(Long personId, ChannelType channelType) throws BusinessException, BusinessLogicException
	{
		Person pers = personService.findById(personId);
		if(pers!=null)
			this.person = pers;
		else
			throw new BusinessLogicException("Ќе найден клиентс id="+personId);

		this.limitsInfo = DocumentLimitManager.buildLimitAmountInfoByPerson(person, channelType);
	}

	/**
	 * @return накопленные суммы по лимитам дл€ текущего клиенат и канала
	 */
	public ClientAccumulateLimitsInfo getClientAccumulateLimitsInfo()
	{
		return limitsInfo;
	}

	public List<GroupRisk> getAllGroupRisk() throws BusinessException
	{
		return groupRiskService.getAllGroupsRisk();
	}

	/**
	 * @return номер тербанка подразделени€, к которому прив€зан клиент
	 * @throws BusinessException
	 */
	public String getTb() throws BusinessException
	{
		return departmentService.findById(person.getDepartmentId()).getRegion();
	}
}
