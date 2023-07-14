package com.rssl.phizic.auth.modes;

//import com.rssl.phizic.auth.modes.generated.impl.AllowOperationDescriptorImpl;
import com.rssl.phizic.auth.modes.generated.AllowOperationTypeDescriptor;
import com.rssl.phizic.auth.modes.generated.impl.AuthenticationStageDescriptorImpl;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 12.12.2006
 * @ $Author: sergunin $
 * @ $Revision: 55894 $
 */

public class Stage implements Serializable
{
	private int                order;
	private AuthenticationMode owner;
	private String             primaryAction;
	private List<AllowOperationBundle>   allowedOperations;
	private StageVerifier      stageVerifier;
	private Set<String>        allAllowedActions;
	private String             key;

	Stage(AuthenticationMode owner, AuthenticationStageDescriptor descriptor, int order) throws SecurityException
	{
		this.owner = owner;
		this.order = order;
		//noinspection unchecked
		List<String> actions = descriptor.getAllowedActions();
		this.primaryAction = actions.get(0);

		this.allAllowedActions = new HashSet<String>(actions);

		//noinspection unchecked
		List<AllowOperationBundle> operationList = new ArrayList<AllowOperationBundle>();
		List<AllowOperationTypeDescriptor> list = descriptor.getAllowOperation();
		for(AllowOperationTypeDescriptor op:list)
		{
			AllowOperationBundle allowOperation = new AllowOperationBundle();
			String opClass = op.getOperationClass();
			String serviceKey = op.getServiceKey();
			allowOperation.setOperationName(opClass);
			allowOperation.setServiceKey(serviceKey);
			String loadConditionVerifier=op.getAllowIf();

			if (!StringHelper.isEmpty(loadConditionVerifier))
				allowOperation.setOperationVerifier(createLoadCondition(loadConditionVerifier));
		else
				allowOperation.setOperationVerifier(NullAllowOperationVerifier.INSTANCE);

			operationList.add(allowOperation);
		}

		this.allowedOperations = operationList;

		AuthenticationStageDescriptor.DemandIfType demandIf = descriptor.getDemandIf();
		if(demandIf == null)
			stageVerifier = NullStageVerifier.INSTANCE;
		else
			stageVerifier = createVerifier(demandIf);

		this.key = descriptor.getKey();
	}

	/**
	 * @return ID (ключ) стадии
	 */
	public String getKey()
	{
		return key;
	}

	private StageVerifier createVerifier(AuthenticationStageDescriptor.DemandIfType demandIf) throws SecurityException
	{
		try
		{
			Class<StageVerifier> clazz = ClassHelper.loadClass(demandIf.getClazz());

			//noinspection unchecked
			List<AuthenticationStageDescriptor.DemandIfType.ParameterType> parameters = demandIf.getParameters();

			StageVerifier verifier;

			if(parameters.isEmpty())
			{
				verifier = clazz.getConstructor().newInstance();
			}
			else
			{
				Properties properties = new Properties();
				for (AuthenticationStageDescriptor.DemandIfType.ParameterType parameter : parameters)
				{
					properties.setProperty(parameter.getName(), parameter.getValue());
				}
				verifier = clazz.getConstructor(Properties.class).newInstance(properties);
			}

			return verifier;
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	private AllowOperationVerifier createLoadCondition(String loadCondition) throws SecurityException
	{
		try
		{
			Class<AllowOperationVerifier> clazz = ClassHelper.loadClass(loadCondition);
			return clazz.getConstructor().newInstance();

		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * @return следующая стадия
	 */
	public Stage getNext()
	{
		int nextOrder = order + 1;

		if(owner.getStages().size() == nextOrder)
			return null;
		else
			return owner.getStages().get(nextOrder);
	}

	/**
	 * @param context стадия необходима
	 * @return true == необходима
	 */
	public boolean isRequired(AuthenticationContext context) throws SecurityException
	{
		return stageVerifier.isRequired(context, this);
	}

	/**
	 * @return основное действиe разрешенное стадии
	 */
	public String getPrimaryAction()
	{
		return primaryAction;
	}


	/**
	 * @return Все разрешенные действия стадии
	 */
	public Set<String> getAllAllowedActions()
	{
		return Collections.unmodifiableSet(allAllowedActions);
	}

	/**
	 * @return операции и варифаеры к ним
	 */
	public List<AllowOperationBundle> getAllowedOperations()
	{
		return Collections.unmodifiableList(allowedOperations);
	}


	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Stage stage = (Stage) o;

		if (order != stage.order) return false;
		if (key != stage.key) return false;
		if (!primaryAction.equals(stage.primaryAction)) return false;

		return true;
	}

	public int hashCode()
	{
		int result = order;
		result = 31 * result + key.hashCode();
		return result;
	}

	public String toString()
	{
		return "[" + order + "," + key +  "] " + primaryAction;
	}
}