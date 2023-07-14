<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<%--
	bundle              	- bundle в котором ищутся текст для commandKey и commandHelpKey
	commandKey          	- ключ команды
	commandHelpKey      	- ключ для описания команды
	commandTextKey      	- ключ для заголовка команды
	image              	 	- название файла с картинкой
	action              	- struts action куда осуществляется переход
--%>

<c:set var="acceptNotAvailableService" value="false"/>
<c:if test="${not empty notAvailableService}">
    <c:set var="acceptNotAvailableService" value="${phiz:impliesOperation(availableOperation, notAvailableService)}"/>
</c:if>
<c:if test="${phiz:impliesOperation(availableOperation, availableService) and not acceptNotAvailableService}">
    <c:choose>
        <c:when test="${not empty action}">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandHelpKey" value="${commandHelpKey}"/>
                <tiles:put name="commandTextKey" value="${commandTextKey}"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="image"  value="${image}"/>
                <tiles:put name="action" value="${action}"/>
            </tiles:insert>
        </c:when>
        <c:otherwise>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey"     value="${commandKey}"/>
                <tiles:put name="commandHelpKey" value="${commandHelpKey}"/>
                <tiles:put name="commandTextKey" value="${commandTextKey}"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="image"   value="${image}"/>
            </tiles:insert>
        </c:otherwise>
    </c:choose>
</c:if>