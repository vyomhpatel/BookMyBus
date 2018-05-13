package b12app.vyom.com.bookmybus.adapters;

import java.util.ArrayList;
import java.util.List;

import b12app.vyom.com.bookmybus.model.JBusByRoute;

public class MockData {
    public static List<JBusByRoute.BusinformationBean> Mock(){
        List<JBusByRoute.BusinformationBean> temp=new ArrayList<>();
        JBusByRoute.BusinformationBean mock1=new JBusByRoute.BusinformationBean("102",
                "TC102",
                "Sleeper,AC,LCD",
                "09:00:00 PM",
                "08:00:00 PM",
                "700",
                "07:15:00 AM",
                "04:00:00 AM");
        JBusByRoute.BusinformationBean mock2=new JBusByRoute.BusinformationBean("110",
                "TC110",
                "Sleeper",
                "21:00:00 PM",
                "17:00:00 PM",
                "900",
                "09:15:00 AM",
                "05:00:00 AM");
        temp.add(mock1);
        temp.add(mock2);
        return temp;
    }

    /**
     * busid : 102
     * busregistrationno : TC102
     * bustype : Sleeper,AC,LCD
     * busdeparturetime : 09:00:00 PM
     * journyduration : 08:00:00 PM
     * fare : 700
     * boardingtime : 07:15:00 AM
     * dropingtime : 04:00:00 AM
     */
}
