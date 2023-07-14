<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/private/fund/group/list">
    <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
    <tiles:insert definition="iphone">
        <tiles:put name="status" value="${form.status}"/>
        <tiles:put name="data">
            <groups>
                <c:forEach items="${form.list}" var="item">
                    <group>
                        <id>
                            ${item.id}
                        </id>
                        <name>
                            ${item.name}
                        </name>
                    </group>
                </c:forEach>
            </groups>
        </tiles:put>
    </tiles:insert>
</html:form>
