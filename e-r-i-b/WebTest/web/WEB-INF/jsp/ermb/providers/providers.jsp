<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>

<html:html>
    <script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/XMLDisplay.js"></script>
    <head>
        <title>СМС Банк: получение поставщиков услуг мобильного банка</title>
    </head>
    
    <style type="text/css">
        .Utility {
            color: black;
        }
        .NodeName {
            font-weight: bold;
            color: #800080;
        }
        .AttributeName {
            font-weight: bold;
            color: black;
        }
        .AttributeValue {
            color: blue;
        }
        .NodeValue {
            color: black;
        }
        .Element {
            border-left-color: #00FF66;
            border-left-width: thin;
            border-left-style: solid;
            padding-top: 0px;
            margin-top: 10px;
        }
        .Clickable {
            font-weight: 900;
            font-size: large;
            color: #800080;
            cursor: pointer;
            vertical-align: middle;
        }
        #XMLtree {
            width: 800px;
        }
    </style>

    <body>
    <h1>СМС Банк: получение поставщиков услуг мобильного банка</h1>

    <html:form action="/mbProviders" show="true">
        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <html:submit property="operation" value="send"/>
        <br/>

        <c:if test="${form.submit}">
            <small>Tree:</small>
            <br/>

            <div id="XMLtree"></div>
            <small>Xml:</small>
            <br/>
            <%-- необходимо заменять & на спецсимвол, так как textarea по умолчанию все спецсимволы переводит в
                               символы и при попытки построить xml дерево экранированные символы для js выглядят как не
                               экранированные --%>
            <html:textarea property="serviceProvidersInfo" styleId="serviceProvidersInfo" cols="140"
                           rows="20"/><br/>
            <script type="text/javascript">
                window.onload = function (){LoadXMLString('XMLtree', trim(document.getElementById('serviceProvidersInfo').value));};
                function trim(string)
                {
                    return string.replace(/(^\s+)|(\s+$)/g, "");
                }
            </script>
        </c:if>
    </html:form>
    </body>
</html:html>