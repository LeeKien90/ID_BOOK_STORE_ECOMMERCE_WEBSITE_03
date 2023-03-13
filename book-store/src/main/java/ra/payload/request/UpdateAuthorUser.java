package ra.payload.request;

import java.util.Set;


public class UpdateAuthorUser {
    private Set<String> listRoles;

    public UpdateAuthorUser() {
    }

    public Set<String> getListRoles() {
        return listRoles;
    }

    public void setListRoles(Set<String> listRoles) {
        this.listRoles = listRoles;
    }
}
