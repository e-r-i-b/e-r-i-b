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
 * Класс с методомаи для получения MAC адресов сетевых карточек локальной машины
 * оригинал: http://sourcezone.wordpress.com/2008/02/14/java-how-to-get-the-mac-addresses-of-network-cards/
 * @author Felipe Lang
 */
public final class MacAddress
{

	static private Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*", Pattern.CASE_INSENSITIVE);

	static final String[] windowsCommand = {"ipconfig", "/all"};

	static final String[] linuxCommand = {"/sbin/ifconfig", "-a"};

	static final String[] solarisCommand = {"ifconfig", "-a"};
	/**
	 * Метод для поиска всех мак адресов локальной машины
	 *
	 * @return список MAC адресов
	 * @throws IOException
	 */
	public final static List<String> getMacAddresses() throws IOException
	{
		final List macAddressList = new ArrayList();

		// получить MAC адреса из stdout
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
	 * Метод для получения MAC адреса для первой сетевой карточки
	 *
	 * @return MAC адрес
	 * @throws IOException
	 */
	public final static String getMacAddress() throws IOException
	{
		return getMacAddress(0);
	}

	/**
	 * Метод для получения мак адреса конкретной карточки
	 *
	 * @param nicIndex номер сетевой карты (0 индекс первой сетевой карты)
	 *
	 * @return a MAC address
	 * @throws IOException
	 */
	public final static String getMacAddress(int nicIndex) throws IOException
	{
		// получить MAC адреса из stdout
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
			throw new IOException("Неизвестная операционная система: " + os);
		}

		final Process process = Runtime.getRuntime().exec(command);

		// Отбрасываем stderr
		new Thread()
		{
			public void run()
			{
				try
				{
					InputStream errorStream = process.getErrorStream();
					while (errorStream.read() != -1)
					{
						//нечего не делаем
					}
					errorStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();

		// получить MAC адреса из stdout
		return new BufferedReader(new InputStreamReader(process.getInputStream()));
	}
}