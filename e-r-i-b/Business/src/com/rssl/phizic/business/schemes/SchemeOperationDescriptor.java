package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.operations.OperationDescriptor;
import com.rssl.phizic.business.resources.own.Accessible;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 30.09.2005 Time: 20:46:52 */
@Deprecated
public class SchemeOperationDescriptor implements Accessible
{
    private Long id;
    private boolean access;
    private OperationDescriptor operationDescriptor;

    public Long getId()
    {
        return id;
    }

    private void setId(Long id)
    {
        this.id = id;
    }

    public boolean isAccess()
    {
        return access;
    }

    public void setAccess(boolean access)
    {
        this.access = access;
    }

    public OperationDescriptor getOperationDescriptor()
    {
        return operationDescriptor;
    }

    public void setOperationDescriptor(OperationDescriptor operationDescriptor)
    {
        this.operationDescriptor = operationDescriptor;
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SchemeOperationDescriptor that = (SchemeOperationDescriptor) o;

        if (access != that.access) return false;
        if (operationDescriptor != null ? !operationDescriptor.getId().equals(that.operationDescriptor.getId()) : that.operationDescriptor != null) return false;

        return true;
    }

    public int hashCode()
    {
        return (operationDescriptor != null ? operationDescriptor.hashCode() : 0);
    }
}
