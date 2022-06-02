package isep.jfx;

public class Time {

    private int second;

    public Time(int second) {
        this.second = second;
    }

    public String getCurrentTime(){
        return ("Timer : "+second+".0s");
    }

    public void oneSecondPassed(){
        second++;
    }

    public int getSecond(){
       return second;
    }
}
