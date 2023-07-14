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
 * ��������� �������,
 * ���������������� ���������� � ������ ��� ������������� � �������� ���������
 * ���� ${getCutCardNumber(x, y)}
 */
public interface MessageVariablesProvider
{
	Map<String, TemplateModel> getTemplateVariables();
}
