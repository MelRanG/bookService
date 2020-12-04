package dto;

public class Login {
    private String userId;
    private String password;
    private boolean useCookie;

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public boolean isUseCookie() {
        return useCookie;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUseCookie(boolean useCookie) {
        this.useCookie = useCookie;
    }

    @Override
    public String toString() {
        return "Login{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", useCookie=" + useCookie +
                '}';
    }
}
