package com.rssl.phizic.operations.access;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.auth.modes.MultiInstanceAccessPolicyService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.log.operations.LogParametersInfoBuilder;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.config.DbOperationsConfig;
import com.rssl.phizic.business.operations.config.OperationsConfig;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.resources.own.MultiInstanceSchemeOwnService;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.MultiInstanceAccessSchemeService;
import com.rssl.phizic.business.schemes.PersonalAccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.scheme.AccessHelper;
import com.rssl.phizic.operations.scheme.SchemeOperationHelper;
import com.rssl.phizic.security.SecurityDbException;
import org.hibernate.Session;

import java.util.*;

/**
 * Класс обеспечивает управление схемами доступа и индивидуальными правами
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: krenev $
 * @ $Revision: 58098 $
 */

public abstract class AssignAccessOperationBase<R extends Restriction> extends OperationBase<R> implements AssignAccessOperation<R>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private LogParametersInfoBuilder logBuilder = new LogParametersInfoBuilder();

	private final static MultiInstanceSchemeOwnService schemeOwnService    = new MultiInstanceSchemeOwnService();
	private final static MultiInstanceAccessSchemeService accessSchemeService = new MultiInstanceAccessSchemeService();
	private final static MultiInstanceAccessPolicyService accessModesService  = new MultiInstanceAccessPolicyService();

	private CommonLogin              login;
	private AccessType               accessType;

	private List<AssignAccessHelper> helpers;

	private AccessScheme             currentAccessScheme;
	private boolean                  isAccessRightModified;
	private Boolean                  newAccessAllowed;
	private boolean                  currentAccessAllowed;
	private List<Long>               newPersonalServiceIds;
	private AccessScheme             newAccessScheme;
	private Properties               properties;
	private String                   newCategory;
	private String                   category;
	protected boolean                isCategoryChanged;

	private StringBuilder logParameters = new StringBuilder();

	protected void initialize(LoginSource loginSource, AccessType accessType, List<AssignAccessHelper> helpers) throws BusinessException
	{
		this.helpers               = new ArrayList<AssignAccessHelper>(helpers);
		this.currentAccessScheme   = null;
		this.isAccessRightModified = false;
		this.accessType            = accessType;
		this.newPersonalServiceIds = null;
		this.isCategoryChanged     = false;

		login = loginSource.getLogin();

		try
		{
			Properties temp = accessModesService.getProperties(login, accessType, getInstanceName());
			currentAccessAllowed = (temp != null);
			properties = temp == null ? new Properties() : temp;
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}

		AccessScheme scheme = getCurrentAccessScheme();
		category = (scheme == null ? null : scheme.getCategory());
	}

	public CommonLogin getLogin()
	{
		return login;
	}

	public AccessScheme getCurrentAccessScheme() throws BusinessException
	{
		if (currentAccessScheme == null)
		{
			currentAccessScheme = schemeOwnService.findScheme(login, accessType, getInstanceName());
		}

		return currentAccessScheme;
	}

	/**
	 * Назначить новую схему
	 * @param accessSchemeId - ID новой схемы
	 * @throws BusinessException
	 */
	public void setNewAccessSchemeId(Long accessSchemeId) throws BusinessException, BusinessLogicException
	{
		if (accessSchemeId == null)
		{
			newAccessScheme = null;
		}
		else
		{
			newAccessScheme = accessSchemeService.findById(accessSchemeId, getInstanceName());

			if (newAccessScheme == null)
			{
				throw new BusinessException("Схема с ID:" + accessSchemeId + " не найдена");
			}

			Employee employee = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee();

			//разрешать назначать новую схему сотрудникам без права "администратор ЦА",
			// если схема содержит услугу "Доступ по-умолчанию ко всем ТБ"
			if (newAccessScheme.isCAAdminScheme() && !employee.isCAAdmin() && !newAccessScheme.isContainAllTbAccessService())
			{
				throw new BusinessLogicException("Вы не можете назначить выбранную схему прав.");
			}
		}

		isAccessRightModified = true;
	}

	/**
	 * Назначить персональную схему
	 * @param services - услуги, входящие в схему
	 */
	public void setPersonalScheme(List<Long> services) throws BusinessException
	{
		newPersonalServiceIds = new ArrayList<Long>(services);
		currentAccessScheme   = null;
		isAccessRightModified = true;
	}

	public Properties getProperties()
	{
		return properties;
	}

	public void setNewProperty(String name, String value)
	{
		properties.setProperty(name, value);
	}

	public AccessPolicy getPolicy()
	{
		AuthenticationConfig config = ConfigFactory.getConfig(AuthenticationConfig.class, accessType.getApplication());
		return config.getPolicy(accessType);
	}

	public AccessType getAccessType()
	{
		return accessType;
	}

	public boolean getAccessTypeAllowed()
	{
		return currentAccessAllowed;
	}

	public void setNewAccessTypeAllowed(boolean flag)
	{
		newAccessAllowed = flag;
	}

	protected Boolean getNewAccessTypeAllowed()
	{
		return newAccessAllowed;
	}

	public String getCategory() throws BusinessException
	{
		return category;
	}

	public void setNewCategory(String category)
	{
		newCategory = category;
	}

	public String getNewCategory()
	{
		return newCategory;
	}

	public List<AssignAccessHelper> getHelpers() throws BusinessException
	{
		return helpers;
	}

	public StringBuilder getLogParameters()
	{
		return logParameters;
	}

	public AccessScheme getNewAccessScheme()
	{
		return newAccessScheme;
	}

	public void setLogParameters(AccessScheme oldScheme, AccessScheme newScheme) throws BusinessException
	{
		logParameters.append(logBuilder.stringUserLoginInfo(login, "Информация о пользователе"));
		setLogParametersForScheme(oldScheme, "Старая схема прав");
		setLogParametersForScheme(newScheme, "Новая схема прав");
	}

	private void setLogParametersForScheme(AccessScheme scheme, String schemeName) throws BusinessException
	{


		if (scheme!=null)
		{
			logParameters.append(logBuilder.stringBuilder("<br/><b><u>"+schemeName+"</u></b><br/>Название схемы", logBuilder.getShemeName(scheme.getName())));
			List<String> categories = AccessHelper.getScopeCategories(accessType.getScope());
			if (categories.isEmpty()) return;
			for (String category: categories)
			{
				boolean isCaAdmin = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
				AssignAccessHelper assignAccessHelper = new CategoryAssignAccessHelper(accessType.getScope(), category, isCaAdmin);
				List<Service> services = assignAccessHelper.getServices();
				if (services.isEmpty()) return;
				logParameters.append("<br/><b><u>Операции</u></b>");
				for (Service service: services)
				{
					logParameters.append(logBuilder.stringBuilder(service.getName(), checkAccess(scheme,service)));
				}
			}
		}
	}

	private String checkAccess(AccessScheme scheme, Service service)
	{
		if (scheme.getServices().contains(service)) return "Разрешен";
		else return "Запрещен";
	}

	/**
	 * Сохранить все изменения в БД
	 * @throws BusinessException
	 */
	@Transactional
	public void assign() throws BusinessException, BusinessLogicException
	{
		updateMode();

		if (isAccessRightModified)
		{
			try
			{
				HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
				{
					public Void run(Session session) throws Exception
					{
						assignInternal();

						afterAssign();

						return null;
					}
				});
			}
			catch (BusinessException e)
			{
				throw e;
			}
			catch (BusinessLogicException e)
			{
				throw e;
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
			isAccessRightModified = false;
		}
	}

	private void updateMode() throws BusinessException
	{
		if (newAccessAllowed == null) // is access not modified
			return;

		try
		{
			if (newAccessAllowed)
				accessModesService.enableAccess(login, accessType, properties, getInstanceName());
			else
				accessModesService.disableAccess(login, accessType, getInstanceName());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	private void assignInternal() throws BusinessException, BusinessLogicException
	{
		AccessScheme oldScheme = schemeOwnService.findScheme(login, accessType, getInstanceName());

		if (newAccessScheme != null)
		{
			schemeOwnService.setScheme(login, accessType, newAccessScheme, getInstanceName());
			log.info("Установлена новая схема доступа " + newAccessScheme.getName() + " для пользователя login ID " + login.getId());
			setLogParameters(oldScheme, newAccessScheme);
			markCategoryChange(oldScheme, newAccessScheme.getCategory());
		}
		else if (newPersonalServiceIds != null)
		{
			if (newCategory == null)
				throw new NullPointerException(newCategory);

			AssignAccessHelper helper = findHelper(newCategory);

			if (helper == null)
				throw new BusinessException("Неверная категория " + newCategory);

			List<Service> newPersonalServices = prepareServicesList(helper);

			AccessScheme newScheme = accessSchemeService.createPersonalScheme(helper.getCategory(), newPersonalServices);
			accessSchemeService.save(newScheme, getInstanceName());
			schemeOwnService.setScheme(login, accessType, newScheme,getInstanceName());
			log.info("Установлен персональный набор прав для пользователя login ID " + login.getId());
			setLogParameters(oldScheme, newScheme);

			this.newAccessScheme = newScheme;
			markCategoryChange(oldScheme, newCategory);
		}
		else
		{
			schemeOwnService.removeScheme(login, accessType, getInstanceName());
			log.info("Удалена схема доступа для пользователя login ID " + login.getId());
			markCategoryChange(oldScheme, null);
		}

		// удалить старую персональную схему доступа
		if (oldScheme instanceof PersonalAccessScheme)
		{
			accessSchemeService.remove(oldScheme, getInstanceName());
		}
	}

	private void markCategoryChange(AccessScheme oldScheme, String newCategory)
	{
		if (oldScheme == null && newCategory!=null)
			isCategoryChanged = true;
		if (oldScheme != null && newCategory == null)
			isCategoryChanged = true;
		if (oldScheme != null && newCategory != null && !oldScheme.getCategory().equals(newCategory))
			isCategoryChanged = true;
	}

	public Map<Service, List<OperationDescriptor>> getServicesTuple() throws BusinessException
	{
		Map<Service, List<OperationDescriptor>> result = new HashMap<Service, List<OperationDescriptor>>();

		for (SchemeOperationHelper helper : helpers)
		{
			addServicesTuple(helper, result);
		}

		return result;
	}

	private void addServicesTuple(SchemeOperationHelper helper, Map<Service, List<OperationDescriptor>> result) throws BusinessException
	{
		List<Service> services = helper.getServices();
		OperationsConfig operationsConfig = DbOperationsConfig.get();
		for (Service service : services)
		{
			List<OperationDescriptor> operationDescriptors = new ArrayList<OperationDescriptor>();
			List<ServiceOperationDescriptor> serviceOperationDescriptors = operationsConfig.getServiceOperationDescriptors(service);
			if (serviceOperationDescriptors != null)
			{
				for (ServiceOperationDescriptor serviceOperationDescriptor : serviceOperationDescriptors)
				{
					operationDescriptors.add(serviceOperationDescriptor.getOperationDescriptor());
				}
			}

			result.put(service, operationDescriptors);
		}
	}

	/**
	 * Выполняется после назначения прав
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	protected abstract void afterAssign() throws BusinessException, BusinessLogicException;

	private AssignAccessHelper findHelper(String newCategory)
	{
		for (AssignAccessHelper helper : helpers)
		{
			if (helper.getCategory().equals(newCategory))
				return helper;
		}
		return null;
	}

	private List<Service> prepareServicesList(AssignAccessHelper helper) throws BusinessException
	{
		List<Service> newPersonalServices = new ArrayList<Service>(newPersonalServiceIds.size());
		for (Long newId : newPersonalServiceIds)
		{

			Service service = helper.findById(newId);
			if (service == null)
				throw new BusinessException("Услуга не найдена ID=" + newId + " категория=" + helper.getCategory());

			newPersonalServices.add(service);
		}
		return newPersonalServices;
	}
}
