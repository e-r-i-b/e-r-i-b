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
	private boolean hasOutput; // признак того, на выходе уже имеются первые данные

	public JSPServletOutputStream(HttpServletResponse hss)
	{
		delegate = hss;
	}

	// метод уберающий любые переходы на новую строку
	private void writeNoNewLine (int c) throws IOException
	{
		if ( c == '\n' || c == '\r' ) // переход на новую строку и табуляция
			return ;
		if (!hasOutput) // признак необходим для того чтобы убрать лишнии пробелы перед данными
						// в случае xml данное весьма критично
			if (c == ' ') return ;
			else hasOutput = true;
		delegate.getOutputStream().write(c);
	}

	// метод устранающий только пустые строки
	private void writeNoEmptyLine (int c)  throws IOException
	{
		c = (c == '\r')?' ':c;
		// убераем все пустые строки и данные перед началом текста документа
		if (buffer == null && (c == '\n' || c == ' ')) return;

		if (buffer == null) buffer = new StringBuffer();

		// начинаем писать в буфер если переход на новую строку
		if ( c == '\n') // переход на новую строку
		{
			// при каждом новом переходе очищаем буфер
			if (buffer.length() > 0)
				buffer.delete(0, buffer.length());

			buffer.append((char)c);
			return;
		}

		if ( buffer == null || buffer != null && buffer.length() > 0 )
		{
		   if ( c != ' ' ) // не пробел
		   {
			   ServletOutputStream outputStream = delegate.getOutputStream();
			   // пишем из буфера в поток
			   for (int i = 0; i < buffer.length(); i++)
				   outputStream.write((int) buffer.charAt(i));
			   buffer.delete(0, buffer.length()); // очищаем буфер
			   // пишем данные
			   outputStream.write(c);
		   }
		   else
			   buffer.append((char)c); // продолжаем собирать пробелы в буфер
		}
		else
			delegate.getOutputStream().write(c);

	}

	public void write(int c) throws IOException
	{
		//writeNoNewLine (c); // вывод без переходов на новую строку
		writeNoEmptyLine (c); // вывод без пустых строк
	}

}
