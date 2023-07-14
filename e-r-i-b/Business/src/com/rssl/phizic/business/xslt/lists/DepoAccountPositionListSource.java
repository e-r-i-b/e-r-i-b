package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.resources.external.DepoAccountFilter;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountDivision;
import com.rssl.phizic.gate.depo.DepoAccountPosition;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MockHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.Map;
import java.util.List;

/**
 * @author mihaylov
 * @ created 01.04.2011
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountPositionListSource extends DepoAccountListSource
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public DepoAccountPositionListSource(EntityListDefinition definition, DepoAccountFilter depoAccountFilter)
	{
		super(definition, depoAccountFilter);
	}

	public DepoAccountPositionListSource(EntityListDefinition definition, Map parameters) throws BusinessException
	{
		super(definition, parameters);
	}

	
	protected void appendDepoAccount(EntityList builder, DepoAccountLink link) throws BusinessException
	{
		DepoAccount depoAccount = link.getDepoAccount();
		//если для линка не смогли получить счет депо, то не добавляем данный элемент в лист
		if (MockHelper.isMockObject(depoAccount))
			return;

		String key = depoAccount.getAccountNumber();
		Entity entity = new Entity(key, null);
		entity.addField(new Field("accountNumber", key));
		entity.addField(new Field("agreementNumber",depoAccount.getAgreementNumber()));
		entity.addField(new Field("externalId", link.getExternalId()));
		entity.addField(new Field("depoAccountName",link.getName()));
		entity.addField(new Field("agreementDate", DateHelper.formatDateWithStringMonth(depoAccount.getAgreementDate())));
		Client depoAccountOwner = link.getOwner();
		if(depoAccountOwner != null)
		{
			String formattedName = PersonHelper.getFormattedPersonName(depoAccountOwner.getFirstName(), depoAccountOwner.getSurName(), depoAccountOwner.getPatrName());
			entity.addField(new Field("owner",formattedName));
		}

		builder.addEntity(entity);

		try
		{
			DepoAccountPosition position = link.getDepoAccountPositionInfo();
			if (position == null)
				return;
			List<DepoAccountDivision> divisions = position.getDepoAccountDivisions();
			EntityList list = new EntityList();
			for(DepoAccountDivision division : divisions)
			{
				String divisionNumber = XmlHelper.escape(division.getDivisionNumber());
				Entity divisionEntity = new Entity(divisionNumber, null);
				divisionEntity.addField(new Field("divisionType",division.getDivisionType()));
				list.addEntity(divisionEntity);
			}
			entity.addEntityList(list);
		}
		catch (GateLogicException e)
		{
		    log.error("Ошибка при получении информации по позиции для счета депо № " + depoAccount.getAccountNumber(),e);
		}
	}
}
