package com.rssl.phizic.web.client.deposits;

import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.operations.deposits.GetDepositListOperation;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.common.forms.Form;

import java.util.HashMap;
import java.util.Map;

/** User: IIvanova Date: 16.05.2006 Time: 12:59:05 */
public class DepositsAction extends ListActionBase
{
	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(GetDepositListOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return DepositsForm.FILTER_FORM;  
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws DataAccessException, BusinessException, BusinessLogicException
	{
		GetDepositListOperation op = (GetDepositListOperation) operation;
		frm.setData(op.getList());
		setFilter(op, filterParams);
		updateFormAdditionalData(frm, operation);
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws BusinessException, BusinessLogicException
	{
		DepositsForm            frm = (DepositsForm)            form;
		GetDepositListOperation op  = (GetDepositListOperation) operation;

		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		PersonData personData = provider.getPersonData();
		frm.setUser(personData.getPerson());
		frm.setDepositKinds(op.getDepositsKindList());
	}

	private void setFilter(GetDepositListOperation operation, Map<String, Object> filterParams)
	{
		Map<String, Object> result = new HashMap<String, Object>();
		for (Map.Entry<String,Object> entry : filterParams.entrySet())
		{
		    String name = entry.getKey();
		    Object value = entry.getValue();
		    if(value!=null)
		    	result.put(name, value);
		}
		BeanHelper.SetPropertiesFromMap(operation,result);

	}
}
