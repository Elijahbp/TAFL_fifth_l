import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Reader {

    private static final String END_WORD = "-END-";

    private ArrayList<String> rules;

    public Reader() throws IOException {
        boolean l = true;
        rules = new ArrayList();
        String bufReader;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Правила:\n1) Первый нетерминал (и единственный) первого правила должен быть начальным символом!\n" +
                "2) Если вы закончили вводить, введите строку "+END_WORD+" )\n"+
                "3) Для использования -или- в правой части правил, устанавливайте символ | \n"+
                "4) Разделяйте левую и правую части правила символами -> (без проблелов)");
        System.out.println("Введите правила:");
        while (l){
            bufReader = reader.readLine();
            if(bufReader.equals(END_WORD)){
                l= false;
            }else {
                rules.add(bufReader);
            }
        }
        rules.forEach(System.out::println);
    }

    public ArrayList<String> getUnParserRules() {
        return rules;
    }

}
