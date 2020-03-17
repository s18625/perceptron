import java.util.ArrayList;
import java.util.List;

public class Kwiat {
    public List<Double> atrybuts ;
    String name;

    public Kwiat(String[] tabAtr){
        atrybuts = new ArrayList<>();
        for (int i=0;i<tabAtr.length-1;i++) {
            double date = Double.parseDouble(tabAtr[i]);
            atrybuts.add(date);
        }
        name = tabAtr[tabAtr.length-1];
    }
    public String toString(){
        return atrybuts +" "+name;
    }

}
