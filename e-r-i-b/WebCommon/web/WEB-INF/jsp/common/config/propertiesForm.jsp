<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:if test="${pageName == ''}"><c:set var="pageName" value="${pageName}"/> </c:if>
<c:choose>
    <c:when test="${form.pageMode == 'REPLICATE'}">
        <tiles:insert definition="dictionary">
            <tiles:put name="pageTitle" type="string" value="${pageTitle}"/>
            <tiles:put name="messagesPage" type="page" value="/WEB-INF/jsp/common/layout/tablePropertiesMessages.jsp"/>
            <tiles:put name="data" type="string">
                <tiles:insert definition="paymentForm" flush="false">
                    <tiles:put name="name" value="Номера блоков для репликации"/>
                    <tiles:put name="additionalStyle" value="${additionalStyle}"/>
                    <tiles:put name="alignTable" value="${formAlign}"/>
                    <tiles:put name="tableStyle" value="tableStyleHeight"/>
                    <tiles:put name="data" type="string">
                        <c:forEach var="element" items="${form.nodes}">
                            <tr>
                                <td class="Width120 alignRight">${element}</td>
                                <td><html:checkbox property="selectedNodes" value="${element}"/></td>
                            </tr>
                        </c:forEach>
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="paymentForm" flush="false">
                    <tiles:put name="name" value="${pageName}"/>
                    <tiles:put name="description" value="${pageDescription}"/>
                    <tiles:put name="additionalStyle" value="${additionalStyle}"/>
                    <tiles:put name="alignTable" value="${formAlign}"/>
                    <tiles:put name="tableStyle" value="tableStyleHeight"/>
                    <tiles:put name="data" type="string" value="${data}"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="menu" type="string">
                <tiles:insert definition="commandButton" flush="false" service="${replicateAccessService}" operation="${replicateAccessOperation}">
                    <tiles:put name="commandKey"     value="button.replicate"/>
                    <tiles:put name="commandHelpKey" value="button.replicate"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            var res = checkSelection("selectedNodes", "Выберите блоки для репликации");
                            if (!res)
                                return false;
                            return checkSelection("selectedProperties", "Выберите настройки для репликации")
                        }
                    </tiles:put>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false"  service="${replicateAccessService}" operation="${replicateAccessOperation}">
                    <tiles:put name="commandTextKey"     value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="onclick" value="window.close();"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </c:when>
    <c:when test="${form.pageMode == 'EDIT'}">
        <c:if test="${replicateAction!= ''}"> <c:set var="replicateAction" value="${replicateAction}&"/></c:if>
        <tiles:insert definition="${tilesDefinition}">
            <c:if test="${mainmenu != ''}"> <tiles:put name="mainmenu" type="string" value="${mainmenu}"/></c:if>
            <tiles:put name="submenu" type="string" value="${submenu}"/>
            <tiles:put name="pageTitle" value="${pageTitle}"/>
            <tiles:put name="data" type="string">
                <tiles:insert definition="paymentForm" flush="false">
                    <tiles:put name="name" value="${pageName}"/>
                    <tiles:put name="description" value="${pageDescription}"/>
                    <tiles:put name="additionalStyle" value="${additionalStyle}"/>
                    <tiles:put name="data" type="string" value="${data}"/>
                    <tiles:put name="buttons">${formButtons}</tiles:put>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="menu" type="string">
                ${menuButtons}
                <tiles:insert definition="clientButton" flush="false"  service="${replicateAccessService}" operation="${replicateAccessOperation}">
                    <tiles:put name="commandTextKey"     value="button.replicate"/>
                    <tiles:put name="commandHelpKey" value="button.replicate"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="onclick" value="window.open('?${replicateAction}replication=true${id}', 'ExternalSystem','resizable=1,menubar=0,toolbar=0,scrollbars=1');"/>
                    <tiles:put name="viewType"  value="blueBorder"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </c:when>
    <c:when test="${form.pageMode == 'VIEW_SYNC_INFO'}">
        <tiles:insert definition="dictionary">
            <tiles:put name="pageTitle" type="string" value=""/>
            <tiles:put name="messagesPage" type="page" value="/WEB-INF/jsp/common/layout/tablePropertiesMessages.jsp"/>
            <tiles:put name="data" type="string">
                <tiles:insert definition="paymentForm" flush="false">
                    <tiles:put name="name">
                        <bean:message key="label.syncInfo.title" bundle="commonBundle"/> ${form.replicationGUID}
                    </tiles:put>
                    <tiles:put name="additionalStyle" value="${additionalStyle}"/>
                    <tiles:put name="alignTable" value="${formAlign}"/>
                    <tiles:put name="tableStyle" value="tableStyleHeight"/>
                    <tiles:put name="data" type="string">
                        <input type="hidden" name="replicationGUID" value="${form.replicationGUID}"/>
                        <input type="hidden" name="date" value="${form.date}"/>
                        <input type="hidden" name="replicatedNodes" value="${form.replicatedNodes}"/>
                        <tr>
                            <div class="Width120 alignRight floatLeft"><bean:message key="label.syncInfo.nodeNumber" bundle="commonBundle"/></div>
                            <div class="Width120 alignRight floatLeft"><bean:message key="label.syncInfo.state" bundle="commonBundle"/></div>
                        </tr>
                        <c:forEach var="item" items="${form.syncInfo}">
                            <div class="clear"></div>
                            <tr>
                                <div class="Width120 alignRight floatLeft">${item[0]}</div>
                                <div class="Width120 alignRight floatLeft">${item[1].description}</div>
                            </tr>

                        </c:forEach>
                    </tiles:put>
                </tiles:insert>
                <div class="repSync-hint">
                    <bean:message key="label.syncInfo.hint" bundle="commonBundle"/>
                </div>
            </tiles:put>
            <tiles:put name="menu" type="string">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.refresh"/>
                    <tiles:put name="commandHelpKey" value="button.refresh"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                </tiles:insert>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"     value="button.ok"/>
                    <tiles:put name="commandHelpKey" value="button.ok"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="onclick" value="window.close()"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>
    </c:when>
</c:choose>