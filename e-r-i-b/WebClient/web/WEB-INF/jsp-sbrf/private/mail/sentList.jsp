<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/mail/sentList">
    <tiles:importAttribute/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="mailMain">

        <tiles:put name="data" type="string">
            <script type="text/javascript">

                function checkSelection()
                {
                    checkIfOneItem("selectedIds");
                    var qnt = getSelectedQnt("selectedIds");
                    if (qnt < 1)
                    {
                        addError("Выберите хотя бы одно письмо для удаления!");
                        return false;
                    }
                    return true;
                }

                <c:if test="${'true' == param['send']}">
                    <c:set var="mess" value="${phiz:getStaticMessage('com.rssl.iccs.SEND_MAIL')}"/>
                    <c:set var="processed" value="${phiz:processBBCode(mess)}"/>
                    <c:set var="escaped" value="${phiz:escapeStringForJavaScript(processed)}"/>
                    addMessage('${escaped}');
                </c:if>
            </script>
            <div id="feedback">
                <tiles:insert definition="mainWorkspace" flush="false">
                      <tiles:put name="title"><bean:message bundle="mailBundle" key="list.title"/></tiles:put>
                      <tiles:put name="data">
                            <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
                            <c:set var="searchText" value="${fn:trim(form.filters.subject)}"/>
                            <c:set var="searchInputText" value="${searchText}"/>
                            <c:if test="${searchText == ''}">
                                <c:set var="searchInputText" value="введите тему письма"/>
                            </c:if>
                            <tiles:insert definition="formHeader" flush="false">
                                <tiles:put name="image" value="${imagePath}/MailTo_big.png"/>
                                <tiles:put name="description">
                                    <c:set var="mess" value="${phiz:getStaticMessage('com.rssl.iccs.CREATE_MAIL_FORM')}"/>
                                    <c:set var="processed" value="${phiz:processBBCode(mess)}"/>
                                    <h3>${processed}</h3>
                                </tiles:put>
                            </tiles:insert>
                            <div class="tabContainer">
                                <tiles:insert definition="paymentTabs" flush="false">
                                    <tiles:put name="count" value="3"/>
                                    <tiles:put name="tabItems">
                                        <tiles:insert definition="paymentTabItem" flush="false">
                                            <tiles:put name="position" value="first"/>
                                            <tiles:put name="active" value="true"/>
                                            <tiles:put name="title"><bean:message key="sentList.title" bundle="mailBundle"/></tiles:put>
                                            <tiles:put name="action" value="/private/mail/sentList.do"/>
                                        </tiles:insert>
                                        <tiles:insert definition="paymentTabItem" flush="false">
                                            <tiles:put name="active" value="false"/>
                                            <tiles:put name="title"><bean:message key="receivedList.title" bundle="mailBundle"/></tiles:put>
                                            <tiles:put name="action" value="/private/mail/list"/>
                                        </tiles:insert>
                                        <tiles:insert definition="paymentTabItem" flush="false">
                                            <tiles:put name="position" value="last"/>
                                            <tiles:put name="active" value="false"/>
                                            <tiles:put name="title"><bean:message key="removedList.title" bundle="mailBundle"/></tiles:put>
                                            <tiles:put name="action" value="/private/mail/archive.do"/>
                                        </tiles:insert>
                                    </tiles:put>
                                </tiles:insert>
                            <tiles:insert definition="addToFavouriteButton" flush="false">
                                <tiles:put name="formName"><bean:message bundle="mailBundle" key="sentList.title"/></tiles:put>
                                <tiles:put name="typeFormat">SENT_MAIL_LINK</tiles:put>
                            </tiles:insert>

                            <div class="clear"></div>
                            <c:set var="type" value="true"/>
                            <%@ include file="mailFilter.jsp"%>

                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid">
                                    <sl:collection id="listElement" model="simple-pagination" property="data" bundle="mailBundle">
                                        <sl:collectionParam id="selectType" value="checkbox"/>
                                        <sl:collectionParam id="selectName" value="selectedIds"/>
                                        <sl:collectionParam id="selectProperty" value="id"/>

                                        <c:set var="type" value="${listElement.type}"/>

                                        <sl:collectionItem title="label.number" property="num"/>

                                        <sl:collectionItem title="label.subject" styleClass="letterTitle">
                                            <sl:collectionItemParam id="action" value="/private/outgoing/mail/edit.do?id=${listElement.id}" condition="${phiz:impliesService('ClientMailManagment')}"/>
                                            <span><c:out value="${listElement.subject}"/></span>
                                        </sl:collectionItem>
                                        <sl:collectionItem title="label.sendDate">
                                            ${phiz:formatDateDependsOnSysDate(listElement.date, true, false)} 
                                        </sl:collectionItem>
                                        <sl:collectionItem title="label.status">
                                            <sl:collectionItemParam id="value" condition="${listElement.state=='NEW'}"><bean:message key="label.sent" bundle="mailBundle"/></sl:collectionItemParam>
                                            <sl:collectionItemParam id="value" condition="${listElement.state=='CLIENT_DRAFT'}"><bean:message key="label.draft" bundle="mailBundle"/></sl:collectionItemParam>
                                        </sl:collectionItem>
                                        <sl:collectionItem title="label.mailType">
                                             <c:out value="${type.description}"/>
                                        </sl:collectionItem>
                                    </sl:collection>
                                </tiles:put>
                                <tiles:put name="isEmpty" value="${empty form.data}"/>
                                <tiles:put name="emptyMessage">
                                    <bean:message bundle="mailBundle" key="message.empty"/>
                                </tiles:put>
                            </tiles:insert>

                            <div class="buttonsArea">
                            <div style="height:20px;">&nbsp;</div>

                            <tiles:insert definition="confirmationButton" flush="false" operation="RemoveClientMailOperation">
                                <tiles:put name="winId" value="confirmation"/>
                                <tiles:put name="title" value="Подтверждение удаления документа"/>
                                <tiles:put name="currentBundle"  value="commonBundle"/>
                                <tiles:put name="confirmCommandKey" value="button.remove"/>
                                <tiles:put name="validationFunction">checkSelection();</tiles:put>
                                <tiles:put name="message"><bean:message key="confirm.text" bundle="mailBundle"/></tiles:put>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false" operation="EditClientMailOperation">
                                <tiles:put name="commandKey" value="button.add"/>
                                <tiles:put name="commandHelpKey" value="button.add.help"/>
                                <tiles:put name="bundle" value="mailBundle"/>
                            </tiles:insert>
                                <div class="clear"></div>
                         </div>
                       </div>
                    </tiles:put>
                </tiles:insert>
            </div>
        </tiles:put>
    </tiles:insert>
</html:form>