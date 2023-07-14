<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<script type="text/javascript">
function printIMAPayments(event, mode, activity)
{
    var print = "${phiz:calculateActionURL(pageContext,"/private/ima/4replace")}".replace('4replace', activity);

    var periodType = getTypePeriod();
    var params  = '&mode=' + mode;

    if(mode != 'info')
    {
        params += "&filter(typeAbstract)=" + periodType;
    }

    if(mode != 'info' && periodType == 'period')
    {
        params += "&filter(fromAbstract)=" + getElementValue('filter(fromAbstract)');
        params += "&filter(toAbstract)="   + getElementValue('filter(toAbstract)');

        if(!validatePeriod('filter(fromAbstract)', 'filter(toAbstract)', 'c', 'по'))
        {
            return;
        }
    }

    openWindow(event, print + "?id=" + ${imAccountLink.id} + params, 'PrintIMAInfo',
              "resizable=1,menubar=1,toolbar=0,scrollbars=1");
}

function getTypePeriod()
{
    var typePeriods = document.getElementsByName('filter(typeAbstract)');
    var i = 0;
    while (typePeriods && typePeriods.length > 0)
    {
        var el = typePeriods[i++];
        if (el.checked)
        {
            return el.value;
        }
    }
    return '';
}
</script>