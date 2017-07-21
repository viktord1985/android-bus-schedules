package com.test.smartbus.dbase;

import java.io.IOException;
import java.sql.SQLException;

import static com.j256.ormlite.android.apptools.OrmLiteConfigUtil.writeConfigFile;

/**
 * Created by Viktor Derk on 12/13/16.
 */

public class DatabaseConfigUtil {

    public static void main(String[] args) throws SQLException, IOException {

        // Provide the mFileName of .txt file which you have already created and kept in res/raw directory
        writeConfigFile("ormlite_config.txt");
    }
}
