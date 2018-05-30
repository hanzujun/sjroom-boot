package com.github.sjroom;

import com.github.sjroom.jdbc.generatecode.AutoGenerator;
import com.github.sjroom.jdbc.generatecode.ConfigGenerator;

/**
 * <B>说明：</B><BR>
 *
 * @author ZhouWei
 * @version 1.0.0.
 * @date 2018-02-24 16-08
 */
public class GenerateCode {

    public static void main(String[] args) {
        ConfigGenerator configGenerator = new ConfigGenerator();

        configGenerator.setDbUrl("jdbc:mysql://172.16.21.15:3306/yunying");
        configGenerator.setDbUser("maoyi");
        configGenerator.setDbPassword("6qjiaVv6!nlz1JSo");
        configGenerator.setDbSchema("yunying");

        configGenerator.setBasePackage("com.github.sjroom");
        configGenerator.setGenerateTableName("sys_user");

        new AutoGenerator(configGenerator).run();
    }

}
