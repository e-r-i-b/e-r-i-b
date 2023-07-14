<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/private/dictionary/kbk/replicate">
    <tiles:insert definition="listKBK">
        <c:set var="frm" value="${ReplicateKBKForm}"/>

        <c:set var="recordCount" value="${frm.recordCount}"/>
        <c:set var="wrongCount" value="${frm.wrongCount}"/>

        <c:set var="errors" value="${frm.errors}"/>

        <tiles:put name="submenu" value="KBK"/>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle"         value="providerBundle"/>
                <tiles:put name="action"         value="/private/dictionary/kbk/list.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="label.report.title" bundle="kbkBundle"/>
                </tiles:put>

                <tiles:put name="data">
                    <table cellspacing="0" cellpadding="0">
                        <tr>
                            <td>
                                <bean:message key="label.entry.count" bundle="kbkBundle"/>&nbsp;
                                <c:out value="${recordCount}"/><br/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="label.delimiter" bundle="kbkBundle"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="label.providers.count" bundle="kbkBundle"/>&nbsp;
                                <c:out value="${recordCount - wrongCount}"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <bean:message key="label.errors.count" bundle="kbkBundle"/>&nbsp;
                                <c:out value="${wrongCount}"/><br/>
                            </td>
                        </tr>
                        <c:if test="${not empty errors}">
                            <tr>
                                <td>
                                    <bean:message key="label.errors" bundle="kbkBundle"/>&nbsp;
                                </td>
                            </tr>
                            <c:forEach var="item" items="${errors}">
                                <tr>
                                    <td>
                                        <c:out value="${item}"/>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </table>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
