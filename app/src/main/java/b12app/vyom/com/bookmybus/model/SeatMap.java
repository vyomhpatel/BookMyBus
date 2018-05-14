package b12app.vyom.com.bookmybus.model;

import com.google.gson.Gson;

import java.util.List;

public class SeatMap {

    private List<SeatinformationBean> seatinformation;

    public static SeatMap objectFromData(String str) {

        return new Gson().fromJson(str, SeatMap.class);
    }

    public List<SeatinformationBean> getSeatinformation() {
        return seatinformation;
    }

    public void setSeatinformation(List<SeatinformationBean> seatinformation) {
        this.seatinformation = seatinformation;
    }

    public static class SeatinformationBean {
        /**
         * id : 101
         * totalseat : 47
         * seatone : 1
         * seattwo : 1
         * seatthree : 1
         * seatfour : 1
         * seatfive : 1
         * seatsix : 1
         * seatseven : 1
         * seateight : 1
         * seatnine : 1
         * seatten : 1
         * seateleven : 0
         * seattwelve : 0
         * seatthirteen : 1
         * seatfourteen : 0
         * seatfifteen : 0
         * seatsixteen : 1
         * seatseventeen : 0
         * seateighteen : 1
         * seatnineteen : 0
         * seattwenty : 0
         * seattwentyone : 0
         * seattwentytwo : 0
         * seattwentythree : 0
         * seattwentyfour : 0
         * seattwentyfive : 0
         * seattwentysix : 0
         * seattwentyseven : 0
         * seattwentyeight : 0
         * seattwentynine : 0
         * seatthirty : 0
         * seatthirtyone : 0
         * seatthirtytwo : 0
         * seatthirtythree : 0
         * seatthirtyfour : 0
         * seatthirtyfive : 0
         * seatthirtysix : 0
         * seatthirtyseven : 0
         * seatthirtyeight : 0
         * seatthirtynine : 0
         * seatforty : 0
         * seatfortyone : 0
         * seatfortytwo : 0
         * seatfortythree : 0
         * seatfortyfour : 0
         * seatfourtyfive : 0
         * seatfortysix : 0
         * seatfourtyseven : 1
         */

        private String id;
        private String totalseat;
        private String seatone;
        private String seattwo;
        private String seatthree;
        private String seatfour;
        private String seatfive;
        private String seatsix;
        private String seatseven;
        private String seateight;
        private String seatnine;
        private String seatten;
        private String seateleven;
        private String seattwelve;
        private String seatthirteen;
        private String seatfourteen;
        private String seatfifteen;
        private String seatsixteen;
        private String seatseventeen;
        private String seateighteen;
        private String seatnineteen;
        private String seattwenty;
        private String seattwentyone;
        private String seattwentytwo;
        private String seattwentythree;
        private String seattwentyfour;
        private String seattwentyfive;
        private String seattwentysix;
        private String seattwentyseven;
        private String seattwentyeight;
        private String seattwentynine;
        private String seatthirty;
        private String seatthirtyone;
        private String seatthirtytwo;
        private String seatthirtythree;
        private String seatthirtyfour;
        private String seatthirtyfive;
        private String seatthirtysix;
        private String seatthirtyseven;
        private String seatthirtyeight;
        private String seatthirtynine;
        private String seatforty;
        private String seatfortyone;
        private String seatfortytwo;
        private String seatfortythree;
        private String seatfortyfour;
        private String seatfourtyfive;
        private String seatfortysix;
        private String seatfourtyseven;

        public static SeatinformationBean objectFromData(String str) {

            return new Gson().fromJson(str, SeatinformationBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTotalseat() {
            return totalseat;
        }

        public void setTotalseat(String totalseat) {
            this.totalseat = totalseat;
        }

        public String getSeatone() {
            return seatone;
        }

        public void setSeatone(String seatone) {
            this.seatone = seatone;
        }

        public String getSeattwo() {
            return seattwo;
        }

        public void setSeattwo(String seattwo) {
            this.seattwo = seattwo;
        }

        public String getSeatthree() {
            return seatthree;
        }

        public void setSeatthree(String seatthree) {
            this.seatthree = seatthree;
        }

        public String getSeatfour() {
            return seatfour;
        }

        public void setSeatfour(String seatfour) {
            this.seatfour = seatfour;
        }

        public String getSeatfive() {
            return seatfive;
        }

        public void setSeatfive(String seatfive) {
            this.seatfive = seatfive;
        }

        public String getSeatsix() {
            return seatsix;
        }

        public void setSeatsix(String seatsix) {
            this.seatsix = seatsix;
        }

        public String getSeatseven() {
            return seatseven;
        }

        public void setSeatseven(String seatseven) {
            this.seatseven = seatseven;
        }

        public String getSeateight() {
            return seateight;
        }

        public void setSeateight(String seateight) {
            this.seateight = seateight;
        }

        public String getSeatnine() {
            return seatnine;
        }

        public void setSeatnine(String seatnine) {
            this.seatnine = seatnine;
        }

        public String getSeatten() {
            return seatten;
        }

        public void setSeatten(String seatten) {
            this.seatten = seatten;
        }

        public String getSeateleven() {
            return seateleven;
        }

        public void setSeateleven(String seateleven) {
            this.seateleven = seateleven;
        }

        public String getSeattwelve() {
            return seattwelve;
        }

        public void setSeattwelve(String seattwelve) {
            this.seattwelve = seattwelve;
        }

        public String getSeatthirteen() {
            return seatthirteen;
        }

        public void setSeatthirteen(String seatthirteen) {
            this.seatthirteen = seatthirteen;
        }

        public String getSeatfourteen() {
            return seatfourteen;
        }

        public void setSeatfourteen(String seatfourteen) {
            this.seatfourteen = seatfourteen;
        }

        public String getSeatfifteen() {
            return seatfifteen;
        }

        public void setSeatfifteen(String seatfifteen) {
            this.seatfifteen = seatfifteen;
        }

        public String getSeatsixteen() {
            return seatsixteen;
        }

        public void setSeatsixteen(String seatsixteen) {
            this.seatsixteen = seatsixteen;
        }

        public String getSeatseventeen() {
            return seatseventeen;
        }

        public void setSeatseventeen(String seatseventeen) {
            this.seatseventeen = seatseventeen;
        }

        public String getSeateighteen() {
            return seateighteen;
        }

        public void setSeateighteen(String seateighteen) {
            this.seateighteen = seateighteen;
        }

        public String getSeatnineteen() {
            return seatnineteen;
        }

        public void setSeatnineteen(String seatnineteen) {
            this.seatnineteen = seatnineteen;
        }

        public String getSeattwenty() {
            return seattwenty;
        }

        public void setSeattwenty(String seattwenty) {
            this.seattwenty = seattwenty;
        }

        public String getSeattwentyone() {
            return seattwentyone;
        }

        public void setSeattwentyone(String seattwentyone) {
            this.seattwentyone = seattwentyone;
        }

        public String getSeattwentytwo() {
            return seattwentytwo;
        }

        public void setSeattwentytwo(String seattwentytwo) {
            this.seattwentytwo = seattwentytwo;
        }

        public String getSeattwentythree() {
            return seattwentythree;
        }

        public void setSeattwentythree(String seattwentythree) {
            this.seattwentythree = seattwentythree;
        }

        public String getSeattwentyfour() {
            return seattwentyfour;
        }

        public void setSeattwentyfour(String seattwentyfour) {
            this.seattwentyfour = seattwentyfour;
        }

        public String getSeattwentyfive() {
            return seattwentyfive;
        }

        public void setSeattwentyfive(String seattwentyfive) {
            this.seattwentyfive = seattwentyfive;
        }

        public String getSeattwentysix() {
            return seattwentysix;
        }

        public void setSeattwentysix(String seattwentysix) {
            this.seattwentysix = seattwentysix;
        }

        public String getSeattwentyseven() {
            return seattwentyseven;
        }

        public void setSeattwentyseven(String seattwentyseven) {
            this.seattwentyseven = seattwentyseven;
        }

        public String getSeattwentyeight() {
            return seattwentyeight;
        }

        public void setSeattwentyeight(String seattwentyeight) {
            this.seattwentyeight = seattwentyeight;
        }

        public String getSeattwentynine() {
            return seattwentynine;
        }

        public void setSeattwentynine(String seattwentynine) {
            this.seattwentynine = seattwentynine;
        }

        public String getSeatthirty() {
            return seatthirty;
        }

        public void setSeatthirty(String seatthirty) {
            this.seatthirty = seatthirty;
        }

        public String getSeatthirtyone() {
            return seatthirtyone;
        }

        public void setSeatthirtyone(String seatthirtyone) {
            this.seatthirtyone = seatthirtyone;
        }

        public String getSeatthirtytwo() {
            return seatthirtytwo;
        }

        public void setSeatthirtytwo(String seatthirtytwo) {
            this.seatthirtytwo = seatthirtytwo;
        }

        public String getSeatthirtythree() {
            return seatthirtythree;
        }

        public void setSeatthirtythree(String seatthirtythree) {
            this.seatthirtythree = seatthirtythree;
        }

        public String getSeatthirtyfour() {
            return seatthirtyfour;
        }

        public void setSeatthirtyfour(String seatthirtyfour) {
            this.seatthirtyfour = seatthirtyfour;
        }

        public String getSeatthirtyfive() {
            return seatthirtyfive;
        }

        public void setSeatthirtyfive(String seatthirtyfive) {
            this.seatthirtyfive = seatthirtyfive;
        }

        public String getSeatthirtysix() {
            return seatthirtysix;
        }

        public void setSeatthirtysix(String seatthirtysix) {
            this.seatthirtysix = seatthirtysix;
        }

        public String getSeatthirtyseven() {
            return seatthirtyseven;
        }

        public void setSeatthirtyseven(String seatthirtyseven) {
            this.seatthirtyseven = seatthirtyseven;
        }

        public String getSeatthirtyeight() {
            return seatthirtyeight;
        }

        public void setSeatthirtyeight(String seatthirtyeight) {
            this.seatthirtyeight = seatthirtyeight;
        }

        public String getSeatthirtynine() {
            return seatthirtynine;
        }

        public void setSeatthirtynine(String seatthirtynine) {
            this.seatthirtynine = seatthirtynine;
        }

        public String getSeatforty() {
            return seatforty;
        }

        public void setSeatforty(String seatforty) {
            this.seatforty = seatforty;
        }

        public String getSeatfortyone() {
            return seatfortyone;
        }

        public void setSeatfortyone(String seatfortyone) {
            this.seatfortyone = seatfortyone;
        }

        public String getSeatfortytwo() {
            return seatfortytwo;
        }

        public void setSeatfortytwo(String seatfortytwo) {
            this.seatfortytwo = seatfortytwo;
        }

        public String getSeatfortythree() {
            return seatfortythree;
        }

        public void setSeatfortythree(String seatfortythree) {
            this.seatfortythree = seatfortythree;
        }

        public String getSeatfortyfour() {
            return seatfortyfour;
        }

        public void setSeatfortyfour(String seatfortyfour) {
            this.seatfortyfour = seatfortyfour;
        }

        public String getSeatfourtyfive() {
            return seatfourtyfive;
        }

        public void setSeatfourtyfive(String seatfourtyfive) {
            this.seatfourtyfive = seatfourtyfive;
        }

        public String getSeatfortysix() {
            return seatfortysix;
        }

        public void setSeatfortysix(String seatfortysix) {
            this.seatfortysix = seatfortysix;
        }

        public String getSeatfourtyseven() {
            return seatfourtyseven;
        }

        public void setSeatfourtyseven(String seatfourtyseven) {
            this.seatfourtyseven = seatfourtyseven;
        }
    }
}
