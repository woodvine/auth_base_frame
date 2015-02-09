//获取当前格式化后的时间
function getNowFormatDate()
{
   var day = new Date();

   var Year = 0;
   var Month = 0;
   var Day = 0;
var CurrentDate = "";
   //初始化时间
   //Year       = day.getYear();//有火狐下2008年显示108的bug
   Year       = day.getFullYear();//ie火狐下都可以
   Month      = day.getMonth()+1;
   Day        = day.getDate();
   
   CurrentDate += Year + "-";
   
   if (Month >= 10 )
   {
    CurrentDate += Month + "-";
   }
   else
   {
    CurrentDate += "0" + Month + "-";
   }
   if (Day >= 10 )
   {
    CurrentDate += Day ;
   }
   else
   {
    CurrentDate += "0" + Day ;
   }

   return CurrentDate;
}

//alert(getNowFormatDate());


//获取当前格式化后的时间
function formatDate(_date)
{
   var day = _date;

   var Year = 0;
   var Month = 0;
   var Day = 0;
var CurrentDate = "";
   //初始化时间
   //Year       = day.getYear();//有火狐下2008年显示108的bug
   Year       = day.getFullYear();//ie火狐下都可以
   Month      = day.getMonth()+1;
   Day        = day.getDate();
   
   CurrentDate += Year + "-";
   
   if (Month >= 10 )
   {
    CurrentDate += Month + "-";
   }
   else
   {
    CurrentDate += "0" + Month + "-";
   }
   if (Day >= 10 )
   {
    CurrentDate += Day ;
   }
   else
   {
    CurrentDate += "0" + Day ;
   }

   return CurrentDate;
}

function addDate(_day)
{
var a = new Date();   
var b = a.getDate();   
b = b + parseInt(_day);      
a.setDate(b);
return formatDate(a);
}
//alert(addDate(3));
//alert(addDate(-3));

function addDateHMS(_day)
{
var a = new Date();   
var b = a.getDate();   
b = b + parseInt(_day);      
a.setDate(b);
return getDayTime(a);
}

function subDate(_day)
{
var a = new Date();   
var b = a.getDate();   
b = b - parseInt(_day);      
a.setDate(b);
return formatDate(a);
}
//alert(subDate(3));

function getFormatWeek()
{
   var day=new Date();
   var weekday = day.getDay();
   var Week_EN="";
   switch(weekday)
   {
    case 1:
     Week_EN=" Mon.";
     break;
    case 2:
        Week_EN=" Tues.";
     break;
    case 3:
     Week_EN=" Wed.";
     break;
    case 4:
        Week_EN=" Thur.";
     break;
    case 5:
        Week_EN=" Fri.";
     break;
    case 6:
        Week_EN=" Sat.";
     break;
     Week_EN=" Sun.";
    case 7:
     break;
    default:
     break;
   }
   return Week_EN;
}
//当前时间YYYY-MM-DD HH:mm:ss
function getFormatDate()
{
   var day=new Date();

   var Year=0;
   var Month=0;
   var Day=0;
   var Hour = 0;
   var Minute = 0;
   var Second = 0;
   var CurrentDate="";

   //初始化时间
   Year       = day.getYear();
   Month      = day.getMonth()+1;
   Day        = day.getDate();
   Hour       = day.getHours();
   Minute     = day.getMinutes();
   Second     = day.getSeconds();

   CurrentDate = CurrentDate  + Year + "-";
   if (Month >= 10 )
   {
    CurrentDate = CurrentDate + Month + "-";
   }
   else
   {
    CurrentDate = CurrentDate + "0" + Month + "-";
   }
   if (Day >= 10 )
   {
    CurrentDate = CurrentDate + Day ;
   }
   else
   {
    CurrentDate = CurrentDate + "0" + Day ;
   }
   
   if(Hour >=10)
   {
    CurrentDate = CurrentDate + " "+ Hour ;
   }
   else
   {
    CurrentDate = CurrentDate+ " "+ "0" + Hour ;
   }
   if(Minute >=10)
   {
    CurrentDate = CurrentDate + ":" + Minute ;
   }
   else
   {
    CurrentDate = CurrentDate + ":0" + Minute ;
   }      
   if(Second>=10)
   {
    CurrentDate = CurrentDate + ":" + Second;
   }
   else
   {
    CurrentDate = CurrentDate + ":0" + Second;
   }
 

   return CurrentDate;
}


function getDayTime( _date,_num){
	   var day = _date;
	   var Year=0;
	   var Month=0;
	   var Day=0;
	   var Hour = 0;
	   var Minute = 0;
	   var Second = 0;
	   var CurrentDate="";

	   //初始化时间
	   Year       = day.getYear();
	   Month      = day.getMonth()+1;
	   Day        = day.getDate();
	   Hour       = day.getHours();
	   Minute     = day.getMinutes();
	   Second     = day.getSeconds();

	   CurrentDate = CurrentDate  + Year + "-";
	   if (Month >= 10 )
	   {
	    CurrentDate = CurrentDate + Month + "-";
	   }
	   else
	   {
	    CurrentDate = CurrentDate + "0" + Month + "-";
	   }
	   if (Day >= 10 )
	   {
	    CurrentDate = CurrentDate + Day ;
	   }
	   else
	   {
	    CurrentDate = CurrentDate + "0" + Day ;
	   }
	   
	   if(Hour >=10)
	   {
	    CurrentDate = CurrentDate + " "+ Hour ;
	   }
	   else
	   {
	    CurrentDate = CurrentDate+ " "+ "0" + Hour ;
	   }
	   if(Minute >=10)
	   {
	    CurrentDate = CurrentDate + ":" + Minute ;
	   }
	   else
	   {
	    CurrentDate = CurrentDate + ":0" + Minute ;
	   }      
	   if(Second>=10)
	   {
	    CurrentDate = CurrentDate + ":" + Second;
	   }
	   else
	   {
	    CurrentDate = CurrentDate + ":0" + Second;
	   }
	 

	   return CurrentDate;
	
}

function getSystemcnLongDate(){ 
	var mydate=new Date(); 
	
	var str = "" + mydate.getFullYear() + "年"; 
	str += (mydate.getMonth() + 1) + "月"; 
	str += mydate.getDate() + "日"; 
	str += " 星期"; 
	str += "天一二三四五六".charAt(mydate.getDay()); 
	return str;
		
}

function formatExtZoneTime( time){
	if(time == null || time ==''){
		return '';
	}
	return time.substr(0,19);
}
