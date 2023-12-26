package taplink.network.menu.api.secutiry;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OtpAuthenticationToken extends AbstractAuthenticationToken {

    private final String email;
    private final String otp;


    // Constructor for an authenticated token (after authentication)
    public OtpAuthenticationToken(String email, String otp, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.email = email;
        this.otp = otp;
        super.setAuthenticated(true); // Must use super as we override
    }

    public OtpAuthenticationToken(String email, String otp) {
        super(null);
        this.email = email;
        this.otp = otp;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return otp;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

}
