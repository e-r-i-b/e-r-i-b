<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="chooseNode"/>
    <tiles:put name="data">
        <div class="confirmWindowTitle">
            <h2>
                Список блоков системы
            </h2>
        </div>
        <c:set var="currentNode" value="${phiz:getCurrentNode()}"/>
        <c:set var="nodeList" value="${phiz:getNodes()}"/>
        <c:forEach var="node" items="${nodeList}">
            <div>
                <c:choose>
                    <c:when test="${currentNode.id == node.id or not node.adminAvailable}">
                        <c:out value="${node.name}"/>
                    </c:when>
                    <c:otherwise>
                        <phiz:link action="/nodes/change/self" operationClass="SelfChangeNodeOperation">
                            <phiz:param name="nodeId" value="${node.id}"/>
                            <c:out value="${node.name}"/>
                        </phiz:link>
                    </c:otherwise>
                </c:choose>
            </div>
        </c:forEach>

        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey"    value="button.cancel"/>
                <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="viewType" value="simpleLink"/>
                <tiles:put name="onclick" value="win.close('chooseNode');"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
