package pitagoras.prointer.mobile.domain.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import pitagoras.prointer.mobile.infra.ok.repositories.Repo;

@DatabaseTable(tableName = "Alunos")
public class Aluno {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String email;
    @DatabaseField
    private String userId;
    @DatabaseField
    private String dateAdded;

    public Aluno() { }

    public Aluno(String id, String name, String email, String userId, String dateAdded) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.dateAdded = dateAdded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }


    public int save(Repo repo)
    {
        if(repo.Alunos.getById(id) == null)
        {
            return repo.Alunos.create(this);
        }
        else
        {
            return repo.Alunos.update(this);
        }
    }

    public int delete(Repo repo)
    {
        return repo.Alunos.delete(this);
    }

    public String toString()
    {
        return name;
    }
}
