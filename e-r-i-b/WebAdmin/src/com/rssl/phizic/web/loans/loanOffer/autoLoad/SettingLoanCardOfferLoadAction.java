package com.rssl.phizic.web.loans.loanOffer.autoLoad;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanOffer.SettingLoanCardOfferLoad;
import com.rssl.phizic.business.loanOffer.SettingLoanAbstract;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.loanOffer.SettingLoanCardOfferLoadOperation;
import com.rssl.phizic.operations.loanOffer.ScheduleData;
import com.rssl.phizic.operations.loanOffer.LoanCardOfferLoadOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.CronExpressionUtils;

import java.util.Map;
import java.math.BigInteger;

/**
 * @author Mescheryakova
 * @ created 11.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SettingLoanCardOfferLoadAction extends EditActionBase
{
	private static final String DIRECTORY_MANUAL = "manualLoadPath";
	private static final String FILE_NAME_MANUAL = "manualLoadFile";
	private static final String DIRECTORY_AUTO = "automaticLoadPath";
	private static final String FILE_NAME_AUTO = "automaticLoadFile";
	private static final String RADIO_DAYS_IN_MONTH = "MonthDay";
	private static final String RADIO_HOURS = "Hours";
	private static final String RADIO_DAYS = "Days";
	private static final String DAY_IN_MONTH = "DayInMonth";
	private static final String DAY = "Day";
	private static final String DAY_TIME = "DayTime";
	private static final String HOUR = "Hour";

	/**
	 * —оздать и проинициализировать операцию (операци€ редактировани€).
	 * @param frm форма
	 * @return созданна€ операци€.
	 */
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		SettingLoanCardOfferLoadOperation operation = createOperation(SettingLoanCardOfferLoadOperation.class, "SetttingLoanLoanService");
		operation.init();
		return operation;
	}

	/**
	 * ¬ернуть форму редактировани€.
	 * @param frm struts-форма
	 * @return форма редактировани€
	 */
	protected Form getEditForm(EditFormBase frm)
	{
		return com.rssl.phizic.web.loans.loanOffer.autoLoad.SettingLoanCardOfferLoadForm.EDIT_FORM;
	}

	/**
	 * ќбновить сужность данными.
	 * @param entity сужность
	 * @param data данные
	 */
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Pair<SettingLoanAbstract, Map<String, ScheduleData>> settings = (Pair<SettingLoanAbstract, Map<String, ScheduleData>>) entity;

		SettingLoanCardOfferLoad dirSetting = (SettingLoanCardOfferLoad) settings.getFirst();
		dirSetting.setDirectory((String) data.get(DIRECTORY_MANUAL));
		dirSetting.setFileName((String) data.get(FILE_NAME_MANUAL));
		dirSetting.setAutomaticLoadDirectory((String) data.get(DIRECTORY_AUTO));
		dirSetting.setAutomaticLoadFileName((String) data.get(FILE_NAME_AUTO));

		Map<String, ScheduleData> scheduleMap = settings.getSecond();
		ScheduleData scheduleData = scheduleMap.get(LoanCardOfferLoadOperation.class.getName());
		String periodType = (String) data.get("radio");
		if (periodType.equals(RADIO_HOURS))
		{
			BigInteger hour = (BigInteger) data.get(HOUR);
			scheduleData.setSimple(true);
			scheduleData.setHour(hour.toString());
		}
		else if(periodType.equals(RADIO_DAYS))
		{
			BigInteger day = (BigInteger) data.get(DAY);
			String[] dayTime = data.get(DAY_TIME).toString().split(" ");
			String time = dayTime[3];

        	String expression = CronExpressionUtils.getDayTimeExp(time, day.toString());
			scheduleData.setExpression(expression);
		}
		else if (periodType.equals(RADIO_DAYS_IN_MONTH))
		{
			String dayInMonth = String.valueOf(data.get(DAY_IN_MONTH));
        	String expression = CronExpressionUtils.getDayMonthExp(dayInMonth);
			scheduleData.setExpression(expression);
		}
	}

	/**
	 * ѕроинициализировать/обновить struts-форму
	 * @param frm форма дл€ обновлени€
	 * @param entity объект дл€ обновлени€.
	 */
	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Pair<SettingLoanAbstract, Map<String, ScheduleData>> settings = (Pair<SettingLoanAbstract, Map<String, ScheduleData>>) entity;

		SettingLoanCardOfferLoad dirSetting = (SettingLoanCardOfferLoad) settings.getFirst();
		frm.setField(DIRECTORY_MANUAL, dirSetting.getDirectory());
		frm.setField(FILE_NAME_MANUAL, dirSetting.getFileName());
		frm.setField(DIRECTORY_AUTO, dirSetting.getAutomaticLoadDirectory());
		frm.setField(FILE_NAME_AUTO, dirSetting.getAutomaticLoadFileName());

		Map<String, ScheduleData> scheduleMap = settings.getSecond();
		ScheduleData scheduleData = scheduleMap.get(LoanCardOfferLoadOperation.class.getName());
		frm.setField(DAY_IN_MONTH, scheduleData.getDayInMonth());
		frm.setField(HOUR, scheduleData.getHour());
		frm.setField(DAY, scheduleData.getDay());
		frm.setField(DAY_TIME, scheduleData.getTime());
	}
}