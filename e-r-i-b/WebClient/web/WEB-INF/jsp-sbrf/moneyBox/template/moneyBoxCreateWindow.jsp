<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript" src="${initParam.resourcesRealPath}/scripts/MoneyBox.js"></script>
<c:set var="moneyBoxCreateUrl" value="${phiz:calculateActionURL(pageContext, '/private/async/moneybox/claim/create')}"/>
<c:set var="moneyBoxWinId" value="moneyBoxWinDiv"/>
<tiles:insert definition="window" flush="false">
   <tiles:put name="id" value="${moneyBoxWinId}"/>
   <tiles:put name="loadAjaxUrl" value="${moneyBoxCreateUrl}"/>
   <tiles:put name="closeCallback" value="onCloseMoneyBoxCreateWindow"/>
</tiles:insert>