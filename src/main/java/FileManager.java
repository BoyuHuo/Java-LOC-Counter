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
        if(dir.isFile()){
            if (dir.getAbsolutePath().endsWith(".java")) {
                javaFileNum++;
                uniqueJavaFileNum++;
                readFileContent(dir);
            }
        } else {
            File[] dirFiles = dir.listFiles();
            for (File temp : dirFiles) {
                if (!temp.isFile()) {
                    findFile(temp);
                }

                if (temp.isFile() && temp.getAbsolutePath().endsWith(".java")) {
                    javaFileNum++;
                    uniqueJavaFileNum++;
                    fileList.add(readFileContent(temp));
                }
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
            fd.setContent(fd.getContent()+tempStr);
            if (tempStr.length() < 1) {
                blankLineNum++;
            } else if (isSectionComments) {
                commentsLineNum++;
                if (tempStr.endsWith("*/")) {
                    isSectionComments = false;

                }
            } else if (tempStr.startsWith("//")) {
                commentsLineNum++;
            }
            else if (tempStr.startsWith("/*")) {
                commentsLineNum++;
                if(!tempStr.endsWith("*/")){
                    isSectionComments = true;
                }
            }
            else {
                lineOfLogicCode++;
            }
        }
        fd.setSize(fd.getContent().length());
        return fd;
    }

    public static void checkUniqueness(){
        fileList.stream().forEach(file -> {
            file.repeatFlag = true;
            for(FileDetail f : fileList){
                if(f.repeatFlag){
                    continue;
                }
                if(file.getSize()==f.getSize()){  //optimation, if the size is different then pass, it never gonna be the same
                    if(file.getContent().equals(f.getContent())){
                        f.repeatFlag = true;
                        uniqueJavaFileNum--;
                        System.out.println(uniqueJavaFileNum);
                    }
                };
            }
        });
    }

    public static void printResult() {
        checkUniqueness();
        System.out.println(javaFileNum + "-" + uniqueJavaFileNum + "-" + blankLineNum + "-" + commentsLineNum + "-" + lineOfLogicCode);
    }
}
