package test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 杜奕明
 * @date 2019/2/24 12:33
 */
public class Test {
    public static void main(String[] args) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = df.parse("1980-01-01");

        System.out.println(parse);
    }
}
