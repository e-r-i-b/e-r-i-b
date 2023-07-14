package com.rssl.phizic.business.loans.claims;

import com.rssl.phizic.business.loans.claims.generated.LoanClaimDefinitionDescriptor;
import com.rssl.phizic.business.loans.claims.generated.FieldDescriptor;
import com.rssl.phizic.business.loans.claims.generated.EntityDescriptor;
import com.rssl.phizic.business.loans.claims.generated.GroupDescriptor;
import com.rssl.phizic.business.loans.kinds.LoanKind;
import com.rssl.phizic.business.loans.kinds.LoanKindService;
import com.rssl.phizic.business.BusinessException;

import java.io.StringReader;
import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;

import org.xml.sax.InputSource;

/**
 * @author Krenev
 * @ created 21.03.2008
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimDefinitionProvider
{
	private static final LoanKindService loanKindService = new LoanKindService();

	public LoanClaimDefinitionDescriptor getLoanDefinition(String kindID) throws BusinessException
	{
		LoanKind kind = loanKindService.findById(Long.valueOf(kindID));
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			JAXBContext context = JAXBContext.newInstance("com.rssl.phizic.business.loans.claims.generated", classLoader);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (LoanClaimDefinitionDescriptor) unmarshaller.unmarshal(new InputSource(new StringReader(kind.getClaimDescription())));
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	public List<FieldDescriptor> getLoanDefinitionFields(String loanKind) throws BusinessException
	{
		LoanClaimDefinitionDescriptor definition = getLoanDefinition(loanKind);
		return buildList(definition.getFields().getEntities());
	}

	private List<FieldDescriptor> buildList(List<EntityDescriptor> entities)
	{
		List<FieldDescriptor> list = new ArrayList<FieldDescriptor>();
		for (EntityDescriptor descriptor : entities)
		{
			//noinspection ChainOfInstanceofChecks
			if (descriptor instanceof FieldDescriptor)
			{
				list.add((FieldDescriptor) descriptor);
			}
			else if (descriptor instanceof GroupDescriptor)
			{
				//noinspection unchecked
				List<EntityDescriptor> groupEntities = ((GroupDescriptor) descriptor).getEntities();
				list.addAll(buildList(groupEntities));
			}
		}
		return list;
	}
}
