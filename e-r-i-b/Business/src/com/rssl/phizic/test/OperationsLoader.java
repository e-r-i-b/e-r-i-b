package com.rssl.phizic.test;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.OperationDescriptorService;
import com.rssl.phizic.business.operations.config.DbOperationsConfig;
import com.rssl.phizic.business.operations.config.XmlOperationsConfig;
import com.rssl.phizic.business.resources.ResourceService;
import com.rssl.phizic.business.resources.ResourceType;
import com.rssl.phizic.business.schemes.*;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.utils.test.RSSLTestCaseBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Evgrafov
 * @ created 24.11.2005
 * @ $Author$
 * @ $Revision$
 */

public class OperationsLoader
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

    private static ResourceService            resourceService            = new ResourceService();
    private static OperationDescriptorService operationDescriptorService = new OperationDescriptorService();
    private static SimpleService              simpleService              = new SimpleService();
    private static AccessSchemeService        accessSchemeService        = new AccessSchemeService();
	private static ServiceService             serviceService             = new ServiceService();

    private static XmlOperationsConfig        xmlResourceConfig;
    private static DbOperationsConfig         dbResourceConfig;
    private static XmlAccessSchemesConfig     xmlSchemesConfig;

	private boolean deleteUnknownOperations = false;
	private boolean deleteUnknownServices = false;

	public boolean isDeleteUnknownOperations()
	{
		return deleteUnknownOperations;
	}

	public void setDeleteUnknownOperations(boolean deleteUnknownOperations)
	{
		this.deleteUnknownOperations = deleteUnknownOperations;
	}

	public boolean isDeleteUnknownServices()
	{
		return deleteUnknownServices;
	}

	public void setDeleteUnknownServices(boolean deleteUnknownServices)
	{
		this.deleteUnknownServices = deleteUnknownServices;
	}

	public void initAndLoad() throws BusinessException, BusinessLogicException
	{
		RSSLTestCaseBase.initializeDataSource();
		load();
	}

	/**
	 * Загрузить в БД данные из XML конфига
	 * @throws BusinessException
	 */
	public void load() throws BusinessException, BusinessLogicException
	{
	    xmlResourceConfig = XmlOperationsConfig.get();
		xmlSchemesConfig = ConfigFactory.getConfig(XmlAccessSchemesConfig.class);

		log.info("updateResourceTypes");
	    updateResourceTypes();
		log.info("uptateOperations");
		uptateOperations();
		log.info("addUpdateServices");
		addUpdateServices();
		log.info("updateSchemes");
		updateSchemes();
	}

    /**
     * Обновить схемы
     */
    private void updateSchemes() throws BusinessException
    {
	    readDbConfig();
        DbAccessSchemesConfig dbSchemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);

        List<SharedAccessScheme> xmlSchemes = xmlSchemesConfig.getSchemes();

        for (SharedAccessScheme xmlScheme : xmlSchemes)
        {
            SharedAccessScheme dbScheme  = null;

	        try
            {
                dbScheme = dbSchemesConfig.getByCode( xmlScheme.getKey() );
            }
            catch (Exception e)
            { /* такой схемы нет в БД */ }

            if (dbScheme == null)
            {
	            log.info("Создана схема " + xmlScheme.toString());
	            addSchemeServices(xmlScheme, xmlScheme);
	            accessSchemeService.save(xmlScheme);

            }
            else
            {
	            log.info("Обновлена схема " + xmlScheme.toString());
	            dbScheme.setName( xmlScheme.getName() );
	            dbScheme.setCategory(xmlScheme.getCategory());
	            dbScheme.setCAAdminScheme(xmlScheme.isCAAdminScheme());

                removeSchemeServices(dbScheme);
	            addSchemeServices(xmlScheme, dbScheme);

                accessSchemeService.save(dbScheme);
            }
        }

	    updateDefaultSchemes();

    }

	private void updateDefaultSchemes()
	{
		DbAccessSchemesConfig dbSchemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class);
		dbSchemesConfig.doRefresh();

		SharedAccessScheme scheme;
		SharedAccessScheme dbScheme;

		AccessType[] accessTypes = AccessType.values();
		for (AccessType accessType : accessTypes)
		{
			if (dbSchemesConfig.getDefaultAccessScheme(accessType) == null)
			{
				scheme = xmlSchemesConfig.getDefaultAccessScheme(accessType);
				if(scheme == null)
				{
					log.warn("Cхема доступа по умолчанию для типа доступа " + accessType + " не найдена");
					continue;
				}
				dbScheme = dbSchemesConfig.getByCode(scheme.getKey());
				DbPropertyService.updateProperty(Constants.DEFAULT_SCHEME + accessType, dbScheme.getId().toString());

				log.info("Обновлена схема доступа по умолчанию для типа доступа " + accessType);
			}
		}

		if(dbSchemesConfig.getBuildinAdminAccessScheme() == null)
		{
			scheme = xmlSchemesConfig.getBuildinAdminAccessScheme();
			dbScheme = dbSchemesConfig.getByCode(scheme.getKey());
			DbPropertyService.updateProperty(Constants.BUILDIN_ADMIN_SCHEME, dbScheme.getId().toString());

			log.info("Обновлена схема доступа встроенной учетной записи администратора");
		}

		if (dbSchemesConfig.getAnonymousClientAccessScheme() == null)
		{
			scheme = xmlSchemesConfig.getAnonymousClientAccessScheme();
			if(scheme != null)
			{
				dbScheme = dbSchemesConfig.getByCode(scheme.getKey());
				DbPropertyService.updateProperty(Constants.ANONYMOUS_CLIENT_SCHEME, dbScheme.getId().toString());

				log.info("Обновлена схема доступа встроенной учетной записи анонимного клиента");
			}
		}
	}

	/**
	 * Заменить SchemeOperationDescriptor на считанные из БД
	 * @param xmlScheme
	 */
	private void addSchemeServices(SharedAccessScheme xmlScheme, SharedAccessScheme dbScheme)
	{
		List<Service> xmlSchemeServices = xmlScheme.getServices();
		List<Service> newSchemeServices = new ArrayList<Service>();

		for (Service xmlService : xmlSchemeServices)
		{
			Service dbService = dbResourceConfig.findService(xmlService.getKey());
			newSchemeServices.add(dbService);
		}

		dbScheme.getServices().clear();
		dbScheme.getServices().addAll(newSchemeServices);
	}

    private void removeSchemeServices(AccessScheme dbScheme) throws BusinessException
    {
        dbScheme.getServices().clear();
        accessSchemeService.save(dbScheme);
    }

    /**
     * Обновить ресурсы
     * @throws BusinessException
     */
    private void updateResourceTypes() throws BusinessException
    {
        readDbConfig();

        List<ResourceType> xmlResourceTypes = xmlResourceConfig.getResourceTypes();

	    for (ResourceType xmlRt : xmlResourceTypes)
	    {
		    ResourceType dbRt = safeGetResouceTypeFromDB(xmlRt);

		    if (dbRt == null)
		    {
			    log.info("Добавлен ресурс " + xmlRt.getClassName());
			    resourceService.addResourceTypes(xmlRt);
		    }
		    else
		    {
			    log.info("Обновлен ресурс " + dbRt.getClassName());
			    dbRt.setName(xmlRt.getName());
			    resourceService.updateResourceType(dbRt);
		    }
	    }

        List<ResourceType> dbResourceTypes = dbResourceConfig.getResourceTypes();

	    for (ResourceType dbRt : dbResourceTypes)
	    {
		    ResourceType xmlRt = null;

		    try
		    {
			    xmlRt = xmlResourceConfig.findResourceType(dbRt.getClassName());
		    }
		    catch (BusinessException e)
		    { /* такого типа ресурса нет в БД */ }

		    if (xmlRt == null)
		    {
			    log.info("Удален ресурс " + dbRt.getClassName());
			    resourceService.removeResourceType(dbRt);
		    }
	    }
	    ConfigFactory.sendRefreshAllConfig();
    }

    private ResourceType safeGetResouceTypeFromDB(ResourceType xmlRt)
    {
        ResourceType dbRt  = null;

        try
        {
            dbRt = dbResourceConfig.findResourceType(xmlRt.getClassName());
        }
        catch (BusinessException e)
        { /* такого ресурса нет в БД */ }
        return dbRt;
    }

    /**
     * Обновить операции
     * @throws BusinessException
     */
    private void uptateOperations() throws BusinessException
    {
        readDbConfig();

	    List<OperationDescriptor> xmlOds       = xmlResourceConfig.getOperationDescriptors();
	    Set<OperationDescriptor>  unknownDbOds = new HashSet<OperationDescriptor>();
	    unknownDbOds.addAll(dbResourceConfig.getOperationDescriptors());

	    for (OperationDescriptor xmlOd : xmlOds)
	    {
		    OperationDescriptor dbOd = null;
		    String operationKey = xmlOd.getKey();

		    try
		    {
			    dbOd = dbResourceConfig.findOperationByKey(operationKey);
		    }
		    catch (Exception e)
		    {
			    // такой операции нет в БД
		    }

		    if (dbOd == null)
		    {
			    log.info("Создана операция " + xmlOd.toString());
			    dbOd = new OperationDescriptor();
			    dbOd.setKey(operationKey);
		    }
		    else
		    {
			    unknownDbOds.remove(dbOd);
		    }

		    dbOd.setName(xmlOd.getName());
		    dbOd.setOperationClassName(xmlOd.getOperationClassName());
		    dbOd.setRestrictionInterfaceName(xmlOd.getRestrictionInterfaceName());
		    dbOd.setDefaultRestrictionClassName(xmlOd.getDefaultRestrictionClassName());
		    dbOd.setStrategy(xmlOd.getStrategy());
		    log.info("Обновлена операция " + xmlOd.toString());
		    operationDescriptorService.update(dbOd);
	    }

	    if (deleteUnknownOperations)
	    {
		    log.info("Удаляем неизвестные операции");
		    for (OperationDescriptor dbOd : unknownDbOds)
		    {
			    operationDescriptorService.remove(dbOd, true);
			    log.info("Операция удалена " + dbOd);
		    }
	    }
	    ConfigFactory.sendRefreshAllConfig();
    }

    /**
     * Перечитать конфиг из БД
     */
    private void readDbConfig()
    {
        dbResourceConfig = DbOperationsConfig.get();
	    dbResourceConfig.doRefresh();
    }

	/**
	 * Добавляет отсутствующие услуги (services) или обновляет существующие.
	 * @throws BusinessException
	 */
	private void addUpdateServices() throws BusinessException, BusinessLogicException
	{
		readDbConfig();

		List<Service> xmlServices     = xmlResourceConfig.getServices();
		Set<Service>  unknownServices = new HashSet<Service>();
		unknownServices.addAll(dbResourceConfig.getServices());

		for (Service xmlService : xmlServices)
		{
			Service dbService = dbResourceConfig.findService(xmlService.getKey());

			if (dbService != null)
			{
				log.info("Обновлена услуга " + xmlService);
				dbService.setName(xmlService.getName());
				dbService.setCategory(xmlService.getCategory());
				dbService.setCaAdminService(xmlService.isCaAdminService());
				serviceService.update(dbService);

				replaceServiceOperations(dbService);
				unknownServices.remove(dbService);
			}
			else
			{
				log.info("Создана услуга " + xmlService);
				serviceService.add(xmlService);
				addServiceOprerations(xmlService);
			}
		}

		if (deleteUnknownServices)
		{
			log.info("Удаляем неизвестные услуги");

			for (Service dbService : unknownServices)
			{
				serviceService.remove(dbService, true);
				log.info("Удалена услуга " + dbService);
			}
		}
		ConfigFactory.sendRefreshAllConfig();
	}

	private void replaceServiceOperations(Service dbService) throws BusinessException
	{
		deleteServiceOperations(dbService);
		addServiceOprerations(dbService);
	}

	private void deleteServiceOperations(Service dbService) throws BusinessException
	{
		simpleService.removeList(dbResourceConfig.getServiceOperationDescriptors(dbService));
	}

	private void addServiceOprerations(Service dbService) throws BusinessException
	{
		List<ServiceOperationDescriptor> xmlServiceOperationDescriptors = xmlResourceConfig.getServiceOperationDescriptors(dbService);

		for (ServiceOperationDescriptor serviceOperationDescriptor : xmlServiceOperationDescriptors)
		{
			serviceOperationDescriptor.setService(dbService);

			OperationDescriptor od = serviceOperationDescriptor.getOperationDescriptor();

			if (od.getId() == null)
			{
				od = dbResourceConfig.findOperationByKey(od.getKey());
				serviceOperationDescriptor.setOperationDescriptor(od);
			}
		}
		simpleService.addList(xmlServiceOperationDescriptors);
		ConfigFactory.sendRefreshAllConfig();
	}
}
