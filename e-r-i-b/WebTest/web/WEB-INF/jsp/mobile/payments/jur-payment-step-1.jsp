<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>ѕеревод организации</title>
</head>

<body>
<h1>¬вод первичных данных</h1>

<html:form action="/mobileApi/jurPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="mobile" flush="false">
        <tiles:put name="address" value="/private/payments/jurPayment/edit.do"/>
        <tiles:put name="operation" value="next"/>

        <tiles:put name="data">
            <table>
                <tr><td>id</td>         <td><html:text property="id" readonly="true"/></td></tr>
                <tr><td>template</td>   <td><html:text property="template" readonly="true"/></td></tr>
            </table>
            <c:set var="initialData" value="${form.response.initialData.jurPayment}"/>

            operationUID:
            <input name="operationUID" value="${initialData.operationUID}" size="100" readonly><br/>

            <tiles:insert page="fields-table.jsp" flush="false">
                <tiles:put name="data">
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="receiverAccount"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="receiverINN"/>
                    </tiles:insert>
                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="receiverBIC"/>
                    </tiles:insert>
                    <c:if test="${not empty initialData.externalReceiver}">
                        <tiles:insert page="field.jsp" flush="false">
                            <tiles:put name="field" beanName="initialData" beanProperty="externalReceiver"/>
                        </tiles:insert>
                    </c:if>

                    <tiles:insert page="field.jsp" flush="false">
                        <tiles:put name="field" beanName="initialData" beanProperty="availableFromResources"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>

</body>
</html:html>
