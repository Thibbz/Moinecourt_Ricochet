package isep.jfx;

/*
Classe permettant le fonctionnement du Timer

Attribut : second => le nombre de seconde écoulé

Fonction : getter, constructeur, oneSecondPassed => incrémente de 1 seconde
 */
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
