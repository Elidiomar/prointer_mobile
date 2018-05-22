package pitagoras.prointer.mobile.domain.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import pitagoras.prointer.mobile.infra.ok.repositories.Repo;

@DatabaseTable(tableName = "Usuarios")
public class Usuario {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String userName;
    @DatabaseField
    private String password;
    @DatabaseField
    private String lastUpdate;
    @DatabaseField
    private String dateAdded;
    @DatabaseField
    private String active;

    @DatabaseField
    private boolean tipoProfessor;

    public Usuario(String id, String userName, String password, String lastUpdate, String dateAdded, String active, Boolean tipoProfessor) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.lastUpdate = lastUpdate;
        this.dateAdded = dateAdded;
        this.active = active;
        this.tipoProfessor = tipoProfessor;
    }

    public int save(Repo repo)
    {
        if(repo.Usuarios.getById(id) == null)
        {
            return repo.Usuarios.create(this);
        }
        else
        {
            return repo.Usuarios.update(this);
        }
    }

    public int delete(Repo repo)
    {
        return repo.Usuarios.delete(this);
    }

    public String toString()
    {
        return userName;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getTipoProfessor() {
        return tipoProfessor;
    }

    public void setTipoProfessor(Boolean tipoProfessor) {
        this.tipoProfessor = tipoProfessor;
    }
}
