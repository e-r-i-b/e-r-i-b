<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/persons/restorepassword" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="personEdit">
        <tiles:put name="needSave" type="string" value="false"/>
        <tiles:put name="submenu" value="Edit"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false" >
                <tiles:put name="id" value=""/>
                <tiles:put name="name" value="Смена пароля"/>
                <tiles:put name="additionalStyle" value="autoWidth"/>
                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Идентификатор подключения
                        </tiles:put>
                        <tiles:put name="data">
                            <html:radio property="field(restoreType)" value="card">
                                Подключенные карты <html:select property="field(cardId)" styleId="field(cardId)" styleClass="select">

                                <c:choose>
                                    <c:when test="${not empty form.cardLinks}">
                                        <c:forEach var="cardLink" items="${form.cardLinks}">
                                            <html:option value="${cardLink.id}" styleClass="text-grey"><c:out value="${cardLink.description} ${phiz:getCutCardNumber(cardLink.number)}"/></html:option>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <html:option value="" styleClass="text-grey">Нет доступных карт</html:option>
                                    </c:otherwise>
                                </c:choose>
                                </div>
                            </html:select>
                            </html:radio>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="data">
                            <html:radio property="field(restoreType)" value="login">
                                Идентификатор ЕРИБ
                            </html:radio>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false" service="CSAClientManagement">
                        <tiles:put name="commandKey" value="button.password.change"/>
                        <tiles:put name="commandHelpKey" value="button.password.change.help"/>
                        <tiles:put name="bundle" value="personsBundle"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="personsBundle"/>
                        <tiles:put name="action" value="persons/edit.do?person=${form.id}"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
            <c:if test="${form.failureIdentification}">
                <tiles:insert definition="userConfirmation" flush="false" service="CSAClientManagement">
                    <tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="question" value="password.change.identification.failure.confirm.message"/>
                    <tiles:put name="okAction" value="window.location = '${phiz:calculateActionURL(pageContext,'/persons/list/full')}'"/>
                </tiles:insert>
            </c:if>
            <c:if test="${form.failureIMSICheck}">
                <script>
                    $(document).ready(function ()
                    {
                        win.open('failureIMSICheckWin');
                    });
                </script>
            </c:if>
        </tiles:put>
    </tiles:insert>

    <tiles:insert definition="failureIMSICheckWin" flush="false">
        <tiles:put name="id" value="failureIMSICheckWin"/>
    </tiles:insert>
</html:form>