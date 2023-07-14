<%--
  Created by IntelliJ IDEA.
  User: tisov
  Date: 29.06.15
  Time: 19:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<html:form action="/fraud/async/confirm">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <div>
        <div class="alignCenter">
            <h1>Операция запрещена офицером безопасности</h1>
        </div>
        <div class="floatRight marginTop20">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.close.payment"/>
                <tiles:put name="commandHelpKey" value="button.close.payment.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="onclick" value="closeFraudWindow()"/>
            </tiles:insert>
        </div>
    </div>
</html:form>

