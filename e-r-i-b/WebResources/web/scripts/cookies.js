
//  ����� ������� ��� ������ � ������ �������� ����� JavaScript

//��������������� ������� ��� ��������� �������� �� �����
function trim(str) {
        return str.replace(/^\s+|\s+$/g,"");
}
// ��������������� ������� ��� ��������� ����������� ������� �������� ��� ���
function getExpDate(days, hours, minutes) { 
    var expDate = new Date(); 
    if (typeof days == "number" && typeof hours == "number" && 
        typeof minutes == "number") { 
        expDate.setDate(expDate.getDate() + parseInt(days)); 
        expDate.setHours(expDate.getHours() + parseInt(hours)); 
        expDate.setMinutes(expDate.getMinutes() + parseInt(minutes)); 
        return expDate.toUTCString(); 
    }
}

// ��������������� ������� ������ ������, � ������� �������� ����, ��� ��������� �������� �� �����
function getCookieVal(offset) { 
    var endstr = document.cookie.indexOf (";", offset); 
    if (endstr == -1) { 
        endstr = document.cookie.length; 
    } 
    return decodeURI(document.cookie.substring(offset, endstr)); 
}

// �������� �������� ���� �� �����
function getCookie(name) { 
    var arg = name + "="; 
    var alen = arg.length;
    var clen = document.cookie.length;
    var i = 0;
    while (i < clen) {
        var j = i + alen; 
        if (document.cookie.substring(i, j) == arg) { 
            return getCookieVal(j); 
        } 
        i = document.cookie.indexOf(" ", i) + 1; 
        if (i == 0) break; 
    }
    return "";
}

// ��������� ���� (������������ ��������� name � value)
// name - ��� cookie
// value - �������� cookie
// [expires] - ���� ��������� �������� cookie (�� ��������� - 10 ���)
// [path] - ����, ��� �������� cookie ������������� (�� ��������� - "/")
// [domain] - �����, ��� �������� cookie ������������� (�� ��������� - �����, � ������� �������� ���� �����������)
// [secure] - ���������� ��������, ������������ ��������� �� ���������� �������� �������� cookie �.�. ����� HTTPS
function setCookie(name, value, expires, path, domain, secure) {
    if (expires == undefined)
    {
        var expDate = new Date();
        expDate.setTime(expDate.getTime() + 1000 * 60 * 10);
        expires = expDate.toUTCString();
    }
    //������������ ���� �� "name" � "path", � ������ ������ ������ ������ �� "name"
    if (path == undefined)
    {
        path = "/";
    }

    document.cookie = name + "=" + encodeURI(value) +
        ((expires) ? "; expires=" + expires : "") +
        ((path) ? "; path=" + path : "") +
        ((domain) ? "; domain=" + domain : "") +
        ((secure) ? "; secure" : "");
}

// ������� ���� (������������ �������� name)
function deleteCookie(name) {
    var allcookies = document.cookie.split(";");
    for (var i = 0; i < allcookies.length; i++) {
        var cookie = allcookies[i];
        var eqPos = cookie.indexOf("=");
        var pname = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
        if (trim(pname) == name)
        {
            document.cookie = name + "=; expires=Thu, 01-Jan-1970 00:00:01 GMT";
        }
    }
}
