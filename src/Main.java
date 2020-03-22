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
        alfa = Double.valueOf(args[2]);
        System.out.println("Prog t wynosi: "+t+", a stala uczenia:  "+alfa);

        int ileAtrybutow = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(train));
            String line[] = bufferedReader.readLine().split(",");
            Kwiat kwiat = new Kwiat(line);
            ileAtrybutow = kwiat.atrybuts.size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        List<Double> wektorWag = new ArrayList<>();
        for (int i =0; i <ileAtrybutow; i++){
            wektorWag.add(Math.random()*5*Math.pow(-1,(int)(Math.random()*2)));
        }
        System.out.println("Wektor wag: "+wektorWag);
        ////
        //zalożmy że Iris-setosa ma warosc 1, a Iris-versicolor wartosc 0
        ////


        System.out.println("ile iteracji treningowego ?");
        int iteracja = Integer.parseInt(scanner.nextLine());
        for (int k = 0;k< iteracja;k++){
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
                trainReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(test));
            System.out.println("Po zbiorze treningowym - nowy wektor wag: "+wektorWag+" ,a prog t:"+t);
            double licznikSetosa = 0;
            double poprawnoscSetosa=0;
            double licznikVersicolor=0;
            double poprawnoscVersicolor=0;
            String line;
            while ( (line=bufferedReader.readLine())!= null ){
                String split[] = line.split(",");
                Kwiat kwiat = new Kwiat(split);
                double net =0;
                for (int i =0; i<wektorWag.size();i++){
                    net+= wektorWag.get(i) * kwiat.atrybuts.get(i);
                }
                System.out.println("net: "+net+"  "+" wartosc t: "+t+"   "+kwiat.name);
                if (kwiat.name.equals("Iris-setosa")){
                    licznikSetosa++;
                    if (net>t){
                        poprawnoscSetosa++;
                    }
                }else if (kwiat.name.equals("Iris-versicolor")){
                    licznikVersicolor++;
                    if (net<t){
                        poprawnoscVersicolor++;
                    }
                }

            }
            double procentOgolny = 100*(poprawnoscSetosa+poprawnoscVersicolor)/(licznikSetosa+licznikVersicolor);
            double procentSetosa = (poprawnoscSetosa/licznikSetosa)*100;
            double procentVersicolor = 100*(poprawnoscVersicolor/licznikVersicolor);
            System.out.println("Poprawnosc wynosi: "+procentOgolny+"% w czym procent Irys-setos to: "+procentSetosa+" oraz Irys-versicolor: "+procentVersicolor+"%");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Chcesz podac wlasny irys? [tak/nie]");
        String  response = scanner.nextLine();
        if (response.equals("tak")){
            String kwiat[] = new String[wektorWag.size()+1];
            for (int i =0; i<wektorWag.size();i++){
                System.out.println("Podaj "+(i+1)+" wartosc wektora ["+(i+1)+"/4]");
                kwiat[i]  = scanner.nextLine();
            }
            kwiat[kwiat.length-1] = null; // nie wiadomo jaki irys
            Kwiat kwiat1 = new Kwiat(kwiat);
            double net = 0;
            for (int i =0; i<wektorWag.size();i++){
                net+= wektorWag.get(i) * kwiat1.atrybuts.get(i);
            }
            if (net<t){
                System.out.println("net: " +net+"a prog t: "+t +", wiec twoj irys najprawdopodobniej nalezy do Iris-versicolor");
            }else {
                System.out.println("net: " +net+"a prog t: "+t +", wiec twoj irys najprawdopodobniej nalezy do Iris-setosa");
            }
        }
        scanner.close();
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
        double t = staryProg+alfa*(oczekiwana-rzeczywsita)*(-1);
        return t;
    }
}
