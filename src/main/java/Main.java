import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static List<String> execAsList(String... command) {
        List<String> result = new ArrayList<>();

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            final Process p = pb.start();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            p.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return result;
    }

    public static void main(String[] args) {
        // 1) Создать временную папку
        // 2) Добавить remote origin в этой папке
        // 3) Создать временный файл
        // 4) Залить diff одной ветки относительно master
        // 5) Закоммитить этот diff в репо

        

        System.out.println(execAsList("docker", "--help"));;
    }
}
