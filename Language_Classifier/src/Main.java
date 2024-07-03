import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Wybierz 1 by przetestwoać grupe testowa lub wybierz 2 by sklasyfikowac wpisany tekst");
        Scanner scanner = new Scanner(System.in);
        String dir = "TrainingGroup";
        Machine machine = new Machine(dir);
        machine.learn();
        System.out.println("nauczone");
        String dir2 = "TestGroup";
        while(true){
            int choice = scanner.nextInt();
            if(choice == 1){
                machine.assertLanguagesFromTestGroups(dir2);
            } else if (choice == 2) {
                System.out.println("wpisz tekst do sklasyfikowania");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                StringBuilder str = new StringBuilder();
                String line ;
                try{
                    while((line = br.readLine()) != null && !line.isEmpty()){
                        str.append(line);
                    }
                    machine.assertLanguageFromGivenText(str.toString());
                    //System.out.println(str);
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }
    }
}