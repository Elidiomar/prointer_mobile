package pitagoras.prointer.mobile.infra.ok.repositories;

import android.content.Context;

import pitagoras.prointer.mobile.infra.ok.DatabaseHelper;
import pitagoras.prointer.mobile.infra.ok.DatabaseManager;

public class Repo {

    DatabaseHelper db;

    public RepoUsuarios Usuarios;
    public RepoAlunos Alunos;

    public Repo(Context context)
    {
        DatabaseManager<DatabaseHelper> manager = new DatabaseManager<DatabaseHelper>();
        db = manager.getHelper(context);

        Usuarios = new RepoUsuarios(db);
        Alunos = new RepoAlunos(db);

    }

}
