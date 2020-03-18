import java.util.ArrayList;
import java.util.List;

public class Kwiat {
    public List<Double> atrybuts ;
    String name = null;

    public Kwiat(String[] tabAtr){
        atrybuts = new ArrayList<>();
        for (int i=0;i<tabAtr.length-1;i++) {
            double dana = Double.parseDouble(tabAtr[i]);
            atrybuts.add(dana);
        }
        name = tabAtr[tabAtr.length-1];
    }
    public String toString(){
        return atrybuts +" "+name;
    }

}
