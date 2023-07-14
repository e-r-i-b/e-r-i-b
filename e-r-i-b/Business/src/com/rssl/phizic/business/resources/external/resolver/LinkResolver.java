package com.rssl.phizic.business.resources.external.resolver;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.auth.CommonLogin;

import java.util.List;

/**
 * @author eMakarov
 * @ created 08.10.2008
 * @ $Author$
 * @ $Revision$
 */
public interface LinkResolver<T extends ExternalResourceLink>
{
	List<T> getLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException;

	List<T> getLinks(Long loginId, String instanceName) throws BusinessException, BusinessLogicException;

	List<T> getInSystemLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException;

	List<T> getInMobileLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException;

	List<T> getInSocialLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException;

	List<T> getInATMLinks(CommonLogin login, String instanceName) throws BusinessException, BusinessLogicException;

	/**
	 * Ищет линк пользователя по внешнему ID
	 * @param login - логин пользователя
	 * @param externalId - внешний ID
	 * @param instanceName
	 * @return линк или null, если не найден
	 * @throws BusinessException
	 */
	T findByExternalId(CommonLogin login, String externalId, String instanceName)
			throws BusinessException;
}
