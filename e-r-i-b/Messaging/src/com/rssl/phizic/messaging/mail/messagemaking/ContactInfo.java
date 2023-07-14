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
	 * @return ������������� ������ ���������
	 */
	Long getLoginId();
	/**
     * @return ����� ������������
     */
    String getEmailAddress();

     /**
     * @return ����� ���������� ��������
     */
    String getMobilePhone();

	/**
     * @return ����� �������������� ���-���������
     */
    TranslitMode getSmsTranslitMode();

	MailFormat getMailFormat();
}
