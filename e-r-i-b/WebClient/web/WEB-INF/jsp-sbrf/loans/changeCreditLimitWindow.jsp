<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="changeCreditLimitMess"/>
    <tiles:put name="data">
        <h1>¬ы действительно хотите отказатьс€ <br />от изменени€ лимита по кредитной карте?</h1>
        <div class="confirmDescText">
            »зменение лимита по ¬ашей кредитной карте будет отклонено.
        </div>
        <div class="limitsSeparate"></div>
        <div class="buttonsArea buttonsAreaRight">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.goToClaim"/>
                <tiles:put name="commandHelpKey" value="button.goToClaim"/>
                <tiles:put name="bundle" value="loanOfferBundle"/>
                <tiles:put name="onclick" value="win.close('changeCreditLimitMess')"/>
            </tiles:insert>
            <div class="cancelBtn">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="viewType" value="simpleLink"/>
                    <tiles:put name="commandKey" value="button.save"/>
                    <tiles:put name="commandTextKey" value="button.confirmCancel"/>
                    <tiles:put name="commandHelpKey" value="button.confirmCancel"/>
                    <tiles:put name="bundle" value="loanOfferBundle"/>
                </tiles:insert>
            </div>
        </div>
    </tiles:put>
</tiles:insert>