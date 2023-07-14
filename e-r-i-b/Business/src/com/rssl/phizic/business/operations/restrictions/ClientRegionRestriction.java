package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.business.login.LoginHelper;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author mihaylov
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ограничение проверяющее, что сотрудник и клиент находятся в одном тербанке
 */
public class ClientRegionRestriction implements ClientRestriction
{
	public boolean accept(Client client) throws BusinessException
	{
		//если сотруднику разрешен поиск по всем ТБ
		OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());
		if (operationFactory.checkAccess("SearchPersonOperation", "SeachClientsByTB"))
			return true;

		Office office = client.getOffice();
		if (office == null)
			return true;

		if (office.getCode() == null)
			return true;

		String clientRegion = new SBRFOfficeCodeAdapter(office.getCode()).getRegion();
		String employeeRegion = LoginHelper.getEmployeeOfficeRegion();

		if(clientRegion.equals(employeeRegion))
			return true;
		//если регионы не совпали проверяем синонимы.
		String clientRegionSynonym = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(clientRegion);
		String employeeRegionSynonym = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(employeeRegion);
		clientRegion = StringHelper.isEmpty(clientRegionSynonym) ? clientRegion : clientRegionSynonym;
		employeeRegion = StringHelper.isEmpty(employeeRegionSynonym) ? employeeRegion : employeeRegionSynonym;

		//если не совпали синонимы, проверяем костыль 38-99, 44-2
		return TBSynonymsDictionary.isSameTB(clientRegion, employeeRegion);
	}

}
