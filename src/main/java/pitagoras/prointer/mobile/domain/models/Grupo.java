package pitagoras.prointer.mobile.domain.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import pitagoras.prointer.mobile.infra.ok.repositories.Repo;

@DatabaseTable(tableName = "Grupos")
public class Grupo {

    @DatabaseField(id = true)
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String professorId;
    @DatabaseField
    private String dateAdded;

    public Grupo() { }

    public Grupo(String id, String name, String professorId, String dateAdded) {
        this.id = id;
        this.name = name;
        this.professorId = professorId;
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

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String toString()
    {
        return name;
    }

    public String getProfessorId() {
        return professorId;
    }

    public void setProfessorId(String professorId) {
        this.professorId = professorId;
    }
}

