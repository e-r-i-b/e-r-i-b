<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:html>
    <head>
        <title>Создание заявки на получение выписки из ПФР</title>
    </head>

    <body>
    <h1>Создание заявки на получение выписки из ПФР: сохранение заявки</h1>

    <html:form action="/atm/PFRStatementClaim" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
        <tiles:insert definition="atm" flush="false">
            <tiles:put name="address"   value="/private/payments/payment.do"/>
            <tiles:put name="formName"  value="PFRStatementClaim"/>
            <c:if test="${not empty form.response.initialData}">
                <tiles:put name="operation" value="save"/>
                <c:set var="claim" value="${form.response.initialData.PFRStatementClaim}"/>
            </c:if>
            <c:if test="${not empty form.response.document}">
                <c:set var="document" value="${form.response.document.PFRStatementClaim}"/>
            </c:if>
            <tiles:put name="data">
                <small>
                    <table>
                        <tr><td>id</td>     <td><input name="id" type="text" value="${form.response.document.id}" readonly/></td></tr>
                        <tr><td>form</td>   <td><input type="text" value="${form.response.document.form}" readonly="true"/></td></tr>
                        <tr><td>status</td> <td><input name="documentStatus" type="text" value="${form.response.document.status}" readonly="true"/></td></tr>
                    </table>
                </small>
                <c:choose>
                    <c:when test="${not empty claim}">
                        <tiles:insert page="fields-table.jsp" flush="false">
                            <tiles:put name="data">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="claim" beanProperty="documentNumber"/>
                                </tiles:insert>

                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="claim" beanProperty="documentDate"/>
                                </tiles:insert>

                                <c:choose>
                                    <c:when test="${not empty claim.passport}">
                                        <tiles:insert page="field.jsp" flush="false">
                                            <tiles:put name="field" beanName="claim" beanProperty="passport"/>
                                        </tiles:insert>
                                    </c:when>
                                    <c:when test="${not empty claim.SNILS}">
                                        <tiles:insert page="field.jsp" flush="false">
                                            <tiles:put name="field" beanName="claim" beanProperty="SNILS"/>
                                        </tiles:insert>
                                    </c:when>
                                </c:choose>

                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="claim" beanProperty="claimSendMethod"/>
                                </tiles:insert>

                            </tiles:put>
                        </tiles:insert>
                    </c:when>

                    <c:when test="${not empty document}">
                        <c:set var="document" value="${form.response.document.PFRStatementClaim}"/>

                        <tiles:insert page="fields-table.jsp" flush="false">
                            <tiles:put name="data">
                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="documentNumber"/>
                                </tiles:insert>

                                <tiles:insert page="field.jsp" flush="false">
                                    <tiles:put name="field" beanName="document" beanProperty="documentDate"/>
                                </tiles:insert>

                                <c:choose>
                                    <c:when test="${not empty document.passport}">
                                        <tiles:insert page="field.jsp" flush="false">
                                            <tiles:put name="field" beanName="document" beanProperty="passport"/>
                                        </tiles:insert>
                                    </c:when>
                                    <c:when test="${not empty document.SNILS}">
                                        <tiles:insert page="field.jsp" flush="false">
                                            <tiles:put name="field" beanName="document" beanProperty="SNILS"/>
                                        </tiles:insert>
                                    </c:when>
                                </c:choose>

                            </tiles:put>
                        </tiles:insert>
                    </c:when>
                </c:choose>
            </tiles:put>
        </tiles:insert>

    </html:form>
    </body>
</html:html>