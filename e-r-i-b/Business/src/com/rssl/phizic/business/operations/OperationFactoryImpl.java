package com.rssl.phizic.business.operations;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.modes.ConfirmStrategySource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.builder.OperationProxyBuilder;
import com.rssl.phizic.business.operations.config.DbOperationsConfig;
import com.rssl.phizic.business.operations.config.OperationsConfig;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.operations.restrictions.RestrictionProvider;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.operations.DefaultLogDataReader;
import com.rssl.phizic.logging.operations.LogDataReader;
import com.rssl.phizic.logging.operations.LogWriter;
import com.rssl.phizic.logging.operations.OperationLogFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.permissions.OperationPermission;

import java.security.AccessControlException;
import java.security.Permission;
import java.util.*;


/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 27.09.2005
 * Time: 15:13:41
 */
@SuppressWarnings({"RedundantTypeArguments"})
public class OperationFactoryImpl implements OperationFactory
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private OperationsConfig operationConfig;

	private synchronized OperationsConfig operationsConfig()
	{
		if (operationConfig == null)
			operationConfig = DbOperationsConfig.get();
		return operationConfig;
	}

	protected RestrictionProvider restrictionProvider;
	private Map<String, Class<? extends Operation>> proxyMap = new HashMap<String, Class<? extends Operation>>();

	public OperationFactoryImpl(RestrictionProvider restrictionProvider)
	{
		this.restrictionProvider = restrictionProvider;
	}

	/**
	 * —оздает инстанс операции. ѕоиск услуги осущесвл€етс€ по экслюзивным операци€м
	 *
	 * @param operationClass - конкретный тип операции
	 * @return операци€
	 * @throws java.security.AccessControlException нет доступа
	 * @throws com.rssl.phizic.business.BusinessException
	 *
	 */
	public <T extends Operation> T create(Class<T> operationClass) throws AccessControlException, BusinessException
	{
		return create(operationClass, null);
	}

	public <T extends Operation> T create(String operationKey) throws AccessControlException, BusinessException
	{
		return this.<T>create(operationKey, null);
	}

	public <T extends Operation> T create(Class<T> operationClass, String serviceKey) throws BusinessException
	{
		OperationsConfig config = operationsConfig();
		String operationKey = calculateKey(operationClass);
		OperationDescriptor od = config.findOperationByKey(operationKey);
		Service service = null;

		if (serviceKey != null)
			service = config.findService(serviceKey);

		return this.<T>create(od, service);
	}

	protected <T extends Operation> T create(OperationDescriptor od, Service service) throws BusinessException
	{
		if(isCheckAccess())
		{
			try
			{
				AuthModule authModule = AuthModule.getAuthModule();
				Permission permission = createPermission(od, service);

				authModule.checkPermission(permission);
				log.trace("ƒоcтуп разрешен: услуга: " + service + " операци€: " + od);
			}
			catch (AccessControlException e)
			{
				log.warn("ƒоcтуп запрещен: услуга: " + service + " операци€: " + od);

				Calendar end = GregorianCalendar.getInstance();

				DefaultLogDataReader reader = new DefaultLogDataReader("ƒоступ запрещен:" + od.getName());
				reader.setOperationKey(com.rssl.phizic.security.config.Constants.LOGIN_DEFAULT_OPERATION_KEY);
				writeLog(reader, end);
				throw e;
			}	
		}

		T operation = this.<T>createOperation(od);
		Restriction restriction = getRestriction(od, service);
		//noinspection unchecked
		operation.setRestriction(restriction);
		try
		{
			if (od.getStrategy() != null)
				operation.setStrategy((ConfirmStrategySource)Class.forName(od.getStrategy()).newInstance());
		}
		catch (ClassNotFoundException e)
		{
			log.error("‘ильтрации стратегии подтверждени€ дл€ операции " + od.getKey() + " не загружена.", e);
			
		}
		catch (InstantiationException e)
		{
			log.error("‘ильтрации стратегии подтверждени€ дл€ операции " + od.getKey() + " не загружена.", e);
		}
		catch (IllegalAccessException e)
		{
			log.error("‘ильтрации стратегии подтверждени€ дл€ операции " + od.getKey() + " не загружена.", e);
		}
		return operation;
	}

	private void writeLog(LogDataReader logreader, Calendar end) throws BusinessException
	{
		LogWriter logWriter = OperationLogFactory.getLogWriter();

		try
		{
			logWriter.writeSecurityOperation(logreader, end, end);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public <T extends Operation> T create(String operationKey, String serviceKey) throws AccessControlException, BusinessException
	{
		OperationsConfig config = operationsConfig();
		OperationDescriptor od = config.findOperationByKey(operationKey);
		Service service = null;

		if (serviceKey != null)
			service = config.findService(serviceKey);

		return this.<T>create(od, service);
	}

	private String calculateKey(Class operationClass)
	{
		return operationClass.getSimpleName();
	}

	@SuppressWarnings({"unchecked"})
	private synchronized <T extends Operation> T createOperation(OperationDescriptor od) throws BusinessException
	{
		String key = od.getKey();
		Class proxyClass = proxyMap.get(key);
		if (proxyClass == null)
		{
			proxyClass = createProxy(od);
			proxyMap.put(key, proxyClass);
		}

		try
		{
			return (T) proxyClass.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new BusinessException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	private Class createProxy(OperationDescriptor od) throws BusinessException
	{
		List<MethodMatcher> removeMatchers = operationsConfig().getRemoveMethodMatchers(od);

		OperationProxyBuilder builder = new OperationProxyBuilder(od, removeMatchers);

		return builder.createProxyFactory();
	}

	protected Restriction getRestriction(OperationDescriptor od, Service service) throws BusinessException
	{
		Restriction restriction = null;

		String restrictionInterfaceName = od.getRestrictionInterfaceName();

		if (restrictionInterfaceName != null)
			restriction = restrictionProvider.get(service, od);

		return restriction;
	}

	public boolean checkAccess(Class operationClass) throws BusinessException
	{
		return checkAccess(operationClass, null);
	}

	public boolean checkAccess(String operationKey) throws BusinessException
	{
		OperationsConfig config = operationsConfig();
		OperationDescriptor od;
		try
		{
			od = config.findOperationByKey(operationKey);
		}
		catch(BusinessException be)
		{
			//если операци€ не найдена, считаем что к ней нет доступа.
			return false;
		}

		return checkAccess(od, null);
	}

	public boolean checkAccess(String operationKey, String serviceKey) throws BusinessException
	{
		OperationsConfig config = operationsConfig();
		OperationDescriptor od;
		try
		{
			od = config.findOperationByKey(operationKey);
		}
		catch(BusinessException be)
		{
			//если операци€ не найдена, считаем что к ней нет доступа.
			return false;
		}
		Service service = null;
		if (serviceKey != null)
			service = config.findService(serviceKey);

		return checkAccess(od, service);
	}

	public boolean checkAccess(Class operationClass, String serviceKey) throws BusinessException
	{
		OperationsConfig config = operationsConfig();
		String operationKey = calculateKey(operationClass);

		OperationDescriptor od = null;
		try
		{
			od = config.findOperationByKey(operationKey);
		}
		catch(BusinessException ex)
		{ //если операци€ не найдена, считаем что к ней нет доступа.
			return false;
		}

		Service service = null;
		if (serviceKey != null)
			service = config.findService(serviceKey);

		return checkAccess(od, service);
	}

	private boolean checkAccess(OperationDescriptor od, Service service) throws BusinessException
	{
		boolean result = true;
		AuthModule authModule = AuthModule.getAuthModule();

		Permission permission = createPermission(od, service);

		if (authModule != null)
			result = authModule.implies(permission);

		return result;
	}

	private Permission createPermission(OperationDescriptor od, Service service) throws BusinessException
	{
		OperationPermission op = new OperationPermission(od.getKey(), service == null ? null : service.getKey());
		if(!op.isRigid())
			throw new BusinessException("“ребуетс€ строга€ проверка доступности операции");
		return op;
	}

	protected boolean isCheckAccess()
	{
		return true;
	}
}
