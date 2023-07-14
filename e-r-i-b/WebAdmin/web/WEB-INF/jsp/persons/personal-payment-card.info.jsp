<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/personal-payment-card/add" onsubmit="return setEmptyAction(event);">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="person" value="${form.activePerson}"/>
    <c:set var="personId" value="${(not empty form.activePerson) ? form.activePerson.id : null}"/>

    <script type="text/javascript">


    </script>
    <tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="PersonalPayments"/>
        <tiles:put name="pageTitle" type="string" value="Информация по карте персональных платежей"/>

        <tiles:put name="menu" type="string">
            <c:if test="${form.client != null && form.validCard}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save"/>
                    <tiles:put name="bundle" value="personsBundle"/>
                    <tiles:put name="image" value=""/>
                    <tiles:put name="postbackNavigation" value="true"/>
                    <tiles:put name="viewType" value="blueBorder"/>
                </tiles:insert>
            </c:if>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="image" value=""/>
                <tiles:put name="action" value="/persons/personal-payment-card/list.do?person=${personId}"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="data">

                    <td height="100%">
                        <tiles:insert definition="tableTemplate" flush="false">
                            <tiles:put name="data">
                                <c:choose>
                                    <c:when test="${empty form.client}">
                                        <tr>
                                            <td>
                                                <b> Карта не найдена </b>
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:when test="${form.validCard}">
                                        <tr>
                                            <td>
                                                <b>Номер карты персональных платежей (ПАН)</b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <c:out value="${form.cardId}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <b>Данные о владельце</b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <c:out value="${form.client.fullName}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <b>Адрес</b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <c:out value="${form.client.realAddress}"/>
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td>
                                                <b>Владелец карты не совпадает с текущим клиентом</b>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <b>Владелец карты:</b>&nbsp;
                                                <c:out value="${form.client.fullName}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <b>Клиент</b>&nbsp;
                                                <c:out value="${form.activePerson.fullName}"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <b>Данные сохранены не будут</b>
                                            </td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </tiles:put>
                        </tiles:insert>
                    </td>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
