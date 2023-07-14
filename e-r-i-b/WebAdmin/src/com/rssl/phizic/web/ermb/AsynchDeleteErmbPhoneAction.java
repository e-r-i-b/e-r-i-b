package com.rssl.phizic.web.ermb;

import com.rssl.phizic.operations.ermb.person.ErmbProfileOperation;
import com.rssl.phizic.operations.ermb.person.ErmbProfileOperationEntity;
import com.rssl.phizic.utils.PhoneEncodeUtils;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AsynchDeleteErmbPhoneAction extends OperationalActionBase
{
	protected Map<String, String> getKeyMethodMap()
	{
		return new HashMap<String,String>();
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ErmbProfileOperation operation = createOperation(ErmbProfileOperation.class);

		AsynchDeleteErmbPhoneForm frm = (AsynchDeleteErmbPhoneForm) form;
		Long personId = Long.parseLong(frm.getProfileId());
		operation.initialize(personId, true);

		ErmbProfileOperationEntity entity = operation.getEntity();
		//Номер телефона для удаления в раскодированном виде
		Set<String> phonesToDelete = new HashSet<String>(1);
		//Номер телефона в закодированном виде
		Set<String> phoneToDecode = new HashSet<String>(1);

		phoneToDecode.add(frm.getPhoneCode());
		phonesToDelete.addAll(PhoneEncodeUtils.decodePhoneNumbers(phoneToDecode, entity.getPhonesNumber(), true));

		operation.sendConfirmMessage(entity.getProfile().getPerson(), phonesToDelete);
		return null;
	}

	protected boolean isAjax()
	{
		return true;
	}
}
