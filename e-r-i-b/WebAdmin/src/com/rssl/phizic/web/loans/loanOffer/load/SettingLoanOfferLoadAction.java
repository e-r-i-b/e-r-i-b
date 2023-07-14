package com.rssl.phizic.web.loans.loanOffer.load;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanOffer.SettingLoanOfferLoad;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.loanOffer.SettingLoanOfferLoadOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.loans.loanOffer.autoLoad.SettingLoanOfferLoadForm;

import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 07.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SettingLoanOfferLoadAction extends EditActionBase
{
	private static final String DIRECTORY = "path";
	private static final String FILE_NAME = "file";

	/**
	 * —оздать и проинициализировать операцию (операци€ редактировани€).
	 * @param frm форма
	 * @return созданна€ операци€.
	 */
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		SettingLoanOfferLoadOperation operation = createOperation(SettingLoanOfferLoadOperation.class, "SetttingLoanLoanService");
		operation.init();
		return operation;
	}

	/**
	 * ¬ернуть форму редактировани€.
	 * ¬ большинстве случаев(до исправлени€ ENH00319) будет возвращатьс€ frm.EDIT_FORM
	 * @param frm struts-форма
	 * @return форма редактировани€
	 */
	protected Form getEditForm(EditFormBase frm)
	{
		return SettingLoanOfferLoadForm.EDIT_FORM;
	}

	/**
	 * ќбновить сужность данными.
	 * @param entity сужность
	 * @param data данные
	 */
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		SettingLoanOfferLoad settingLoanOfferLoad = (SettingLoanOfferLoad) entity;
		settingLoanOfferLoad.setDirectory((String) data.get(DIRECTORY));
		settingLoanOfferLoad.setFileName((String) data.get(FILE_NAME));
	}

	/**
	 * ѕроинициализировать/обновить struts-форму
	 * @param frm форма дл€ обновлени€
	 * @param entity объект дл€ обновлени€.
	 */
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		SettingLoanOfferLoad settingLoanOfferLoad = (SettingLoanOfferLoad) entity;
		frm.setField(DIRECTORY, settingLoanOfferLoad.getDirectory());
		frm.setField(FILE_NAME, settingLoanOfferLoad.getFileName());
	}
}
