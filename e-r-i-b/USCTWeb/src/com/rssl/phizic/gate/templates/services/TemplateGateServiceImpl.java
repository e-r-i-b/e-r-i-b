package com.rssl.phizic.gate.templates.services;

import com.rssl.phizic.gate.operations.*;
import com.rssl.phizic.gate.owners.person.ProfileNotFoundException;
import com.rssl.phizic.gate.templates.services.generated.*;
import com.rssl.phizic.gate.templates.services.generated.holders.ListHolder;
import org.apache.commons.collections.CollectionUtils;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

/**
 * Серверная часть TemplateGateService
 *
 * @author khudyakov
 * @ created 02.03.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateGateServiceImpl implements TemplateGateService
{
	public GateTemplate findById(Long id) throws RemoteException
	{
		try
		{
			return new FindByIdTemplateOperation(id).toGeneratedEntity();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public List getAll(List guids) throws RemoteException
	{
		try
		{
			return new GetAllTemplatesOperation(guids).toGeneratedEntity();
		}
		catch (ProfileNotFoundException ignore)
		{
			return Collections.emptyList();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void addOrUpdate(ListHolder holder) throws RemoteException
	{
		if (CollectionUtils.isEmpty(holder.value))
		{
			return;
		}

		try
		{
			UpdateTemplatesOperation operation = new UpdateTemplatesOperation(holder.value);
			operation.update();

			holder.value = operation.toGeneratedEntity();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void remove(List templates) throws RemoteException
	{
		if (CollectionUtils.isEmpty(templates))
		{
			return;
		}

		try
		{
			new RemoveTemplatesOperation(templates).remove();
		}
		catch (Exception e)
		{
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public Field __forGeneratedField() throws RemoteException
	{
		return null;
	}

	public FieldValidationRule __forGeneratedFieldValidationRule() throws RemoteException
	{
		return null;
	}

	public ListValue __forGeneratedListValue() throws RemoteException
	{
		return null;
	}

	public ExtendedAttribute __forGeneratedExtendedAttribute()
	{
		return null;
	}

	public WriteDownOperation __forGeneratedWriteDownOperation() throws RemoteException
	{
		return null;
	}

	public ClientDocument __forGeneratedClientDocument() throws RemoteException
	{
		return null;
	}

	public Profile __forGeneratedProfile() throws RemoteException
	{
		return null;
	}
}
