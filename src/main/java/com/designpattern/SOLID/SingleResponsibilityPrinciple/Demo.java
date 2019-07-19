package com.designpattern.SOLID.SingleResponsibilityPrinciple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


class Journal{
    private final List<String> entries = new ArrayList<>();
    private static int count = 0;

    public  void addEnty(String text ){
        entries.add(""+ (++count)+": "+text);
    }
    public void removeEntry (int index){
        entries.remove(index);
    }
    @Override
    public String toString() {
        return String.join(System.lineSeparator(),entries);
    }
    public void save(String fileName){
        try {
            PrintStream out  = new PrintStream(fileName);
            out.println(toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void load (String fileName){
    }
    public void load (URL url){

    }
}

class Persistence{
    public void saveToFile(Journal journal,String filename,boolean overwrite){
        if(overwrite || new File(filename).exists()){
            try {
                PrintStream out = new PrintStream(filename);
                out.println(journal.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void load(Journal journal, String filename) {}
    public void load(Journal journal, URL url) {}
}

class Demo {
    public static void main(String[] args) throws IOException {
        Journal j = new Journal();
        j.addEnty("I cried today");
        j.addEnty("I ate a bug");
        System.out.println(j);
        Persistence persistence = new Persistence();
        String fileName = "D:\\Temp\\temp\\journal.txt";
        persistence.saveToFile(j,fileName,true);
        Runtime.getRuntime().exec("notepad.exe " + fileName );
}
//Một class chỉ làm 1 nhiệm vụ duy nhất.
// ví dụ ở đây class Journal làm nhiệm vụ thêm bỏ entries thì class Persistence theo save file. ko nên để class Journal làm thêm nhiệm vụ save file
}

