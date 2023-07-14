package com.rssl.phizic.web.util;

import com.rssl.phizic.web.struts.forms.ActionMessagesManager;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.*;

/**
 * @author mihaylov
 * @ created 30.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class ValidationErrorUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	/**
	 *
	 * @return ������ ��������� �����
	 */
	public static String getValidationError()
	{
		try
		{
			String msg = "";
			List<String> errors = ActionMessagesManager.getFormValidationError(ActionMessagesManager.COMMON_BUNDLE,
																				ActionMessagesManager.ERROR);
			for(String error:errors)
				msg += error+"\n";
			return msg;
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ ��������� �����", e);
			return "";
		}
	}

	/**
	 *
	 * @param fieldName ��� ����
	 * @return ������ ��������� ��� ��������� ����
	 */
	public static String getFieldValidationError(String fieldName)
	{
		try
		{
			Map<String,String> errors = ActionMessagesManager.getFieldsValidationError(ActionMessagesManager.COMMON_BUNDLE,
																						ActionMessagesManager.ERROR);
			return errors.get(fieldName);
		}
		catch (Exception e)
		{
			log.error("������ ����������� ������ ��������� ��� ��������� ����", e);
			return "";
		}
	}



}
