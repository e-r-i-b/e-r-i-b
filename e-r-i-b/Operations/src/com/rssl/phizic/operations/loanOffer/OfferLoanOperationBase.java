package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loanOffer.OffersService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.pereodicalTask.PereodicalTaskResult;
import com.rssl.phizic.business.pereodicalTask.unload.DirInteractionPereodicalTask;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.pereodicalTask.PeriodicalTaskOperationBase;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Moshenko
 * Date: 16.06.2011
 * Time: 9:23:23
 */
public class OfferLoanOperationBase  extends PeriodicalTaskOperationBase<DirInteractionPereodicalTask, Restriction>
{
	protected static final int BATCH_SIZE = 1000;              //кол-во операций в одной транзакции

    protected List<String> commonErrors = new ArrayList<String>();  //общие ошибки репликации
    protected List<String> personsErrors = new ArrayList<String>(); //ошибки по клиентам
    protected int loadCount = 0; //кол-во пользователей которым загрузил предложение.
    protected int totalCount = 0;                              //кол-во обработанных записей
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

    protected static final PersonService personService   = new PersonService();
    protected static final DepartmentService departmentService   = new DepartmentService();
    protected static final OffersService offersService   = new OffersService();

	public DirInteractionPereodicalTask createBackroundTask()
	{
		return null;
	}

	public PereodicalTaskResult execute() throws BusinessException
	{
		return null;
	}

	public void initialize(DirInteractionPereodicalTask backroundTask) throws BusinessException
	{
	}

    public void initialize(InputStream inputStream,OfferParserBase reader, char delimiter,int colCount)
	{
		reader.initialize(inputStream,delimiter,colCount);
	}

	public List<String> getCommonErrors()
    {
	    // noinspection ReturnOfCollectionOrArrayField
	    return commonErrors;
    }

    public List<String> getPersonsErrors()
    {
	    // noinspection ReturnOfCollectionOrArrayField
        return personsErrors;
    }

    public int getLoadCount()
    {
        return  loadCount;
    }

    public int getTotalCount()
    {
        return totalCount;
    }

	/**
	 * Получить номер ТБ для пользователя
	 * @param person - клиент, для которого ищем ТБ
	 * @return номер тб
	 * @throws BusinessException
//	 */
//	protected Long getTerbankByPerson(ActivePerson person) throws BusinessException
//	{
//		Department department = departmentService.findById(person.getDepartmentId());
//		if (department != null && department.getCode() != null)
//		{
//
//			Map<String, String> fields = department.getCode().getFields();
//			if (fields != null)
//			{
//				String region = fields.get(com.rssl.phizic.operations.loanOffer.Constants.REGION);
//				return StringHelper.isEmpty(region) ? null : Long.parseLong(region);
//			}
//		}
//
//		return null;
//	}
}
