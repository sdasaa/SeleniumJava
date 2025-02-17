package com.sj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;

public class DummyTest {

    public static void main(String[] args) throws FileNotFoundException {
        /*
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
        Date date = new Date();
        System.out.println( "Log_"+ formatter.format(date) + ".txt");
        File file = new File(System.getProperty("user.dir") + "SimpleLog.txt");
        FileInputStream fis = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(file);
        // fos.write(Bye."das");
        */

        String test = "https://the-internet.herokuapp.com/jqueryui/menu";
        StringBuilder sb = new StringBuilder("https://the-internet.herokuapp.com/jqueryui/menu");
        System.out.println(test.contains("JQuery"));
        System.out.println(sb.indexOf("jquery"));
        System.out.println(StringUtils.compareIgnoreCase(test, "JQuery UI Menu"));

    }
}