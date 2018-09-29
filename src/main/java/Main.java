import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {

    public static List<String> execAsList(File workingDit, String... command) {
        List<String> result = new ArrayList<>();

        try {
            ProcessBuilder pb = new ProcessBuilder(command);
            if (workingDit != null) {
                pb.directory(workingDit);
            }
            pb.redirectErrorStream(true);
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

    /**
     * git diff master Branch1 > ../patchfile
     * git checkout Branch2
     * git apply ../patchfile
     */
    public static List<String> getDiff(String baseBranchName, String branchName) {
        return execAsList(null, "git", "diff", baseBranchName, branchName);
    }

    public static void main(String[] args) throws IOException {
        // 1) Создать временную папку
        // 2) Добавить remote origin в этой папке
        // 3) Создать временный файл
        // 4) Залить diff одной ветки относительно master
        // 5) Закоммитить этот diff в репо

        String newBranchName = UUID.randomUUID().toString();

        Path tempDir = Files.createTempDirectory("few");
        String fileName = UUID.randomUUID().toString() + ".diff";
        Path fullPath = tempDir.resolve(fileName);

        List<String> diff = getDiff("master", "feature/PLAN-2");

        Files.write(fullPath, diff, Charset.forName("UTF-8"));

//        System.out.println(execAsList(tempDir.toFile(), "git", "init"));
//
//        System.out.println(execAsList(tempDir.toFile(), "git", "add", fileName));
//
//        System.out.println(execAsList(tempDir.toFile(), "git", "commit",
//                "--message", "add new file"));
//
//        System.out.println(execAsList(tempDir.toFile(), "git", "push",
//                "origin", "master", "--force"));

        System.out.println(execAsList(null,"git", "push",
                "origin", "--delete", "feature/PLAN-2"));

//        System.out.println(execAsList("docker", "--help"));;
    }
}
