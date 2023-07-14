package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.reports.ReportAbstract;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mescheryakova
 * @ created 09.06.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class ReportsEditAction extends EditActionBase
{
	private final static String LEVEL_ID_SEPARATOR = "_";

	protected enum ReportPeriodType //типы периодов выгрузки для отчета
	{
		simple,
		week,
		month
	}

	/**
	 * Создать и проинициализировать операцию (операция редактирования).
	 * @param frm форма
	 * @return созданная операция.
	 */
	protected  EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ReportEditOperation operation = createOperation(ReportEditOperation.class);  
		return operation;
	}

	/**
	 * Вернуть форму редактирования.
	 * @param frm struts-форма
	 * @return форма редактирования
	 */
	protected  Form getEditForm(EditFormBase frm)
	{
		return ReportEditForm.EDIT_FORM;
	}

	/**
	 * Обновить сущность данными.
	 * @param entity сужность
	 * @param data данные
	 */
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{

	}
	                                     
	/**
	 * Проинициализировать/обновить struts-форму
	 * @param form форма для обновления
	 * @param entity объект для обновления.
	 */
	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		ReportEditForm frm = (ReportEditForm) form;
		ReportAbstract report = (ReportAbstract) entity;

		List<ReportPeriodType> periodTypeList = getReportPeriodTypes();
		frm.setPeriodTypes(buildFormPeriodTypes());
		frm.setField("periodType", periodTypeList.get(0).toString());
		// устанавливаем дату (по умолчанию текущую)
		frm.setField("startDate", report == null ? getFormatDate() : getFormatDate(report.getStartDate()));
		frm.setField("endDate",   report == null ? getFormatDate() : getFormatDate(report.getEndDate()));

		for (ReportPeriodType periodType : periodTypeList)
		{
			if(periodType == ReportPeriodType.simple)
				continue;

			Calendar fromDate = Calendar.getInstance();
			if (periodType == ReportPeriodType.week)
				fromDate.add(Calendar.DAY_OF_MONTH, -6);
			else
			{
				fromDate.add(Calendar.MONTH, -1);
				fromDate.add(Calendar.DATE, 1);
			}
			frm.setField(periodType + "startDate", getFormatDate(fromDate));
			frm.setField(periodType + "endDate",   getFormatDate());
		}
	}

	/**
	 * Возвращает текущую дату в формате dd.mm.yyyy
	 * @return Возвращает текущую дату в формате dd.mm.yyyy
	 */
	protected String getFormatDate()
	{
		return  getFormatDate(Calendar.getInstance());
	}

	/**
	 * Возвращает переданную дату типа Calendar в формате dd.mm.yyyy
	 * @param date
	 * @return Возвращает переданную дату типа Calendar в формате dd.mm.yyyy
	 */
	protected String getFormatDate(Calendar date)
	{
		return  String.format("%1$td.%1$tm.%1$tY", date);
	}

	/**
	 * Получаем нужные нам id департаментов (только для тб, осб, всп, хотя отметить могли больше)
	 * @param frm форма
	 */	
	protected List<String>  parseSelectedIds(ReportEditForm frm)
	{
		String[] selectedIds = frm.getSelectedIds();

        //получаем отмеченные номера галок, разделенных запятой
		List<String> parseSelectedIds = new ArrayList<String>();
		
		for (int i = 0; i < selectedIds.length; i++)
		{
			if ( selectedIds[i].contains(frm.getLevel() + LEVEL_ID_SEPARATOR) )             // отбираем только те id, кот. соответствуют уровню вложенности департаментов
				parseSelectedIds.add( selectedIds[i].split(LEVEL_ID_SEPARATOR)[1] );        // отбираем id из шаблона вида level_id
		}
		return parseSelectedIds;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ReportEditForm frm = (ReportEditForm) form;
		List<String> parseSelectedIds = parseSelectedIds(frm);
		ReportEditOperation operation = getReportOperation();
		if (checkSelectedIds(parseSelectedIds))
		{
			String message = "Выберите подразделения";
			switch(frm.getLevel())
			{
				case 1 :
					message += " ТБ";
					break;
				case 2 :
					message += " ОСБ";
					break;
				case 3 :
					message += " ВСП";
					break;
			}
			return errorForward(request, frm, operation, message);
		}

		// Узнаем минимально и максимально возможные даты для получения отчетов
		Calendar[] minMax = operation.getPeriodUserLog();
		frm.setField("minDate", String.format("%1$td.%1$tm.%1$tY", minMax[0]));
		frm.setField("maxDate", String.format("%1$td.%1$tm.%1$tY", minMax[1]));

		String[] startDate = ((String) frm.getField("startDate")).split("\\.");
		if (startDate != null && startDate.length == 3 )
			frm.setField("lastYear",  String.format("01.01.%1$s", startDate[2]));       // начало года
		
		//Фиксируем данные, введенные пользователем
		addLogParameters(frm);

		FormProcessor<ActionMessages, ?> processor = getFormProcessor(frm, operation);
		if (processor.process() )
		{
			try
			{
				Map<String, Object> result = processor.getResult();
				operation.initialize(DateHelper.toCalendar((Date) result.get("startDate")), DateHelper.toCalendar((Date) result.get("endDate")), parseSelectedIds);
				operation.save();
			}
			catch(BusinessLogicException e)
			{
				return errorForward(request, frm, operation, e.getMessage());
			}
			return mapping.findForward(FORWARD_SUCCESS);
		}
		else
		{
				saveErrors(request, processor.getErrors());
				return createSaveActionForward(operation, frm);
		}
	}

	protected ActionForward errorForward(HttpServletRequest request, ReportEditForm frm, ReportEditOperation operation, String message) throws Exception
	{
		ActionMessages actionMessages = new ActionMessages();
		actionMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		saveErrors(request, actionMessages);
		return createSaveActionForward(operation, frm);
	}

	public abstract ReportEditOperation getReportOperation() throws Exception;

	/**
	 * проверяет есть ли корректные выбранные id
	 * @return true -  нет, false - есть
	 */
	protected boolean checkSelectedIds(List<String> selectedIds)
	{
		return (selectedIds == null || selectedIds.size() <= 0);
	}

	/**
	 * @return тип возможных периодов выгрузки для отчета
	 */
	protected List<ReportPeriodType> getReportPeriodTypes()
	{
		List<ReportPeriodType> result = new ArrayList<ReportPeriodType>();
		result.add(ReportPeriodType.simple);
		return result;
	}

	private String buildFormPeriodTypes()
	{
		StringBuilder formPeriodTypes = new StringBuilder();
		for (ReportPeriodType periodType : getReportPeriodTypes())
		{
			formPeriodTypes.append(periodType);
			formPeriodTypes.append('|');
		}
		formPeriodTypes.delete(formPeriodTypes.length()-1, formPeriodTypes.length());

		return formPeriodTypes.toString();
	}
}
