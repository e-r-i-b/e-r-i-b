<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/departments/useTemplateFactorInFullMAPI" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="propertiesForm">
        <tiles:put name="tilesDefinition" value="departmentsEdit"/>
        <tiles:put name="submenu" type="string" value="UseTemplateFactorInFullMAPI"/>
        <tiles:put name="pageName" type="string" value="�������� ��������� ����� � PRO�����"/>
        <tiles:put name="id" type="string" value="&id=${form.id}"/>
        <tiles:put name="data">
            <table>
                <tiles:insert definition="propertyField" flush="false">
                    <tiles:put name="fieldName" value="com.rssl.iccs.use.template.factor.in.full.mapi|${form.id}"/>
                    <tiles:put name="fieldDescription">��������� ��������� �����</tiles:put>
                    <tiles:put name="showHint" value="none"/>
                    <tiles:put name="fieldType" value="checkbox"/>
                    <tiles:put name="imagePath" value="${imagePath}"/>
                </tiles:insert>
            </table>
        </tiles:put>
        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save"/>
                <tiles:put name="bundle" value="departmentsBundle"/>
                <tiles:put name="postbackNavigation" value="true"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
