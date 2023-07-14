package com.rssl.phizic.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.*;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.xml.sax.SAXParseException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Утилитных класс для работы с исключениями
 * @author Evgrafov
 * @ created 28.03.2006
 * @ $Author: niculichev $
 * @ $Revision: 54544 $
 */
public final class ExceptionUtil
{
	private ExceptionUtil(){}

	/**
	 * Форматирует stack trace исключение
	 * @param e - исключение
	 * @return - отформатированное исключение
	 * @noinspection IOResourceOpenedButNotSafelyClosed
	*/
	public static String printStackTrace(Throwable e)
	{
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		printStackTrace(printWriter, e);

		return stringWriter.toString();
	}

	private static void printStackTrace(PrintWriter printWriter, Throwable e)
	{
		printWriter.println(e);
		StackTraceElement[] stackTrace = e.getStackTrace();

		for (StackTraceElement traceElement : stackTrace)
		{
			printWriter.print("\tat ");
			printWriter.println(traceElement);
		}

		if (e.getCause() != null)
		{
			printWriter.print("Caused by: ");
			printStackTrace(printWriter, e.getCause());
		}
	}

	/**
	 * Фозвращает текст сообщения исключения
	 * (используется для вывода номера строки столбца SAXParse-исключений)
	 * @param ex - SAXParseException
	 * @return строка с сообщением
	 */
	public static String formatExceptionString(SAXParseException ex)
	{
		return String.format("(строка %d, символ %d) %s",
				ex.getLineNumber(), ex.getColumnNumber(),  ex.getMessage());
	}

	/**
	 * Возвращает сообщения стека исключений ввиде строки
	 * @param throwable исключение, относительно которого берется стек исключений
	 * @param delimiter разделитель
	 * @return сообщения стека исключений ввиде строки через разделитель
	 */
	public static String getStackTraceMessages(Throwable throwable, String delimiter)
	{
		List<Throwable> throwables = ExceptionUtils.getThrowableList(throwable);

		StringBuilder builder = new StringBuilder();
		if(CollectionUtils.isEmpty(throwables))
			return builder.toString();

		for(Throwable thr : throwables)
			builder.append(thr.getMessage()).append(delimiter);

		return builder.toString();
	}
}