package com.rssl.phizic.messaging.mail.messagemaking;

import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.utils.BeanHelper;
import freemarker.template.TemplateModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Erkin
 * @ created 02.07.2010
 * @ $Author$
 * @ $Revision$
 */
public abstract class MessageBuilderBase implements MessageBuilder
{

	protected Long priority;
	protected Map prepareMessageBean(Object messageBean)
	{
		Map map = null;
		if (messageBean == null)
			map = new HashMap();
		else if (messageBean instanceof Map)
			map = (Map)messageBean;
		else map = BeanHelper.createMapFromProperties(messageBean);

		setupMessageTemplates(map);

		return map;
	}

	private void setupMessageTemplates(Map root)
	{
		MessageVariablesProvider provider =
				MessagingSingleton.getInstance().getMessageVariablesProvider();
		if (provider != null) {
			Map<String, TemplateModel> vars = provider.getTemplateVariables();
			root.putAll(vars);
		}
	}

	public Long getPriority()
	{
		return priority;
	}

	public void  setPriority(Long priority)
	{
		this.priority = priority;
	}

}
