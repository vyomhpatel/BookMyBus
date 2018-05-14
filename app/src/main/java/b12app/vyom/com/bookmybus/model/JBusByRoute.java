package b12app.vyom.com.bookmybus.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class JBusByRoute {


    private List<BusinformationBean> businformation;



    public List<BusinformationBean> getBusinformation() {
        return businformation;
    }

    public void setBusinformation(List<BusinformationBean> businformation) {
        this.businformation = businformation;
    }

    public static class BusinformationBean implements Serializable {


        public BusinformationBean(String busid, String busregistrationno, String bustype, String busdeparturetime, String journyduration, String fare, String boardingtime, String dropingtime) {
            this.busid = busid;
            this.busregistrationno = busregistrationno;
            this.bustype = bustype;
            this.busdeparturetime = busdeparturetime;
            this.journyduration = journyduration;
            this.fare = fare;
            this.boardingtime = boardingtime;
            this.dropingtime = dropingtime;
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



        private String busid;
        private String busregistrationno;
        private String bustype;
        private String busdeparturetime;
        private String journyduration;
        private String fare;
        private String boardingtime;
        private String dropingtime;

        public static BusinformationBean objectFromData(String str) {

            return new Gson().fromJson(str, BusinformationBean.class);
        }

        public String getBusid() {
            return busid;
        }

        public void setBusid(String busid) {
            this.busid = busid;
        }

        public String getBusregistrationno() {
            return busregistrationno;
        }

        public void setBusregistrationno(String busregistrationno) {
            this.busregistrationno = busregistrationno;
        }

        public String getBustype() {
            return bustype;
        }

        public void setBustype(String bustype) {
            this.bustype = bustype;
        }

        public String getBusdeparturetime() {
            return busdeparturetime;
        }

        public void setBusdeparturetime(String busdeparturetime) {
            this.busdeparturetime = busdeparturetime;
        }

        public String getJournyduration() {
            return journyduration;
        }

        public void setJournyduration(String journyduration) {
            this.journyduration = journyduration;
        }

        public String getFare() {
            return fare;
        }

        public void setFare(String fare) {
            this.fare = fare;
        }

        public String getBoardingtime() {
            return boardingtime;
        }

        public void setBoardingtime(String boardingtime) {
            this.boardingtime = boardingtime;
        }

        public String getDropingtime() {
            return dropingtime;
        }

        public void setDropingtime(String dropingtime) {
            this.dropingtime = dropingtime;
        }
    }
}
