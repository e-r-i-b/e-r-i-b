package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.AccessSchemeService;
import com.rssl.phizic.business.schemes.AccessSchemesConfig;
import com.rssl.phizic.business.schemes.DbAccessSchemesConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.access.AssignAccessHelper;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizic.security.config.Constants;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author basharin
 * @ created 08.04.14
 * @ $Author$
 * @ $Revision$
 */

public class SchemeConfigureOperation extends EditPropertiesOperation<Restriction>
{
	private static final AccessSchemeService accessSchemeService = new AccessSchemeService();

	private final AccessType accessType = AccessType.employee;
	private AccessScheme                defaultScheme;
	private List<AssignAccessHelper> helpers;

	public void initialize(PropertyCategory propertyCategory) throws BusinessException
	{
		super.initialize(propertyCategory);
		init();
	}

	public void initialize(PropertyCategory propertyCategory, Set<String> propertyKeys) throws BusinessException
	{
		super.initialize(propertyCategory, propertyKeys);
		init();
	}

	private void init() throws BusinessException
	{
		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class, accessType.getApplication());

		this.defaultScheme = schemesConfig.getDefaultAccessScheme(accessType);
		boolean isCaAdmin = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
		helpers = AccessHelper.createAssignAccessHelpers(accessType.getScope(), isCaAdmin);
	}

	public List<AssignAccessHelper> getHelpers()
	{
		return Collections.unmodifiableList(helpers);
	}

	public AccessScheme getDefaultScheme()
	{
		return defaultScheme;
	}

	public void setDefaultSchemeId(Long schemeId) throws BusinessException
	{
		this.defaultScheme = accessSchemeService.findById(schemeId);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		try
		{
			String strId = getEntity().get(Constants.DEFAULT_SCHEME + accessType);
			setDefaultSchemeId(strId != null ? Long.valueOf(strId) : null);
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        setDefaultSchemeProperty(getDefaultScheme(), Constants.DEFAULT_SCHEME + accessType);
			        return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	private void setDefaultSchemeProperty(AccessScheme scheme, String clientDefaultSchemePropertyName)
	{
		String value;
		if( scheme != null )
		{
			value = scheme.getId().toString();
		}
		else
		{
			value = "none";
		}

		DbPropertyService.updateProperty(clientDefaultSchemePropertyName, value, getPropertyCategory());
	}
}