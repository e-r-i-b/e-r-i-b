package com.rssl.phizic.operations.sberbankForEveryDay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardService;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * просмотр заявки СБНКД
 * @author basharin
 * @ created 19.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ViewSBNKDClaimOperation extends OperationBase implements EditEntityOperation
{
	private static final IssueCardService service = new IssueCardService();
	private static final PersonService personService = new PersonService();
	private IssueCardDocumentImpl document;
	private ActivePerson person;

	public void initialize(Long id, Long personId) throws BusinessException, BusinessLogicException, GateException
	{
	 	document = service.getClaim(id);
		person =(ActivePerson) personService.findById(personId);
	}


	public void save() throws BusinessException, BusinessLogicException	{}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return document;
	}

	public ActivePerson getActivePerson()
	{
		return person;
	}
}
