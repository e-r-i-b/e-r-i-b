package com.rssl.phizic.web.bannedAccount;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.bannedAccount.AccountBanType;
import com.rssl.phizic.business.bannedAccount.BannedAccount;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.bannedAccount.EditBannedAccountOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;

/**
 * @author: vagin
 * @ created: 01.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditBannedAccountAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditBannedAccountOperation operation = createOperation("EditBannedAccountOperation", "BannedAccountManagment");
		if (frm.getId() != null)
			operation.initialize(frm.getId());
		else
			operation.initializeNew();
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditBannedAccountForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		BannedAccount bannedAccount = (BannedAccount) entity;
		bannedAccount.setBanType(AccountBanType.valueOf((String) data.get("banType")));
		bannedAccount.setAccountNumber(convert((String) data.get("account"), '*', '_'));
		bannedAccount.setBicList((String) data.get("BICList"));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditBannedAccountForm form = (EditBannedAccountForm) frm;
		BannedAccount bannedAccount = (BannedAccount) entity;
		form.setField("account", convert(bannedAccount.getAccountNumber(), '_', '*'));
		form.setField("BICList", bannedAccount.getBicList());
		form.setField("banType", bannedAccount.getBanType());
	}

	/**
	 * Метод преобразования строки маски
	 * @param mask - значение маски счета
	 * @param charFrom - символ который необходимо заменить
	 * @param charTo - символ на который необходимо заменить
	 * @return преорбразованая маска для записи в базу либо для отображения на странице(банк желает видеть звездочки).
	 */
	private String convert(String mask, char charFrom, char charTo)
	{
		if (!StringHelper.isEmpty(mask))
			return mask.replace(charFrom, charTo);
		return null;
	}

	protected ActionForward doSave(EditEntityOperation operation, ActionMapping mapping, EditFormBase frm) throws Exception
	{
		addLogParameters(new BeanLogParemetersReader("Данные редактируемой сущности", operation.getEntity()));
		return super.doSave(operation, mapping, frm);
	}
}
