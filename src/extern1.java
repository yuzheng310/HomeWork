import com.sun.xml.internal.ws.util.xml.CDATA;
import org.w3c.dom.CDATASection;

import java.util.Scanner;

public class extern1 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Scanner key=new Scanner(System.in);
        System.out.println("请选择；1时间转化为时间戳 2时间戳转化为时间");
        int key1= key.nextInt();;
        if (key1==1){
            System.out.println("请按0000-00-00 00:00:00输入数据");
            String time=sc.nextLine();
           timeToDate(time);
        }
        if (key1==2){
            System.out.println("请输入时间戳");
            int date=sc.nextInt();
            dateToTime(date);
        }


    }
    public  static void timeToDate(String s){
        String A[]=s.split("-| |:");//分割年月日时分秒
        int C[] = new int[A.length];
        int Y,M,D,X,T;
        for (int i = 0; i < A.length; i++) {
            C[i] = Integer.parseInt(A[i]);//将年月日时分秒转化为int类型以便算数
        }
        int i = 0;
        int sec = 0;
        int days[]= {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};//判断具体月份的天数第一个为0是为0让其与月份对应
        for(i = 1970; i < C[0]; i++)//计算年
        {
            if(isleapYear(i))//判断是否是闰年
                sec += 366 * 24 * 60 * 60;
            else
                sec += 365 * 24 * 60 * 60;
        }

        /* 月计算 */
        for(i = 1; i < C[1]; i++)
        {
            sec += days[i] * 24 * 60 * 60;
            if(i == 2 && isleapYear(C[0]))
            {
                sec += 29*24 * 60 * 60;
            }
        }

        /* 天计算 */
        sec += (C[2] - 1) * 24 * 60 * 60;

        /* 时分秒计算 */
        sec += C[3] * 60 * 60 + C[4] * 60 + C[5]-8*60*60;//以北京时间为标准所有是1970 08:00:00
        System.out.println(sec);
    }
    public static void dateToTime(int date){
        int time =date;//得到当前系统时间
        int daymax=24*3600;
        int hourmax=3600;
        int year4=366+365+365+365;//以4年为一个周期以次来使尽可能少的去判断闰年
        int Days = time/daymax + 1;//data是从1970年以来的毫秒数，因此需要先得到天数
        int HH=time%daymax/hourmax+8;//不足一天的小时数，以北京时间为标准
        if (HH>=24){
            HH=HH-24;
            Days++;//输入的是不是以北京时间为标准的所有可能存在小时数大与24而日期不加1；
        }
        int MM=time%daymax%hourmax/60;
        int SS=time%daymax%hourmax%60;
        int Year4 = Days/year4;    //得到从1970年以来的周期的次数
        int cut = Days%year4;    //得到不足一个周期的天数
        int realYear = 1970 + Year4*4;//不算超出周期的年数
        int cutMonth = 0, cutDay = 0;
        boolean bLeapYear = false;
        if ( cut<365 )//一个周期内，第一年
        {//平年

        }
        else if ( cut<(365+365) )//平年
        {
            realYear += 1;
            cut -= 365;
        }
        else if ( cut<(365+365+365) )//平年
        {
            realYear += 2;
            cut -= (365+365);
        }
        else//润年
        {
            realYear += 3;
            cut -= (365+365+365);
            bLeapYear = true;
        }
        GetMonthAndDay(HH,MM,SS,realYear,cut, cutMonth, cutDay, bLeapYear);


    }
  public static   void GetMonthAndDay(int HH,int MM,int SS,int realyear, int cut, int cutMonth, int cutDay, boolean IsLeapYear)
    {

             int day[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        for ( int i=0; i<12; ++i )
        {
            int Temp;
            if (i==1&&IsLeapYear){
                 Temp = cut -29;
            }
            else  Temp = cut -day[i];
            if ( Temp<=0 )
            {
                cutMonth = i+1;
                if ( Temp == 0 )//表示刚好是这个月的最后一天，那么天数就是这个月的总天数了
                    cutDay = day[i];
                else
                    cutDay = cut;
                break;
            }
            cut = Temp;
        }
        System.out.println(realyear+"-"+cutMonth+"-"+cutDay+"  "+HH+":"+MM+":"+SS);
    }


public static boolean isleapYear(int y){
    if(y%4==0||y%100==0||y%400==0){
        return true;
    }
    else return false;
}
}