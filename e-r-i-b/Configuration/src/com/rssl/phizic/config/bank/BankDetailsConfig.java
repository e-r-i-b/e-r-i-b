package com.rssl.phizic.config.bank;

import com.rssl.phizic.config.*;

/**
 * ��������� ���������� �����, ������� �� ����� ���� �������� �� ������������
 * @author Pankin
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class BankDetailsConfig extends Config
{
	public static final String PARTICIPANT_CODE_PROPERTY = "com.rssl.iccs.participant.code";
	public static final String OGRN_PROPERTY = "com.rssl.iccs.ogrn";
	private static final String TB_DICTIONARY_PROPERTY = "com.rssl.iccs.terbanks.dictionary.path";
	private static final String SEND_TO_EMAIL_IMAGE_URL = "com.rssl.iccs.email.image.url";

	private String participantCode;
	private String OGRN;
	private String tbDictionary;
	private String sendToEmailImageUrl;

	public BankDetailsConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		participantCode = getProperty(PARTICIPANT_CODE_PROPERTY);
		OGRN = getProperty(OGRN_PROPERTY);
		tbDictionary = getProperty(TB_DICTIONARY_PROPERTY);
		sendToEmailImageUrl = getProperty(SEND_TO_EMAIL_IMAGE_URL);
	}

	/**
	 * @return ��� ��������� �������� � ���� ����� ������
	 */
	public String getParticipantCode()
	{
		return participantCode;
	}

	/**
	 * @return ���� �����
	 */
	public String getOGRN()
	{
		return OGRN;
	}

	/**
	 * @return ���� � ����� ����������� ���������
	 */
	public String getTbDictionatyPath()
	{
		return tbDictionary;
	}

	/**
	 * @return URL � �������� �������� ��� �������� ���������� ������ �� ����
	 */
	public String getSendToEmailImageUrl()
	{
		return sendToEmailImageUrl;
	}
}
