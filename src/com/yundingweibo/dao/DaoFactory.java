package com.yundingweibo.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author 杜奕明
 * @date 2019/2/20 10:09
 */
public class DaoFactory {
    private static Properties properties = new Properties();

    static {
        InputStream in = null;
        try {
            in = DaoFactory.class
                    .getResourceAsStream("daoFactoryConfig.properties");
            System.out.println();
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static UserDao getUserDao() {
        try {
            String className = properties.getProperty("UserDaoimpl");
            Class clazz = Class.forName(className);
            return (UserDao) clazz.newInstance();
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public static WeiboDao getWeiboDao() {
        try {
            String className = properties.getProperty("WeiboDaoimpl");
            Class clazz = Class.forName(className);
            return (WeiboDao) clazz.newInstance();
        } catch (IllegalAccessException | ClassNotFoundException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
