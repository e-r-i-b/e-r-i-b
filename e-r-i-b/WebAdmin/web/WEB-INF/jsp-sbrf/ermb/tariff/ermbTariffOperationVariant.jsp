<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<tiles:importAttribute/>

<html:select property="${operationFieldName}">
    <html:option value="PROVIDED">
        <bean:message key="ermb.free" bundle="ermbBundle"/>
    </html:option>
    <html:option value="NOT_PROVIDED">
        <bean:message key="ermb.not.provide" bundle="ermbBundle"/>
    </html:option>
    <html:option value="NOT_PROVIDED_WHEN_NO_PAY">
        <bean:message key="ermb.in.license" bundle="ermbBundle"/>
    </html:option>
</html:select>
