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

	protected enum ReportPeriodType //���� �������� �������� ��� ������
	{
		simple,
		week,
		month
	}

	/**
	 * ������� � ������������������� �������� (�������� ��������������).
	 * @param frm �����
	 * @return ��������� ��������.
	 */
	protected  EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ReportEditOperation operation = createOperation(ReportEditOperation.class);  
		return operation;
	}

	/**
	 * ������� ����� ��������������.
	 * @param frm struts-�����
	 * @return ����� ��������������
	 */
	protected  Form getEditForm(EditFormBase frm)
	{
		return ReportEditForm.EDIT_FORM;
	}

	/**
	 * �������� �������� �������.
	 * @param entity ��������
	 * @param data ������
	 */
	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{

	}
	                                     
	/**
	 * �������������������/�������� struts-�����
	 * @param form ����� ��� ����������
	 * @param entity ������ ��� ����������.
	 */
	protected void updateForm(EditFormBase form, Object entity) throws Exception
	{
		ReportEditForm frm = (ReportEditForm) form;
		ReportAbstract report = (ReportAbstract) entity;

		List<ReportPeriodType> periodTypeList = getReportPeriodTypes();
		frm.setPeriodTypes(buildFormPeriodTypes());
		frm.setField("periodType", periodTypeList.get(0).toString());
		// ������������� ���� (�� ��������� �������)
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
	 * ���������� ������� ���� � ������� dd.mm.yyyy
	 * @return ���������� ������� ���� � ������� dd.mm.yyyy
	 */
	protected String getFormatDate()
	{
		return  getFormatDate(Calendar.getInstance());
	}

	/**
	 * ���������� ���������� ���� ���� Calendar � ������� dd.mm.yyyy
	 * @param date
	 * @return ���������� ���������� ���� ���� Calendar � ������� dd.mm.yyyy
	 */
	protected String getFormatDate(Calendar date)
	{
		return  String.format("%1$td.%1$tm.%1$tY", date);
	}

	/**
	 * �������� ������ ��� id ������������� (������ ��� ��, ���, ���, ���� �������� ����� ������)
	 * @param frm �����
	 */	
	protected List<String>  parseSelectedIds(ReportEditForm frm)
	{
		String[] selectedIds = frm.getSelectedIds();

        //�������� ���������� ������ �����, ����������� �������
		List<String> parseSelectedIds = new ArrayList<String>();
		
		for (int i = 0; i < selectedIds.length; i++)
		{
			if ( selectedIds[i].contains(frm.getLevel() + LEVEL_ID_SEPARATOR) )             // �������� ������ �� id, ���. ������������� ������ ����������� �������������
				parseSelectedIds.add( selectedIds[i].split(LEVEL_ID_SEPARATOR)[1] );        // �������� id �� ������� ���� level_id
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
			String message = "�������� �������������";
			switch(frm.getLevel())
			{
				case 1 :
					message += " ��";
					break;
				case 2 :
					message += " ���";
					break;
				case 3 :
					message += " ���";
					break;
			}
			return errorForward(request, frm, operation, message);
		}

		// ������ ���������� � ����������� ��������� ���� ��� ��������� �������
		Calendar[] minMax = operation.getPeriodUserLog();
		frm.setField("minDate", String.format("%1$td.%1$tm.%1$tY", minMax[0]));
		frm.setField("maxDate", String.format("%1$td.%1$tm.%1$tY", minMax[1]));

		String[] startDate = ((String) frm.getField("startDate")).split("\\.");
		if (startDate != null && startDate.length == 3 )
			frm.setField("lastYear",  String.format("01.01.%1$s", startDate[2]));       // ������ ����
		
		//��������� ������, ��������� �������������
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
	 * ��������� ���� �� ���������� ��������� id
	 * @return true -  ���, false - ����
	 */
	protected boolean checkSelectedIds(List<String> selectedIds)
	{
		return (selectedIds == null || selectedIds.size() <= 0);
	}

	/**
	 * @return ��� ��������� �������� �������� ��� ������
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
