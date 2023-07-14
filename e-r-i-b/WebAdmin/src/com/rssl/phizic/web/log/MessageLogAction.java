package com.rssl.phizic.web.log;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.logging.translateMessages.TypeMessageTranslate;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.log.MessageLogOperation;
import com.rssl.phizic.utils.PhoneNumberUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.lang.BooleanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 29.06.2007
 * @ $Author$
 * @ $Revision$
 */

public class MessageLogAction extends LogFilterBaseAction
{
	private static final String AUTO_COMPLETE = "AutoComplete";


	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> map = super.getAditionalKeyMethodMap();

		map.put("button.ajaxSearch", "ajaxSearch");
		return map;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		if(checkAccess(MessageLogOperation.class, "MessageLogService"))
			return createOperation(MessageLogOperation.class, "MessageLogService");
		else if(checkAccess(MessageLogOperation.class, "MessageLogServiceEmployee"))
			return createOperation(MessageLogOperation.class, "MessageLogServiceEmployee");
		else
			return createOperation(MessageLogOperation.class);

	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return MessageLogForm.MESSAGE_LOG_FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		super.fillQuery(query, filterParams);

		query.setParameter("system", filterParams.get("system"));
		query.setParameter("requestTag", filterParams.get("requestTag"));
		//����� �� ����� � �������/������ ��������� � ���� ����������: UTF-8 � windows-1251, �.�. ��������� �� ����������������.
		//� ���� �� ����� ��������� ������������ xml, ��� ������ �� ���: � ��������� encoding="UTF-8", �� ���� ��������� � ����� � windows-1251.
		query.setParameter("requestWordWin1251", filterParams.get("requestWord"));
		query.setParameter("requestWordUTF8", getWordUTF8((String)filterParams.get("requestWord")));
		query.setParameter("responceWordWin1251", filterParams.get("responceWord"));
		query.setParameter("responceWordUTF8", getWordUTF8((String)filterParams.get("responceWord")));
		query.setParameter("requestresponceWordWin1251", filterParams.get("requestresponceWord"));
		query.setParameter("requestresponceWordUTF8", getWordUTF8((String)filterParams.get("requestresponceWord")));
		Boolean isGuest = (Boolean)filterParams.get("isGuestLog");
		if(BooleanUtils.isTrue(isGuest))
		{
			query.setParameter("phone", PhoneNumberUtil.getMobileInternationalPhoneNumber((String) filterParams.get(MessageLogForm.PHONE_NUMBER_NAME)));
			query.setParameter("isGuestLog", filterParams.get("isGuestLog"));
		}
	}	

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation)
	{
		MessageLogForm frm = (MessageLogForm) form;
		MessageLogOperation op = (MessageLogOperation) operation;
		frm.setFromStart(frm.isFromStart());
		frm.setSystemList(op.getSystemList());
	}

	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase form) throws DataAccessException, BusinessException, BusinessLogicException
	{
		updateFormAdditionalData(form, operation);
		super.doFilter(filterParams, operation, form);
	}

	protected String[] getIndexedParameters()
	{
		//CL_APP_DATE_INDEX,  CL_FIO_DATE_INDEX, CL_DI_DATE_INDEX, CL_LOGIN_DATE_INDEX, CL_OUID_INDEX
		return new String[]{"application", "fio", "number", "loginId", "operationUID"};
	}

	protected void completeParametersForIndex(Query query)
	{
		Boolean isGuest = BooleanUtils.isTrue((Boolean) query.getParameter("isGuestLog"));
		//��� ��������� ���� � ��� ������ ����� � ������� ��������������� ��������.
		if(isGuest)
			return;
		super.completeParametersForIndex(query);
	}

	protected String getLogName()
	{
		return "gate-log.html";
	}

	/**
	 * � �� ��������� �������� � ����: &#x421;&#x41C; � �.�.
	 * ������� ����� ��������� � ������ ������ ����������� � ������ �� ����
	 * ����� ���������� �����������������.
	 * @param word
	 * @return
	 */
	protected String getWordUTF8(String word)
	{
		if(StringHelper.isEmpty(word))
			return "";

		StringBuilder result = new StringBuilder();
		char[] charArr = word.toCharArray();
		for(char ch : charArr)
		{
			if(ch >= 1024 && ch <= 1279) //���������
				result.append("&#x").append(Integer.toHexString(ch).toUpperCase()).append(";");
			else
				result.append(ch);
		}
		return result.toString();
	}

	/**
	 * ���������� "������" ajax ������ �� ������� �������
	 */
	public ActionForward ajaxSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		DownloadLogFilterBaseForm frm = (DownloadLogFilterBaseForm) form;
		MessageLogOperation messageOperation = (MessageLogOperation) createListOperation(frm);
		frm.setData(
				messageOperation.getMessTranslateList(
					new String (((String) frm.getField("q")).getBytes(),"UTF-8"), // ��������� � ���������� ���������
					TypeMessageTranslate.valueOf(frm.getField("type").toString())
				)
		);
		return mapping.findForward(AUTO_COMPLETE);
	}

	protected Calendar getDefaultFilterStartDate()
	{
		Calendar date = Calendar.getInstance();
	    date.add(Calendar.HOUR,-1);
	    return date;
	}

	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		Boolean isGuest = BooleanUtils.toBoolean((String) frm.getFilters().get(MessageLogForm.GUEST_LOG_NAME));
		return isGuest ? "guestList" : "list";
	}
}
