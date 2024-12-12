package com.junkai.picture_enhancement_platform.ultils;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 用于处理Mybatis无法解析Set<GrantedAuthority>类型的问题
 */
@Component
@MappedTypes(Set.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class AuthoritiesTypeHandler extends BaseTypeHandler<Set<GrantedAuthority>> {

    @Override
    public void setNonNullParameter(@NotNull PreparedStatement ps, int i, @NotNull Set<GrantedAuthority> parameter, JdbcType jdbcType) throws SQLException {
        String authoritiesStr = parameter.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        ps.setString(i, authoritiesStr);
    }

    @Override
    public Set<GrantedAuthority> getNullableResult(@NotNull ResultSet rs, String columnName) throws SQLException {
        String authoritiesStr = rs.getString(columnName);
        return Arrays.stream(authoritiesStr.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GrantedAuthority> getNullableResult(@NotNull ResultSet rs, int columnIndex) throws SQLException {
        String authoritiesStr = rs.getString(columnIndex);
        return Arrays.stream(authoritiesStr.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<GrantedAuthority> getNullableResult(@NotNull CallableStatement cs, int columnIndex) throws SQLException {
        String authoritiesStr = cs.getString(columnIndex);
        return Arrays.stream(authoritiesStr.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}

