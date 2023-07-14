package com.rssl.phizic.web.ext.sbrf.history;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.type.PersonOldIdentity;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.ermb.person.identity.PersonIdentityHistoryListOperation;
import com.rssl.phizic.operations.ermb.person.identity.RemovePersonIdentityHistoryOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.persons.PersonUtils;

import java.util.List;

/**
 * User: moshenko
 * Date: 23.03.2013
 * Time: 23:35:18
 */
public class PersonHistoryIdentityListAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		PersonIdentityHistoryListOperation operation = createOperation(PersonIdentityHistoryListOperation.class);
		operation.initialize(PersonUtils.getPersonId(currentRequest()));
		return operation;
	}

	protected RemoveEntityOperation createRemoveOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(RemovePersonIdentityHistoryOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return FormBuilder.EMPTY_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase frm, ListEntitiesOperation operation) throws Exception
	{
		PersonHistoryIdentityListForm form = (PersonHistoryIdentityListForm) frm;
		PersonIdentityHistoryListOperation op = (PersonIdentityHistoryListOperation) operation;
		List<PersonOldIdentity> data = form.getData();
		form.setData(op.appendCurrentIdentity(data));
		form.setActivePerson(op.getPerson());
	}
}
