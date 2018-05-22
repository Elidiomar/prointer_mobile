package pitagoras.prointer.mobile.infra.ok;

import java.io.IOException;
import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import pitagoras.prointer.mobile.domain.models.Aluno;
import pitagoras.prointer.mobile.domain.models.Usuario;

import static com.j256.ormlite.dao.DaoManager.createDao;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "prointer.db.sqlite";
    private static final int DATABASE_VERSION = 1;

    private Dao<Usuario, String> userDao = null;
    private Dao<Aluno, String> alunoDao = null;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        DatabaseInitializer initializer = new DatabaseInitializer(context);
        try {
            initializer.createDatabase();
            initializer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");

            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Aluno.class);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");

            TableUtils.dropTable(connectionSource, Usuario.class, true);
            TableUtils.dropTable(connectionSource, Aluno.class, true);

            onCreate(db);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }


    public Dao<Usuario, String> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = createDao(getConnectionSource(), Usuario.class);
        }
        return userDao;
    }

    public Dao<Aluno, String> getAlunoDao() throws SQLException {
        if (alunoDao == null) {
            alunoDao = createDao(getConnectionSource(), Aluno.class);
        }
        return alunoDao;
    }


    @Override
    public void close() {
        super.close();
        userDao = null;
        alunoDao = null;
    }
}
