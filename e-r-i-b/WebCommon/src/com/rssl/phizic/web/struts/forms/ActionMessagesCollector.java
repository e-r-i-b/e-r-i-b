package com.rssl.phizic.web.struts.forms;

import com.rssl.common.forms.ErrorsCollector;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.validators.MessageHolder;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.Collection;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 29.05.2006
 * @ $Author: lepihina $
 * @ $Revision: 81876 $
 */

public class ActionMessagesCollector implements ErrorsCollector<ActionMessages>
{
	private ActionMessages errors = new ActionMessages();
	private boolean isTemporalUnAccesible = false; 

	/** ��������� ������ */
	public ActionMessages errors()
	{
		return errors;
	}

	/**
	 * �������� ������
	 *
	 * @param value     ��������� ��������
	 * @param field     ����������� ����
	 * @param holder ��������� �������� ������
	 */
	public void add(String value, Field field, MessageHolder holder)
	{
		String message = holder.getMessage();
		if (StringHelper.isNotEmpty(message))
		{
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(field.getName(),new ActionMessage(message, false)));
		}
		else if(StringHelper.isNotEmpty(holder.getMessageKey()))
		{
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(field.getName(),new ActionMessage(holder.getMessageKey(), field.getDescription())));
		}
		else
		{
			String messageKey = holder.getClass().getName() + ".message";
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(field.getName(),new ActionMessage(messageKey, field.getDescription())));
		}
	}

	/** ���������� ��������� ������ */
	public int count()
	{
		return errors.size();
	}

	/**
	 * �������� ������
	 * @param values    �������� ����� �����
	 * @param errorFieldNames - �������� ��������� �����
	 * @param holder ��������� �������� ������
	 */
	public void add(Map<String, Object> values, Collection<String> errorFieldNames, MessageHolder holder)
	{
		String message = holder.getMessage();
		if(StringUtils.isNotEmpty(message))
		{
			if (errorFieldNames.isEmpty())
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
			else
			{
				for (String errorField : errorFieldNames)
				{
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorField,new ActionMessage(message, false)));
				}
			}
		}
		else
		{
			String messageKey = holder.getClass().getName() + ".message";

			if (errorFieldNames.isEmpty())
				errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(messageKey));
			else
			{
				for (String errorField : errorFieldNames)
				{
					errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(errorField,new ActionMessage(messageKey)));
				}
			}
		}
	}

	public void add(MessageHolder holder)
	{
		String message = holder.getMessage();
		if(message != null)
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
	}

	/**
	 * �������� ������������ � ���, ��� ��� ����������� �� ����� ���� ��������, �.�. ��������� ������� �������� ����������
	 */
	public void setTemporalUnAccesible()
	{
		//��������� ���������� ������ ���
		if(!isTemporalUnAccesible)
		{
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ErrorsCollector.UNACCESSIBLE_MESSAGE));
			isTemporalUnAccesible = true;
		}
	}
}