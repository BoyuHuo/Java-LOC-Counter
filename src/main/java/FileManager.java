import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    static public int javaFileNum = 0;
    static public int uniqueJavaFileNum = 0;
    static public int blankLineNum = 0;
    static public int commentsLineNum = 0;
    static public int lineOfLogicCode = 0;
    static public ArrayList<FileDetail> fileList = new ArrayList<FileDetail>();
    static String basePath = "/home/blue/Downloads/test";

    public FileManager(String path){
        this.basePath = path;
    }
    /**
     * 　　* 查找文件夹下所有符合java的文件
     * 　　*
     * 　　* @param dir 要查找的文件夹对象
     * 　　*
     */
    public static void findFile(File dir) throws IOException {
        File[] dirFiles = dir.listFiles();
        for (File temp : dirFiles) {
            if (!temp.isFile()) {
                findFile(temp);
            }

            if (temp.isFile() && temp.getAbsolutePath().endsWith(".java")) {
                javaFileNum++;
                uniqueJavaFileNum++;
                readFileContent(temp);
            }
        }
    }

    /**
     * 　　* @param file　要读取的文件对象
     * 　　* @return 返回文件的内容
     * 　　*
     */

    public static FileDetail readFileContent(File file) throws IOException {
        FileReader fr = new FileReader(file);
        FileDetail fd = new FileDetail();
        fd.setPath(file.getAbsolutePath());
        BufferedReader br = new BufferedReader(fr);
        StringBuffer sb = new StringBuffer();
        boolean isSectionComments = false;
        while (br.ready()) {
            String tempStr = br.readLine().trim();
            if (tempStr.length() < 1) {
                blankLineNum++;
            } else if (isSectionComments) {
                commentsLineNum++;
                if (tempStr.endsWith("*/")) {
                    isSectionComments = false;
                }
            } else if (tempStr.startsWith("//")) {
                commentsLineNum++;
            } else if (tempStr.startsWith("/*")) {
                commentsLineNum++;
                isSectionComments = true;
            } else {
                lineOfLogicCode++;
            }
        }
        return fd;
    }

/*
    public static void main(String[] args) {
        try {
            findFile(new File(basePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        printResult();

    }

 */

    public static void printResult() {
        System.out.println(javaFileNum + "-" + uniqueJavaFileNum + "-" + blankLineNum + "-" + commentsLineNum + "-" + lineOfLogicCode);
    }
}
