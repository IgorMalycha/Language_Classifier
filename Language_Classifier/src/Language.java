import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Language {

    String language;

    Map<Character, Double> letters;
    List<Double> lettersAtr;

    public Language(String language, Map<Character, Double> letters) {
        this.language = language;
        this.letters = letters;
        lettersAtr = new ArrayList<>();
        for(Map.Entry<Character, Double> ch : letters.entrySet()){
            lettersAtr.add(ch.getValue());
        }
    }

    public static Language makeLanguage(File file, String name){
        Map<Character, Double> letters = new LinkedHashMap<>();
        for(int i = 97; i <= 122; i++){
            letters.put((char)i, 0.0);
        }
        int allLetters = 0;
            try{
                FileReader fileReader = new FileReader(file);
                int character;
                while((character = fileReader.read()) != -1){
                    if((character >= 65 && character <= 90 ) || (character >= 97 && character <= 122)){
                        if(character >= 97){
                            character-=32;
                        }
                        char ch = (char)character;
                        //System.out.println(ch);
                        ch = Character.toLowerCase(ch);
                        //System.out.println(ch);
                        double num = letters.get(ch);
                        num++;
                        letters.put(ch, num);
                        allLetters++;
                    }
                }
            }catch (IOException e){
                System.out.println(e);
            }
        for(char key : letters.keySet()){
            double num = letters.get(key);
            letters.put(key, num/allLetters);
        }
        return new Language(name, letters);
    }

    public static Language makeLanguage(String txt){
        Map<Character, Double> letters = new LinkedHashMap<>();
        for(int i = 97; i <= 122; i++){
            letters.put((char)i, 0.0);
        }
        int allLetters = 0;
        try{
            StringReader reader = new StringReader(txt);
            int character;
            while((character = reader.read()) != -1){
                if((character >= 65 && character <= 90 ) || (character >= 97 && character <= 122)){
                    if(character >= 97){
                        character-=32;
                    }
                    char ch = (char)character;
                    //System.out.println(ch);
                    ch = Character.toLowerCase(ch);
                    //System.out.println(ch);
                    double num = letters.get(ch);
                    num++;
                    letters.put(ch, num);
                    allLetters++;
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
        for(char key : letters.keySet()){
            double num = letters.get(key);
            letters.put(key, num/allLetters);
        }
        return new Language("", letters);
    }

}
