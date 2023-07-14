package com.rssl.phizic.business.dictionaries.finances;

import com.rssl.phizic.business.dictionaries.QueryReplicaDestinationBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gololobov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class MerchantCategoryCodeReplicaDestinations extends QueryReplicaDestinationBase<MerchantCategoryCode>
{
	//���� � MCC-������ �� ����
	private Map<Long,MerchantCategoryCode> mccCodesMap = null;

	public MerchantCategoryCodeReplicaDestinations()
	{
		super("com.rssl.phizic.business.dictionaries.finances.getAllMccCodes", true);
	}

	public void initialize(GateFactory factory) throws GateException
	{
		if (this.mccCodesMap == null)
			this.mccCodesMap = this.loadMerchantCagoryCodes();
	}

	/**
	 * �������� MCC-����� � ����
	 * @return - Map<Long, MerchantCategoryCode> ���� � MCC-������ (key - ��� MCC, value - MerchantCategoryCode)
	 * @throws GateException
	 */
	private Map<Long, MerchantCategoryCode> loadMerchantCagoryCodes() throws GateException
	{
		//��������� ��� MCC-����
		try
		{
			List<MerchantCategoryCode> mccCodeslist = HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<List<MerchantCategoryCode>>()
				{
					public List<MerchantCategoryCode> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.finances.getAllMccCodes");
						return query.list();
					}
				}
			);

			Map<Long, MerchantCategoryCode> mccMap = new HashMap<Long, MerchantCategoryCode>(mccCodeslist.size());
			for (MerchantCategoryCode mcc : mccCodeslist)
				mccMap.put(mcc.getCode(), mcc);
			return mccMap;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ���������� ����� MCC-����
	 * @param newValue - ����� ������ MCC-����
	 * @throws GateException
	 */
	public void add(MerchantCategoryCode newValue) throws GateException
	{
		MerchantCategoryCode oldMerchantCategoryCode = this.mccCodesMap.get(newValue.getCode());
		//���� � ���� ��� ���� ����� MCC-��� �� ������ ���������
		if (oldMerchantCategoryCode != null)
			update(oldMerchantCategoryCode, newValue);
		else
			super.add(newValue);
	}

	/**
	 * �������� ������� MCC-����
	 * @param oldValue - ������ ������ MCC-����
	 * @throws GateException
	 */
	public void remove(MerchantCategoryCode oldValue) throws GateException
	{
		//�� ������� MCC-����
	}
}
