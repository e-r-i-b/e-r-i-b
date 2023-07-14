package com.rssl.phizic.config.notification;

import com.rssl.phizic.config.*;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;


import java.io.*;

/**
 * ������ ��� �������� �������� �������
 * @author tisov
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 */

public class BusinessNotificationConfig extends Config
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public static final String PATH_KEY = "com.rssl.phizia.notifications.outerTemplatePath";

	private String pathKey;

	public BusinessNotificationConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override protected void doRefresh() throws ConfigurationException
	{
		String property = getProperty(PATH_KEY);
		Reader reader = null;
		try
		{
			File file = new File(property);
			if (file != null) {
				reader = new BufferedReader(new FileReader(new File(property)));
				StringBuilder text = new StringBuilder();
				int readCode = reader.read();
				while (readCode != -1)
				{
					text.append((char)readCode);
					readCode = reader.read();
				}
				pathKey = text.toString();
			}
			else
			{
				log.error("���� �� ������ " + property + "�� ����������");
			}
		}
		catch (Exception e)
		{
			log.error("������ ������ ����� " + property);
		}
		finally
		{
			try
			{
				if (reader != null)
					reader.close();
			}
			catch (IOException e)
			{
				log.error("������ ��� ������� ��������� ������� ������ �����");
			}
		}

	}

	/**
	 * ������ �������� �������(� ���� ������)
	 * @return - ������� ������
	 */
	public String getOuterTemplate()
	{
		return pathKey;
	}

	/**
	 * ������ ���� � �������� �������
	 * @return - ����
	 */
	public String getPath()
	{
		return getProperty(PATH_KEY);
	}
}
