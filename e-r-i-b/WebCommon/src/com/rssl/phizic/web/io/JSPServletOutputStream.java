package com.rssl.phizic.web.io;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 13.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class JSPServletOutputStream extends ServletOutputStream
{
	private HttpServletResponse delegate;
	private StringBuffer buffer;
	private boolean hasOutput; // ������� ����, �� ������ ��� ������� ������ ������

	public JSPServletOutputStream(HttpServletResponse hss)
	{
		delegate = hss;
	}

	// ����� ��������� ����� �������� �� ����� ������
	private void writeNoNewLine (int c) throws IOException
	{
		if ( c == '\n' || c == '\r' ) // ������� �� ����� ������ � ���������
			return ;
		if (!hasOutput) // ������� ��������� ��� ���� ����� ������ ������ ������� ����� �������
						// � ������ xml ������ ������ ��������
			if (c == ' ') return ;
			else hasOutput = true;
		delegate.getOutputStream().write(c);
	}

	// ����� ����������� ������ ������ ������
	private void writeNoEmptyLine (int c)  throws IOException
	{
		c = (c == '\r')?' ':c;
		// ������� ��� ������ ������ � ������ ����� ������� ������ ���������
		if (buffer == null && (c == '\n' || c == ' ')) return;

		if (buffer == null) buffer = new StringBuffer();

		// �������� ������ � ����� ���� ������� �� ����� ������
		if ( c == '\n') // ������� �� ����� ������
		{
			// ��� ������ ����� �������� ������� �����
			if (buffer.length() > 0)
				buffer.delete(0, buffer.length());

			buffer.append((char)c);
			return;
		}

		if ( buffer == null || buffer != null && buffer.length() > 0 )
		{
		   if ( c != ' ' ) // �� ������
		   {
			   ServletOutputStream outputStream = delegate.getOutputStream();
			   // ����� �� ������ � �����
			   for (int i = 0; i < buffer.length(); i++)
				   outputStream.write((int) buffer.charAt(i));
			   buffer.delete(0, buffer.length()); // ������� �����
			   // ����� ������
			   outputStream.write(c);
		   }
		   else
			   buffer.append((char)c); // ���������� �������� ������� � �����
		}
		else
			delegate.getOutputStream().write(c);

	}

	public void write(int c) throws IOException
	{
		//writeNoNewLine (c); // ����� ��� ��������� �� ����� ������
		writeNoEmptyLine (c); // ����� ��� ������ �����
	}

}
