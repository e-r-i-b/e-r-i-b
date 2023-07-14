package com.rssl.phizic.asfilial.listener;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.messaging.TranslitMode;

/**
 * User: Moshenko
 * Date: 24.04.2013
 * Time: 11:33:02
 * ��������� ASFilialListener
 */
public class ASFilialConfig extends Config
{

	private static final String TRANSLIT_MODE = "confirm.phone.holder.sms.translit.mode";
	private static final String LISTENER_CLASS_KEY = "asfilial.backservice.listener";
	private static final String SERVICE_REDIRECT_URL = "com.rssl.phizic.service.redirect.url.";
	private static final String HOLDER_CODE_LIFE_TIME = "confirm.phone.holder.sms.life.time";

	private TranslitMode translitMode;
	private String listenerClassKey;
	private int holdeCodeLifeTime;
	private boolean useV19spec;

	public ASFilialConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
        translitMode = TranslitMode.fromValue(getProperty(TRANSLIT_MODE));
		listenerClassKey = getProperty(LISTENER_CLASS_KEY);
		holdeCodeLifeTime = Integer.valueOf(getProperty(HOLDER_CODE_LIFE_TIME));
		useV19spec = getBoolProperty("asfilial.specification.v19");
	}

	/**
	 * @return ����� ������������������
	 */
	public TranslitMode getTranslitMode()
	{
		return translitMode;
	}

	/**
	 * @return ����� ����������� �������� �������� �� �� ������
	 */
	public String getListenerClassKey()
	{
		return listenerClassKey;
	}

	/**
	 * ���������� ����� ��� ��������� ��������� ��������� �� �� � ����.
	 * ������������ � ������ ��������� ��������� �� �� ������.
	 * @param nodeId - ����� �����
	 * @return �����.
	 */
	public String getRedirectServiceUrl(Long nodeId)
	{
		return getProperty(SERVICE_REDIRECT_URL + nodeId.toString());
	}

	/**
	 * @return  ����� �������� ���� �������������
	 */
	public int getHoldeCodeLifeTime()
	{
		return holdeCodeLifeTime;
	}

	/**
	 * @return �������������� �� ����� ������������ (19 �����, ������ ������������ 1.10.01)
	 */
	public boolean isUseV19spec()
	{
		return useV19spec;
	}
}
