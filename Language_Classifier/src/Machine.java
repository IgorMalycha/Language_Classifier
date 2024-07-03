import java.io.File;
import java.security.KeyPair;
import java.util.*;

public class Machine {

    List<Language> trainingGroup = new ArrayList<>();
    Map<String , double[]> perceptronsWeights = new LinkedHashMap<>();
    double learningRate = 0.01;

    int n = 10000000;

    public Machine(String direcotry){
        File dir = new File(direcotry);
        File[] files = dir.listFiles();
        for(File file : files){
            double[] weight = new double[26];
            Arrays.fill(weight, 0.5);
            perceptronsWeights.put(file.getName(), weight);
            for(File file2 : file.listFiles()){
                trainingGroup.add(Language.makeLanguage(file2, file.getName()));
            }
        }
    }
    public void learn(){
        for(Map.Entry<String, double[]> lang : perceptronsWeights.entrySet()){
            int learned = 0;
            double error;
            int iter = 0;
            while(learned != trainingGroup.size() && iter <= n){
                for (Language language : trainingGroup) {
                    double d = 0;
                    double res = 0;
                    double y = 0;
                    for (int j = 0; j < language.lettersAtr.size(); j++) {
                        y += language.lettersAtr.get(j) * lang.getValue()[j];
                    }

                    d = language.language.equals(lang.getKey()) ? 1.0 : 0.0;
                    res = y >= 0 ? 1.0 : 0.0;

                    error = Math.abs((res - y));
                    //System.out.println(error);
                    if(error >= 0.00001){
                        double[] newWeights = new double[26];
                        for (int j = 0; j < language.lettersAtr.size(); j++) {
                            newWeights[j] = lang.getValue()[j] + (d - res) * learningRate * language.lettersAtr.get(j);
                        }
                        lang.setValue(newWeights);
                    }
                    if ((d - res) != 0) {
                        learned = 0;
                    }
                    learned++;
                    iter++;
                    if(learned == trainingGroup.size()){
                        break;
                    }
                }

            }
        }
    }
    public String assertLanguagesFromTestGroups(String direcotry){
        String res = "";
        File dir = new File(direcotry);
        File[] files = dir.listFiles();
        Map<String, Language> languagesTest = new LinkedHashMap<>();
        int howMany = 0;
        int right = 0;
        for(File file : files){
            for(File file1 : file.listFiles()){
                languagesTest.put(file.getName()+" "+file1.getName(), Language.makeLanguage(file1, file.getName()));
                howMany++;
            }
        }

        for(Map.Entry<String, Language> lang : languagesTest.entrySet()){
            //Map<String, Integer> witchIsActivated = new HashMap<>();
            Map<String, Double> witchIsActivated = new HashMap<>();
            for (Map.Entry<String, double[]> per : perceptronsWeights.entrySet()) {
                double y = 0;
                for (int j = 0; j < per.getValue().length; j++) {
                    y += per.getValue()[j] * lang.getValue().lettersAtr.get(j);
                }
                int klasa = y >= 0 ? 1 : 0;
                witchIsActivated.put(per.getKey(), y);//klasa
            }

            String jakiJSkl = "";

            double max = Integer.MIN_VALUE;
            for(Map.Entry<String, Double> activ : witchIsActivated.entrySet()) {
                if(max < activ.getValue()){
                    max = activ.getValue();
                    jakiJSkl = activ.getKey();
                }
            }
            if(max < 0){
                System.out.println("plik: " + lang.getKey() + " sklasyfikowany: Język inny niż podane w zbiorze treningowym");
                //return "Język inny niż podane w zbiorze treningowym";
            }else{
                System.out.println("plik: " + lang.getKey() + " sklasyfikowany: " + jakiJSkl);
                if(lang.getKey().split(" ")[0].equals(jakiJSkl)){
                    right++;
                }
                //return "Sklasyfikowano Jezyk: " + jakiJSkl;
            }

//            for(Map.Entry<String, Integer> activ : witchIsActivated.entrySet()){
//                if(activ.getValue() == 1){
//                    System.out.println("plik: " + lang.getKey() +
//                            " prawdziwy j: " + lang.getValue().language + " sklasyfikowany: " + activ.getKey());
//                }
//            }
        }

        System.out.println("poprawnie sklasyfikowano " + right + " z "+ howMany + " co daje: "+(right*100/howMany)+"% skuteczności");
        return res;
    }
    public String assertLanguageFromGivenText(String txt) {
        String res = "";
        Language langToAsses = Language.makeLanguage(txt);
        Map<String, Double> witchIsActivated = new HashMap<>();
        for (Map.Entry<String, double[]> per : perceptronsWeights.entrySet()) {
            double y = 0;
            for (int j = 0; j < per.getValue().length; j++) {
                y += per.getValue()[j] * langToAsses.lettersAtr.get(j);
            }
            int klasa = y >= 0 ? 1 : 0;
            witchIsActivated.put(per.getKey(), y);//klasa
        }
        String jakiJSkl = "";
        double max = Integer.MIN_VALUE;
        for (Map.Entry<String, Double> activ : witchIsActivated.entrySet()) {
            if (max < activ.getValue()) {
                max = activ.getValue();
                jakiJSkl = activ.getKey();
            }
        }
        if(max < 0){
            System.out.println("Język inny niż podane w zbiorze treningowym");
            return "Język inny niż podane w zbiorze treningowym";
        }else{
            System.out.println("Sklasyfikowano Jezyk: " + jakiJSkl);
            return "Sklasyfikowano Jezyk: " + jakiJSkl;
        }
    }
}
