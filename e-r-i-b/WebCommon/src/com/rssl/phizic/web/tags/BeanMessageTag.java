package com.rssl.phizic.web.tags;

import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.locale.ERIBLocaleConfig;
import com.rssl.phizic.config.locale.MultiLocaleContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.messages.MessageConfigRouter;
import com.rssl.phizic.web.common.messages.MultiLocaleMessageConfig;
import org.apache.struts.taglib.TagUtils;

import javax.servlet.jsp.JspException;

/**
 * User: Balovtsev
 * Date: 01.12.2011
 * Time: 19:02:39
 */
public class BeanMessageTag extends org.apache.struts.taglib.bean.MessageTag
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	/**
	 * флажок "выбрасывать исключение, если сообщение не удалось найти"
	 */
	private boolean failIfNone = true;

	public boolean isFailIfNone()
	{
		return failIfNone;
	}

	public void setFailIfNone(boolean failIfNone)
	{
		this.failIfNone = failIfNone;
	}

	private String getMessage() throws JspException
	{
		if(ConfigFactory.getConfig(ERIBLocaleConfig.class).isUseERIBMessagesMode(ApplicationInfo.getCurrentApplication(), MultiLocaleContext.getLocaleId()))
			return MultiLocaleMessageConfig.getInstance().message(this.bundle, key, new Object[] { arg0, arg1, arg2, arg3, arg4 });
		else
			return TagUtils.getInstance().message(pageContext, this.bundle, this.localeKey, key, new Object[] { arg0, arg1, arg2, arg3, arg4 });
	}

	public int doStartTag() throws JspException
	{
		String message = getMessage();

		if (StringHelper.isEmpty(message) && !failIfNone)
		{
			log.warn("В ресурс-бандле " + this.bundle + " не найдена текстовка с ключом " + key);
			return SKIP_BODY;
		}

		if (message == null) {
			JspException e =
					new JspException(
							messages.getMessage("message.message", "\"" + key + "\""));
			TagUtils.getInstance().saveException(pageContext, e);
			throw e;
		}
		TagUtils.getInstance().write(pageContext, message);
		return (SKIP_BODY);
	}
}
