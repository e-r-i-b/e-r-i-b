package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.modes.generated.AuthenticationModeDescriptor;
import com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor;
import com.rssl.phizic.auth.modes.generated.CompositeDescriptor;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 12.12.2006
 * @ $Author: moshenko $
 * @ $Revision: 54585 $
 */

public class AuthenticationMode implements Serializable
{
	private final UserVisitingMode userVisitingMode;
	private List<Stage> stages;
	private AthenticationCompleteAction athenticationCompleteAction;

	AuthenticationMode(AuthenticationModeDescriptor descriptor) throws SecurityException
	{
		this.userVisitingMode = UserVisitingMode.valueOf(descriptor.getUserVisitingMode().toUpperCase());
		this.athenticationCompleteAction = createCompleteAction(descriptor);

		//noinspection unchecked
		List<AuthenticationStageDescriptor> stageDescriptors = descriptor.getStages();
		stages = new ArrayList<Stage>();

		AuthenticationStageDescriptor first = stageDescriptors.get(0);
		if(first.getDemandIf() != null)
			throw new SecurityException("Первая стадия режима аутентификации не может быть опциональной. Stage key=" + first.getKey());

		int pos = 0;
		for(AuthenticationStageDescriptor stageDescriptor : stageDescriptors)
		{
			addStage(stageDescriptor, pos);
			pos++;
		}
	}

	private void addStage(AuthenticationStageDescriptor stageDescriptor, int pos)
	{
		Stage newStage = new Stage(this, stageDescriptor, pos);
		stages.add(newStage);
	}

	private AthenticationCompleteAction createCompleteAction(AuthenticationModeDescriptor descriptor)
	{
		String onCompleteAction = descriptor.getOnCompleteAction();
		if (onCompleteAction != null)
			return createClass(onCompleteAction);

		CompositeDescriptor onCompleteDescriptor = descriptor.getOnComplete();
		if(onCompleteDescriptor == null)
			return NullAthenticationCompleteAction.INSTANCE;

		List<String> actionsClassName = onCompleteDescriptor.getAction();
		if (actionsClassName==null || actionsClassName.isEmpty())
			return NullAthenticationCompleteAction.INSTANCE;

		CompositeAthenticationCompleteAction compositeAction = new CompositeAthenticationCompleteAction();
		for (String actionClassName : actionsClassName)
		{
            if  (!StringHelper.isEmpty(actionClassName))
			    compositeAction.addAction(createClass(actionClassName));
		}
		return compositeAction;
	}

	private AthenticationCompleteAction createClass(String className)
	{
		try
		{
			Class<AthenticationCompleteAction> actionClazz = ClassHelper.loadClass(className);
			return actionClazz.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			throw new SecurityException(e);
		}
		catch (InstantiationException e)
		{
			throw new SecurityException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * @return режим работы пользователя
	 */
	public UserVisitingMode getUserVisitingMode()
	{
		return userVisitingMode;
	}

	/**
	 * @return список стадий аутентификации режима
	 */
	public List<Stage> getStages()
	{
		return Collections.unmodifiableList(stages);
	}

	/**
	 * @return действие выполняемое после окончания аутентификации
	 */
	public AthenticationCompleteAction getAuthenticationCompleteAction()
	{
		return athenticationCompleteAction;
	}
}