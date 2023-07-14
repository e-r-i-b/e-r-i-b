<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

    <%@ include file="viewClaimData.jsp" %>

    <div class="printLeftBtn margin_top_60">
        <script type="text/javascript">
            function openPrintClaimInfo()
            {
                var url = "${phiz:calculateActionURL(pageContext,'/private/sberbankForEveryDay/printViewClaim')}?id=${form.id}";
                openWindow(null, url, "printViewClaim", "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=1024");
            }
        </script>

        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.print"/>
            <tiles:put name="commandHelpKey" value="button.print"/>
            <tiles:put name="bundle" value="sbnkdBundle"/>
            <tiles:put name="viewType" value="buttonGrey"/>
            <tiles:put name="onclick">openPrintClaimInfo();</tiles:put>
            <tiles:put name="image" value="print-check.gif"/>
            <tiles:put name="imageHover" value="print-check-hover.gif"/>
            <tiles:put name="imagePosition" value="left"/>
        </tiles:insert>
    </div>
    <div class="orderseparate orderseparate40"></div>
