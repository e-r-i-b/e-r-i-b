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
		//������� � null �� �������������, �.�. ���� ����������� ��������, ������ � ������ ����� ��������� ������ ����
		Method interfaceMethod = CacheAnnotationHelper.getInterfaceMethodByMethod(method);
		log.trace("����:"+Thread.currentThread().getId() + " ������������ �����:" + interfaceMethod.toString());
		Cachable annot = interfaceMethod.getAnnotation(Cachable.class);
		CacheKeyComposer composer = getComposer(annot);

		Cache cache = getCacheByMethod(interfaceMethod);
		if(cache==null)
			return proxy.invokeSuper(obj, args);

	    //���� �������� ��������, �� ���� � ���� ��������� ��� ������� ��������� ��������� ��������
		//�.�. ��������� ����� ������ � �� ������ ��������.
		String resolveParams = annot.resolverParams();
		if(isGroupOperation(interfaceMethod))
		{
			//�����, �� ������� ���� ������
			List<Object> substractKeys = new ArrayList<Object>();
			//��� ������� ����������
			List<Pair<Object,Object>> readyResults =new ArrayList<Pair<Object,Object>>();
			//�������� ����� �� ���� ��������� ��������� ��������
			List<Pair<Serializable, Object>> keys = composer.getKeys(args, resolveParams);
			//���� �� ���� ��� ���� ������� ������
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
					//System.out.println("����������� � ��� � ���� "+ Thread.currentThread().getId() +" ����� ����������� " +notChacheHit.incrementAndGet());
					//log.trace("����:"+Thread.currentThread().getId() + " ����������� � ���: ����� - " + method.toGenericString() + " , ���� -" + key.getFirst());
				}
			}

			//�������� �� ������� �������� ���������� �� ������� ��� ����
			Object[] newArgs = args;
			if(substractKeys.size()!=0)
			{
				newArgs = composer.substractReadyArgs(args,resolveParams, substractKeys);
			}
			//������ ������ ������ ��� ���, �� ������� ������ �� �����
			Object res;
			if(newArgs!=null)
			{
				res = proxy.invokeSuper(obj, newArgs);
				//��������� � ��� ����������
				putGroupResultInCache(args, interfaceMethod, composer, cache, resolveParams, res);

				//��������� ��������� ��������
				putInDependCacheOrGroup(interfaceMethod, res,args);
			}
			else
			{
				res = new GroupResult();
			}
			//��������� ���, ��� ��� ���� ��������
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
					throw new GateWaitCreateCacheException("��������� ������, ��� ������ ��������������. ���������� ���������.");
				}

				return resValue;
			}

			res = invokeSuper(obj, cache, key, interfaceMethod, args, proxy);

			putInCache(cache, key, res, interfaceMethod,args, CacheKeyType.CACHE_KEY);
			//log.trace("����:"+Thread.currentThread().getId() + " ����������� � ���: ����� - " + method.toGenericString() + " , ���� -" + key);
			//System.out.println("����������� � ��� � ���� "+ Thread.currentThread().getId() +" ����� ����������� " +notChacheHit.incrementAndGet());

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
	 * �������� ���������� ��������� �������� � ���
	 * @param args ��������� ������ ������
	 * @param interfaceMethod ������
	 * @param composer ��������
	 * @param cache ���
	 * @param resolveParams ��������� ���������
	 * @param res ��������� ����������
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
				//��������� �� ��������� �������� � ���������� � ���
				proxyArgs[arrayParam] = mapKey;
				Serializable key = composer.getKey(proxyArgs,resolveParams);
				putInCache(cache,key, resMap.get(mapKey),interfaceMethod,proxyArgs, CacheKeyType.CACHE_KEY);
			}
		}
		else
			log.error("�� ������� ����� �������� � �������� � ��������� �������� "+interfaceMethod.toString());
	}

	/**
	 * ��������� �� ��������, ���������� �� ���� ������������� ��������
	 * @param interfaceMethod �����
	 * @return true - ���������
	 */
	private boolean isGroupOperation(Method interfaceMethod)
	{
		return interfaceMethod.getReturnType().isAssignableFrom(GroupResult.class) && !ClassHelper.isGetClientProduct(interfaceMethod);
	}

	/**
	 * �������� ��� ��� ������
	 * @param interfaceMethod ����� ���������� �����
	 * @return ��� ��� ������, ��� ������ � ���� �����, ������ ��� ������ ����
	 */
	private Cache getCacheByMethod(Method interfaceMethod)
	{
		GateBusinessCacheSingleton businessCacheSingleton = GateBusinessCacheSingleton.getInstance();
		Cache cache = businessCacheSingleton.getCache(GateBusinessCacheConfig.getCacheNameByMethod(interfaceMethod));
		if(cache==null)
			log.error("�� ������ ��� ��� ������:" + interfaceMethod.toString());
		return cache;
	}

	/**
	 * �������� �������� �� ���������(���� �� �����, �� ����������)
	 * @param annot ��������� � ������ ���������� �����
	 * @return ��������
	 */
	private CacheKeyComposer getComposer(Cachable annot)
	{
		Class<? extends CacheKeyComposer> composerClass = annot.keyResolver();

		CacheKeyComposer composer = GateBusinessCacheConfig.getComposer(composerClass);
		if(composer==null)
			throw new RuntimeException("� ������ CacheCallback �� ��������������� �������� ��� ������:"+composerClass.toString());
		return composer;
	}

	/**
	 * ��������� ��������� �������� � ��������� � ��� �������� � ������ �� ������������� ����������
	 * @param method ����� ���������� ����� ��� �������� ���� ���������
	 * @param res ��������� ���������� ������
	 */
	private void putInDependCacheOrGroup(Method method, Object res, Object args[])
	{
		log.trace("����:"+Thread.currentThread().getId() + " ������ ��������� ��������� ��� ������:" + method.toString());

		//������� ������ �� ��������.
		if(res==null)
			return;

		//���� ��������� �������� ��������� ������������ � ������ ������
		if(!method.getAnnotation(Cachable.class).linkable())
		{
			log.trace("����:"+Thread.currentThread().getId() + " ����� ��������� ��������� ��� ������:" + method.toString());
			return;
		}

		Map<Class, List<Object>> returnTypesWithValues;
		if(!ClassHelper.isGetClientProduct(method))
		{
			Class returnType = ClassHelper.getReturnClassIgnoreCollections(method);
			putInDependCacheOrGroupInternal(method, res, args, returnType);
		}
		else
		{   // ��� GFL ��������� ��������� � ������ ����� ����� �������� �� ���������� � ���.
			//�� ������������ � ������ ������ ��� ������ ��� ������ � ���, �.�. � ��������� �������
			// ����� ���� �������� � ������������ ������
			GroupResult<Class, List<Pair<Object, AdditionalProductData>>> result = (GroupResult<Class, List<Pair<Object, AdditionalProductData>>>)res;
			for (Class productType : result.getKeys())
			{
				if((Account.class).equals(productType) || (IMAccount.class).equals(productType) || (Card.class).equals(productType) || (Loan.class).equals(productType))
				{
					List<Pair<Object, AdditionalProductData>> list = result.getResult(productType);
					// ����������, ���� �� ������� ���� �������� �� ������� ���������
					if (list == null)
						continue;

					for (Pair<Object, AdditionalProductData> pair : list)
					{
						putInDependCacheOrGroupInternal(method, pair.getFirst(), args, productType);
					}
				}
			}
		}

		log.trace("����:"+Thread.currentThread().getId() + " ����� ��������� ��������� ��� ������:" + method.toString());
	}

	private void putInDependCacheOrGroupInternal(Method method, Object res, Object[] args, Class returnType)
	{
		GateBusinessCacheSingleton businessCacheSingleton = GateBusinessCacheSingleton.getInstance();
		GateBusinessCacheConfig businessConfig = businessCacheSingleton.getCacheGateBusinessConfig();

		boolean isSame = false;


		List<Method> otherMethods = businessConfig.getMethodsByReturnType(returnType);

		for (Method otherMethod : otherMethods)
		{
			//��� ������ ��������
			if( !isSame && method.equals(otherMethod))
			{
				isSame = true;
				continue;
			}

			//������ �� ��������� �� ����������� ���������� ������ �������
			//��� ��� ���������� ��������� � ����� ������ ������ ��������.
			if(ClassHelper.checkIfCollection(otherMethod.getGenericReturnType()) )
			{
				continue;
			}

			Cachable annot = otherMethod.getAnnotation(Cachable.class);
			CacheKeyComposer composer = getComposer(annot);
			//������������ �� �������� ��������� ����� ��� ���� �� ����������
			if(composer.isKeyFromResultSupported())
			{
				parseResults(res,composer,otherMethod,method,args);
			}
		}
	}

	/**
	 * ��������� ���������� ��������� ��������, ��� ����� � ���������� ������ ��������� � ������ � ��� �����������
	 * @param res
	 * @param composer
	 * @param targetMethod
	 * @param srcMethod
	 * @param args
	 */
	private void parseResults(Object res, CacheKeyComposer composer, Method targetMethod, Method srcMethod, Object[] args)
	{
		//���� ��������� ������ ��� �������� ���������, �� ��������� ������ ������� �� �����������
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
					//��������� �� ��������� ��������
					newArgs[arrayParam] = mapKey;
					putLinkedResult(composer,targetMethod,mapResults.get(mapKey),newArgs);
				}
				return;
			}
			else
				log.error("�� ������� ����� �������� � �������� � ��������� �������� "+srcMethod.toString());
		}
		putLinkedResult(composer,targetMethod,res,args);
	}

	/**
	 * ��������� ����� ��������� �������, � ������� ����������� ������.
	 * �������, ��� ������ � ��� ����� ����� ��� GroupResult. ������� ������ � �����.
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
			//���� ������ ���������� ��� ��������� ��������.
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
	 * ��������� ��������� ��������� � ����
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
			builder.append("���������� �������� ������� � ������");
			if(key!=null)
				builder.append(key.toString());
			else
				builder.append("null");
			builder.append(" � ���");
			builder.append(", �.�. cache==null");
			log.error(builder.toString());
			return;
		}
		//������� �������� � ��� �� ����������, �� ���� ����� ����� (�� ����� � ���� ������ ���), �� �����
		Cachable annot = intefaceMethod.getAnnotation(Cachable.class);
		//���������� ��, ���� �������� value == null
		boolean cachingWithNullValue = annot.cachingWithNullValue();

		if ((value!=null || cachingWithNullValue) && !((value instanceof String) && ( ((String)value).length()==0)))
		{
			cache.put(new Element(key, value));

			StringBuilder builder = new StringBuilder();
			builder.append("����:");
			builder.append(Thread.currentThread().getId());
			builder.append(" ���������� � ��� ");
			builder.append(cache.getName());
			builder.append(" ������ � ������ ");
			if(key!=null)
				builder.append(key);
			else
				builder.append("null");
			builder.append(" �� ������ ");
			builder.append(intefaceMethod.toString());

			log.trace(builder.toString());

			//��������� ��� ��� �������.
			addToCallBackCache(key, value, intefaceMethod, args, cacheKeyType);
		}
	}

	/**
	 * �������� � ��� ������ ��� ������
	 * @param key ���� �� �������� ����� ��������� ���������
	 * @param returnValue ��������� ���������� ������
	 * @param intefaceMethod ����� ����������
	 */
	private void addToCallBackCache(Serializable key, Object returnValue, Method intefaceMethod, Object[] args, CacheKeyType cacheKeyType)
	{
		log.trace("����:"+Thread.currentThread().getId() + " ������ ��������� ���� ��� ������� ��� ������:" + intefaceMethod.toString());
		//������ ������������ �� ������������� �������� - ���� ��� ������, �� ������ �� ��������.
		if (returnValue == null)
		{
			return;
		}

		if (CacheKeyType.WAIT_KEY == cacheKeyType)
		{
			addToCallBackCacheInternal(key, returnValue, intefaceMethod.getParameterTypes(),args,intefaceMethod);
			log.trace("����:"+Thread.currentThread().getId() + " ����� ��������� ���� ��� ������� ��� ������:" + intefaceMethod.toString());

			return;
		}
		//��. ProxyCacheServiceImpl
		Class returnClass = intefaceMethod.getReturnType();
		//���� ��������� ������ ��� �������, �� ��� ������� ���������� ������ �� ���������
		//�.�. ���� ���� ����� ������� �� ������ �����, ������� ���� ������.
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
		log.trace("����:"+Thread.currentThread().getId() + " ����� ��������� ���� ��� ������� ��� ������:" + intefaceMethod.toString());
	}

	/**
	 * ��� �������, ��� ������� ��������� � �������� ����������� �� ����������.
	 * @param key ����, �� �������� ����������� ��������� ��������� � �������� ����
	 * @param returnValue ������������ �������� ��� ��� ���������
	 * @param parametersTypes ���� ���������� ������ ����������
	 * @param parameters ��������� ������ � �������� �� ��� ������
	 * @param interfaceMethod ��� ����� �� ����������
	 */
	private void addToCallBackCacheInternal(Serializable key, Object returnValue,
	                                        Class[] parametersTypes, Object[] parameters, Method interfaceMethod)
	{
		Collection<Class> cleanableClasses = GateBusinessCacheConfig.getCleanableClasses();
		Class returnClass;

		//���� ����� ��������� ����� �� �������� ����� �������� ������� ���
		if(!ClassHelper.isGetClientProduct(interfaceMethod))
			returnClass = ClassHelper.getReturnClassIgnoreCollections(interfaceMethod);
		else
			returnClass = ClassHelper.getReturnClassforGetClientProduct(returnValue,cleanableClasses);

		Class returnInterfaceByValue = returnClass;
		Class returnClassByValue = returnValue.getClass();
		//���� �� ������������� �������� �� ������
		boolean isReturnValue = cleanableClasses.contains(returnClass);
		if(!isReturnValue)
		{
			//���� �� ������ �� �������, ������ ��� ���� ����������� ������� �����, ��������� ���������� ������ ��������
			Class[] returnInterfacesByValue = returnClassByValue.getInterfaces();
			returnInterfaceByValue = checkIfInterfaceIntersection(cleanableClasses, returnInterfacesByValue);
			isReturnValue = returnInterfaceByValue!=null;
		}
		if(isReturnValue)
		{
			//��������� ���� �� ����������
			CacheKeyComposer composer;
			if(ClassHelper.isGetClientProduct(interfaceMethod))
				composer = GateBusinessCacheConfig.getCleanableClientProductComposer();
			else
				composer = GateBusinessCacheConfig.getCleanableComposer(returnInterfaceByValue);
			//�������� ���� �� �������� ��������, ���� ����� �� ���� ����� ���������
			Serializable callBackKey = composer.getClearCallbackKey(returnValue, parameters);
			//���������� � ������ ���, ��� ����������� �������
			if(callBackKey != null)
				fillCallBackCache(interfaceMethod, key, callBackKey);
		}
		else
		{   //����  ����� ��������� ����� ��������� � ��������� ���������
			//�������� AccountInfo getAccountInfo(Account), ����� accountInfo ����� ���� ���������,
			// ��� ������� ��� �� �����
			returnInterfaceByValue = returnClass;
			Collection<Class> linkedClasses = GateBusinessCacheConfig.getLinkedItems();
			//�������� ��������� ����� �� ������������� ����������
			Class cleanableClass = GateBusinessCacheConfig.getCleanableClassByLinkedItems(returnClass);
			//���� �� �����, ������ ��� ������������ ������� �����, ���� ������ ��������� ���������� �� �����������
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
			//���� ��������� ���� �� ���������, � ������� ���� AccountInfo
			if(cleanableClass!=null && linkedClasses.contains(returnInterfaceByValue))
			{
				//����� ������ � ���������� ���������(��������), � ������� ���� Account
				CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(cleanableClass);
				if(parameters==null)
					return;

				int size = parameters.length;
				//todo �������� ��������� ���������� ������� �.�.�.
				for (int i=0;i<size;i++)
				{
					Object param = parameters[i];
					if(param!=null && cleanableClass.isAssignableFrom(param.getClass()))
					{
						//�������� ���� �� �������� ��������, ���� ����� �� ���� ����� ���������
						Serializable callBackKey = composer.getClearCallbackKey(parameters[i], parameters);
						//���� ������� � ����������(�� ������� ����� ����� object)
						if(callBackKey!=null)
						{
							//���������� � ������ ���, ��� ����������� �������
							fillCallBackCache(interfaceMethod, key, callBackKey);
							break;
						}
					}

				}
			}
		}		
	}

	/**
	 * �������� ���� �� � ��������� ���� ���� ��������� �� �������
	 * @param classes ����� �������
	 * @param intefaces ����� �������
	 * @return ������ ��������� ����� ��� null, ���� ������ �� �������
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
	 * ��������� ��������� ���
	 * @param interfaceMethod ��� ������ �� ����������
	 * @param key ����, �� �������� ����������� ��������� ��������� � �������� ����
	 * @param callBackKey ���� ��� �������� � ������ ����
	 */
	private void fillCallBackCache(Method interfaceMethod, Serializable key,Serializable callBackKey)
	{
		//������� ��� ��������
		Cache callBackCache = GateBusinessCacheSingleton.getInstance().getCache(GateBusinessCacheSingleton.LINKS_CACHE_NAME);
		Element container = callBackCache.get(callBackKey);
		//��� ������ �������� ��������, ������ ���� ��� ���� ���������� ������ - ���� �������� ��� �������� � ���� ����
		//����� �������, ����� �������� �������� ��� �� ��������, ��������� �� ��� ����, ���� � ������ ����
		//�� ����� �������� ��� �� ������� �������� ���� ����, � ������.
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
		builder.append("����:");
		builder.append(Thread.currentThread().getId());
		builder.append(" ���������� � ������ ������ � ������ ");
		if(key!=null)
			builder.append(key);
		else
			builder.append("null");
		builder.append(" �� ������ ");
		builder.append(interfaceMethod.toString());
		builder.append("������ ���� ");
		builder.append(callBackKey);

		log.trace(builder.toString());
	}

	private Object getValueFromCache(Cache cache, Serializable key, Method intefaceMethod, Object[] args)
	{
		Element res = cache.get(key);
		if(res!=null)
		{
			StringBuilder builder = new StringBuilder();
			builder.append("����:");
			builder.append(Thread.currentThread().getId());
			builder.append(" �������� �������� �� ���� � ������ ");
			builder.append(cache.getName());
			builder.append(" �� ����� ");
			if(key!=null)
				builder.append(key.toString());
			else
				builder.append("null");
			builder.append(" �� ������ ");
			builder.append(intefaceMethod.toString());
			log.trace(builder.toString());
			//���� �������, ��������� ������ ���.
			//System.out.println("��������� � ��� � ���� "+ Thread.currentThread().getId() +" ����� ��������� " +chacheHit.incrementAndGet());
			refreshCallbackCache(intefaceMethod, res.getObjectValue(),args);
		}

		return res;
	}

	/**
	 * �������� ��� �������, ���� �� �� ������ ������ ��������
	 * @param intefaceMethod ����� �� ���������� �� �������� �������� ��������.
	 * @param res ��������� ���������� ������
	 */
	private void refreshCallbackCache(Method intefaceMethod, Object res, Object[] parameters)
	{
		//��. ProxyCacheServiceImpl
		Class returnInteface = ClassHelper.getReturnClassIgnoreCollections(intefaceMethod);
		Collection<Class> cleanableClasses = GateBusinessCacheConfig.getCleanableClasses();
		//���� ��� ��������� ��������
		if(cleanableClasses.contains(returnInteface))
		{
			CacheKeyComposer composer = GateBusinessCacheConfig.getCleanableComposer(returnInteface);
			Serializable callBackKey = composer.getClearCallbackKey(res, parameters);
			Cache callBackCache = GateBusinessCacheSingleton.getInstance().getCache(GateBusinessCacheSingleton.LINKS_CACHE_NAME);
			callBackCache.get(callBackKey);
		}
		else
		{   //���� ��� ��������� � ��������� �������� ��������
			Collection<Class> linkedClasses = GateBusinessCacheConfig.getLinkedItems();
			if(linkedClasses.contains(returnInteface))
			{   //����� ������ � ���������� ���������
				Class cleanableClass = GateBusinessCacheConfig.getCleanableClassByLinkedItems(returnInteface);
				Class[] params = intefaceMethod.getParameterTypes();
				int size = params.length;
				//todo �������� ��������� ���������� ������� �.�.�.
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
