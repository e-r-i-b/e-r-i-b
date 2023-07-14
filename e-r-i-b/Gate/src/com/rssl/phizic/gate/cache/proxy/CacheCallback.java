package com.rssl.phizic.gate.cache.proxy;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.AdditionalProductData;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateWaitCreateCacheException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Omeliyanchuk
 * @ created 29.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class CacheCallback implements MethodInterceptor
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CACHE);
	private static final String PLEASE_WAIT_CACHE_KEY = "PLEASE_WAIT_CACHE_CALLBACK_KEY";

	private enum CacheKeyType
	{
		CACHE_KEY,
		WAIT_KEY
	}

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable
	{
		//вариант с null не рассматриваем, т.к. если интерсептор сработал, значит у метода такая аннотация должна быть
		Method interfaceMethod = CacheAnnotationHelper.getInterfaceMethodByMethod(method);
		log.trace("Нить:"+Thread.currentThread().getId() + " Обрабатываем метод:" + interfaceMethod.toString());
		Cachable annot = interfaceMethod.getAnnotation(Cachable.class);
		CacheKeyComposer composer = getComposer(annot);

		Cache cache = getCacheByMethod(interfaceMethod);
		if(cache==null)
			return proxy.invokeSuper(obj, args);

	    //если операции груповая, то ищем в кеше результат для каждого участника групповой операции
		//т.к. результат может прийти и из других операций.
		String resolveParams = annot.resolverParams();
		if(isGroupOperation(interfaceMethod))
		{
			//ключи, по которым есть данные
			List<Object> substractKeys = new ArrayList<Object>();
			//уже готовые результаты
			List<Pair<Object,Object>> readyResults =new ArrayList<Pair<Object,Object>>();
			//получаем ключи по всем элементам групповой операции
			List<Pair<Serializable, Object>> keys = composer.getKeys(args, resolveParams);
			//ищем по чему уже есть готовые данные
			for (Pair<Serializable,Object> key : keys)
			{
				Object el = getValueFromCache(cache, key.getFirst(),interfaceMethod,args);
				if(el!=null)
				{
					readyResults.add(new Pair<Object,Object>(key.getSecond(), ((Element)el).getObjectValue()));
					substractKeys.add(key.getSecond());
				}
				else
				{
					//System.out.println("Непопадание в кеш в нити "+ Thread.currentThread().getId() +" всего непопаданий " +notChacheHit.incrementAndGet());
					//log.trace("Нить:"+Thread.currentThread().getId() + " Непопадание в кеш: метод - " + method.toGenericString() + " , ключ -" + key.getFirst());
				}
			}

			//вычитаем из запроса элементы результаты по которым уже есть
			Object[] newArgs = args;
			if(substractKeys.size()!=0)
			{
				newArgs = composer.substractReadyArgs(args,resolveParams, substractKeys);
			}
			//делаем запрос только для тех, по которым данные не нашли
			Object res;
			if(newArgs!=null)
			{
				res = proxy.invokeSuper(obj, newArgs);
				//сохраняем в кеш результаты
				putGroupResultInCache(args, interfaceMethod, composer, cache, resolveParams, res);

				//дополняем связанные сущности
				putInDependCacheOrGroup(interfaceMethod, res,args);
			}
			else
			{
				res = new GroupResult();
			}
			//дополняем тем, что уже было получено
			GroupResult groupResult = (GroupResult)res;
			for (Pair<Object, Object> readyResult : readyResults)
			{
				groupResult.putResult(readyResult.getFirst(), readyResult.getSecond());
			}
			return groupResult;
		}
		else
		{
			Serializable key = composer.getKey(args, resolveParams);
			Object res = getValueFromCache(cache, key, interfaceMethod,args);
			if(res!=null)
			{
				Element el = (Element)res;
				Object resValue = el.getObjectValue();

				if (resValue instanceof String && resValue.equals(PLEASE_WAIT_CACHE_KEY))
				{
					throw new GateWaitCreateCacheException("Уважаемый клиент, Ваш запрос обрабатывается. Пожалуйста подождите.");
				}

				return resValue;
			}

			res = invokeSuper(obj, cache, key, interfaceMethod, args, proxy);

			putInCache(cache, key, res, interfaceMethod,args, CacheKeyType.CACHE_KEY);
			//log.trace("Нить:"+Thread.currentThread().getId() + " Непопадание в кеш: метод - " + method.toGenericString() + " , ключ -" + key);
			//System.out.println("Непопадание в кеш в нити "+ Thread.currentThread().getId() +" всего непопаданий " +notChacheHit.incrementAndGet());

			putInDependCacheOrGroup(interfaceMethod, res,args);

			return res;
		}
	}

	private Object invokeSuper(Object obj, Cache cache, Serializable key, Method interfaceMethod, Object[] args, MethodProxy proxy) throws Throwable
	{
		Cachable annot = interfaceMethod.getAnnotation(Cachable.class);
		if (annot.cachingWithWaitInvoke())
		{
			return invokeSuperWithWait(obj, cache, key, interfaceMethod, args, proxy);
		}

		return proxy.invokeSuper(obj, args);
	}

	private Object invokeSuperWithWait(Object obj, Cache cache, Serializable key, Method interfaceMethod, Object[] args, MethodProxy proxy) throws Throwable
	{
		try
		{
			putInCache(cache, key, PLEASE_WAIT_CACHE_KEY, interfaceMethod, args, CacheKeyType.WAIT_KEY);
			return proxy.invokeSuper(obj, args);
		}
		catch (Throwable e)
		{
			cache.remove(key);
			throw e;
		}
	}

	/**
	 * положить результаты групповой операции в кеш
	 * @param args агрументы вызова метода
	 * @param interfaceMethod метода
	 * @param composer композер
	 * @param cache кеш
	 * @param resolveParams параметры аннотации
	 * @param res результат выполнения
	 */
	private void putGroupResultInCache(Object[] args, Method interfaceMethod, CacheKeyComposer composer, Cache cache, String resolveParams, Object res)
	{
		GroupResult newResults = (GroupResult)res;
		Map resMap= newResults.getResults();
		int arrayParam = getGroupResultKeyParameterNum(interfaceMethod);
		if(arrayParam!=-1)
				{
					int size = args.length;
			Object[] proxyArgs = new Object[size];
			System.arraycopy(args, 0 , proxyArgs, 0,size);

			for (Object mapKey : resMap.keySet())
			{
				//эмулируем не групповую операцию и складываем в кеш
				proxyArgs[arrayParam] = mapKey;
				Serializable key = composer.getKey(proxyArgs,resolveParams);
				putInCache(cache,key, resMap.get(mapKey),interfaceMethod,proxyArgs, CacheKeyType.CACHE_KEY);
			}
		}
		else
			log.error("Не удалось найти параметр с массивом у групповой операции "+interfaceMethod.toString());
	}

	/**
	 * Групповая ли операции, определяет по типу возвращаемого значения
	 * @param interfaceMethod метод
	 * @return true - групповая
	 */
	private boolean isGroupOperation(Method interfaceMethod)
	{
		return interfaceMethod.getReturnType().isAssignableFrom(GroupResult.class) && !ClassHelper.isGetClientProduct(interfaceMethod);
	}

	/**
	 * Получить кеш для метода
	 * @param interfaceMethod метод ИНТЕРФЕЙСА гейта
	 * @return кеш для метода, раз попали в этот класс, значит кеш должен быть
	 */
	private Cache getCacheByMethod(Method interfaceMethod)
	{
		GateBusinessCacheSingleton businessCacheSingleton = GateBusinessCacheSingleton.getInstance();
		Cache cache = businessCacheSingleton.getCache(GateBusinessCacheConfig.getCacheNameByMethod(interfaceMethod));
		if(cache==null)
			log.error("Не найден кеш для метода:" + interfaceMethod.toString());
		return cache;
	}

	/**
	 * Получить композер из аннотации(если не нашел, то исключение)
	 * @param annot аннотация с метода интерфейса гейта
	 * @return композер
	 */
	private CacheKeyComposer getComposer(Cachable annot)
	{
		Class<? extends CacheKeyComposer> composerClass = annot.keyResolver();

		CacheKeyComposer composer = GateBusinessCacheConfig.getComposer(composerClass);
		if(composer==null)
			throw new RuntimeException("В классе CacheCallback не инициализирован композер для класса:"+composerClass.toString());
		return composer;
	}

	/**
	 * разбирает результат операции и сохраняет в кеш операций с такими же возвращаемыми значениями
	 * @param method метод интерфейса гейта для которого ищем связанные
	 * @param res результат исполнения метода
	 */
	private void putInDependCacheOrGroup(Method method, Object res, Object args[])
	{
		log.trace("Нить:"+Thread.currentThread().getId() + " Начало обработки связанных для метода:" + method.toString());

		//нулевые данные не кешируем.
		if(res==null)
			return;

		//если результат операции запрешено использовать в других местах
		if(!method.getAnnotation(Cachable.class).linkable())
		{
			log.trace("Нить:"+Thread.currentThread().getId() + " Конец обработки связанных для метода:" + method.toString());
			return;
		}

		Map<Class, List<Object>> returnTypesWithValues;
		if(!ClassHelper.isGetClientProduct(method))
		{
			Class returnType = ClassHelper.getReturnClassIgnoreCollections(method);
			putInDependCacheOrGroupInternal(method, res, args, returnType);
		}
		else
		{   // для GFL разбираем результат и только после этого посылаем на добавление в кеш.
			//по согласованию с банком делаем это только для счетов и омс, т.к. в остальных случаях
			// могут быть проблемы с расхождением данных
			GroupResult<Class, List<Pair<Object, AdditionalProductData>>> result = (GroupResult<Class, List<Pair<Object, AdditionalProductData>>>)res;
			for (Class productType : result.getKeys())
			{
				if((Account.class).equals(productType) || (IMAccount.class).equals(productType) || (Card.class).equals(productType) || (Loan.class).equals(productType))
				{
					List<Pair<Object, AdditionalProductData>> list = result.getResult(productType);
					// Пропускаем, если по данному типу продукта не получен результат
					if (list == null)
						continue;

					for (Pair<Object, AdditionalProductData> pair : list)
					{
						putInDependCacheOrGroupInternal(method, pair.getFirst(), args, productType);
					}
				}
			}
		}

		log.trace("Нить:"+Thread.currentThread().getId() + " Конец обработки связанных для метода:" + method.toString());
	}

	private void putInDependCacheOrGroupInternal(Method method, Object res, Object[] args, Class returnType)
	{
		GateBusinessCacheSingleton businessCacheSingleton = GateBusinessCacheSingleton.getInstance();
		GateBusinessCacheConfig businessConfig = businessCacheSingleton.getCacheGateBusinessConfig();

		boolean isSame = false;


		List<Method> otherMethods = businessConfig.getMethodsByReturnType(returnType);

		for (Method otherMethod : otherMethods)
		{
			//его самого вычитаем
			if( !isSame && method.equals(otherMethod))
			{
				isSame = true;
				continue;
			}

			//списки не заполняем из результатов выполнения других методов
			//так как невозможно вычислить с каким ключом список положить.
			if(ClassHelper.checkIfCollection(otherMethod.getGenericReturnType()) )
			{
				continue;
			}

			Cachable annot = otherMethod.getAnnotation(Cachable.class);
			CacheKeyComposer composer = getComposer(annot);
			//поддерживает ли композер получения ключа для кеша из результата
			if(composer.isKeyFromResultSupported())
			{
				parseResults(res,composer,otherMethod,method,args);
			}
		}
	}

	/**
	 * Разбирает результаты групповых операций, или когда в результате пришла коллекция и кладет в кеш поэлементно
	 * @param res
	 * @param composer
	 * @param targetMethod
	 * @param srcMethod
	 * @param args
	 */
	private void parseResults(Object res, CacheKeyComposer composer, Method targetMethod, Method srcMethod, Object[] args)
	{
		//если результат список или операция групповая, то разбираем каждый элемент по отдельности
		if(srcMethod.getReturnType().isAssignableFrom(List.class))
		{
			List results = (List)res;
			for (Object result : results)
			{
				putLinkedResult(composer, targetMethod, result, args);
			}
			return;
		}
		if(isGroupOperation(srcMethod))
		{
			GroupResult results = (GroupResult)res;
			Map mapResults = results.getResults();

			Class[] parameterTypes = srcMethod.getParameterTypes();
			int size = parameterTypes.length;
			int arrayParam = getGroupResultKeyParameterNum(srcMethod);

			if(arrayParam!=-1)
			{
				Object[] newArgs = new Object[size];
				System.arraycopy(args, 0 , newArgs, 0,size);

				for (Object mapKey : mapResults.keySet())
				{
					//эмулируем не групповую операцию
					newArgs[arrayParam] = mapKey;
					putLinkedResult(composer,targetMethod,mapResults.get(mapKey),newArgs);
				}
				return;
			}
			else
				log.error("Не удалось найти параметр с массивом у групповой операции "+srcMethod.toString());
		}
		putLinkedResult(composer,targetMethod,res,args);
	}

	/**
	 * вычисляет номер параметра функции, в котором передаеться массив.
	 * Считаем, что именно в нем лежат ключи для GroupResult. Находим первый с конца.
	 * @param interfaceMethod
	 * @return
	 */
	private int getGroupResultKeyParameterNum(Method interfaceMethod)
	{
		Class[] parameterTypes = interfaceMethod.getParameterTypes();
		int size = parameterTypes.length;
		int arrayParam = -1;
		for(int i=size-1; i>=0; i--)
		{
			//ищем массив параметров для групповой операции.
			Class paramClass= parameterTypes[i];
			if(paramClass.isArray())
			{
				arrayParam = i;
				break;
			}
		}
		return arrayParam;
	}

	/**
	 * сохранить связанный результат в кеше
	 * @param composer
	 * @param intefaceMethod
	 * @param result
	 */
	private void putLinkedResult(CacheKeyComposer composer, Method intefaceMethod, Object result, Object[] args)
	{
		Serializable key = composer.getClearCallbackKey(result, args);
		if(key!=null)
		{
			Cache linkedCache= getCacheByMethod(intefaceMethod);
			putInCache(linkedCache,key, result,intefaceMethod,args, CacheKeyType.CACHE_KEY);
		}
	}

	private void putInCache(Cache cache, Serializable key, Object value, Method intefaceMethod, Object[] args, CacheKeyType cacheKeyType)
	{
		if(cache==null)
		{
			StringBuilder builder = new StringBuilder();
			builder.append("Невозможно добавить элемент с ключем");
			if(key!=null)
				builder.append(key.toString());
			else
				builder.append("null");
			builder.append(" в кеш");
			builder.append(", т.к. cache==null");
			log.error(builder.toString());
			return;
		}
		//нулевые значения в кеш не складываем, но если очень нужно (не лезть в шину лишний раз), то можно
		Cachable annot = intefaceMethod.getAnnotation(Cachable.class);
		//Кешировать ли, если значения value == null
		boolean cachingWithNullValue = annot.cachingWithNullValue();

		if ((value!=null || cachingWithNullValue) && !((value instanceof String) && ( ((String)value).length()==0)))
		{
			cache.put(new Element(key, value));

			StringBuilder builder = new StringBuilder();
			builder.append("Нить:");
			builder.append(Thread.currentThread().getId());
			builder.append(" Складываем в кеш ");
			builder.append(cache.getName());
			builder.append(" объект с ключем ");
			if(key!=null)
				builder.append(key);
			else
				builder.append("null");
			builder.append(" по методу ");
			builder.append(intefaceMethod.toString());

			log.trace(builder.toString());

			//заполняем кеш для очистки.
			addToCallBackCache(key, value, intefaceMethod, args, cacheKeyType);
		}
	}

	/**
	 * Добавить в кеш колбек для очисти
	 * @param key ключ по которому будет храниться результат
	 * @param returnValue результат выполнения метода
	 * @param intefaceMethod метод интерфейса
	 */
	private void addToCallBackCache(Serializable key, Object returnValue, Method intefaceMethod, Object[] args, CacheKeyType cacheKeyType)
	{
		log.trace("Нить:"+Thread.currentThread().getId() + " Начало обработки кеша для очистки для метода:" + intefaceMethod.toString());
		//колбек вычисляеться по возвращаемому значению - если оно пустое, то ничего не сделаешь.
		if (returnValue == null)
		{
			return;
		}

		if (CacheKeyType.WAIT_KEY == cacheKeyType)
		{
			addToCallBackCacheInternal(key, returnValue, intefaceMethod.getParameterTypes(),args,intefaceMethod);
			log.trace("Нить:"+Thread.currentThread().getId() + " Конец обработки кеша для очистки для метода:" + intefaceMethod.toString());

			return;
		}
		//см. ProxyCacheServiceImpl
		Class returnClass = intefaceMethod.getReturnType();
		//если вернулись списки или массивы, то для очистки записываем каждую из сущностей
		//т.е. даже если хотят чистить по одному счету, очищаем весь список.
		if(returnClass.isAssignableFrom(List.class))
		{
			List<Object> list = (List<Object>)returnValue;
			for (Object o : list)
			{
				addToCallBackCacheInternal(key, o, intefaceMethod.getParameterTypes(),args,intefaceMethod);
			}
			return;

		}
		if(returnClass.isAssignableFrom(Array.class))
		{
			Object[] arr = (Object[])returnValue;
			for (Object o : arr)
			{
				addToCallBackCacheInternal(key, o,  intefaceMethod.getParameterTypes(),args,intefaceMethod);
			}
			return;
		}
		if(ClassHelper.isGetClientProduct(intefaceMethod))
		{
			addToCallBackCacheInternal(key, args[0], intefaceMethod.getParameterTypes(),args,intefaceMethod);
			return;
		}

		addToCallBackCacheInternal(key, returnValue, intefaceMethod.getParameterTypes(),args,intefaceMethod);
		log.trace("Нить:"+Thread.currentThread().getId() + " Конец обработки кеша для очистки для метода:" + intefaceMethod.toString());
	}

	/**
	 * Тут считаем, что никаких коллекций и груповых результатов не существует.
	 * @param key ключ, по которому собираються сохранять результат в основном кеше
	 * @param returnValue возвращаемое значение уже без коллекций
	 * @param parametersTypes типы параметров метода интерфейса
	 * @param parameters параметры метода с которыми он был вызван
	 * @param interfaceMethod сам метод из интерфейса
	 */
	private void addToCallBackCacheInternal(Serializable key, Object returnValue,
	                                        Class[] parametersTypes, Object[] parameters, Method interfaceMethod)
	{
		Collection<Class> cleanableClasses = GateBusinessCacheConfig.getCleanableClasses();
		Class returnClass;

		//если метод возращает класс по которому могут захотеть чистить кеш
		if(!ClassHelper.isGetClientProduct(interfaceMethod))
			returnClass = ClassHelper.getReturnClassIgnoreCollections(interfaceMethod);
		else
			returnClass = ClassHelper.getReturnClassforGetClientProduct(returnValue,cleanableClasses);

		Class returnInterfaceByValue = returnClass;
		Class returnClassByValue = returnValue.getClass();
		//ищем по возвращаемому значению из метода
		boolean isReturnValue = cleanableClasses.contains(returnClass);
		if(!isReturnValue)
		{
			//если из метода не удалось, значит мог быть использован базовый класс, проверяем интерфейсы самого значения
			Class[] returnInterfacesByValue = returnClassByValue.getInterfaces();
			returnInterfaceByValue = checkIfInterfaceIntersection(cleanableClasses, returnInterfacesByValue);
			isReturnValue = returnInterfaceByValue!=null;
		}
		if(isReturnValue)
		{
			//вычисляем ключ по результату
			CacheKeyComposer composer;
			if(ClassHelper.isGetClientProduct(interfaceMethod))
				composer = GateBusinessCacheConfig.getCleanableClientProductComposer();
			else
				composer = GateBusinessCacheConfig.getCleanableComposer(returnInterfaceByValue);
			//получаем ключ по основной сущности, чтоб потом по нему найти связанные
			Serializable callBackKey = composer.getClearCallbackKey(returnValue, parameters);
			//запоминаем в колбек кеш, для последующей очистки
			if(callBackKey != null)
				fillCallBackCache(interfaceMethod, key, callBackKey);
		}
		else
		{   //если  метод возращает класс связанный с очищаемой сущностью
			//например AccountInfo getAccountInfo(Account), тогда accountInfo также надо почистить,
			// при чистеке кеш по счету
			returnInterfaceByValue = returnClass;
			Collection<Class> linkedClasses = GateBusinessCacheConfig.getLinkedItems();
			//получаем очищаемый класс по возвращаемому результату
			Class cleanableClass = GateBusinessCacheConfig.getCleanableClassByLinkedItems(returnClass);
			//если не нашли, значит был использовано базовый класс, ищем каждый интерфейс результата по отдельности
			if(cleanableClass==null)
			{
				Class[] returnInterfacesByValue = returnClassByValue.getInterfaces();
				for (Class interfaceByValue : returnInterfacesByValue)
				{
					cleanableClass = GateBusinessCacheConfig.getCleanableClassByLinkedItems(interfaceByValue);
					if(cleanableClass!=null)
					{
						returnInterfaceByValue = interfaceByValue;
						break;
					}
				}
			}
			//если вернулась одна из связанных, в примере выше AccountInfo
			if(cleanableClass!=null && linkedClasses.contains(returnInterfaceByValue))
			{
				//будет искать в параметрах очищаемую(основную), в примере выше Account
				CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(cleanableClass);
				if(parameters==null)
					return;

				int size = parameters.length;
				//todo доделать обработку параметров списков и.т.д.
				for (int i=0;i<size;i++)
				{
					Object param = parameters[i];
					if(param!=null && cleanableClass.isAssignableFrom(param.getClass()))
					{
						//получаем ключ по основной сущности, чтоб потом по нему найти связанные
						Serializable callBackKey = composer.getClearCallbackKey(parameters[i], parameters);
						//если угадали с параметром(не угадать могли когда object)
						if(callBackKey!=null)
						{
							//запоминаем в колбек кеш, для последующей очистки
							fillCallBackCache(interfaceMethod, key, callBackKey);
							break;
						}
					}

				}
			}
		}		
	}

	/**
	 * проверка есть ли в коллекции хоть один интерфейс из массива
	 * @param classes набор классов
	 * @param intefaces набор классов
	 * @return первый найденный класс или null, если ничего не найдено
	 */
	private Class checkIfInterfaceIntersection(Collection<Class> classes, Class[] intefaces)
	{
		for (Class inteface : intefaces)
		{
			if(classes.contains(inteface))
				return inteface;
		}
		return null;
	}


	/**
	 * заполняем связанный кеш
	 * @param interfaceMethod сам метода из интерфейса
	 * @param key ключ, по которому собираються сохранять результат в основном кеше
	 * @param callBackKey ключ для хранения в колбек кеше
	 */
	private void fillCallBackCache(Method interfaceMethod, Serializable key,Serializable callBackKey)
	{
		//получем кеш колбеков
		Cache callBackCache = GateBusinessCacheSingleton.getInstance().getCache(GateBusinessCacheSingleton.LINKS_CACHE_NAME);
		Element container = callBackCache.get(callBackKey);
		//под ключем основной сущности, храним пары имя кеша связанного метода - ключ сущности для отчистки в этом кеше
		//таким образом, когда попросят очистить кеш по сущности, вычисляем по ней ключ, ищем в колбек кеше
		//по имени получаем кеш из второго элемента пары ключ, и чистим.
		List<Pair<String,Serializable>> list;
		if(container == null)
		{
			list = new LinkedList<Pair<String,Serializable>>();
		}
		   else
		{
			list = (List<Pair<String,Serializable>>)container.getObjectValue();
		}
		String cacheName = GateBusinessCacheConfig.getCacheNameByMethod(interfaceMethod);
		list.add(new Pair<String,Serializable>(cacheName,key));
		callBackCache.put(new Element(callBackKey, list));

		StringBuilder builder = new StringBuilder();
		builder.append("Нить:");
		builder.append(Thread.currentThread().getId());
		builder.append(" Складываем в колбек объект с ключем ");
		if(key!=null)
			builder.append(key);
		else
			builder.append("null");
		builder.append(" по методу ");
		builder.append(interfaceMethod.toString());
		builder.append("колбек ключ ");
		builder.append(callBackKey);

		log.trace(builder.toString());
	}

	private Object getValueFromCache(Cache cache, Serializable key, Method intefaceMethod, Object[] args)
	{
		Element res = cache.get(key);
		if(res!=null)
		{
			StringBuilder builder = new StringBuilder();
			builder.append("Нить:");
			builder.append(Thread.currentThread().getId());
			builder.append(" Получено значение из кеша с именем ");
			builder.append(cache.getName());
			builder.append(" по ключу ");
			if(key!=null)
				builder.append(key.toString());
			else
				builder.append("null");
			builder.append(" по методу ");
			builder.append(intefaceMethod.toString());
			log.trace(builder.toString());
			//если найдено, обновляем колбек кеш.
			//System.out.println("Попадание в кеш в нити "+ Thread.currentThread().getId() +" всего попаданий " +chacheHit.incrementAndGet());
			refreshCallbackCache(intefaceMethod, res.getObjectValue(),args);
		}

		return res;
	}

	/**
	 * обновить кеш колбека, чтоб он не сгорел раньше сущности
	 * @param intefaceMethod метод из интерфейса из которого получена сущность.
	 * @param res результат выполнения метода
	 */
	private void refreshCallbackCache(Method intefaceMethod, Object res, Object[] parameters)
	{
		//см. ProxyCacheServiceImpl
		Class returnInteface = ClassHelper.getReturnClassIgnoreCollections(intefaceMethod);
		Collection<Class> cleanableClasses = GateBusinessCacheConfig.getCleanableClasses();
		//если это очищаемая сущность
		if(cleanableClasses.contains(returnInteface))
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(returnInteface);
			Serializable callBackKey = composer.getClearCallbackKey(res, parameters);
			Cache callBackCache = GateBusinessCacheSingleton.getInstance().getCache(GateBusinessCacheSingleton.LINKS_CACHE_NAME);
			callBackCache.get(callBackKey);
		}
		else
		{   //если это связанная с очищаемым объектом сущность
			Collection<Class> linkedClasses = GateBusinessCacheConfig.getLinkedItems();
			if(linkedClasses.contains(returnInteface))
			{   //будет искать в параметрах очищаемую
				Class cleanableClass = GateBusinessCacheConfig.getCleanableClassByLinkedItems(returnInteface);
				Class[] params = intefaceMethod.getParameterTypes();
				int size = params.length;
				//todo доделать обработку параметров списков и.т.д.
				for (int i=0;i<size;i++)
				{
					Class param = params[i];
					CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(cleanableClass);
					Cache callBackCache = GateBusinessCacheSingleton.getInstance().getCache(GateBusinessCacheSingleton.LINKS_CACHE_NAME);
					if(param.isAssignableFrom(cleanableClass))
					{
						Serializable callBackKey = composer.getClearCallbackKey(parameters[i], parameters);
						callBackCache.get(callBackKey);
						break;
					}

				}
			}
		}
	}
}
