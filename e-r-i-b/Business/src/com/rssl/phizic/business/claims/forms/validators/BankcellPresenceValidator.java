package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.bankcells.OfficeCellType;
import com.rssl.phizic.business.dictionaries.bankcells.CellType;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.Map;
import java.util.List;
import java.math.BigInteger;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author Kidyaev
 * @ created 29.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class BankcellPresenceValidator extends MultiFieldsValidatorBase
{
	private static final SimpleService simpleService = new SimpleService();

	private static final String OFFICE_ID             = "office";
	private static final String CELL_TYPE_DESCRIPTION = "cellType";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		BigInteger officeId            = (BigInteger) retrieveFieldValue(OFFICE_ID, values);
		String cellTypeDescription = (String) retrieveFieldValue(CELL_TYPE_DESCRIPTION, values);

		if ( isValueEmpty(cellTypeDescription) )
			return false;

		Long           cellTypeId     = getCellTypeIdByDescription(cellTypeDescription);
		OfficeCellType officeCellType = getOfficeCellType(officeId.longValue(), cellTypeId);

		return officeCellType != null && officeCellType.getPresence();
	}

	private boolean isValueEmpty(String value)
	{
	    if (value == null || value.equals(""))
	        return true;

	    return false;
	}

	private Long getCellTypeIdByDescription(String cellTypeDescription)
	{
		Long result = null;

		try
		{
			List<CellType> cellTypes = simpleService.getAll(CellType.class);

			for ( CellType cellType : cellTypes )
			{
				if ( cellTypeDescription.equals(cellType.getDescription()) )
				{
					result = cellType.getId();
					break;
				}
			}
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}

		return result;
	}

	private OfficeCellType getOfficeCellType(final Long officeId, final Long cellTypeId)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<OfficeCellType>()
			{
				public OfficeCellType run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.bankcells.OfficeCellType.getOfficeCellType");
					query.setParameter("officeId", officeId);
					query.setParameter("cellTypeId", cellTypeId);
					return (OfficeCellType) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
