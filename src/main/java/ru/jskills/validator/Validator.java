package ru.jskills.validator;

//import com.sun.tools.javac.*;

import ru.jskills.utils.FileWorker;

import javax.tools.*;
import java.io.*;
        import java.net.URI;
        import java.nio.charset.Charset;
        import java.util.*;

/**
 * Created by safin.v on 08.11.2016.
 */
public class Validator implements Runnable{
    private Process process;
    private PrintWriter pw;
    private volatile boolean redy=true;
    private String user;
    private String packageName = "";
    private String packagePath = "";
    private String code;
    private String mainClassName;
    private String projectPath;



    class MyDiagnosticListener implements DiagnosticListener {
        public void report(Diagnostic diagnostic) {
            System.out.println("Code->" +  diagnostic.getCode());
            System.out.println("Column Number->" + diagnostic.getColumnNumber());
            System.out.println("End Position->" + diagnostic.getEndPosition());
            System.out.println("Kind->" + diagnostic.getKind());
            System.out.println("Line Number->" + diagnostic.getLineNumber());
            System.out.println("Message->"+ diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("Position->" + diagnostic.getPosition());
            System.out.println("Source" + diagnostic.getSource());
            System.out.println("Start Position->" + diagnostic.getStartPosition());
            System.out.println("\n");

        }
    }



    class TaskGobbler extends Thread
    {
        InputStream is;
        String type;

        TaskGobbler(InputStream is, String type)
        {
            this.is = is;
            this.type = type;
        }

        public void run()
        {
            try
            {

                InputStreamReader isr = new InputStreamReader(is, Charset.forName("windows-1251"));
                BufferedReader br = new BufferedReader(isr);
                String line=null;
                while ( (line = br.readLine()) != null)
                {
                    while(!isRedy())
                        Thread.sleep(10);

                    onOutPrint(line);

                }

            } catch (IOException ioe)
            {
                ioe.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean compileJavaxCompiller(String fileName){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector diagnosticsCollector = new DiagnosticCollector();
        // MyDiagnosticListener listener = new MyDiagnosticListener();
        StandardJavaFileManager fileManager  =  compiler.getStandardFileManager(diagnosticsCollector, Locale.ENGLISH, Charset.forName("UTF-8"));
//        JavaObjectFromString javaObjectFromString =  new JavaObjectFromString("Lesson", msgCode);
        String fileToCompile = fileName;
//        Iterable fileObjects = Arrays.asList(javaObjectFromString);
        Iterable fileObjects = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(fileToCompile));

        Iterable<String> options = Arrays.asList(

                "-sourcepath", projectPath + File.separator + "src",
                "-d", projectPath + File.separator + "build" + File.separator + "classes"
        );

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnosticsCollector, options, null, fileObjects);
        Boolean result = task.call();
        List<Diagnostic> diagnostics = diagnosticsCollector.getDiagnostics();

        for(Diagnostic d : diagnostics){

            System.out.println(d);
            onOutPrint(d.toString());
        }

        return result;

    }

    private boolean compileEclipseCompiller(String fileName){
        org.eclipse.jdt.core.compiler.CompilationProgress progress = null; // создать производный класс
        Boolean result= org.eclipse.jdt.core.compiler.batch.BatchCompiler.compile(
                "-classpath jstemp/Lesson.jar -encoding UTF-8 " + fileName,
                new PrintWriter(System.out),
                new PrintWriter(System.err),
                progress);

        return result;
    }

    private String canonicalCode(String str){
        String result = str;

        while(result.contains("  ")) {
            String replace = result.replace("  ", " ");
            result=replace;

        }

//        return str.replaceAll("[\\s]{2,}", " ");
        return result;
    }

    private String getClassNameFromCode(String code){
        String keyWord = "public class ";
        int firstIndex = code.indexOf(keyWord)+ keyWord.length();
        int secondIndex = code.indexOf("{", firstIndex);
        String result = code.substring(firstIndex, secondIndex);
        String[] array = result.split("[\\p{P}]");
        return array[0];
    }

    private String getPackageNameFromCode(String code){
        String keyWord = "package ";
        if(code.contains(keyWord)) {
            int firstIndex = code.indexOf(keyWord)+ keyWord.length();

            int secondIndex = code.indexOf(";", firstIndex);

            String result = code.substring(firstIndex, secondIndex);
            String[] array = result.split("[\\p{P}]");
            StringBuilder sb = new StringBuilder("");
            for (int i = 0; i < array.length; i++) {
                sb.append(array[i]);
                sb.append(File.separator);
            }

            this.packagePath = sb.toString();
            return result + ".";
        }else
            return "";
    }

    public void createProjectDir(){
//        File userDir = new File("jstemp/" + user);
//        if (!userDir.exists())
//            deletefile(userDir);

        File userFolder = new File(projectPath + File.separator
                + "src" + File.separator + packagePath);
        if (!userFolder.exists()) {
            userFolder.mkdirs();

            File buildFolder  = new File(projectPath + "/build/classes");
            buildFolder.mkdirs();

        }
    }

    private void deletefile(File path) {
        if (path.isDirectory()) {
            for (File f : path.listFiles()) {
                if (f.isDirectory()) deletefile(f);
                else f.delete();
            }
        }
        path.delete();
    }

    public Validator(String user, String msgCode, String projectName){
        StringBuilder projectPath = new StringBuilder("jstemp");
        projectPath.append(File.separator)
                .append(user)
                .append(File.separator)
                .append(projectName);

        this.projectPath = projectPath.toString();
                this.user = user;
        this.code = canonicalCode(msgCode);
        this.mainClassName = getClassNameFromCode(this.code);
        this.packageName = getPackageNameFromCode(this.code);

        createProjectDir();





    }

    @Override
    public void run() {


        process = null;
        try {


            StringBuilder mainClassPath = new StringBuilder(this.projectPath);
            mainClassPath.append(File.separator)
                    .append("src")
                    .append(File.separator)
                    .append(packagePath)
                    .append(mainClassName)
                    .append(".java");

            FileWorker.write(mainClassPath.toString(), code);
            Boolean result = compileJavaxCompiller(mainClassPath.toString());



            if(result == true){
                onOutPrint("Compilation has succeeded");

                StringBuilder sb = new StringBuilder(projectPath);
                sb.append(File.separator)
                        .append("build")
                        .append(File.separator)
                        .append("classes ")
                        .append(packageName)
                        .append(mainClassName);

                process = Runtime.getRuntime().exec("java -classpath " + sb.toString());

                // any error message?
                TaskGobbler errorGobbler = new
                        TaskGobbler(process.getErrorStream(), "ERROR");

                // any output?
                TaskGobbler outputGobbler = new
                        TaskGobbler(process.getInputStream(), "OUTPUT");

                errorGobbler.start();
                outputGobbler.start();

                pw = new PrintWriter(process.getOutputStream());

                process.waitFor();
            }else{
                System.out.println("Compilation fails.");
                onOutPrint("Compilation fails.");
            }



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            closeValidator();
        }
    }

    public void closeValidator(){
        if (process != null)
            process.destroy();
        process = null;
        File userDir = new File("jstemp/" + user);
        deletefile(userDir);
    }

    public void onOutPrint(String s){
        setRedy(false);
        //System.out.println(s);
    }

    public synchronized void println(String s){

        pw.write(s);
        pw.flush();

    }

    public synchronized boolean isRedy() {
        return redy;
    }

    public synchronized void setRedy(boolean redy) {
        this.redy = redy;
    }

    public String getUser() {
        return user;
    }

    public Process getProcess() {
        return process;
    }
}
