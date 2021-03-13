package com.homemade.apigateway.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class UserPrincipal extends User {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 9034840503529809003L;

    /**
     * User identifier
     */
    private Long id;

    /**
     * The user name that can be modified.
     * It "overrides" the username field from class {@link User}.
     */
    private String username;

    /**
     * Construct the <code>User</code> with the details required by
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     *
     * @param username the username presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param enabled set to <code>true</code> if the user is enabled
     * @param accountNonExpired set to <code>true</code> if the account has not
     *        expired
     * @param credentialsNonExpired set to <code>true</code> if the credentials
     *        have not expired
     * @param accountNonLocked set to <code>true</code> if the account is not
     *        locked
     * @param authorities the authorities that should be granted to the caller
     *        if they presented the correct username and password and the user
     *        is enabled. Not null.
     *
     * @throws IllegalArgumentException if a <code>null</code> value was passed
     *         either as a parameter or as an element in the
     *         <code>GrantedAuthority</code> collection
     */
    public UserPrincipal(final Long id, final String username, final String password, final boolean enabled,
                         final boolean accountNonExpired, final boolean credentialsNonExpired, final boolean accountNonLocked,
                         final Collection<? extends GrantedAuthority> authorities) throws IllegalArgumentException {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.username = username;
    }

    /**
     * Calls the more complex constructor with all boolean arguments set to {@code true}.
     * @param username the username presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param password the password that should be presented to the
     *        <code>DaoAuthenticationProvider</code>
     * @param authorities the authorities that should be granted to the caller
     *        if they presented the correct username and password and the user
     *        is enabled. Not null.
     */
    public UserPrincipal(final Long id, final String username, final String password,
                         final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the updated username instead of the fixed one.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Updates user name with the given one.
     *
     * @param username
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * Returns {@code true} if the supplied object is a {@code User} instance with the
     * same {@code username} value.
     * <p>
     * In other words, the objects are equal if they have the same user name, representing the
     * same principal.
     *
     * @param obj the other object
     * @return true if equals
     */
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof User && this.username.equals(((User) obj).getUsername());
    }

    /**
     * Returns the hashcode based on updated user name.
     *
     * @return the hash
     */
    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    /**
     * Like {@link User#toString()}, but prints the updated user name.
     *
     * @return the string representation with all details
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(isEnabled()).append("; ");
        sb.append("AccountNonExpired: ").append(isAccountNonExpired()).append("; ");
        sb.append("credentialsNonExpired: ").append(isCredentialsNonExpired()).append("; ");
        sb.append("AccountNonLocked: ").append(isAccountNonLocked()).append("; ");

        if (!getAuthorities().isEmpty()) {
            sb.append("Granted Authorities: ");

            boolean first = true;
            for (GrantedAuthority auth : getAuthorities()) {
                if (!first) {
                    sb.append(",");
                }
                first = false;

                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }
        return sb.toString();
    }

}
