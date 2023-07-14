package com.rssl.phizic.operations.scheme;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.operations.config.DbOperationsConfig;
import com.rssl.phizic.business.operations.config.OperationsConfig;
import com.rssl.phizic.business.schemes.AccessSchemeService;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.business.services.Service;
import com.rssl.phizic.business.services.ServiceOperationDescriptor;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchemeOperationBase extends OperationBase
{
    protected static final AccessSchemeService accessSchemeService = new AccessSchemeService();
    protected static final ServiceService      serviceService = new ServiceService();
    private SharedAccessScheme scheme;
    private SchemeOperationHelper serviceHelper;

    protected void initialize(SchemeOperationHelper operationHelper)
    {
        this.serviceHelper = operationHelper;
    }

    public SharedAccessScheme getScheme() throws BusinessException
    {
        return scheme;
    }

    protected void setScheme(SharedAccessScheme scheme)
    {
        this.scheme = scheme;
    }

    /**
     * Список клиентских операции.
     * @return
     * @throws BusinessException
     */
    public List<Service> getServices() throws BusinessException
    {
        return serviceHelper.getServices();
    }

    public Map<Service, List<OperationDescriptor>> getServicesTuple()throws BusinessException
    {
        List<Service> services = serviceHelper.getServices();
        Map<Service, List<OperationDescriptor>> result = new HashMap<Service, List<OperationDescriptor>>();
        OperationsConfig operationsConfig = DbOperationsConfig.get();
		for (Service service : services)
		{
			List<OperationDescriptor> operationDescriptors = new ArrayList<OperationDescriptor>();
			List<ServiceOperationDescriptor> serviceOperationDescriptors = operationsConfig.getServiceOperationDescriptors(service);

			for (ServiceOperationDescriptor serviceOperationDescriptor : serviceOperationDescriptors)
			{
				operationDescriptors.add(serviceOperationDescriptor.getOperationDescriptor());
			}

			result.put(service, operationDescriptors);
		}
        return result;
    }

    protected Service findService(Long serviceId) throws BusinessException
    {
        return serviceHelper.findById(serviceId);
    }

}
