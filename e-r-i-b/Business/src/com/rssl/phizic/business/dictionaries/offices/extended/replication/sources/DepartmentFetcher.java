package com.rssl.phizic.business.dictionaries.offices.extended.replication.sources;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.dictionaries.offices.extended.OfficeHelper;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.DepartmentFilter;
import com.rssl.phizic.business.dictionaries.offices.extended.replication.ReplicationDepartmentsTaskResult;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.ValidateException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.business.dictionaries.offices.extended.replication.Constants.*;

/**
 * —борщик сущности департамента
 * @author niculichev
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */
public class DepartmentFetcher
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private List<ExtendedDepartment> result = new ArrayList<ExtendedDepartment>();
	private Map<Code, String> errors = new HashMap<Code, String>();

	private ReplicationDepartmentsTaskResult taskResult;
	private DepartmentFilter filter;

	DepartmentFetcher(ReplicationDepartmentsTaskResult taskResult)
	{
		this.taskResult = taskResult;
	}

	/**
	 * ƒобавить подразделение по мапу его полей
	 * @param fieldsRecord мап полей
	 */
	public void addDepartment(Map<String, String> fieldsRecord)
	{
		ExtendedDepartment department = new ExtendedDepartment();

		try
		{
			// увеличиваем общее количество обработанных записей
			taskResult.sourceTotalRecordProcessed();

			// од подразделени€ банка
			ExtendedCodeImpl code = buildCode(fieldsRecord);
			department.setCode(code);

			if(filter != null && !filter.filter(department.getCode()))
				return;

			department.setSynchKey(OfficeHelper.buildSynchKey(code.getRegion(), code.getBranch(), code.getOffice()));

			//наименование
			department.setName(getVerifiedValue(fieldsRecord, SUBNAME_FIELD_NAME));

			//Ѕ» 
			department.setBIC(getVerifiedValue(fieldsRecord, BSNEWNUM_FIELD_NAME));

			//адрес
			department.setAddress(buildAddress(fieldsRecord));

			// телефон
			department.setTelephone(getVerifiedValue(fieldsRecord, TELEPHONE_FIELD_NAME));

			// sbidnt
			department.setSbidnt(getVerifiedValue(fieldsRecord, SBIDNT_FIELD_NAME));

			// isOpenIMAOffice
			department.setOpenIMAOffice(getYesNoAttrValue(getVerifiedValue(fieldsRecord, FL_OMS_FIELD_NAME)));

			// увеличиваем количество обработанных записей
			taskResult.sourceRecordProcessed();

			result.add(department);

		}
		catch (ValidateException e)
		{
			log.error(e.getMessage(), e);

			ExtendedCodeImpl code = (ExtendedCodeImpl) department.getCode();
			String name = fieldsRecord.get(SUBNAME_FIELD_NAME);
			// если не успели сформировать то берем какие есть
			if(code == null)
			{
				addErrorRecord(
						fieldsRecord.get(TERBANK_FIELD_NAME),
						fieldsRecord.get(BRANCH_FIELD_NAME),
						fieldsRecord.get(SUBBRANCH_FIELD_NAME),
						name,
						e.getMessage());
			}
			else
			{
				addErrorRecord(
						code.getRegion(),
						code.getBranch(),
						code.getOffice(),
						name,
						e.getMessage());
			}
		}
	}

	/**
	 * @return список построенных подразделений
	 */
	public List<ExtendedDepartment> getResult()
	{
		return result;
	}

	/**
	 * @return список ошибок
	 */
	public  Map<Code, String> getErrors()
	{
		return errors;
	}

	private boolean getYesNoAttrValue(String value)
	{
		return  !StringHelper.isEmpty(value)  &&  "Y".equals(value.toUpperCase());
	}

	private ExtendedCodeImpl buildCode(Map<String, String> fieldsRecord) throws ValidateException
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl();
		code.setRegion(StringHelper.removeLeadingZeros(getVerifiedValue(fieldsRecord, TERBANK_FIELD_NAME)));
		code.setBranch(StringHelper.removeLeadingZeros(getVerifiedValue(fieldsRecord, BRANCH_FIELD_NAME)));
		code.setOffice(StringHelper.removeLeadingZeros(getVerifiedValue(fieldsRecord, SUBBRANCH_FIELD_NAME)));

		if(StringHelper.isEmpty(code.getBranch()) && StringHelper.isNotEmpty(code.getOffice()))
			throw new ValidateException("Ќевалидно значение кода подразделени€.");

		return code;
	}

	private String buildAddress(Map<String, String> fieldsRecord) throws ValidateException
	{
		String subaddress =  StringHelper.getEmptyIfNull(getVerifiedValue(fieldsRecord, SUBADDRESS_FIELD_NAME));
		String index =  StringHelper.getEmptyIfNull(getVerifiedValue(fieldsRecord, SUBINDEX_FIELD_NAME));

		String addressValue = subaddress + ((StringHelper.isNotEmpty(subaddress) && StringHelper.isNotEmpty(index)) ? " ," : "") + index;
		return getVerifiedValue(ADDRESS_FIELD_NAME, addressValue);
	}

	private void addErrorRecord(String tb, String osb, String vsp, String name, String errorMessage)
	{
		taskResult.addToErrorFormatReport(new ExtendedCodeImpl(tb, osb, vsp), name);
	}

	private String getVerifiedValue(Map<String, String> fieldsRecord, String name) throws ValidateException
	{
		return getVerifiedValue(name, StringUtils.trim(fieldsRecord.get(name)));
	}

	private String getVerifiedValue(String name, String value) throws ValidateException
{
		try
		{
			List<FieldValidator> validators = fieldValidators.get(name);

			if(CollectionUtils.isEmpty(validators))
				return value;

			for(FieldValidator validator : validators)
			{
				if(!validator.validate(value))
					throw new ValidateException(validator.getMessage());
			}

			return value;

		}
		catch (TemporalDocumentException e)
		{
			throw new ValidateException(e);
		}
	}

	public void setFilter(DepartmentFilter filter)
	{
		this.filter = filter;
	}
}
