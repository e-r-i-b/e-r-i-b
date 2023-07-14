package com.rssl.phizic.web.struts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.PropertyMessageResources;

import java.io.*;
import java.net.URISyntaxException;

/**
 *
 * ��������� ������������� ������� �� ������� ������ �� unicode ����. ������ ��� ����, ����� ���� �����������
 * �������� ��������� � ������ .properties, ������� � .ear �������, ��� ����������� ������ �������������
 * ������� unicode ������.
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
	 * @param inputStream �������������� ������� �����
	 */
	public Native2ASCIIStream(InputStream inputStream)
	{
		this.inputStream = inputStream;
	}

	/**
	 * ���������� �����, ��� ������������� ������� �������� ������������ � unicode �����.
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
			 * ������������� charset ������ US-ASCII
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
			 * ��� ����������� ���� ����������� �������� � ����������� �������� ������� 7 ������ ���,
			 * �������, ������ ������ �� 7 ���, �� �� ���������, ��������� ��� � ������������� �� ���������.
			 */
			if ((c >> 7) > 0)
			{
				/*
				 * ������ 2 ����� � ������� ����� unicode ��� ������������� ���� - 04
				 */                              
				sb.append("\\u04");

				/*
				 * ��� ��������������� ������������ ����� ����� ������� �� ������������. ��������, �������
				 * ����� "�"(����� ������ �������� ���������� ���� �� � ������), ��� ������������� ���� "044E",
				 * � ������ �� �������� ���� ����������� �������� ������ �� ��������� ������������� ����� "�"
				 * (�������� ������������� ����� - "010001001110", ��� ����, ���� ������ ������ 4 ������ ����,
				 * ��������� ������������� ����� 0xF).
				 *
				 * ��� ���������� ������� ���������� ������� ���� ������������ ����� ��������� ��������
				 * "010001001110", ������, ��� ������� ���������� �������� ����� ������������� �� 4(��� �������
				 *  ������� ���� ����� �� ������������). ��� ������� ��������������� ���, ��� ��� �����������
				 * ������������ ����� �� 4 ����.
				 *
				 * ������ ������ ���� ����������� �� ���������� ���������:
				 * 1. ��� ���������� �������� ������� �������(� ���� ������� �� ����� "E") ���������� ������
				 * �������� ����� 0xF. � ������ ������, �������� �������� "010001001110" ��������� ���
				 * "000000001110", ��� ������������� ������ ����� 14 � ������������������ "E"(���������� ������
				 * ����������������� ������).
				 *
                 * 2. ��� ����������� ���������� ������� ������������ ����� ������ �� 4, � �����, �������������
                 * ����� 0xF:
                 * "HEX_CHARS[(010001001110 >> 4) & 0xF]" -, ����� ���� �������� ������������� "010001001110"
                 * ����������� ��� "00000000100", ��� ������������� ������������������ ����� 4.
                 *
                 * � ���������� �������������� ������������� ���, ��������� ��������� 2 ����� "04" ���� "044E"
                 * ���������� ��� ����� ��������� ������������� ��������.
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
