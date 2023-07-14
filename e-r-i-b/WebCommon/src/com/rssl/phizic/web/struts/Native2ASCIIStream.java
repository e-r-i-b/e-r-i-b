package com.rssl.phizic.web.struts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.PropertyMessageResources;

import java.io.*;
import java.net.URISyntaxException;

/**
 *
 * Подменяет кириллические символы во входном потоке на unicode коды. Сделан для того, чтобы была возможность
 * изменять сообщения в файлах .properties, лежащих в .ear архивах, без потребности писать кириллические
 * символы unicode кодами.
 *
 * User: Balovtsev
 * Date: 13.06.2012
 * Time: 15:02:36
 */
public class Native2ASCIIStream
{
	private static final char[] HEX_CHARS =
	{
	    '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
	};

	private static final Log    LOG           = LogFactory.getLog(PropertyMessageResources.class);
	private static final String ASCII_CHARSET = "US-ASCII";

	private InputStream inputStream;

	/**
	 * @param inputStream модифицируемый входной поток
	 */
	public Native2ASCIIStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	/**
	 * Возвращаем поток, все кириллические символы которого представлены в unicode кодах.
	 *
	 * @return InputStream
	 */
	public InputStream getInputStream()
	{
		if (inputStream == null)
		{
			return null;
		}

		try
		{
			/*
			 * Устанавливаем charset равный US-ASCII
			 */
			return new ByteArrayInputStream(substituteStream(inputStream).getBytes(ASCII_CHARSET));
		}
		catch (IOException e)
		{
			LOG.error(e.getMessage(), e);
		}
		catch (URISyntaxException e)
		{
			LOG.error(e.getMessage(), e);
		}

		return null;
	}

	private String substituteStream(InputStream inputStream) throws IOException, URISyntaxException
	{
		StringBuffer  builder = new StringBuffer();
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new InputStreamReader(inputStream));

			String line = reader.readLine();
			while ( line != null )
			{
				builder.append( native2ascii(line) );
				builder.append( "\n" );

			    line  = reader.readLine();
			}
		}
		finally
		{
			if (reader != null)
			{
				reader.close();
			}
		}

		return builder.toString();
	}

	private String native2ascii(String line)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < line.length(); i++)
		{
			char c = line.charAt(i);
			/*
			 * Для кодирования букв английского алфавита и специальных символов хватает 7 первых бит,
			 * поэтому, смещая вправо на 7 бит, мы их отсеиваем, поскольку они в перекодировке не нуждаются.
			 */
			if ((c >> 7) > 0)
			{
				/*
				 * Первые 2 цифры в таблице кодов unicode для кириллических букв - 04
				 */                              
				sb.append("\\u04");

				/*
				 * Код соответствующий определенной букве можно разбить на составляющие. Например, возьмем
				 * букву "ю"(далее описан алгоритм вычисления кода на её основе), она соответствует коду "044E",
				 * и каждый из символов кода вычисляется отдельно исходя из двоичного представления буквы "ю"
				 * (двоичное представление равно - "010001001110", при этом, роль играют только 4 первых бита,
				 * поскольку накладывается маска 0xF).
				 *
				 * При вычислении каждого следующего символа кода производится сдвиг бинарного значения
				 * "010001001110", причем, для каждого следующего значения сдвиг увеличивается на 4(для первого
				 *  символа кода сдвиг не производится). Это условие обуславливается тем, что при декодировке
				 * используются блоки по 4 бита.
				 *
				 * Каждый символ кода вычисляется по следующему алгоритму:
				 * 1. Для вычисления крайнего правого символа(в коде примера он равен "E") необходимо просто
				 * наложить маску 0xF. В данном случае, бинарное значение "010001001110" приобретёт вид
				 * "000000001110", что соответствует целому числу 14 и шестнадцатеричному "E"(интересует только
				 * шестнадцатеричный формат).
				 *
                 * 2. Для определения следующего символа производится сдвиг вправо на 4, а затем, накладывается
                 * маска 0xF:
                 * "HEX_CHARS[(010001001110 >> 4) & 0xF]" -, после чего бинарное представление "010001001110"
                 * приобретает вид "00000000100", что соответствует шестнадцатеричному числу 4.
                 *
                 * В дальнейшем преобразовании необходимости нет, поскольку последние 2 цифры "04" кода "044E"
                 * определены для всего диапозона кириллических символов.
				 */
				sb.append(HEX_CHARS[(c >> 4) & 0xF]);
				sb.append(HEX_CHARS[c & 0xF]);
			}
			else
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
