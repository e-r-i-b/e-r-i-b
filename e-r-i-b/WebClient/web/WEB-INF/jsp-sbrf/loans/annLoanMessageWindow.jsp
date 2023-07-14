<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="annLoanMessage"/>
    <tiles:put name="data">
        <span class="title"> <h1>Оплата аннуитетного кредита</h1> </span>
        <div class="messageContainer">
            <bean:message key="label.annuity.desc" bundle="loansBundle"/>
        </div>
        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="viewType" value="simpleLink"/>
                <tiles:put name="commandTextKey" value="button.closeInfo"/>
                <tiles:put name="commandHelpKey" value="button.closeInfo"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="win.close('annLoanMessage')"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>