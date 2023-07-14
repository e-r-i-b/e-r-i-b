<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"  prefix="phiz"%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>


<div class="warningMessage" id="warningMessages">
    <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="red"/>
        <tiles:put name="data">
            <div id="messageContainer" class="messageContainer">
                ¬ы не можете добавить новую цель, потому что ¬ы  создали максимальное количество целей.
            </div>
            <div class="clear"></div>
        </tiles:put>
    </tiles:insert>
</div>

<div class="tableArea pfpButtonsBlock">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancelAddTarget"/>
        <tiles:put name="commandHelpKey" value="button.cancelAddTarget.help"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="win.close('tooManyTargets');"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>    
</div>