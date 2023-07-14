<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>

<tiles:insert page="../officesList.jsp">
    <tiles:put name="formAction" value="/private/reissueCard/office/list"/>
    <tiles:put name="hintMessage" value="Отметьте интересующее Вас отделение в списке и нажмите кнопку «Выбрать»."/>
    <tiles:put name="userMessage" value="Если вы не нашли нужное подразделение в списке, то это означает, что в этом подразделении нельзя получить перевыпущенную карту."/>
    <tiles:put name="additionalFilterFields" value=""/>
    <tiles:put name="needParentSynchKey" value="false"/>
</tiles:insert>