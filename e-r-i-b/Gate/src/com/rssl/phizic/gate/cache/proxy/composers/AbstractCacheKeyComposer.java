package com.rssl.phizic.gate.cache.proxy.composers;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.cache.proxy.CacheKeyComposer;
import com.rssl.phizic.utils.StringHelper;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Omeliyanchuk
 * @ created 03.05.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class AbstractCacheKeyComposer implements CacheKeyComposer
{
	/**
	 * �������� �� ������ ����������, ����� ���������, ���� ��� ������ ������ ���.
	 * @param params
	 * @return
	 */
	protected int getOneParam(String params)
	{
		int paramNum = 0;
		if(!StringHelper.isEmpty(params))
		{
			paramNum = Integer.parseInt(params);
		}
		return paramNum;
	}
	/**
	 * �������� ������ ��� ��������� �������� � ������ �������� �����!!!!
	 * @param args ��������� ������ ������
	 * @param params ��������� ��� ������ ���������
	 * @return
	 */
	public List<Pair<Serializable, Object>> getKeys(Object[] args, String params)
	{
		//�������� ����� ��������� � ������
		int paramNum = getMainParamNum(params);

		//�������� ������ �������� ��������
		if(args[paramNum]==null)
			throw new IllegalArgumentException("��������� ������ �������� ����. ���������� ��������� ������������ ������ ������� ����� ��� �������� ���� ��. resolverParams");

		Object[] keyObjects = (Object[])args[paramNum];
		List<Pair<Serializable, Object>> results = new ArrayList<Pair<Serializable, Object>>(keyObjects.length);
		//������ ����� ���������� ������, ��� ������ ���������� �����
		int length = args.length;
		Object[] newArgs = new Object[length];
		System.arraycopy(args, 0 , newArgs, 0,length);
		for (Object keyObject : keyObjects)
		{
			//��������� ��������� ������ �� ����
			newArgs[paramNum] = keyObject;
			results.add( new Pair<Serializable, Object>(getKey(newArgs, params),keyObject) );
		}
		return results;
	}

	protected int getMainParamNum(String params)
	{
		int paramNum = 0;
		if(!StringHelper.isEmpty(params))
		{
			paramNum = Integer.parseInt(params);
		}
		return paramNum;
	}

	/**
	 * �������� ������ ��� ��������� �������� � ������ �������� �����!!!!
	 * @param args ��������� ������ ������
	 * @param params ��������� ��� ������ ���������
	 * @param readyKeys
	 * @return
	 */
	public Object[] substractReadyArgs(Object[] args, String params, List<Object> readyKeys)
	{
		if(readyKeys == null || readyKeys.size()==0)
			return args;

		int readySize = readyKeys.size();

		//�������� ����� ��������� � ������
		int paramNum = getMainParamNum(params);
		//�������� ������ �������� ��������
		Object[] keyObjects = (Object[])args[paramNum];

		int keyObjectsSize = keyObjects.length;

		//���� ������� ���� ���.
		if(readySize == keyObjectsSize )
			return null;

		//������ �������� ��������

		Object[] newKeyObjects = (Object[])Array.newInstance(keyObjects.getClass().getComponentType(),keyObjectsSize - readySize);;

		//�������� ������ �� ���������� � �������� ����������� ���������
		int i = 0;
		for (Object keyObject : keyObjects)
		{
			if(!readyKeys.contains(keyObject))
			{
				newKeyObjects[i] = keyObject;
				i++;
			}
		}
		int length = args.length;
		Object[] newArgs = new Object[length];
		System.arraycopy(args, 0 , newArgs, 0,length);
		newArgs[paramNum] = newKeyObjects;
		return newArgs;
	}


	public boolean isKeyFromResultSupported()
	{
		return false;
	}

	public Serializable getClearCallbackKey(Object result, Object[] params)
	{
		return null;
	}
}
