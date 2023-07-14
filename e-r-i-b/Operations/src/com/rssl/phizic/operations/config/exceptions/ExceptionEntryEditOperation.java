package com.rssl.phizic.operations.config.exceptions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.exception.ExceptionEntryHelper;
import com.rssl.phizic.business.exception.ExceptionEntryService;
import com.rssl.phizic.business.exception.ExceptionMapping;
import com.rssl.phizic.business.exception.locale.ExceptionMappingResourcesService;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.logging.exceptions.ExceptionEntry;
import com.rssl.phizic.logging.exceptions.ExternalExceptionEntry;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizicgate.manager.routing.AdapterService;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;

/**
 * @author mihaylov
 * @ created 19.04.2013
 * @ $Author$
 * @ $Revision$
 * Операция редактирования записи маппинга ошибок
 */
public class ExceptionEntryEditOperation extends OperationBase implements EditEntityOperation, RemoveEntityOperation
{
	private static final ExceptionEntryService service = new ExceptionEntryService();
	private static final ExceptionMappingResourcesService RESOURCE_SERVICE = new ExceptionMappingResourcesService();
	private static AdapterService adapterService = new AdapterService();

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private ExceptionEntry exceptionEntry;
	private List<ExceptionMapping> exceptionMappings;
	private Long[] deletedGroups;
	
	/**
	 * Инициализируем операцию
	 * @param exceptionEntryId - идентификатор записи об ошибке
	 */
	public void initialize(Long exceptionEntryId) throws BusinessException, BusinessLogicException
	{
		exceptionEntry = service.getById(exceptionEntryId);
		if( exceptionEntry == null)
			throw new BusinessLogicException("Запись об ошибке с идентификатором id=" +exceptionEntryId+" не найдена" );
		exceptionMappings = service.getByHash(exceptionEntry.getHash());
	}

	public void initializeNew() throws BusinessException, BusinessLogicException
	{

	}

	public ExceptionEntry getNewEntry(Long id) throws BusinessException
	{
		exceptionEntry = service.getById(id);
		exceptionMappings = service.getByHash(exceptionEntry.getHash());
		return exceptionEntry;
	}

	@Transactional
	public void save() throws BusinessException, BusinessLogicException
	{
		if (ArrayUtils.isNotEmpty(deletedGroups))
		{
			RESOURCE_SERVICE.delete(exceptionEntry.getHash(), deletedGroups);
		}
		service.removeExceptionMapping(exceptionEntry.getHash());
		service.addExceptionMappings(exceptionMappings);
	}

	public ExceptionEntry getEntity()
	{
		return exceptionEntry;
	}

	/**
	 * @param deletedGroups список удалённых групп
	 */
	public void setDeletedGroups(Long[] deletedGroups)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.deletedGroups = deletedGroups;
	}

	/**
	 * @return спиосок текстовок
	 */
	public List<ExceptionMapping> getExceptionMapping()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return exceptionMappings;
	}

	/**
	 * Очащиет список текстовок
	 */
	public void clearExceptionMapping()
	{
		exceptionMappings.clear();
	}

	/**
	 * @return внешняя система
	 * @throws BusinessException
	 */
	public String getSystemName() throws BusinessException
	{
		if (exceptionEntry instanceof ExternalExceptionEntry)
		{
			String uuid = ((ExternalExceptionEntry) exceptionEntry).getSystem();
			return ExceptionEntryHelper.getSystemName(uuid);
		}
		return null;
	}

	/**
	 * Сущность не удаляем, а только очищаем сообщение для клиента
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Transactional
	public void remove() throws BusinessException, BusinessLogicException
	{
		RESOURCE_SERVICE.deleteAll(exceptionEntry.getHash());
		service.removeExceptionMapping(exceptionEntry.getHash());
	}
}
