package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.schemes.AccessScheme;
import com.rssl.phizic.business.schemes.AccessSchemeService;
import com.rssl.phizic.business.schemes.AccessSchemesConfig;
import com.rssl.phizic.business.schemes.DbAccessSchemesConfig;
import com.rssl.phizic.config.*;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.access.AssignAccessHelper;
import com.rssl.phizic.security.config.Constants;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

/**
 * @author Roshka
 * @ created 26.02.2006
 * @ $Author$
 * @ $Revision$
 */

public class MarkSchemeAsDefaultOperation extends OperationBase
{
	private static final AccessSchemeService accessSchemeService = new AccessSchemeService();

	private AccessType                  accessType;
	private AccessScheme                defaultSchemeOld;
	private AccessScheme                defaultScheme;
	private List<AssignAccessHelper> helpers;

	public void initialize(AccessType accessType) throws BusinessException
	{
		AccessSchemesConfig schemesConfig = ConfigFactory.getConfig(DbAccessSchemesConfig.class, accessType.getApplication());

		this.accessType    = accessType;
		this.defaultScheme = schemesConfig.getDefaultAccessScheme(accessType);
		boolean isCaAdmin = EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().isCAAdmin();
		helpers = AccessHelper.createAssignAccessHelpers(accessType.getScope(), isCaAdmin);
	}

	public AccessType getAccessType()
	{
		return accessType;
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
		this.defaultSchemeOld = getDefaultScheme();
		this.defaultScheme = accessSchemeService.findById(schemeId);
	}

	public Long getDefaultSchemeId()
	{
		if(defaultScheme == null)
			return null;

		return defaultScheme.getId();
	}

	//название схем прав для записи в log
	public String getDefaultSchemeOldName()
	{
		if (defaultSchemeOld != null && defaultSchemeOld.getName() != null)
		{
			return defaultSchemeOld.getName();
		}
		return "none";
	}
	public String getDefaultSchemeName()
	{
		if (defaultScheme != null && defaultScheme.getName() != null)
		{
			return defaultScheme.getName();
		}
		return "none";
	}

	/***
	 * Промаркировать схемы как схемы по умолчанию
	 * @throws BusinessException
	 */
	@Transactional
	public void markSchemesAsDefault() throws BusinessException
	{
		try
		{
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

		DbPropertyService.updateProperty(clientDefaultSchemePropertyName, value);
	}
}