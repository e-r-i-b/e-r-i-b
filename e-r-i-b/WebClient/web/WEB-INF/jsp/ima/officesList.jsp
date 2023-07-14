<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<tiles:insert page="../officesList.jsp">
    <tiles:put name="formAction" value="/private/ima/office/list"/>
    <tiles:put name="hintMessage" value="Отметьте интересующее Вас отделение, в котором можно открыть металлический счет, и нажмите кнопку «Выбрать»."/>
    <tiles:put name="needParentSynchKey" value="true"/>
    <tiles:put name="userMessage" value=""/>
    <tiles:put name="additionalFilterFields" value=""/>
</tiles:insert>