package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.readers.CompositeConfirmResponseReader;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.common.forms.doc.DocumentSignature;

import java.util.*;

/**
 * @author emakarov
 * @ created 19.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class CompositeConfirmStrategy implements ConfirmStrategy
{
	Map<ConfirmStrategyType, ConfirmStrategy> strategies;
	ConfirmStrategyType defaultStrategy;
	private CompositeException warning = new CompositeException();

	public ConfirmStrategyType getDefaultStrategy()
	{
		return defaultStrategy;
	}

	public void setDefaultStrategy(ConfirmStrategyType defaultStrategy)
	{
		this.defaultStrategy = defaultStrategy;
	}
	/**
	 * �����������
	 */
	public CompositeConfirmStrategy()
	{
		strategies = new HashMap<ConfirmStrategyType, ConfirmStrategy>();
	}

	/**
	 * ���������� � ��� ���������
	 * @param strategy
	 */
	public void addStrategy(ConfirmStrategy strategy)
	{
		strategies.put(strategy.getType(), strategy);
	}

	public ConfirmStrategy getStrategy(ConfirmStrategyType type)
	{
		return strategies.get(type);
	}

	public void removeStrategy(ConfirmStrategyType type)
	{
		//������� sms ��������� ������!
		if (type==ConfirmStrategyType.sms)
			return;
		
		strategies.remove(type);
		if (defaultStrategy.equals(type)) 
			defaultStrategy = ConfirmStrategyType.sms;

	}

	public Map<ConfirmStrategyType, ConfirmStrategy> getStrategies()
	{
		return strategies;
	}

	/**
	 * @return ��� - ��� ���������
	 */
	public ConfirmStrategyType getType()
	{
		return ConfirmStrategyType.composite;
	}

	/**
	 * ��������� �� �������� ��� �������� �������
	 * @return true == ���������
	 */
	public boolean hasSignature()
	{
		boolean anyoneSignatured = false;
		Set<ConfirmStrategyType> strategyTypes = strategies.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			if (strategies.get(type).hasSignature())
			{
				if (anyoneSignatured)
				{
					throw new IllegalStateException(); // ���� ��� ��������� ������� �������, �� ������ ����������
				}
				anyoneSignatured = true;
			}
		}
		return anyoneSignatured;
	}

	/**
	 * ������� ������ �� �������������  (���� requireValues() == true)
	 * @param login ����� ��� �������� ��������� ������
	 * @param value �������� ��� �������
	 * @param sessionId ������������� ������� ������
	 * @return ������
	 * @throws SecurityException
	 */
	public ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId,PreConfirmObject preConfirm) throws SecurityException, SecurityLogicException
	{
		CompositeConfirmRequest compositeConfirmRequest = new CompositeConfirmRequest();
		Set<ConfirmStrategyType> strategyTypes = strategies.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			ConfirmRequest request = strategies.get(type).createRequest(login, value, sessionId,preConfirm);
			compositeConfirmRequest.addConfirmRequest(request);
		}
		return compositeConfirmRequest;
	}

	/**
	 * ��������� �����
	 * @param login ����� ��� �������� ����������� ��������
	 * @param request ������
	 * @param response �����
	 * @throws com.rssl.phizic.security.SecurityLogicException �������� ����� (������, ������� etc)
	 */
	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		if (!(request instanceof CompositeConfirmRequest) || !(response instanceof CompositeConfirmResponse))
		{
			throw new SecurityException("CompositeConfirmStrategy ��������� ������ CompositeConfirmRequest � CompositeConfirmResponse");
		}

		CompositeConfirmRequest compositeConfirmRequest = (CompositeConfirmRequest)request;
		CompositeConfirmResponse compositeConfirmResponse = (CompositeConfirmResponse)response;

		Set<ConfirmStrategyType> strategyTypes = strategies.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			ConfirmRequest localRequest = compositeConfirmRequest.getConfirmRequest(type);
			ConfirmResponse localResponse = compositeConfirmResponse.getConfirmResponse(type);
			strategies.get(type).validate(login, localRequest, localResponse);
		}
		return new ConfirmStrategyResult(false);
	}

	/**
	 * ��������, ������� ����� ��������� ����� ��������������
	 * @param callBackHandler CallBackHandler
	 * @throws com.rssl.phizic.security.SecurityLogicException, SecurityException �������� ����� (������, ������� etc)
	 */
	public PreConfirmObject preConfirmActions(CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		PreConfirmObject preConfirmItemObject = null;
		Map<String, Object> paramMap = new HashMap();

		Set<ConfirmStrategyType> strategyTypes = strategies.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			preConfirmItemObject = strategies.get(type).preConfirmActions(callBackHandler);
			if (preConfirmItemObject != null)
				paramMap.putAll(preConfirmItemObject.getPreConfimParamMap());
		}

		return new PreConfirmObject(paramMap);
	}

	public void reset(CommonLogin login, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		Set<ConfirmStrategyType> strategyTypes = strategies.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			strategies.get(type).reset(login, confirmableObject);
		}
	}

	public ConfirmResponseReader getConfirmResponseReader()
	{
		CompositeConfirmResponseReader compositeConfirmResponseReader = new CompositeConfirmResponseReader();

		Set<ConfirmStrategyType> strategyTypes = strategies.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			ConfirmResponseReader confirmResponseReader = strategies.get(type).getConfirmResponseReader();
			compositeConfirmResponseReader.addConfirmResponseReader(type, confirmResponseReader);
		}

		return compositeConfirmResponseReader;
	}

	public DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		if (!hasSignature()) // ����� ���������� �������� �� ��������� SignatureCreator
		{
			throw new UnsupportedOperationException();
		}
		CompositeConfirmRequest compositeRequest = (CompositeConfirmRequest) request;
		CompositeConfirmResponse compositeResponse = (CompositeConfirmResponse) response;
		Set<ConfirmStrategyType> strategyTypes = strategies.keySet();
		for (ConfirmStrategyType type : strategyTypes)
		{
			ConfirmStrategy strategy = strategies.get(type);
			if (strategy.hasSignature())
			{
				// ������� ������ ���������, �.�. � ������������� ����� ��������������� ��������
				return strategy.createSignature(compositeRequest.getConfirmRequest(type),
						(SignatureConfirmResponse)compositeResponse.getConfirmResponse(type));
			}
		}
		throw new UnsupportedOperationException();
	}

	public void setWarning(Exception e)
	{
		warning.clear();
		warning.addException(e);
	}

	public Exception getWarning()
	{
		return warning.isEmpty() ? null : warning;
	}

	public void addWarning(Exception e)
	{
		if(e != null)
			warning.addException(e);
	}

	public void clearWarning()
	{
		warning.clear();
	}

	public Object clone() throws CloneNotSupportedException
	{
		CompositeConfirmStrategy newStrategy = new CompositeConfirmStrategy();
		newStrategy.setDefaultStrategy(getDefaultStrategy());
		for (ConfirmStrategy strategy: strategies.values())
		{
			newStrategy.addStrategy(strategy);
		}
		return newStrategy;
	}

	public boolean filter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, String userChoice, ConfirmableObject object)
	{
		if (StringHelper.isNotEmpty(userChoice))
			setDefaultStrategy(ConfirmStrategyType.valueOf(userChoice));

		if (localFilter(conditions, object))
			return false;

		for (ConfirmStrategyType strategyType: conditions.keySet())
		{
			ConfirmStrategy strategy = strategies.get(strategyType);
			if (strategy == null)
				continue;

			if (getDefaultStrategy() == null)
				setDefaultStrategy(strategyType);

			//����� ��������� � ���� � �� ������. ���� ������� �� �����������, �� ��������� ��������� � ������� ��� ���������
			if(!strategy.filter(conditions, userChoice, object))
			{
				if(getDefaultStrategy().equals(strategyType))
					setDefaultStrategy(null);

				// ��������� ��������� � ������������� ��������� ���������
				addWarning(strategy.getWarning());
				strategies.remove(strategyType);
			}
		}
		return strategies.size()>0;
	}

	private boolean localFilter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, ConfirmableObject object)
	{
		ConfirmStrategyType type = getType();
		List<StrategyCondition> currentConditions = conditions.get(type);
		//���� �� ������ ������� ���������� ���������, �� ������ ������ �� ���������
		if (currentConditions == null)
			return true;

		for (StrategyCondition condition: currentConditions)
		{
			//�������� ������������� �������. ���� ������� �� �����������, �� ���������� false.
			//���� �� �����������, ������� ��������� �������. ���� ��� �� �����������, ���������� true.
			if (!condition.checkCondition(object))
			{
				//���� ������� �� �����������, �� �������
				//  �������� ���� �������������� ������� ���������� ���������� �������������,
				//  ���� ����, ���������� �������������� � ���������.
				if (condition.getWarning()!=null)
					setWarning(new SecurityLogicException(condition.getWarning()));
				return false;
			}
		}
		return true;
	}

	/**
	 * ��������� ������������ �� ��������� � ������� ����������� ���������.
	 * @param type ��� ��������� �������������
	 * @return ��������� ��������
	 */
	public boolean isContainStrategy(String type)
	{
		ConfirmStrategyType enumType = ConfirmStrategyType.valueOf(type);
		if (strategies==null)
			return false;

		if (strategies.get(enumType)!=null)
			return true;
		else
			return false;
	}
}
