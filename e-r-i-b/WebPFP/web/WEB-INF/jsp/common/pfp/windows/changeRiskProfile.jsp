<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"  prefix="phiz"%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="changeUrl" value="${phiz:calculateActionURL(pageContext,'/async/change/personRiskProfile')}"/>
<div class="changeRiskProfileWindowTitle">
    Изменение распределения средств
</div>

<div id="editRiskProfile" style="width: 650px; height: 340px;">    
</div>
<script type="text/javascript">
    function redrawGraph(id)
    {
        var data = [];
        var i = 0;
        $('[id^=scrolValue'+id+']').each(function(){
            data[i] ={ percent: $(this).val() };            
            i++;
        });
        editPieDiagram.updateChartData(data);
        editPieDiagram.redraw();
    }

    function savePersonRiskProfile(id)
    {
        var params="";
        $('[id^=scrolValue'+id+']').each(function(){
            params+='&field('+$(this).attr("name")+')='+ $(this).val();            
        });
        ajaxQuery('id=${form.id}&operation=button.save.riskProfile'  + params, "${changeUrl}",
                        function(data){
                            $.extend(true, chartData, chartDataForWindow);
                            pieDiagram.updateChartData(chartData);
                            pieDiagram.redraw();
                            win.close('changeRiskProfile');
                        });
    }

</script>

<div class="editRiskProfileScroll">
    <c:set var="scrolID" value="1"/>
    <tiles:insert definition="multiplyScrollTemplate" flush="false">
        <tiles:put name="id" value="${scrolID}"/>
        <tiles:put name="minValue" value="0"/>
        <tiles:put name="maxValue" value="100"/>
        <tiles:put name="unit" value="%"/>
        <tiles:put name="dataName" value="personRiskProfile.productsWeights"/>
        <tiles:put name="callback" value="redrawGraph('${scrolID}');"/>
    </tiles:insert>
</div>

<div class="buttonsArea">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="win.close('changeRiskProfile');"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.save.riskProfile"/>
        <tiles:put name="commandHelpKey" value="button.save.riskProfile.help"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="savePersonRiskProfile('${scrolID}');"/>
    </tiles:insert>
</div>