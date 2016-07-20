package net.dontdrinkandroot.example.angularrestspringsecurity.model;

public class Roles {
    private Integer id;

    private String rolesDesc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolesDesc() {
        return rolesDesc;
    }

    public void setRolesDesc(String rolesDesc) {
        this.rolesDesc = rolesDesc;
    }
}