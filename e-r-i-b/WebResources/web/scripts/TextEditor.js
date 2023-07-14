//��������� ��������

//������� � ����� ��� ������
function changeURLText(url, oSelTxt)
{
    if (oSelTxt)
       oSelTxt = "[url=" + url + "]" + oSelTxt + "[/url]";
    else
        alert("�� ��� ������� ����� ��� ���������!");

    return oSelTxt;
}
//������� � ����� ��� ���������� � �������
function pasteClientInfo(tag, oSelTxt)
{
    oSelTxt = "["+tag+"/]";
    return oSelTxt;
}

//������� � ����� ��� �����
function changeTextColor(numColor, oSelTxt)
{
    if (oSelTxt)
        oSelTxt = "[color='"+numColor+"']"+oSelTxt+"[/color]"
    else
        alert("�� ��� ������� ����� ��� ���������!");

    return oSelTxt;
}

//������� � ����� ��� [chsign]
function changeGetText(chsign, oSelTxt)
{
    var strUrl = new String(oSelTxt);
    if (oSelTxt)
    {
       if (chsign!="l")
           oSelTxt = "["+chsign+"]"+oSelTxt+"[/"+chsign+"]";
       else
       {
          var substString = '[*]';
          srcStr=strUrl;
          // �������� ��� �������v� ������v �������� ������ �� ����
          srcStr = srcStr.replace( /<br.*?>|\r\n|\r|\n/igm, substString);
          // ������� ��� ����������
          var re = new RegExp( "(" + substString + ")\\1", 'g');
          while ( srcStr.match( substString+substString) ) srcStr = srcStr.replace( re, '$1');
          // ������� �������� ������� ������
          srcStr = srcStr.replace( new RegExp( substString + "$"), '');

          oSelTxt = "[list][*]"+ srcStr+"[/list]";
       }
    }
    else alert("�� ��� ������� ����� ��� ���������!");

    return oSelTxt;
}

/* ������� � ����� ����
 * @param chsign - ���
 * @param textAreaIds - ������ ���������, � ������� ��������� ������, ������������ �������
 *                      ���� ��������� ���������, �� ��� ����� �������� � �������� �������
 * @param params - ��� �������� ��� ������� � ���
 */
function changeSelText(chsign, textAreaIds, params)
{
    if (!textAreaIds.contains(document.activeElement.id))
    {
        alert("�� ���������� ������!");
        return;
    }

    var func = "";

    if (chsign=='a')
    {
        if (isEmpty(params))
        {
            alert("����������, ������� ����� ��������, � ������� ����� ����� ������� �� ������� ���������.");
            return;
        }
        func = 'changeURLText("' + params;
    }
    else if (chsign=='c')
        func = 'changeTextColor("' + params;
    else if (chsign=='client')
        func = 'pasteClientInfo("' + params;
    else
        func = 'changeGetText("' + chsign;

    if (navigator.appName.indexOf("Microsoft Internet Explorer") != -1)
    {
        var oSelTxt = document.selection.createRange();
        var parentElement = oSelTxt.parentElement();
        if (!textAreaIds.contains(parentElement.id) || parentElement.type != 'textarea' && parentElement.type != 'text')
        {
            alert("�� ��� ������� ����� ��� ���������!");
            return;
        }
        var textLength = getLineSize($(parentElement).val());
        var maxLength  = $(parentElement).attr("area-max-length");
        var newText    = eval(func + '", oSelTxt.text);');
        if(getLineSize(newText) + textLength-getLineSize(oSelTxt.text) > maxLength)
        {
            alert("������������ ����� ������ " + maxLength+" ��������.");
            return;
        }
        oSelTxt.text=newText;        
        document.selection.empty();
    }
    else
    {
        var changed = false;
        for (var i=0; i<textAreaIds.length; i++)
        {
            var el = document.getElementById(textAreaIds[i]);
            if (document.activeElement.id == textAreaIds[i] && typeof(el.selectionStart) == "number" && ( el.selectionStart != el.selectionEnd || el.selectionStart == el.selectionEnd && chsign == 'client' ))
            {
                var start = el.selectionStart;
                var end = el.selectionEnd;
                var maxLength = $(el).attr("area-max-length");
                eval('var rs = ' + func + '", el.value.substr(start,end-start));');
                var startText = el.value.substr(0, start);
                var endText =   el.value.substr(end);
                if(getLineSize(startText + rs + endText) > maxLength)
                {
                    alert("������������ ����� ������ " + maxLength+" ��������.");
                    return;
                }
                el.value = startText + rs + endText;
                el.setSelectionRange(end, end);
                changed = true;
            }
        }
        if (!changed)
            alert("�� ��� ������� ����� ��� ���������!");
    }
}

/* ������� � ����� ��� ������
 * @param textAreaIds - ������ ���������, � ������� ��������� ������, ������������ �������
 *                      ���� ��������� ���������, �� ��� ����� �������� � �������� �������
 */
function addHyperlink(textAreaIds)
{
    var url = prompt('�����������\n�������� ��������, ����� � ���������� ������� ������ �������� ���������, url ������ ���������� � http:// ��� https://.', '');
    if(url == null)
       return;

    changeSelText('a', textAreaIds, url);
}

var fileIndex = 1;
var cursorPosition = 0;

/* ������� � ����� �������
 * @param textAreaId - ������� ���� ���������� �������� ��������
 * @param fileName - �������� �����
 */
function fileSelected(textAreaId, fileName)
{
    var prevFile = document.getElementsByName('field(imageFile' + fileIndex + ')')[0];
    prevFile.style.display = 'none';

    fileIndex++;
    var div = document.getElementById('fileInput' + fileIndex);
    var elId = "'" + textAreaId + "'";
    div.innerHTML = '<input type="file" size="1" onchange="fileSelected(' + elId + ', this.value);" onmousedown="updateCursorPosition(' + elId + ');" name="field(imageFile' + fileIndex + ')" class="inputFileOpacity">' +
                    '<div id="fileInput' + (fileIndex+1) + '" class="fileInputContainer"></div>';
    updateMarginLeftFile('field(imageFile' + fileIndex + ')');
    var text = '[img]' + fileName.substring(fileName.lastIndexOf("\\")+1) + '[/img]';
    var element = document.getElementById(textAreaId);
    element.focus();

    if ((element.selectionStart) || (element.selectionStart=='0'))
    {
        element.value=element.value.substring(0, element.selectionStart) + text + element.value.substring(element.selectionEnd, element.value.length);
        element.setSelectionRange(cursorPosition, cursorPosition+text.length);
        return;
    }

    if ( document.selection ) // IE
    {
        var rng = element.createTextRange();
        rng.collapse();
        rng.moveStart("character", cursorPosition);
        rng.select();
        rng.text = text;
    }
}

/**
 * ���������� ������� ������� �������,
 * ������� ������ ���������� ��� ������� onmousedown
 * �������� ������ ����� (input type="file")
 * @param textAreaId - ������� ������ ����� ������� �������
 */
function updateCursorPosition(textAreaId)
{
    var element = document.getElementById(textAreaId);
    element.focus();

    if (element.selectionStart)
    {
        cursorPosition = element.selectionStart;
        return;
    }

    if (document.selection) // IE
    {
        var sel = document.selection.createRange();
        var clone=sel.duplicate();
        sel.collapse(true);
        clone.moveToElementText(element);
        clone.setEndPoint('EndToEnd', sel);
        cursorPosition = clone.text.length;
        return;
    }

    cursorPosition = 0;
}

/**
 * ��� IE.
 * �������� ������� �������� ������ ����� (input type="file")
 * @param elementName - ��� ��������
 */
function updateMarginLeftFile(elementName)
{
    if (navigator.appName.indexOf("Microsoft Internet Explorer") != -1)
    {
        var element = document.getElementsByName(elementName)[0];
        element.style.marginLeft = '-30px';
    }
}