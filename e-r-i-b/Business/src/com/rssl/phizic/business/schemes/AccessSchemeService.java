package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Evgrafov Date: 30.09.2005 Time: 21:03:06
 */
public class AccessSchemeService extends MultiInstanceAccessSchemeService
{

	public List<SharedAccessScheme> findByCategory(final String category) throws BusinessException
	{
		return super.findByCategory(category, null);
	}

	public <T extends AccessScheme> T findById(Long schemeId) throws BusinessException
	{
		return super.<T>findById(schemeId, null);
	}

	public SharedAccessScheme findByKey(final String key) throws BusinessException
	{
		return super.findByKey(key, null);
	}

	public AccessScheme findByName(String name) throws BusinessException
	{
		return super.findByName(name, null);
	}

	public List<SharedAccessScheme> getAll() throws BusinessException
	{
		return super.getAll(null);
	}

	public void remove(final AccessScheme accessScheme) throws BusinessLogicException, BusinessException
	{
		super.remove(accessScheme, null);
	}

	public <T extends AccessScheme> T save(T accessScheme) throws BusinessException
	{
		return super.save(accessScheme, null);    
	}

	public SharedAccessScheme findByExternalId(Long externalId) throws BusinessException
	{
		return super.findByExternalId(externalId,null);
	}
}
