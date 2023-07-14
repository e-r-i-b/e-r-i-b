<%@ page import="com.rssl.phizic.web.skins.SkinUrlValidator" %>
<%@ page import="com.rssl.phizic.web.util.SkinHelper" %>
<%--
  Created by IntelliJ IDEA.
  User: egorova
  Date: 31.03.2009
  Time: 15:26:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="dictionary">
	<tiles:put name="data" type="string">
        <%
            String globalUrl =  request.getParameter("globalUrl");
            SkinUrlValidator urlValidator = new SkinUrlValidator();
            String newGlobalUrl = "";

            if (urlValidator.validate(globalUrl))
            newGlobalUrl+="http://";

            newGlobalUrl = SkinHelper.updateSkinPath(newGlobalUrl + globalUrl);
        %>
        <c:set var="tempGlobalUrl"><%=newGlobalUrl%></c:set>
        <c:set var="globalImagePath" value="${tempGlobalUrl}/images"/>
        <tiles:insert definition="simpleFormRow" flush="false">
            <tiles:put name="title">
                Если вы видите логотип банка, значит путь к каталогу общих стилей корректный
            </tiles:put>
            <tiles:put name="data">
                <img src='${globalImagePath}/logoIB.gif' alt='Логотип банка'/>
            </tiles:put>
        </tiles:insert>
	</tiles:put>
</tiles:insert>