package com.rssl.phizic.business.schemes;

import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.*;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 24.11.2005
 * @ $Author$
 * @ $Revision$
 */

public class DbAccessSchemesConfig extends AccessSchemesConfig
{
    private static AccessSchemeService accessSchemeService = new AccessSchemeService();
	private static final String DEFAULT_MOBILE_LIMITED_KEY = "com.rssl.iccs.default.scheme.mobileLimited";

	private Map<AccessType, SharedAccessScheme> defaultSchemeByType;
	private SharedAccessScheme                  buildinAdminScheme;
	private SharedAccessScheme                  anonymousClientScheme;

	private List<SharedAccessScheme>            schemes;
    private Map<String, SharedAccessScheme>     schemesByCode;

	public DbAccessSchemesConfig(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
		try
		{
			loadSchemes();
			loadDefaultSchemes();
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}

	}

	private void loadDefaultSchemes() throws BusinessException
	{
		defaultSchemeByType = new HashMap<AccessType, SharedAccessScheme>();

		AccessType[] accessTypes = AccessType.values();
		for (AccessType accessType : accessTypes)
		{
			String id = getProperty(Constants.DEFAULT_SCHEME + accessType);
			if(StringHelper.isNotEmpty(id) && !id.equals("none") && !accessType.getKey().equals(AccessType.mobileLimited.getKey()))
			{
				SharedAccessScheme scheme = accessSchemeService.findById(Long.decode(id));
				defaultSchemeByType.put(accessType, scheme);
			}
		}

		SharedAccessScheme mobileLimitedScheme = accessSchemeService.findByKey(getProperty(DEFAULT_MOBILE_LIMITED_KEY));
		if (mobileLimitedScheme != null)
		{
			defaultSchemeByType.put(AccessType.mobileLimited, mobileLimitedScheme);
		}

		String buildinAdminSchemeId = getProperty(Constants.BUILDIN_ADMIN_SCHEME);

		if (StringHelper.isNotEmpty(buildinAdminSchemeId))
			buildinAdminScheme = accessSchemeService.findById(Long.decode(buildinAdminSchemeId));

		String anonymousClientSchemeId = getProperty(Constants.ANONYMOUS_CLIENT_SCHEME);
		if(StringHelper.isNotEmpty(anonymousClientSchemeId))
			anonymousClientScheme = accessSchemeService.findById(Long.decode(anonymousClientSchemeId));
	}

	private void loadSchemes() throws BusinessException
	{
	    schemes = accessSchemeService.getAll();
	    schemesByCode = new HashMap<String, SharedAccessScheme>();

	    for (int i = 0; i < schemes.size(); i++)
	    {
	        SharedAccessScheme scheme = schemes.get(i);
	        String code = scheme.getKey();
	        if(code != null)
	            schemesByCode.put(code, scheme);
	    }
	}

    public List<SharedAccessScheme> getSchemes()
    {
	    return Collections.unmodifiableList(schemes);
    }

    public SharedAccessScheme getByCode(String code)
    {
        return schemesByCode.get(code);
    }

	public SharedAccessScheme getBuildinAdminAccessScheme()
	{
		return buildinAdminScheme;
	}

	public SharedAccessScheme getAnonymousClientAccessScheme()
	{
		return anonymousClientScheme;
	}

	public SharedAccessScheme getDefaultAccessScheme(AccessType accessType)
	{
		return defaultSchemeByType.get(accessType);
	}
}
