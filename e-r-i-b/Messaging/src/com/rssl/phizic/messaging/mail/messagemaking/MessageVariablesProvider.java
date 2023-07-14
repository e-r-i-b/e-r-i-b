package com.rssl.phizic.messaging.mail.messagemaking;

import freemarker.template.TemplateModel;

import java.util.Map;

/**
 * @author Erkin
 * @ created 02.07.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * »нтерфейс объекта,
 * предоставл€ющего переменные и методы дл€ использовани€ в шаблонах сообщений
 * типа ${getCutCardNumber(x, y)}
 */
public interface MessageVariablesProvider
{
	Map<String, TemplateModel> getTemplateVariables();
}
