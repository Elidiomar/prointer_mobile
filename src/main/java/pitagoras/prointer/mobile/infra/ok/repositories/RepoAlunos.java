package pitagoras.prointer.mobile.infra.ok.repositories;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import pitagoras.prointer.mobile.domain.models.Aluno;
import pitagoras.prointer.mobile.infra.ok.DatabaseHelper;

public class RepoAlunos {

    Dao<Aluno, String> dao;

    public RepoAlunos(DatabaseHelper db)
    {
        try {
            dao = db.getAlunoDao();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
    }

    public int create(Aluno entity)
    {
        try {
            return dao.create(entity);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public int update(Aluno entity)
    {
        try {
            return dao.update(entity);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public int delete(Aluno entity)
    {
        try {
            return dao.delete(entity);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return 0;
    }
    public Aluno getById(String id)
    {
        try {
            QueryBuilder<Aluno, String> qb = dao.queryBuilder();

            qb.where().eq("id", id);

            PreparedQuery<Aluno> pq = qb.prepare();
            return dao.queryForFirst(pq);
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }
    public List<Aluno> getAll()
    {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            // TODO: Exception Handling
            e.printStackTrace();
        }
        return null;
    }

}


