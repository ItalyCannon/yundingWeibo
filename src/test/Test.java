package test;

import java.util.HashMap;

/**
 * @author 杜奕明
 * @date 2019/2/24 12:33
 */
public class Test {
    public static void main(String[] args) throws Exception {
        HashMap map = new HashMap(16);
        map.put("1", "xxx");
        map.put("2", "yyy");
        System.out.println(map);
    }
}
