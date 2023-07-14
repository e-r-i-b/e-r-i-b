package com.rssl.phizic.messaging.mobilebank;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringUtils;

/**
 * User: Moshenko
 * Date: 26.06.12
 * Time: 10:44
 * ������ ��� �������� �� � �������� ����������
 */
public class MobileBankSmsConfig extends Config
{
	private static final String SMS_SPLIT                      = "sms.split";
	private static final String SMS_TRANSLIT                   = "sms.translit";
	private static final String SMS_WORD_OF                    = "sms.word.of";

	//////////////////////////////////////////////////////////////////////////////////////////////

	private boolean smsSplit; //��������� �� sms ���������
	private boolean smsTranslit; //����������������� �� sms ���������
	private String wordOf; //����� "��" (1 �� 2, 2 �� 2 � �.�.)

	public MobileBankSmsConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException
	{
		smsSplit = getBoolProperty(SMS_SPLIT);
		smsTranslit = getBoolProperty(SMS_TRANSLIT);
		wordOf = getProperty(SMS_WORD_OF);
		if(smsTranslit)
			wordOf = StringUtils.translit(wordOf);
	}

	public boolean isSmsSplit()
	{
		return smsSplit;
	}

	public boolean isSmsTranslit()
	{
		return smsTranslit;
	}

	public String getWordOf()
	{
		return wordOf;
	}
}
