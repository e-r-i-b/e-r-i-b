package com.rssl.phizic.business.ext.sbrf.csa.blockingrules.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * Класс с локалезависимыми текстовками для блокировок
 * @author komarov
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class BlockingRulesResources extends LanguageResource
{
	private String ERIBMessage;     //сообщение для канала ЕРИБ
	private String mapiMessage;     //сообщение для канала мАпи
	private String ATMMessage;      //сообщение для канала АТМ
	private String ERMBMessage;     //сообщение для канала ЕРМБ

	/**
	 * @return сообщение для канала ЕРИБ
	 */
	public String getERIBMessage()
	{
		return ERIBMessage;
	}

	/**
	 * @param ERIBMessage сообщение для канала ЕРИБ
	 */

	public void setERIBMessage(String ERIBMessage)
	{
		this.ERIBMessage = ERIBMessage;
	}

	/**
	 * @return сообщение для канала мАпи
	 */
	public String getMapiMessage()
	{
		return mapiMessage;
	}

	/**
	 * @param mapiMessage сообщение для канала мАпи
	 */
	public void setMapiMessage(String mapiMessage)
	{
		this.mapiMessage = mapiMessage;
	}

	/**
	 * @return сообщение для канала АТМ
	 */
	public String getATMMessage()
	{
		return ATMMessage;
	}

	/**
	 * @param ATMMessage сообщение для канала АТМ
	 */
	public void setATMMessage(String ATMMessage)
	{
		this.ATMMessage = ATMMessage;
	}

	/**
	 * @return сообщение для канала ЕРМБ
	 */
	public String getERMBMessage()
	{
		return ERMBMessage;
	}

	/**
	 * @param ERMBMessage сообщение для канала ЕРМБ
	 */
	public void setERMBMessage(String ERMBMessage)
	{
		this.ERMBMessage = ERMBMessage;
	}
}
