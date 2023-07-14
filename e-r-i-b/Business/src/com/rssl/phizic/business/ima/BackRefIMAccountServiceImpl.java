package com.rssl.phizic.business.ima;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ima.BackRefIMAccountService;
import com.rssl.phizic.gate.ima.IMAccountNotFoundException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.GroupResultHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pankin
 * @ created 01.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class BackRefIMAccountServiceImpl extends AbstractService implements BackRefIMAccountService
{
	private static final SimpleService service = new SimpleService();

	protected BackRefIMAccountServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	@SuppressWarnings({"ThrowableInstanceNeverThrown"})
	public GroupResult<String, String> findIMAccountExternalId(final Long loginId, final String... imAccountNumbers)
	{
		GroupResult<String, String> result = new GroupResult<String, String>();
		List<IMAccountLink> foundLinks;

		try
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(IMAccountLink.class);
			criteria.add(Expression.eq("loginId", loginId));
			criteria.add(Expression.in("number", imAccountNumbers));
			foundLinks = service.find(criteria);
		}
		catch (BusinessException e)
		{
			return GroupResultHelper.getOneErrorResult(imAccountNumbers, new GateException(e));
		}

		if (CollectionUtils.isEmpty(foundLinks))
			return GroupResultHelper.getOneErrorResult(imAccountNumbers,
					new GateException("Не найдены IMAccountLinks по номерам: " +
							StringUtils.join(imAccountNumbers, ',')));

		List<String> imAccountNumberList = new ArrayList<String>(Arrays.asList(imAccountNumbers));

		for (IMAccountLink link : foundLinks)
		{
			result.putResult(link.getNumber(), link.getExternalId());
			imAccountNumberList.remove(link.getNumber());
		}

		for (String imAccountNumber : imAccountNumberList)
		{
			result.putException(imAccountNumber, new IMAccountNotFoundException(imAccountNumber));
		}

		return result;
	}
}
