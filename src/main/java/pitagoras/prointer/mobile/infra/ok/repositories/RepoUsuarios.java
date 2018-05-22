package pitagoras.prointer.mobile.infra.ok.repositories;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import pitagoras.prointer.mobile.domain.models.Usuario;
import pitagoras.prointer.mobile.infra.ok.DatabaseHelper;

public class RepoUsuarios {

    Dao<Usuario, String> userDao;

    public RepoUsuarios(DatabaseHelper db)
    {
        try {
            userDao = db.getUserDao();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
    }

    public int create(Usuario user)
    {
        try {
            return userDao.create(user);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public int update(Usuario user)
    {
        try {
            return userDao.update(user);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public int delete(Usuario user)
    {
        try {
            return userDao.delete(user);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public Usuario getById(String id)
    {
        try {
            QueryBuilder<Usuario, String> qb = userDao.queryBuilder();

            qb.where().eq("id", id);

            PreparedQuery<Usuario> pq = qb.prepare();
            return userDao.queryForFirst(pq);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }
    public List<Usuario> getAll()
    {
        try {
            return userDao.queryForAll();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }

}


