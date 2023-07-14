package com.rssl.phizic.gate.mobilebank;

/**
 * @author osminin
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * Информация о сообщении
 */
public interface MessageInfo
{
	/**
	 * @return текст сообщения
	 */
	String getText();

	/**
	 * @return текст, отправляемый в лог
	 */
	String getTextToLog();

	/**
	 * @return заглушечный текст
	 */
	String getStubText();
}
