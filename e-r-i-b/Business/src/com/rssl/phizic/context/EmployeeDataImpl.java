package com.rssl.phizic.context;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.clients.list.ClientNodeState;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.displayedEntries.NumberDisplayedEntriesService;
import com.rssl.phizic.business.displayedEntries.NumberDisplayedEntry;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.business.skins.SkinsService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.service.group.access.ServicesGroupAccessHelper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kidyaev
 * @ created 16.02.2006
 * @ $Author: komarov $
 * @ $Revision: 83490 $
 */
public class EmployeeDataImpl implements EmployeeData
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final NumberDisplayedEntriesService numberDisplayedEntriesService = new NumberDisplayedEntriesService();
	private final Department department;
	private Employee employee;
	private String skinUrl;
	private String globalUrl;
	private boolean allTbAcces;
	private final Map<String, Long> numberDisplayedEntries = new ConcurrentHashMap<String, Long>();
	private final Set<String> availableGroups = new HashSet<String>();

	private Long clientId;
	private ClientNodeState clientNodeState;
	private Long csaProfileId;
	private String mdmId;

	/**
	 * Конструктор
	 * @param employee сотрудник
	 */
	public EmployeeDataImpl(Employee employee)
	{
		this.employee = employee;
		department = getSafeDepartment(employee);
		allTbAcces = loadAllTBAccess();
		loadNumberDisplayedEntries();
		loadGroups();
	}

	private void loadGroups()
	{
		availableGroups.addAll(ServicesGroupAccessHelper.getInstance().getImpliesGroup());
	}

	public Employee getEmployee()
	{
		return employee;
	}

	public Department getDepartment()
	{
		return department;
	}

	public String getSkinUrl()
	{
		return skinUrl;
	}

	public void setSkinUrl(String skinUrl)
	{
		this.skinUrl = skinUrl;
	}

	public String getGlobalUrl() throws BusinessException
	{
		if (globalUrl==null)
			loadGlobalUrl();
		return globalUrl;
	}

	public ClientNodeState getCurrentClientNodeState()
	{
		return clientNodeState;
	}

	public ClientNodeState setCurrentClientNodeState(ClientNodeState state)
	{
		this.clientNodeState = state;
		return state;
	}

	private void loadGlobalUrl() throws BusinessException
	{
		SkinsService skinsService = new SkinsService();
		Skin global = skinsService.getGlobalUrl();
		this.globalUrl = global.getUrl();
	}

	private void loadNumberDisplayedEntries()
	{
		List<NumberDisplayedEntry> list = Collections.emptyList();
		try
		{
			list = numberDisplayedEntriesService.getNumberDisplayedEntriesByLogin(getEmployee().getLogin());
		}
		catch(BusinessException be)
		{
			log.error("Ошибка получения данных о размерах grid-ов" , be);
		}
		for(NumberDisplayedEntry entry : list)
		{
			numberDisplayedEntries.put(entry.getGridId(), entry.getRecordCount());
		}
	}

	private NumberDisplayedEntry createEntry(Long loginId, String gridId, Long countRecord)
	{
		NumberDisplayedEntry entry = new NumberDisplayedEntry();
		entry.setLoginId(loginId);
		entry.setGridId(gridId);
		entry.setRecordCount(countRecord);
		return entry;
	}

	public void setNumberDisplayedEntry(String gridId, Long countRecord) throws BusinessException
	{
		NumberDisplayedEntry entry = createEntry(getEmployee().getLogin().getId(), gridId, countRecord); 
		numberDisplayedEntriesService.addOrOpdateNumberDisplayedEntry(entry);
		numberDisplayedEntries.put(entry.getGridId(), entry.getRecordCount());
	}

	public Long getNumberDisplayedEntries(String gridId) throws BusinessException
	{
		return numberDisplayedEntries.get(gridId);
	}

	public boolean isAllTbAccess()
	{
		return allTbAcces;
	}

	public Long getLoginId()
	{
		return employee.getLogin().getId();
	}

	private boolean loadAllTBAccess()
	{
		if(employee == null)
			return false;

		try
		{
			SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
			return AuthModule.getAuthModule().implies(new ServicePermission("AllTBAccess")) ||
					departmentService.isAllTBAccess(employee.getLogin().getId()) ||
					employee.getLogin().getUserId().equals(securityConfig.getDefaultAdminName());
		}
		catch (Exception e)
		{
			log.error("Не удалось определить признак доступности всех департаментов.", e);
			return false;
		}
	}

	private Department getSafeDepartment(Employee employee)
	{
		if (employee== null)
		{
			return null;
		}
		try
		{
			return departmentService.findById(employee.getDepartmentId());
		}
		catch (BusinessException e)
		{
			log.error("Ошибка получения подраздлениея для сотрудника " + employee.getFullName(), e);
			return null;
		}
	}

	public boolean availableGroup(String groupName)
	{
		return availableGroups.contains(groupName);
	}

	public Long getCurrentClientCsaProfileId()
	{
		return csaProfileId;
	}

	public void setCurrentClientCsaProfileId( Long csaProfileId)
	{
		this.csaProfileId = csaProfileId;

	}

	public String getCurrentClientMdmId()
	{
		return mdmId;
	}

	public void setCurrentClientMdmId( String mdmId)
	{
		this.mdmId = mdmId;

	}

	public void setCurrentClientId(Long clientId)
	{
		if(clientId.equals(this.clientId))
			return;
		this.clientId = clientId;

		clientNodeState = null;
		csaProfileId = null;
		mdmId = null;
	}
}
