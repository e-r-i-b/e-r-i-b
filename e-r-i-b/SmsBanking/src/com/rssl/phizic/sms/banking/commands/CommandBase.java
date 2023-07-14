package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;

import java.util.Map;
import java.io.StringWriter;
import java.io.IOException;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Configuration;

/**
 * @author hudyakov
 * @ created 07.11.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class CommandBase implements Command
{
	protected static final String BLANK = " ";
	protected static final String COLON = ":";
	protected static final String SEMICOLON = ";";
	private static final String WRONG_COMMAND = "{0}"; //меняем на ошибочную команду
	private static final String WRONG_ACTION  = "{1}"; //меняем на ошибку

    private OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());

	protected Configuration configuration;
	protected Map<String, String> parameters;
	protected Map<String, String> exceptions;
	protected String message;
	private String help;

	public void initialize(String message)
	{
		this.message = message;
	}

	public String getHelp()
	{
		return help;
	}

	public void setHelp(String help)
	{
		this.help = help;
	}

	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}

	public void setExceptions(Map<String, String> exceptions)
	{
		this.exceptions = exceptions;
	}

	public String getException(String ex)
	{
		return exceptions.get(ex);
	}

	public Operation createOperation(Class clazz) throws BusinessException, BusinessLogicException
	{
		return operationFactory.create(clazz, "SmsBanking");
	}

	/**
	 * разбивает строку на лексемы, разделенные символами ' ', ',', ';'
	 * @param line - строка на разработку
	 * @return массив из лексем строки
	 */
	public static String[] parseString(String line)
	{
		if (line == null)
		{
			return new String[]{null};
		}

		return line.trim().split("( )*( |,|;)( )*"); // regexp by Krenev
	}

	/**
	 * приводим сообщение об ошибке к виду,
	 * удобному для клиента
	 * @param ex - сообщение об ошибке
	 * @return обработанное сообщение об ошибке
	 */
	protected String getMessageException(String ex)
	{
		String exception = getException("ex0").replace(WRONG_COMMAND, message).replace(WRONG_ACTION, ex);

		return exception.trim();
	}

	/**
	 * создаем клиенту сообщение
	 * @param rootMap
	 * @param str - сообщение
	 * @return обработанное сообщение
	 */
	protected String buildMessage(Map<String, Object> rootMap, String str) throws BusinessException
	{
		StringWriter writer = new StringWriter();
		try
		{
			Template template = configuration.getTemplate(parseString(message)[0] + '#' + str);
			template.process(rootMap, writer);
		}
		catch (TemplateException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
		 	throw new BusinessException(e);
		}
		return writer.toString().trim();
	}
}
