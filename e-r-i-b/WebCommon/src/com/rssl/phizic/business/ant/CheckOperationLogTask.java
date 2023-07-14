package com.rssl.phizic.business.ant;

import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;

/**
 * Таск для проверки соответствия экшенов из struts-config их описанию в operation-log
 * @author basharin
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class CheckOperationLogTask extends Task
{
	// имя файла с текстами для методов экшенов
	private static final String OPERATION_LOG_FILE = "/Business/src/com/rssl/phizic/business/log/operations/config/operation-log.properties";
	private static final String OPERATION_DESCIPTION_SUFFIX = ".description";
	private static final String OPERATION_TYPE_SUFFIX = ".type";
	private static final String OPERATION_TYPE_PASSIVE = "PASSIVE";
	private static final String DELIMITER = ".";
	private static final Class returnTypeTemplate = ActionForward.class;
	private static final Class[] parameterTypesTemplate =
            {
                ActionMapping.class,
                ActionForm.class,
                HttpServletRequest.class,
                HttpServletResponse.class
            };
	private static final List<String> blackListNameMethods;                 //имена методов которые не должны быть описаны
	private static final List<String> defaultOperations;                    //имена методов которые имеют тип по умолчанию

	private String baseDir;
	private FileSet strutsFiles = new FileSet();                            // анализируемые struts файлы
	private Set<String> descriptionMethodsOfActions;                        // тексты для методов экшенов
	private Set<String> typeMethodsOfActions;                               // типы методов экшенов
	private Map<String, List<String>> errorInDescriptions;                  // имена экшенов которые оказались без описания
	private Set<String> errorInType;                                        // имена экшенов которые оказались без типа

	static
	{
		blackListNameMethods = new ArrayList<String>();
		blackListNameMethods.add("execute");
		blackListNameMethods.add("getFavouriteForward");

		defaultOperations = new ArrayList<String>();
		defaultOperations.add("remove");
		defaultOperations.add("save");
		defaultOperations.add("start");
		defaultOperations.add("filter");
    }

	public void execute() throws BuildException
	{
		ClassLoader oldClassLoader = null;
		try
		{
			oldClassLoader = Thread.currentThread().getContextClassLoader();
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
			safeExecute();
		}
		finally
		{
			if (oldClassLoader != null)
				Thread.currentThread().setContextClassLoader(oldClassLoader);
		}
	}

	private void safeExecute()
	{
		errorInDescriptions = new HashMap<String, List<String>>();
		errorInType = new HashSet<String>();
		initDescriptions();

		List<String> actionNames = parseStruts();

		for (String actionName : actionNames)
		{
			checkAction(actionName);
		}
		if (!errorInDescriptions.isEmpty() || !errorInType.isEmpty())
		{
			throwError();
		}
	}

	private void initDescriptions() throws BuildException
	{
		descriptionMethodsOfActions = new HashSet<String>();
		typeMethodsOfActions = new HashSet<String>();
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(baseDir + OPERATION_LOG_FILE));
			String line;
			while ((line = reader.readLine()) != null)
			{
				if (line.contains(OPERATION_DESCIPTION_SUFFIX))
				{
					String nameActionAndMethod = line.substring(0, line.lastIndexOf(OPERATION_DESCIPTION_SUFFIX));
					descriptionMethodsOfActions.add(nameActionAndMethod);
				}
				if (line.contains(OPERATION_TYPE_SUFFIX))
				{
					String nameActionAndMethod = line.substring(0, line.lastIndexOf(OPERATION_TYPE_SUFFIX));
					typeMethodsOfActions.add(nameActionAndMethod);
				}
			}
		}
		catch (IOException e)
		{
			throw new BuildException("Ошибка при чтении operation-log.properties", e);
		}
		finally
		{
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException ignore)
				{
					log("Не удалось закрыть operation-log.properties");
				}
			}
		}
	}

	private List<String> parseStruts() throws BuildException
	{
		List<String> actionNames = new ArrayList<String>();
		Iterator<FileResource> iterator = strutsFiles.iterator();
		File strutsFile = null;
		while (iterator.hasNext())
		{
			try
			{
				FileResource resource = iterator.next();
				strutsFile = resource.getFile();

				DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();

				documentBuilder.setEntityResolver(new EntityResolver() {
					public InputSource resolveEntity(String publicId, String systemId)
							throws SAXException, IOException {
						//не пытаемся лезть за dtd файлом. т.к. не во всех офисах правильно настроен прокси сервер, и приложение может где то не запуститься
						return new InputSource(new StringReader(""));
					}
				});

				Document struts = documentBuilder.parse(strutsFile);
				NodeList nodeList = struts.getElementsByTagName("action");
				for (int i = 0; i<nodeList.getLength(); ++i)
				{
					Node node = nodeList.item(i);
					actionNames.add(((Element)node).getAttribute("type"));
				}
			}
			catch (Exception e)
			{
				throw new BuildException("Ошибка при чтении " + strutsFile.getName(), e);
			}
		}
		return actionNames;
	}

	private void checkAction(String actionName)
	{
		try
		{
			if (StringHelper.isEmpty(actionName))
				return;

			List<String> methodNames = new ArrayList<String>();
			Class clazz = ClassHelper.loadClass(actionName);

			Method[] publicMethods = clazz.getMethods();

			Method getKeyMap = findGetKeyMethodMapMethod(clazz);
			Map<String, String> map = null;
			if (getKeyMap != null)
			{
				Object object = clazz.newInstance();
				getKeyMap.setAccessible(true);
				map = (Map<String, String>)getKeyMap.invoke(object, null);
			}

			if (map == null)
				map = new HashMap<String, String>();

			for (Method method : publicMethods)
			{
				if (ArrayUtils.isEquals(method.getParameterTypes(), parameterTypesTemplate) && method.getReturnType().equals(returnTypeTemplate)
						&& !blackListNameMethods.contains(method.getName()) && (map.containsValue(method.getName()) || method.getName().equals("start")))
				{
					methodNames.add(method.getName());
				}
			}

			for (String methodName : methodNames)
			{
				if (!descriptionMethodsOfActions.contains(actionName + DELIMITER + methodName))
					addDescriptionError(actionName, methodName);
				if (!defaultOperations.contains(methodName) && !typeMethodsOfActions.contains(actionName + DELIMITER + methodName))
			        addTypeError(actionName, methodName);
			}
		}
		catch (Exception ignore)
		{
			log("Класс " + actionName + " не удается найти по указанному в struts пути.");
		}
		catch (LinkageError ignore)
		{
			log("Не удалось загрузить " + actionName + " для проверки operation-log.properties");
		}

	}

	private Method findGetKeyMethodMapMethod(Class clazz)
	{
		while (clazz != null)
		{
			Method[] methods = clazz.getDeclaredMethods();

			for (Method method : methods)
			{
				if (method.getName().equals("getKeyMethodMap"))
				{
					return method;
				}
			}

			clazz = clazz.getSuperclass();
		}
		return null;
	}

	private void addTypeError(String actionName, String methodName)
	{
		errorInType.add(actionName + DELIMITER + methodName);
	}

	private void addDescriptionError(String actionName, String methodName)
	{
		if (errorInDescriptions.containsKey(actionName))
		{
			if (!errorInDescriptions.get(actionName).contains(methodName))
				errorInDescriptions.get(actionName).add(methodName);
		}
		else
		{
			errorInDescriptions.put(actionName, new ArrayList<String>());
			errorInDescriptions.get(actionName).add(methodName);
		}
	}

	private void throwError() throws BuildException
	{
		log("");
		for (String action : errorInDescriptions.keySet())
		{
			for (String method : errorInDescriptions.get(action))
			{
				log(action + DELIMITER + method + OPERATION_DESCIPTION_SUFFIX + " = ");
				if (errorInType.contains(action + DELIMITER + method))
				{
					errorInType.remove(action + DELIMITER + method);
					log(action + DELIMITER + method + OPERATION_TYPE_SUFFIX + " = " + OPERATION_TYPE_PASSIVE);
				}
			}
			log("");
		}

		//пишем в лог методы из operation-log в которых не прописан типа
		for (String name : errorInType)
		{
			log(name + OPERATION_TYPE_SUFFIX + " = " + OPERATION_TYPE_PASSIVE);
		}

		String msg = "Для указанных выше методов не прописаны сообщения при логировании в operation-log.properties\n" +
				     "Для успешного создания struts файлов необходимо прописать сообщения в operation-log.properties";

		throw new BuildException(msg);
	}

	/**
	 * Получить корневую директорию
	 * @return корневая директория
	 */
	public String getBaseDir()
	{
		return baseDir;
	}

	/**
	 * Установить корневую директорию
	 * @param baseDir корневая директория
	 */
	public void setBaseDir(String baseDir)
	{
		this.baseDir = baseDir;
	}

	/**
	 * Добавить список struts файлов для проверки
	 * @param fileset список struts файлов для проверки
	 */
	public void addFileset(FileSet fileset)
	{
		this.strutsFiles = fileset;
	}
}