var KEY_ENTER=13;
var KEY_0=48;
var KEY_9=57;
var NUMKEY_0=96;
var NUMKEY_9=105;
var KEY_BACKSPACE=8;
var KEY_DELETE=46;
var KEY_TAB=9;
var KEY_END=35;
var KEY_DOWN=40;

var INPUT_SYM='_';


function templateItem(template, regexp)
{
    this.regexp = new RegExp(regexp);

    //валидируем дату по указанному regexp выражению.
    this.validate = function (data)
    {
        return this.regexp.test(data);
    };

    this.toString = function()
    {
        return  template;
    }
}

function RequiredValidator()
{
    this.validate = function(data)
    {
        return data != undefined && trim(data) != '';
    };
};

/**
 *  Воладирует различные виды тад.
 *  Пример:	templateObj.MONTH.validate(’12.2000’);
 */
var  templateObj = {

       //MM.YYYY
       MONTH:          new templateItem ('mm.yyyy', '^([0][1-9]|[1][0-2])[.](\\d{4})'),
        //MM/YYYY
       ENGLISH_MONTH:  new templateItem ('mm/yyyy', '^([0][1-9]|[1][0-2])[/](\\d{4})'),
       //DD.MM.YYYY
       DATA:           new templateItem ('dd.mm.yyyy', '^([0][1-9]|[1-2][\\d]|[3][0-1])[.]([0][1-9]|[1][0-2])[.](\\d{4})'),
       //DD/MM/YYYY
       ENGLISH_DATE:   new templateItem ('dd/mm/yyyy', '^([0][1-9]|[1-2][\\d]|[3][0-1])[/]([0][1-9]|[1][0-2])[/](\\d{4})'),
       //hh:mm:ss
       TIME:           new templateItem ('hh:mm:ss', '^([0-1][\\d]|[2][0-3])[:]([0-5][\\d])[:]([0-5][\\d])'),
       //hh:mm
       SHORT_TIME:     new templateItem ('hh:mm', '^([0-1][\\d]|[2][0-3])[:]([0-5][\\d])'),
       //DD.MM.YYYY hh:mm
       DATE_TIME:      new templateItem ('dd.MM.yyyy hh:mm', '^([0][1-9]|[1-2][\\d]|[3][01])[.]([0][1-9]|[1][0-2])[.](\\d{4}) ([0-1][\\d]|[2][0-3])[:]([0-5][\\d])'),
       //DD.MM.YYYY hh:mm:ss
       DATE_FULLTIME:  new templateItem ('dd.MM.yyyy hh:mm:ss', '^([0][1-9]|[1-2][\\d]|[3][01])[.]([0][1-9]|[1][0-2])[.](\\d{4}) ([0-1][\\d]|[2][0-3])[:]([0-5][\\d])[:]([0-5][\\d])'),
       //+n(___)_______
        PHONE_NUMBER:  new templateItem ('+9(999)9999999', '^(\\+\\d)(\\(\\d{3}\\))\\d{7}$'),
       //+n__________
        PHONE_NUMBER_2:  new templateItem ('+99999999999', '^(\\+\\d)(\\d{3})\\d{7}$'),
        //+7(___)__-__-__
        PHONE_NUMBER_NEW: new templateItem ('+7 (999) 999-99-99', '^(\\+7)  \\((\\d{3})\\) (\\d{3}-\\d{2}-\\d{2})$'),
        //n__________
        PHONE_NUMBER_SHORT:  new templateItem ('99999999999', '^(\\d)(\\d{3})\\d{7}$'),
        //Только номер (с кодом оператора), 10 цифр.
        SIMPLE_NUMBER: new templateItem ('9999999999', '^\\d{3}\\d{7}$'),
        //Замаскированный номер (с кодом оператора), 10 цифр.
        SIMPLE_NUMBER_MASK: new templateItem ('999•••9999', '^\\d{3}\\•{3}\\d{4}$'),
        //(___)__-__-__
        PHONE_NUMBER_P2P: new templateItem ('(999) 999-99-99', '^\\((\\d{3})\\) (\\d{3}-\\d{2}-\\d{2})$'),
        //+7(___)__-__-__
        PHONE_NUMBER_P2P_NEW: new templateItem ('+9 (999) ppp-99-99', '^(\\+7) \\((\\d{3})\\) (\\d{3}-\\d{2}-\\d{2})$'),
        // Шаблон "7##########" ("7" + 10 цифр)
        PHONE_NUMBER_INTERNATIONAL: new templateItem('79999999999', '^(7)(\\d{3})\\d{7}$'),
        // __ __ ______
        PASSPORT_NUMBER_AND_SERIES_TEMPLATE: new templateItem ('99 99 999999', '^(\\d{2}) (\\d{2}) (\\d{6})$'),

        PASSPORT_ISSUE_BY_CODE_TEMPLATE: new templateItem('999-999', '^(\\d{3}-\\d{3})$'),

        CARD_TEMPLATE_P2P_NEW: new templateItem ('9999 9999 9999 9999 999', '^(\\d{4}) (\\d{4}) (\\d{4}) ((\\d{3}|\\d{4})|((\\d{3}|\\d{4}) (\\d{1}|\\d{2}|\\d{3}|\\d{4})))$'),
        CARD_TEMPLATE_SIMPLE: new templateItem ('9999999999999999999', '^(\\d{15})|(\\d{16})|(\\d{18})|(\\d{19})$'),

        YANDEX_WALLET_TEMPLATE_P2P_NEW: new templateItem ('999999999999999999999999999999999', '^(\\d{10,33})$'),

        // номер документа (иностранный паспорт, вид на жительство)
        FOREIGN_IDENTITY_CARD_NUMBER: new templateItem ('SSSSSSSSSSSSSSSSSSSSSSSSS', '^((\\d|[А-Яа-яA-Za-z]){0,25})$'),
        //номер документа(военнный билет солдата, удостоверение офицера)
        MILITARY_IDENTITY_CARD_NUMBER: new templateItem('ББ 9999990', '^([А-Я]{2}) (\\d{6}(\\d)?)$'),

        //required field
        REQUIRED:  new RequiredValidator()

};

var MONTH_TEMPLATE=templateObj.MONTH.toString();
var ENGLISH_MONTH_TEMPLATE=templateObj.ENGLISH_MONTH.toString();
var DATE_TEMPLATE=templateObj.DATA.toString();
var ENGLISH_DATE_TEMPLATE=templateObj.ENGLISH_DATE.toString();
var TIME_TEMPLATE=templateObj.TIME.toString();
var SHORT_TIME_TEMPLATE=templateObj.SHORT_TIME.toString();
var DATE_TIME_TEMPLATE=templateObj.DATE_TIME.toString();
var DATE_FULLTIME_TEMPLATE=templateObj.DATE_FULLTIME.toString();
var DATE_MASK = DATE_TEMPLATE.replace(/\d/gi,INPUT_SYM);
var PHONE_TEMPLATE_P2P = templateObj.PHONE_NUMBER_P2P.toString();
var PHONE_TEMPLATE_P2P_NEW = templateObj.PHONE_NUMBER_P2P_NEW.toString();
var PHONE_NUMBER_NEW = templateObj.PHONE_NUMBER_NEW.toString();
var CARD_TEMPLATE_P2P_NEW = templateObj.CARD_TEMPLATE_P2P_NEW.toString();
var YANDEX_WALLET_P2P_NEW = templateObj.YANDEX_WALLET_TEMPLATE_P2P_NEW.toString();
var PASSPORT_NUMBER_AND_SERIES_TEMPLATE  = templateObj.PASSPORT_NUMBER_AND_SERIES_TEMPLATE.toString();
var PASSPORT_ISSUE_BY_CODE_TEMPLATE  = templateObj.PASSPORT_ISSUE_BY_CODE_TEMPLATE.toString();
var FOREIGN_IDENTITY_CARD_NUMBER = templateObj.FOREIGN_IDENTITY_CARD_NUMBER.toString();
var MILITARY_IDENTITY_CARD_NUMBER = templateObj.MILITARY_IDENTITY_CARD_NUMBER.toString();

var PASSPORT_SERIES_TEMPLATE="____";
var PASSPORT_NUMBER_TEMPLATE="______";
var PHONE_NUMBER_TEMPLATE="_______";
var AREA_CODE_TEMPLATE="___";
// Шаблон для СНИЛС = Страховой Номер Индивидуального Лицевого Счёта
var SNILS_TEMPLATE="999-999-999 99";

/**
 *
 * @deprecated
 * Валидация даты по заданному формату
 * @param date - дата в String
 * @param template - шаблон формата __R__R____, где R - разделитель
 */
function validateDateTemplate(date, template)
{
    for (var i=0; i<date.length; i++)
    {
        var value = parseInt(date[i]);
        if (isNaN(value))
        {
            if(date[i]!=template[i]) return false;
        }
        else
        {
            if (template[i]!="_") return false;
        }
    }
    return true;
}

function getSelectionStart ( el, range)
{
  var rg0 = el.createTextRange();
  var i=rg0.compareEndPoints("StartToStart",range);
  var res=0;
  while ( i < 0 )
  {
    rg0.moveStart('character',1);
    res++;
    i = rg0.compareEndPoints("StartToStart",range);

  }
  return res;
}

function getSelectionEnd ( el, range)
{
  var rg0 = el.createTextRange();
  var i=rg0.compareEndPoints("EndToEnd",range);
  var res=0;
  while ( i > 0 )
  {
    rg0.moveEnd('character',-1);
    res++;
    i = rg0.compareEndPoints("EndToEnd",range);

  }
  return el.value.length-res;
}

function restoreTemplate (el,template)
{
  var val="",res="";
  var i,j, curr=0;
  for ( i=0; i < el.value.length;i++)
      if (isDigit(el.value.charCodeAt(i))) val+=el.value.charAt(i);


   for ( i=0, j=0; i < template.length;i++)
     if ( j < val.length  &&  (template.charAt(i) == INPUT_SYM ))
     {
       res += val.charAt(j);
       j++;
       curr = res.length;
      }
      else  res = res + template.charAt(i);

    el.value=res;
    return curr;
   }
function isDigit(key)
{
  return ((key >= KEY_0) && (key <= KEY_9)) ||
         ((key >= NUMKEY_0) && (key <= NUMKEY_9));
}
//только для цифр
function getKeyCode(key)
{
  if ((key >= KEY_0) && (key <= KEY_9)) return key;
  return key-48;
}


// Обработчик клавиш для поля значение которого вводится по шаблону
// Шаблон: цифры и любые разделители
function enterNumericTemplateFld(event, el,template ) {
   if (navigator.appName=="Netscape") return;
   if (navigator.appName=="Opera") return;
   var key = event.keyCode;
   var range;
   var i1,i2;

   if (el.value.length != template.length )
   {
       i1=restoreTemplate(el,template);
       range=document.selection.createRange();
       range.moveStart("character",i1);
       range.moveEnd("character",-1*(template.length-i1));
       range.select();
   }
   // клавиши требующие стандартной обработки
   if ( ((key >= KEY_END) && (key <= KEY_DOWN)) || (key==KEY_TAB) || (key==KEY_ENTER)) return;

   cancelEvent(event);
   range = document.selection.createRange();
   if (key ==KEY_BACKSPACE) {
     while (range.moveStart('character',-1)) {
         // пропускаем все не цифры
         if( !isDigit(range.text.charCodeAt(0))) {
            range.moveEnd('character',-1);
         }
         else break;
     }
     range.moveEnd('character',-1);
     key = KEY_DELETE;
   }
   if (key == KEY_DELETE) {
     if (range.text.length==0) if (!range.moveEnd('character')) return false;
     i1 = getSelectionStart ( el, range);
     i2 = getSelectionEnd ( el, range);
     range.text = template.substr(i1,i2-i1);
     range.move('character',-1*(i2-i1));
     range.select();
     return false;
     }

   if ( isDigit(key) )
   {
     if (range.moveEnd('character')) {
        if (range.text == INPUT_SYM || isDigit(range.text.charCodeAt(0))) range.text = String.fromCharCode(getKeyCode(key));
        while (range.moveEnd('character')) {
         if(range.text!= INPUT_SYM && !isDigit(range.text.charCodeAt(0))) range.text = range.text;
         else break;
        }
     }
   }
   return false;
 }

function setInputTemplate(name,template)
{
    if (navigator.appName=="Netscape") return;
    var el=getElement(name);
    try {
       restoreTemplate (el,template);
    }
    catch(e) {}
}

function clearInputTemplate(name,template)
{
    var el=getElement(name);
    var val="";
    try {
      if (el.value==template)
      {
         el.value="";
         return;
      }
      for ( i=0; i < el.value.length;i++)
      if (el.value.charAt(i) != INPUT_SYM ) val+=el.value.charAt(i);
      el.value=val;
    }
    catch(e) {}
 }

function onTabClick(event,next)
{
  if (navigator.appName=="Netscape") return;
  if (event.keyCode==KEY_TAB)    {
    getElement(next).focus();
    cancelEvent(event);
   }
}

