/**********************一。验证类*****************************/
//对象是否 存在
function isObj(str)
{
if(str==null||typeof(str)=='undefined')
return false;
return true;
}
//去除字符串中的空格
function strTrim(str)
{
if(!isObj(str))
return 'undefined';
str=str.replace(/^\s+|\s+$/g,'');
return str;
}
/**********************1数字验 证******************************/
//1。1整数
//整数或者为空
function isIntOrNull(str){
if(!isObj(str))//判断对象是否存在
return 'undefined';
return isNull(str)||isInt(str);
}
//必需是整数
function isInt(str){
var reg = /^(-|\+)?\d+$/ ;
return reg.test(str);
}
//1.2 小数
//小数或者为空
function isFloatOrNull(str){
if(!isObj(str))//判 断对象是否存在
return 'undefined';
if(isInt(str))
return true;
return isNull(str)||isFloat(str);
}
//必需是小数
function isFloat(str){
if(isInt(str))
return true;
var reg = /^(-|\+)?\d+\.\d*$/;
return reg.test(str);
}
//1.3 数字大小判断
//数i不能大于数y
function iMinY(i , y){
if(!isObj(i)||!isObj(y))// 判断对象是否存在
return 'undefined';
if(!(isFloat(i)&&isFloat(y)))
return '比较的必须是数字类型'
if(i<=y)
return true;
return false;
}
// 数i不能小于数y
function iMaxY(i , y){
if(!isObj(i)||!isObj(y))//判断对象是 否存在
return 'undefined';
if(!(isFloat(i)&&isFloat(y)))
return '比较的必须是数字类型'
if(i>=y)
return true;
return false;
}
/**********************1 数字验证******************************/ 
/**********************2时间类验证******************************/
//2.1 短时间，形如 (13:04:06) 
function shortTimeCheck(str)
{
if(!isObj(str))// 判断对象是否存在
return 'undefined';
var a = str.match(/^(\d{1,2})(:)?(\d{1,2})\2(\d{1,2})$/); 
//var a = str.match(/^\d{1,2}:\d{1,2}:\d{1,2}$/); 
if (a == null)
{
alert(" 输入的参数不是时间格式");
return false;
} 
if (a[1]>24 || a[3]>60 || a[4]>60) 
{ 
alert("时间格式不对"); 
return false 
} 
return true; 
}
//2.2 短日期，形如 (2003-12-05) 
function shorDateCheck(str)
{
var r = str.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);   
if(r==null)
return false;   
var d= new Date(r[1], r[3]-1, r[4]);   
return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]); 
}
//2.3 长时间，形如 (2003-12-05 13:04:06) 
function longDateCheck(str)
{
var reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;   
var r = str.match(reg);   
if(r==null)
return false;   
var d= new Date(r[1], r[3]-1,r[4],r[5],r[6],r[7]);   
return (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]&&d.getHours()==r[5]&&d.getMinutes()==r[6]&&d.getSeconds()==r[7]); 
}
//2.4 只有年和月。形如(2003-05,或者2003-5) 
//2.5 只有小时和分钟,形如(12:03) 
/**********************2时间类验证******************************/

/**********************3表单类验证******************************/
//3.1 所有的表单的值都不能为空,对象内容是否为空
function isNull(str)
{
if(!isObj(str))
return 'undefined';
//alert("*"+str+"*");
str=strTrim(str);
//alert("*"+str+"*");
if(str.length>0)
return false;
return true;
}
//3.2 多行文本框的值不能为空。 
//3.3 多行文本框的值不能超过sMaxStrleng 
function sMaxStrleng(str,len)
{
if(!isObj(str))
return 'undefined';
str=strTrim(str);
if(str.length>len)
return false;
return true;
}
//3.4 多行文本框的值不能少于sMixStrleng 
function sMixStrleng(str,len)
{
if(!isObj(str))
return 'undefined';
str=strTrim(str);
if(str.length<len)
return false;
return true;
}
//3.5 判断单选框是否选择。 
//3.6 判断复选框是否选择. 
//3.7 复选框的全选，多选，全不选，反选 
//3.8 文件上传过程中判断文件类型 
function upLoadFileType(str)
{
return str.match(/^(.*)(\.)(.{1,8})$/)[3];
}
/**********************3表单类 验证******************************/

/**********************4字符类验证******************************/

//4.1 判断字符全部由a-Z或者是A-Z的字字母组成 
function isLetter(str)
{
if(!isObj(str))
return 'undefined';
str=strTrim(str);
var reg = /^[a-zA-Z]+$/g;
return reg.test(str)
}

//4.2 判断字符由字母和数字组成。 
function isLetterOrNum(str)
{
if(!isObj(str))
return 'undefined';
str=strTrim(str);
var reg = /^[0-9a-zA-Z]+$/g;
return reg.test(str)
}

//4.3 判断字符由字母和数字，下划线,点号组成.且开头的只能是下划线和字母 
function isLegalityName(str)
{
if(!isObj(str))
return 'undefined';
str=strTrim(str);
var reg = /^([a-zA-z_]{1})([A-Za-z0-9_\.]*)$/g;
return reg.test(str)
}
// /^([a-zA-z_]{1})([\w]*)$/g.test(str) 
//4.4 字符串替换函数.Replace(); 
/**********************4 字符类验证******************************/

/**********************5浏览器类******************************/
//5.1 判断浏览器的类型 
function getIEType()
{
return window.navigator.appName; 
}
//5.2 判断ie的版本 
function getIEVersion()
{
return window.navigator.appVersion;
}
//5.3 判断客户端的分辨率 
function getResolvingPower()
{
return window.screen.height+"*"+window.screen.width; 
}
/**********************5 浏览器类******************************/

/**********************6综合类******************************/
//6.1 email的判断。 
function isMail(mail) 
{ 
return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail)); 
} 
//6.2 手机号码的验证 
function isCellPhoneNum(num)
{
if(isNaN(num)) 
{
alert("输入的不是数字！");
return false;
} 
var len = num.length; 
if(len!=11)
{
return false;
}
return true;
}
//6.3 身份证的验证 
function isIdCardNo(num) 
{ 
/*
if(isNaN(num)) 
{
alert("输入的不是数字！");
return false;
}
 */
var len = num.length;   
if (len == 15) 
re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/); 
else if(len == 18) 
re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d)$/); 
else 
{
alert("输入的身份证位数不对！"); 
return false;
} 
var a = num.match(re); 
if (a != null) 
{ 
if (len==15) 
{ 
var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]); 
var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
} 
else 
{ 
var D = new Date(a[3]+"/"+a[4]+"/"+a[5]); 
var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5]; 
} 
if (!B) 
{
alert("输入的身份证号 "+ a[0] +" 里出生日期不对！"); 
return false;
} 
} 
return true; 
} 

//身份证深度验证
function ValidateCardId(idcard) {
    var Errors = new Array(
"",
"身份证号码位数不对!",
"身份证号码出生日期超出范围或含有非法字符!",
"身份证号码校验错误!",
"身份证地区非法!"
);
    var area = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外" }
    var idcard, Y, JYM;
    var S, M;
    var idcard_array = new Array();
    idcard_array = idcard.split("");
    //地区检验
    if (area[parseInt(idcard.substr(0, 2))] == null) return Errors[4];

    //身份号码位数及格式检验
    switch (idcard.length) {
        case 15:
            if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/; //测试出生日期的合法性
            } else {
                ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/; //测试出生日期的合法性
            }
            if (ereg.test(idcard))
                return Errors[0];
            else
                return Errors[2];
            break;
        case 18:
            //18位身份号码检测
            //出生日期的合法性检查
            //闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
            //平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
            if (parseInt(idcard.substr(6, 4)) % 400 == 0 || (parseInt(idcard.substr(6, 4)) % 100 != 0 && parseInt(idcard.substr(6, 4)) % 4 == 0)) {
                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/; //闰年出生日期的合法性正则表达式
            } else {
                ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/; //平年出生日期的合法性正则表达式
            }
            if (ereg.test(idcard)) {//测试出生日期的合法性
                //计算校验位
                S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
+ parseInt(idcard_array[7]) * 1
+ parseInt(idcard_array[8]) * 6
+ parseInt(idcard_array[9]) * 3;
                Y = S % 11;
                M = "F";
                JYM = "10X98765432";
                M = JYM.substr(Y, 1); //判断校验位
                if (M == idcard_array[17])
                    return Errors[0]; //检测ID的校验位
                else
                    return Errors[3];
            }
            else return Errors[2];
            break;
        default:
            return Errors[1];
            break;
    }
} 

//6.4电话号码的验 证 
function PhoneCheck(str){ 
var reg=/(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/ 
alert(reg.test(str)); 
} 
//6.5验证IP地址 
function isip(s){ 
var check=function(v){try{return (v<=255 && v>=0)}catch(x){return false}}; 
var re=s.split(".") 
return (re.length==4)?(check(re[0]) && check(re[1]) && check(re[2]) && check(re[3])):false 
} 

/**********************6综合类******************************/
//回车键换为 tab 
function enterToTab() 
{ 
if(event.srcElement.type != 'button' && event.srcElement.type != 'textarea' 
&& event.keyCode == 13) 
{ 
event.keyCode = 9; 
} 
} 
//具有在输入非数字字符不回显的效果，即对非数字字符的输入不作反应。 
function numbersonly(field,event){ 
var key,keychar; 
if(window.event){ 
key = window.event.keyCode; 
} 
else if (event){ 
key = event.which; 
} 
else{ 
return true ;
} 
keychar = String.fromCharCode(key); 
if((key == null)||(key == 0)||(key == 8)||(key == 9)||(key == 13)||(key == 27)){ 
return true; 
} 
else if(("0123456789.").indexOf(keychar)>-1){ 
window.status = ""; 
return true; 
} 
else { 
window.status = "Field excepts numbers only"; 
return false; 
} 
} 
//
function txtOnKeyPress()
{
var code=String.fromCharCode(window.event.keyCode)+'';
alert(code);
var temp =eval("isInt('"+code+"')");
window.event.returnValue = temp;
}
