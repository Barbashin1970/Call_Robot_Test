package userdata;

public class UserInfo {

    private String id;
    private String name;
    private String username;
    private String email;
    private String msisdn;
    private boolean verified;
    private boolean phoneVerified;

    public UserInfo(String id, String name, String username, String email, String msisdn, boolean verified, boolean phoneVerified) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.msisdn = msisdn;
        this.verified = verified;
        this.phoneVerified = phoneVerified;
    }

    public UserInfo() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }


}
