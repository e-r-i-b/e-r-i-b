<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<tiles:importAttribute/>

<tiles:insert definition="editAccountingEntityTemplate" flush="false">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <tiles:put name="asyncEditUrl" value="/private/async/basket/accounting/edit${type}"/>
    <tiles:put name="formId" value="edit${type}FormId"/>
    <tiles:put name="winId" value="objectEntityDiv"/>
    <tiles:put name="type" value="${type}"/>
</tiles:insert>