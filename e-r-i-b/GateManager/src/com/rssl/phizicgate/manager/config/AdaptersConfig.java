package com.rssl.phizicgate.manager.config;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizicgate.manager.routing.Adapter;

/**
 * @author krenev
 * @ created 27.04.2010
 * @ $Author$
 * @ $Revision$
 * ������ ������� ����������� �������� ��� ������������ ��������.
 */
public abstract class AdaptersConfig extends Config
{
	public static final String DICTIONARIES_REPLICATION_ADAPTER = "com.rssl.iccs.dictionaries.config.ExternalSystemId";
	public static final String CARD_TRANSFER_ADAPTER = "com.rssl.iccs.cards.transfer.adapter";
	public static final String CARD_WAY_ADAPTER_CODE = "com.rssl.iccs.card.system.id.99way";

	protected AdaptersConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return �������, �� �������� ���������� ���������� ������������
	 */
	public abstract Adapter getDictionatyReplicationAdapter();

	/**
	 * @return ����������������� �������, ������� ����������� ��������� ��������(iqwave, ��������).
	 */
	public abstract Adapter getCardTransfersAdapter();

	/**
	 * @return ������������� Way
	 */
	public abstract String getCardWayAdapterCode();
}