<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/configure/exceptions/synchronize" onsubmit="return setEmptyAction(event);">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="exceptionEntryType" value="${form.exceptionEntryType}"/>

    <tiles:insert definition="dictionary" flush="false">
        <c:choose>
            <c:when test="${exceptionEntryType eq 'internal'}">
                <tiles:put name="submenu" type="string" value="ExceptionEntryList"/>
            </c:when>
            <c:otherwise>
                <tiles:put name="submenu" type="string" value="ExternalExceptionEntryList"/>
            </c:otherwise>
        </c:choose>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.${exceptionEntryType}.list.header" bundle="exceptionEntryBundle"/>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name" value="Номера блоков для репликации"/>
                <tiles:put name="tableStyle" value="tableStyleHeight"/>
                <tiles:put name="data" type="string">
                    <c:forEach items="${phiz:getNodes()}" var="nodeInfo">
                        <tr>
                            <td  class="Width120 alignRight"><input type="checkbox" name="selectedNodes" value="${nodeInfo.id}"></td>
                            <td><c:out value="${nodeInfo.id}"/></td>
                        </tr>
                    </c:forEach>
                </tiles:put>
            </tiles:insert>

            <div class="tblNeedTableStyle">
                <div>
                    <div class="otherButtonsArea inlineButtonsArea">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey" value="button.synchronize"/>
                            <tiles:put name="commandHelpKey" value="button.synchronize.help"/>
                            <tiles:put name="bundle" value="exceptionEntryBundle"/>
                            <tiles:put name="validationFunction">
                                checkSelection('selectedNodes', 'Выберите номера блоков')
                            </tiles:put>
                        </tiles:insert>
                    </div>
                </div>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>