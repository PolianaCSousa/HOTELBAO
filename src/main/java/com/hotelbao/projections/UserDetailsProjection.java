package com.hotelbao.projections;

public interface UserDetailsProjection {
    String getAuthority();
    String getUsername();
    String getPassword();
    Long getRoleId();

}
