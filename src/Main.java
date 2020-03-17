import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String train = args[0];
        String test = args[1];
        List<Kwiat> listKwiatow = new ArrayList();
        double t  =  Math.random()*5*Math.pow(-1,(int)(Math.random()*2));
        double alfa;
        Scanner scanner = new Scanner(System.in);
        System.out.println("podaj stalą uczenia alfa:");
        alfa = Double.valueOf(scanner.nextLine());
        System.out.println("Prog t wynosi: "+t+", a stala uczenia:  "+alfa);

        List<Double> wektorWag = new ArrayList<>();
        for (int i =0; i <4; i++){
            wektorWag.add(Math.random()*5*Math.pow(-1,(int)(Math.random()*2)));
        }
        System.out.println("Wektor wag: "+wektorWag);
        ////
        //zalożmy że Iris-setosa ma warosc 1, a Iris-versicolor wartosc 0
        ////


        try {
            BufferedReader trainReader = new BufferedReader(new FileReader(train));
            String line;
            while ((line=trainReader.readLine())!=null){
                String string[] = line.split(",");
                Kwiat kwiat = new Kwiat(string);
                listKwiatow.add(kwiat);
                double net = 0;
                for (int i =0; i<wektorWag.size();i++){
                    net+= wektorWag.get(i) * kwiat.atrybuts.get(i);
                }
                while ( (net<t && kwiat.name.equals("Iris-setosa"))  ||  (net>t && kwiat.name.equals("Iris-versicolor"))){
                    if (   net<t && kwiat.name.equals("Iris-setosa")  ){
                        wektorWag = nowyWektorWag(wektorWag,1,0,alfa,kwiat.atrybuts);
                        t = nowyProg(t,1,0,alfa);
                    }else if (net>t && kwiat.name.equals("Iris-versicolor") ){
                        wektorWag = nowyWektorWag(wektorWag,0,1,alfa,kwiat.atrybuts);
                        t = nowyProg(t,0,1,alfa);
                    }
                    for (int i =0; i<wektorWag.size();i++){
                        net+= wektorWag.get(i) * kwiat.atrybuts.get(i);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(test));
            String line;
            while ( (line=bufferedReader.readLine())!= null ){

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static List<Double> nowyWektorWag(List<Double> wektorWag, int oczekiwana,int rzeczywsita, double alfa, List<Double> wektorWejsciowy){
        List<Double> wektorWagPrim  = new ArrayList<>();
        for (int i =0; i<wektorWejsciowy.size();i++){
            double wartosc = 0;
            wartosc+= wektorWag.get(i)+(oczekiwana - rzeczywsita)*alfa*wektorWejsciowy.get(i);
            wektorWagPrim.add(wartosc);
        }
        return wektorWagPrim;
    }
    public static double nowyProg(double staryProg, int oczekiwana,int rzeczywsita, double alfa){
        double t = staryProg+alfa+(oczekiwana-rzeczywsita)*(-1);
        return t;
    }
}
