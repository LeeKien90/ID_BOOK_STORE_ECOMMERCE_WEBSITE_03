package ra.payload.request;

public class UserRequest {
    private boolean userStatus;

    public UserRequest() {
    }

    public UserRequest(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }
}
