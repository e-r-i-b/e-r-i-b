package com.rssl.phizic.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author rydvanskiy
 * @ created 14.06.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� � ��������� ��� ��������� MAC ������� ������� �������� ��������� ������
 * ��������: http://sourcezone.wordpress.com/2008/02/14/java-how-to-get-the-mac-addresses-of-network-cards/
 * @author Felipe Lang
 */
public final class MacAddress
{

	static private Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", Pattern.CASE_INSENSITIVE);

	static final String[] windowsCommand = {"ipconfig", "/all"};

	static final String[] linuxCommand = {"/sbin/ifconfig", "-a"};

	static final String[] solarisCommand = {"ifconfig", "-a"};
	/**
	 * ����� ��� ������ ���� ��� ������� ��������� ������
	 *
	 * @return ������ MAC �������
	 * @throws IOException
	 */
	public final static List<String> getMacAddresses() throws IOException
	{
		final List macAddressList = new ArrayList();

		// �������� MAC ������ �� stdout
		BufferedReader reader = getMacAddressesReader();
		try
		{
			for (String line = null; (line = reader.readLine()) != null;)
			{
				Matcher matcher = macPattern.matcher(line);
				if (matcher.matches())
				{
					macAddressList.add(matcher.group(1).replaceAll("[-:]", ""));
				}
			}
		}
		finally
		{
			reader.close();
		}


		return macAddressList;
	}

	/**
	 * ����� ��� ��������� MAC ������ ��� ������ ������� ��������
	 *
	 * @return MAC �����
	 * @throws IOException
	 */
	public final static String getMacAddress() throws IOException
	{
		return getMacAddress(0);
	}

	/**
	 * ����� ��� ��������� ��� ������ ���������� ��������
	 *
	 * @param nicIndex ����� ������� ����� (0 ������ ������ ������� �����)
	 *
	 * @return a MAC address
	 * @throws IOException
	 */
	public final static String getMacAddress(int nicIndex) throws IOException
	{
		// �������� MAC ������ �� stdout
		BufferedReader reader = getMacAddressesReader();
		try
		{
			int nicCount = 0;
			for (String line = null; (line = reader.readLine()) != null;)
			{
				Matcher matcher = macPattern.matcher(line);
				if (matcher.matches())
				{
					if (nicCount == nicIndex)
					{
						reader.close();
						return matcher.group(1).replaceAll("[-:]", "");
					}
					nicCount++;
				}
			}
		}
		finally
		{
			reader.close();
		}

		return null;
	}

	private static BufferedReader getMacAddressesReader() throws IOException
	{
		final String[] command;
		final String os = System.getProperty("os.name");

		if (os.startsWith("Windows"))
		{
			command = windowsCommand;
		}
		else if (os.startsWith("Linux"))
		{
			command = linuxCommand;
		}
		else if (os.startsWith("Solaris"))
		{
			command = solarisCommand;
		}
		else
		{
			throw new IOException("����������� ������������ �������: " + os);
		}

		final Process process = Runtime.getRuntime().exec(command);

		// ����������� stderr
		new Thread()
		{
			public void run()
			{
				try
				{
					InputStream errorStream = process.getErrorStream();
					while (errorStream.read() != -1)
					{
						//������ �� ������
					}
					errorStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();

		// �������� MAC ������ �� stdout
		return new BufferedReader(new InputStreamReader(process.getInputStream()));
	}
}