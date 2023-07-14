package com.rssl.phizic.messaging.mail.messagemaking;

import com.rssl.phizic.messaging.MailFormat;
import com.rssl.phizic.messaging.TranslitMode;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Kidyaev
 * @ created 16.06.2006
 * @ $Author: tisov $
 * @ $Revision: 54976 $
 */
public interface ContactInfo
{
	/**
	 * @return идентификатор логина владельца
	 */
	Long getLoginId();
	/**
     * @return адрес электропочты
     */
    String getEmailAddress();

     /**
     * @return номер мобильного телефона
     */
    String getMobilePhone();

	/**
     * @return режим транслитерации СМС-сообщений
     */
    TranslitMode getSmsTranslitMode();

	MailFormat getMailFormat();
}
