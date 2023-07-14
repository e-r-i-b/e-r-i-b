package com.rssl.phizic.web.blockingrules;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingRules;
import com.rssl.phizic.business.ext.sbrf.csa.blockingrules.BlockingState;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.csa.blockingrules.BlockingRulesEditOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditBlockingRulesAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		BlockingRulesEditOperation operation = createOperation("BlockingRulesEditOperation", "BlockingRulesManagement");

		if(frm.getId() == null || frm.getId() == 0L)
			operation.initializeNew();
		else
			operation.initialize(frm.getId());
		return operation;

	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditBlockingRulesForm.FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		BlockingRules rule = (BlockingRules) entity;
		rule.setDepartments((String) data.get(EditConstants.DEPARTMENTS_FIELD));
		rule.setName((String)data.get(EditConstants.NAME_FIELD));
		rule.setState(BlockingState.valueOf((String)data.get(EditConstants.STATE_FIELD)));
		Date date = (Date)data.get(EditConstants.RESUMING_DATE_FIELD);
		Date time = (Date)data.get(EditConstants.RESUMING_TIME_FIELD);
		rule.setResumingTime(DateHelper.createCalendar(date,time));

	    Date fromPublishDate = (Date)data.get(EditConstants.FROM_PUBLISH_DATE_FIELD);
		Date fromPublishTime = (Date)data.get(EditConstants.FROM_PUBLISH_TIME_FIELD);
		rule.setFromPublishDate(DateHelper.createCalendar(fromPublishDate, fromPublishTime));

		Date toPublishDate = (Date)data.get(EditConstants.TO_PUBLISH_DATE_FIELD);
		Date toPublishTime = (Date)data.get(EditConstants.TO_PUBLISH_TIME_FIELD);
		rule.setToPublishDate(DateHelper.createCalendar(toPublishDate, toPublishTime));

		Date fromRestrictionDate = (Date)data.get(EditConstants.FROM_REGISTRATION_DATE_FIELD);
		Date fromRestrictionTime = (Date)data.get(EditConstants.FROM_REGISTRATION_TIME_FIELD);
		rule.setFromRestrictionDate(DateHelper.createCalendar(fromRestrictionDate, fromRestrictionTime));

		Date toRestrictionDate = (Date)data.get(EditConstants.TO_REGISTRATION_DATE_FIELD);
		Date toRestrictionTime = (Date)data.get(EditConstants.TO_REGISTRATION_TIME_FIELD);

		boolean useMapiMessage = (Boolean) data.get(EditConstants.USE_MAPI_MESSAGE_FIELD);
		boolean useATMMessage = (Boolean) data.get(EditConstants.USE_ATM_MESSAGE_FIELD);
		boolean useERMBMessage = (Boolean) data.get(EditConstants.USE_ERMB_MESSAGE_FIELD);

		rule.setERIBMessage((String) data.get(EditConstants.ERIB_MESSAGE_FIELD));
		rule.setMapiMessage(useMapiMessage ? (String) data.get(EditConstants.MAPI_MESSAGE_FIELD) : null);
		rule.setATMMessage(useATMMessage ? (String) data.get(EditConstants.ATM_MESSAGE_FIELD) : null);
		rule.setERMBMessage(useERMBMessage ? (String) data.get(EditConstants.ERMB_MESSAGE_FIELD) : null);

		rule.setApplyToERIB((Boolean) data.get(EditConstants.APPLY_TO_ERIB_FIELD));
		rule.setApplyToMAPI((Boolean) data.get(EditConstants.APPLY_TO_MAPI_FIELD));
		rule.setApplyToERMB((Boolean) data.get(EditConstants.APPLY_TO_ERMB_FIELD));
		rule.setApplyToATM((Boolean) data.get(EditConstants.APPLY_TO_ATM_FIELD));

		rule.setToRestrictionDate(DateHelper.createCalendar(toRestrictionDate, toRestrictionTime));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		EditBlockingRulesForm form = (EditBlockingRulesForm) frm;
		BlockingRules rule = (BlockingRules) entity;
		form.setField(EditConstants.NAME_FIELD, rule.getName());
		form.setField(EditConstants.DEPARTMENTS_FIELD, rule.getDepartments());
		form.setField(EditConstants.STATE_FIELD, rule.getState());
		Calendar resumingTime = rule.getResumingTime();
		form.setField(EditConstants.RESUMING_TIME_FIELD, resumingTime != null ? resumingTime.getTime() : null);
		form.setField(EditConstants.RESUMING_DATE_FIELD, resumingTime != null ? resumingTime.getTime() : null);

		form.setField(EditConstants.CONFIGURE_NOTIFICATION_FIELD, rule.getFromPublishDate() != null || rule.getToPublishDate() != null ||
											   rule.getFromRestrictionDate()!= null || rule.getToRestrictionDate() != null);

		if( rule.getFromPublishDate() != null )
		{
			form.setField(EditConstants.FROM_PUBLISH_FIELD, true );
			form.setField(EditConstants.FROM_PUBLISH_DATE_FIELD, rule.getFromPublishDate().getTime() );
			form.setField(EditConstants.FROM_PUBLISH_TIME_FIELD, rule.getFromPublishDate().getTime() );
		}

		if( rule.getToPublishDate() != null ){
			form.setField(EditConstants.TO_PUBLISH_FIELD, true );
			form.setField(EditConstants.TO_PUBLISH_DATE_FIELD, rule.getToPublishDate().getTime() );
			form.setField(EditConstants.TO_PUBLISH_TIME_FIELD, rule.getToPublishDate().getTime() );
		}

		Calendar fromRestrictionDate = rule.getFromRestrictionDate();
		form.setField(EditConstants.FROM_REGISTRATION_DATE_FIELD, fromRestrictionDate != null ? fromRestrictionDate.getTime() : null);
		form.setField(EditConstants.FROM_REGISTRATION_TIME_FIELD, fromRestrictionDate != null ? fromRestrictionDate.getTime() : null);

		Calendar toRestrictionDate = rule.getToRestrictionDate();
		form.setField(EditConstants.TO_REGISTRATION_DATE_FIELD, toRestrictionDate != null ? toRestrictionDate.getTime() : null);
		form.setField(EditConstants.TO_REGISTRATION_TIME_FIELD, toRestrictionDate != null ? toRestrictionDate.getTime() : null);

		form.setField(EditConstants.ERIB_MESSAGE_FIELD, rule.getERIBMessage());
		form.setField(EditConstants.ERMB_MESSAGE_FIELD, rule.getERMBMessage());
		form.setField(EditConstants.MAPI_MESSAGE_FIELD, rule.getMapiMessage());
		form.setField(EditConstants.ATM_MESSAGE_FIELD, rule.getATMMessage());

		form.setField(EditConstants.APPLY_TO_ERIB_FIELD, rule.isApplyToERIB());
		form.setField(EditConstants.APPLY_TO_MAPI_FIELD, rule.isApplyToMAPI());
		form.setField(EditConstants.APPLY_TO_ATM_FIELD, rule.isApplyToATM());
		form.setField(EditConstants.APPLY_TO_ERMB_FIELD, rule.isApplyToERMB());

		form.setField(EditConstants.USE_ATM_MESSAGE_FIELD, StringHelper.isNotEmpty(rule.getATMMessage()));
		form.setField(EditConstants.USE_MAPI_MESSAGE_FIELD, StringHelper.isNotEmpty(rule.getMapiMessage()));
		form.setField(EditConstants.USE_ERMB_MESSAGE_FIELD, StringHelper.isNotEmpty(rule.getERMBMessage()));
	}
}
