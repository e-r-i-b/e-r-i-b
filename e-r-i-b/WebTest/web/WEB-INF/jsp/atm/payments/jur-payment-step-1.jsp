<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
<head>
    <title>Перевод организации</title>
</head>

<body>
<h1>Ввод первичных данных</h1>

<html:form action="/atm/jurPayment" show="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="atm" flush="false">
        <tiles:put name="address" value="/private/payments/jurPayment/edit.do"/>

        <tiles:put name="data">
            <small>
                <table>
                    <tr><td>id</td>         <td><html:text property="id" readonly="true"/></td></tr>
                    <tr><td>template</td>   <td><html:text property="template" readonly="true"/></td></tr>
                </table>
            </small>
            <c:choose>
                <c:when test="${not empty form.response and not empty form.response.initialData}">
                    <c:set var="initialData" value="${form.response.initialData.jurPayment}"/>

                    operationUID:
                    <input name="operationUID" value="${initialData.operationUID}" size="100" readonly><br/>
                    <br/>

                    <div style="background-color:lightgray; padding:10px; border: 1px dotted red;">
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
                    </div>
                </c:when>
            </c:choose>
        </tiles:put>

        <tiles:put name="operation" value="next"/>
    </tiles:insert>

    <c:url var="jurPaymentUrl" value="/atm/jurPayment.do">
        <c:param name="url" value="${form.url}"/>
        <c:param name="cookie" value="${form.cookie}"/>
        <c:param name="proxyUrl" value="${form.proxyUrl}"/>
        <c:param name="proxyPort" value="${form.proxyPort}"/>
    </c:url>
    <html:link href="${jurPaymentUrl}">Начать сначала</html:link>
</html:form>
<br/>

</body>
</html:html>
