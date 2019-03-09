package com.baicheng.oauth2client.bos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * @author baicheng
 * @description
 * @create 2019-02-26 20:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBO implements UserDetails, Serializable {
    private static final long serialVersionUID = -3150750809934208799L;

    private Integer id;
    private String userName;
    private String password;
    private Set<String> roles;
    private Set<SimpleGrantedAuthority> authorities;
    private Integer isDeleted;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isDeleted == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isDeleted == 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isDeleted == 0;
    }

    @Override
    public boolean isEnabled() {
        return isDeleted == 0;
    }
}
