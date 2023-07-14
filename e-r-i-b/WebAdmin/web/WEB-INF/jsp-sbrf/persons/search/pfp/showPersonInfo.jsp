<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/pfp/person/showInfo" onsubmit="return setEmptyAction(event);">
    <tiles:insert definition="pfpPassing">
        <tiles:put name="submenu" type="string" value="personInfo"/>

        <c:set var="personInfo" value="${phiz:getPersonInfo()}"/>
        <c:set var="closeButtonActionUrl" value="/person/pfp/edit.do"/>
        <!-- данные -->
        <tiles:put name="data" type="string">
            <tiles:insert definition="showPersonInformationForm" flush="false">
                <tiles:put name="formName"><bean:message key="label.person.info.form.title" bundle="pfpPassingBundle"/></tiles:put>
                <tiles:put name="formDescription"><bean:message key="label.person.info.form.description" bundle="pfpPassingBundle"/></tiles:put>
                <tiles:put name="needCloseButton"     value="true"/>
                <tiles:put name="closeButtonFunction" value="loadNewAction('','');window.location='${phiz:calculateActionURL(pageContext, closeButtonActionUrl)}'"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
