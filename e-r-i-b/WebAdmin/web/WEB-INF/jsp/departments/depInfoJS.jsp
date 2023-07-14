<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
(function(){
    var depInfo = new Object;
    depInfo["id"] = "${currentDep.id}";
    depInfo["name"] = "${currentDep.name}";
    <c:set var="parent" value="${phiz:getTB(currentDep)}"/>  <%-- в расчете на то, что репликация только осб и тб --%>
    <c:if test="${parent != null}">
        depInfo["parent"] = <tiles:insert page="depInfoJS.jsp" flush="false">
                                <tiles:put name="currentDep" beanName="parent"/>
                            </tiles:insert>
    </c:if>
    return depInfo
})();