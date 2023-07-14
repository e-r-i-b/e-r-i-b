package com.rssl.phizic.business.resources.own;

import com.rssl.phizic.business.operations.*;

import java.util.*;

/**
 * User: IIvanova
 * Date: 03.02.2006
 * Time: 16:27:01
 */
public class OperationDescriptorComparator implements Comparator {

    public int compare(Object object, Object object1) {
        OperationDescriptor od1 = (OperationDescriptor) object;
        OperationDescriptor od2 = (OperationDescriptor) object1;
        String name1 = od1.getName();
        String name2 = od2.getName();
        return name1.compareToIgnoreCase(name2);
    }

}
