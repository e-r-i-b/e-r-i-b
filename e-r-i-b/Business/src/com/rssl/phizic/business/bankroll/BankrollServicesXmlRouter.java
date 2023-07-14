package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.generated.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

/**
 * @author Egorova
 * @ created 09.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class BankrollServicesXmlRouter
{
	protected static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	private static final String BANKROLL_CONFIG_FILE_NAME = "bankrollServices.xml";

	private Map<String, BankrollService> sources = new HashMap<String,BankrollService>();
	private Map<String, MethodDescriptor> methods = new HashMap<String,MethodDescriptor>();

	private String defaultSourceAlias;
	/**
	 * @throws GateException
	 */
	public BankrollServicesXmlRouter(GateFactory factory) throws GateException
	{
		try
		{
			ClassLoader classLoader   = Thread.currentThread().getContextClassLoader();
			JAXBContext context       = JAXBContext.newInstance("com.rssl.phizic.gate.bankroll.generated", classLoader);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			InputStream inputStream  = ResourceHelper.loadResourceAsStream(BANKROLL_CONFIG_FILE_NAME);
			ServicesType services      = (ServicesType) unmarshaller.unmarshal(inputStream);
			List<SourceDescriptor> sourcesList= services.getSources().getSource();
			defaultSourceAlias = services.getSources().getDefault();

			for (SourceDescriptor source: sourcesList)
			{
				Class<?> sourceClass = Class.forName(source.getClassName());
				BankrollService bankrollService = (BankrollService) sourceClass.getConstructor(GateFactory.class).newInstance(factory);
				sources.put(source.getAlias(), bankrollService);
			}
			List<MethodDescriptor> methodsList= services.getMethods().getMethod();
			for (MethodDescriptor method : methodsList)
			{
				methods.put(method.getSignature(),method);
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new GateException("В файле "+BANKROLL_CONFIG_FILE_NAME+" в качестве источника задано не правильное название класса. "+e);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/*
	Выполняем метод
	 */
	private Object invokeMethod(BankrollService source, Method currentMethod, Object[] methodParameters) throws GateException, GateLogicException
	{
		try
		{
			Method newMethod = source.getClass().getMethod(currentMethod.getName(), currentMethod.getParameterTypes());
			return newMethod.invoke(source, methodParameters);
		}
		catch (Exception e)
		{
			//метод Method.invoke() оборачивает наш GateLogicException в  InvocationTargetException
			Throwable cause = e.getCause();
			if (cause instanceof GateLogicException)
			{
				throw new GateLogicException(cause.getMessage(), e);
			}
			if (cause instanceof InactiveExternalSystemException)
			{
				throw new InactiveExternalSystemException(cause.getMessage(), e);
			}
			throw new GateException(e);
		}
	}

	/**
	 * Получение конкретного объекта
	 * @param currentMethod - метод вызвавший
	 * @param methodParameters - конкретные параметры, со значениями.
	 * @return объект
	 * @throws GateException
	 */
	public Object getBankrollObject(Method currentMethod, Object[] methodParameters) throws GateException, GateLogicException
	{
		try
		{
			String signature = getMethodSignature(currentMethod);
			MethodDescriptor method = getMethodBySignature(signature);
			//Если прописан condition, то это САМОЕ главное, выполняется метод, с указанным там сорсом, field'ы не смотрятся воообще
			if (!StringHelper.isEmpty(method.getCondition()))
			{
				ConditionDescriptor condition = getMethodCondition(method);
				BankrollService source;
				if (methodParameters[condition.getParameterNmber()] instanceof Object[])
				{
					source = getSourceByInterfaces(((Object[])methodParameters[condition.getParameterNmber()])[0], condition);
				}
				else{
					source = getSourceByInterfaces(methodParameters[condition.getParameterNmber()], condition);
				}
				if (source!=null) return invokeMethod(source, currentMethod, methodParameters);
			}
			//Получаем все описанные в xml для этого метода source'ы
			Map<String, BankrollService> sources = getMethodSources(method);

			//если 1 source, то не мудрим
			if (sources.size() == 1)
				return invokeMethod(sources.values().iterator().next(), currentMethod, methodParameters);

			//Если сорсов больше одного, то первый дефолтный
			//не можем взять дефолтный сорс из xmlRouter'а, т.к. если для всего метода переопределен source - он дефолтный.
			String defauleSourceName = "";

			//записывае результат выполнения для всех сорсов, один помечаем как основной.
			Map<String, Object> objectMap = new HashMap<String, Object>();
			for (String sourceKey : sources.keySet())
			{
				if (StringHelper.isEmpty(defauleSourceName))
					defauleSourceName = sourceKey;

				objectMap.put(sourceKey, invokeMethod(sources.get(sourceKey), currentMethod, methodParameters));
			}
			//Сначала заполняем результат, дефолтными значениями
			Object result = objectMap.get(defauleSourceName);

			//Больше одного сорса может быть ТОЛЬКО если хотя бы один field определен с альтернативням source'м
			List<FieldDescriptor> fields = method.getField();
			BeanUtilsBean beanUtils = new BeanUtilsBean();
			for (FieldDescriptor field : fields)
			{
				Object value = beanUtils.getPropertyUtils().getProperty(objectMap.get(field.getSource()), field.getName());
				BeanUtils.setProperty(result, field.getName(), value);
			}
			return result;
		}
		catch (GateLogicException ex)
		{
		    throw ex;	
		}
		catch (InactiveExternalSystemException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получение MethodDescriptor по сигнатуре вызывающего метода
	 * @param signature сигнатура вызвавшего метода
	 * @return MethodDescriptor
	 * @throws GateException
	 */
	private MethodDescriptor getMethodBySignature(String signature) throws GateException
	{
		MethodDescriptor method = methods.get(signature);
		if (method==null)
			throw new GateException("Метод "+signature+" не описан в файле "+BANKROLL_CONFIG_FILE_NAME);
		return method;
	}

	/**
	 * Получение только необходимых sources'ов для этого method.
	 * Т.е. если в xml для метода не прописан ни для одного поля альтернативный источник,
	 * то и необходимости в нем нет (скорее всего UnsopportedOperationException вообще кинет)
	 * @param methodDescriptor - метод
	 * @return Map только необходимых источников
	 */
	private Map<String,BankrollService> getMethodSources(MethodDescriptor methodDescriptor)
	{
		Map<String,BankrollService> methodSources = new HashMap<String,BankrollService>();
		//Если у method прописан condition, то ничего дальше не смотрим.
		String methodSourceName = methodDescriptor.getSource();
		//Добавляем source, описанный в методе
		if (StringHelper.isEmpty(methodSourceName))
			//дефолтный сорс берем, только если он не переопределен в методе.
			methodSources.put(defaultSourceAlias, sources.get(defaultSourceAlias));
		else
			methodSources.put(methodSourceName, sources.get(methodSourceName));
		List<FieldDescriptor> fields = methodDescriptor.getField();
		if (fields.isEmpty())
			return methodSources;
		for (FieldDescriptor field: fields)
		{
			String sourceName = field.getSource();
			//Добавляем source, описанный в field'e
			if (!StringHelper.isEmpty(sourceName))
				methodSources.put(sourceName, sources.get(sourceName));
		}
		return methodSources;
	}

	/**
	 * Получение всех источников, описанных во ВСЕХ условиях
	 * @param methodDescriptor - метод
	 * @return источники
	 * todo. Оставила на тот случай, когда будет condition у field'ов. Т.к. тогда надо будет получать ВСЕ source'ы
	 */
	private Map<String,BankrollService> getMethodConditionsSources(MethodDescriptor methodDescriptor)
	{
		List<ConditionDescriptor> conditions = methodDescriptor.getConditions().getCondition();
		if (conditions.isEmpty())
			return null;
		Map<String,BankrollService> methodConditionsSources = new HashMap<String,BankrollService>();
		for (ConditionDescriptor condition: conditions)
		{
			methodConditionsSources.putAll(getMethodConditionSources(methodDescriptor, condition.getAlias()));
		}
		return methodConditionsSources;
	}

	/**
	 * Получение source, для КОНКРЕТНОГО условия по названию условия
	 * @param methodDescriptor - метод
	 * @param sourceName - название требуемого условия
	 * @return Map<String,BankrollService>, иначе пустой map
	 */
	private Map<String,BankrollService> getMethodConditionSources(MethodDescriptor methodDescriptor, String sourceName)
	{
		ConditionDescriptor condition = getCondition(methodDescriptor.getConditions().getCondition(), sourceName);
		if (condition==null)
			return new HashMap<String,BankrollService>();
		return getCaseSources(condition.getCase());
	}

	/**
	 * Получение condition для method
	 * @param methodDescriptor - метод
	 * @return ConditionDescriptor
	 */
	private ConditionDescriptor getMethodCondition(MethodDescriptor methodDescriptor) throws GateException
	{
		List<ConditionDescriptor> methodConditions = methodDescriptor.getConditions().getCondition();
		String needMethodCondition = methodDescriptor.getCondition();
		for (ConditionDescriptor methodCondition: methodConditions)
		{
			if (methodCondition.getAlias().equals(needMethodCondition))
				return methodCondition;
		}
		throw new GateException("Проблема получения condition для method. Либо описано не то, либо вообще не описано.");
	}

	/**
	 * Получение конкретного condition по названию
	 * @param conditions - список всех условий
	 * @param sourceName - название требуемого
	 * @return ConditionDescriptor, если нет - null
	 */
	private ConditionDescriptor getCondition(List<ConditionDescriptor> conditions, String sourceName)
	{
		if (conditions.isEmpty())
			return null;
		for (ConditionDescriptor condition: conditions)
		{
			if (condition.getAlias().equals(sourceName))
				return condition;
		}
		return null;
	}

	/**
	 * Получение source прописанных в case, конкретного condition
	 * @param cases - условия
	 * @return Map<String,BankrollService>
	 */
	private Map<String,BankrollService> getCaseSources(List<CaseDescriptor> cases)
	{
		Map<String,BankrollService> caseSources = new HashMap<String,BankrollService>();
		for (CaseDescriptor caseDescriptor: cases)
		{
			String sourceAlias = caseDescriptor.getSource();
			if (!StringHelper.isEmpty(sourceAlias))
				caseSources.put(sourceAlias, sources.get(sourceAlias));
		}
		return caseSources;
	}

	/*
	Получаем BankrollService по интерфейсы "ключевого" параметра, т.е. параметра описанного в condition
	 */
	private BankrollService getSourceByCaseClassName(ConditionDescriptor condition,String className)  throws GateException
	{
		List<CaseDescriptor> cases = condition.getCase();
		for (CaseDescriptor caseDescriptor: cases)
		{
			if (caseDescriptor.getValue().equals(className))
				return sources.get(caseDescriptor.getSource());
		}
		return null;
	}

	/*
	Получаем BankrollService по интерфейсы "ключевого" параметра, т.е. параметра описанного в condition
	 Т.к. интерфесов может быть несколько, перебираем все и по очереди вызываем getSourceByCaseClassName
	 */
	private BankrollService getSourceByInterfaces(Object parameter, ConditionDescriptor condition) throws GateException
	{
		List<CaseDescriptor> cases = condition.getCase();

		for (CaseDescriptor caseDescriptor: cases)
		{

			try{
				if (Class.forName(caseDescriptor.getValue()).isInstance(parameter))
					return sources.get(caseDescriptor.getSource());
			}catch (ClassNotFoundException e)
			{
				throw new GateException(e);
			}

		}
		return null;
	}

	private String getMethodSignature(Method method)
	{
		String result = method.toString();
		if (method.isVarArgs())
			return result.replace("transient ", ""); //костыль. http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6516895
		else
			return result;
	}
}
