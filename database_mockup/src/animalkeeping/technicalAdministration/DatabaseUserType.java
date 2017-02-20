package animalkeeping.technicalAdministration;

import java.util.List;

/**
 * Created by huben on 17.02.17.
 */
public class DatabaseUserType {
    private Long id;
    private String name;
    private String privileges;

    DatabaseUserType(String name, String privileges)
    {
        this.name = name;
        this.privileges = privileges;
            }

    public long getId(){
        return id;
    }

    public void setId(Long id){ this.id = id; }

    public String getName(){
        return name;
    }

    public void setName(String name){ this.name = name; }

    public String getPrivileges(){ return privileges; }

    public String setPrivileges(String privileges){ this.privileges = privileges;


    }
