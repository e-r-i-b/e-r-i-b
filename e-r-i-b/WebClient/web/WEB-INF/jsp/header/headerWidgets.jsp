<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:form action="/private/async/header/widgets">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="definitions" value="${form.definitions}"/>
    <c:choose>
        <c:when test="${not empty definitions}">
            <c:set var="url" value="${phiz:calculateActionURL(pageContext,'/private/async/header/widgets.do')}"/>
            <script type="text/javascript">
                function addWidget(codename) {
                    dojo.xhrPost(
                    {
                        url: '${url}',
                        content: {
                            operation: 'button.add',
                            PAGE_TOKEN : $('input[name = PAGE_TOKEN]').val(),
                            codename: codename
                        },

                        load : dojo.hitch(this, function(response){
                            document.location.reload();
                        }),

                        error : dojo.hitch(this, function(){})
                    });
                }
            </script>
            <c:forEach var="definition" items="${definitions}" varStatus="varStatus">
                <div id="widgetDefinition${varStatus.count}" style="height:80px; width:250px; border:solid 1px;">
                    ${definition.id}&nbsp;${definition.codename}<br/>
                    ${definition.description}&nbsp;
                    <a href="#" onclick="addWidget('${definition.codename}');">добавить</a>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            Описания виджетов отсутствуют
        </c:otherwise>
    </c:choose>
</html:form>