package com.example.pradhuman.entities;

import com.example.pradhuman.utils.Jutil;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@Entity(name = "new2")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Data
@SuperBuilder
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "index1", columnList = "user_id", unique = true)
})
public class User {
    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private String userId;
    @Column(unique = true)
    private String email;

    private String password;
    private String mobile;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Address address;

    private boolean disabled;

    public void setEmail(String email) {
        if(!Jutil.isNullOrEmpty(email)){
            this.email = email;
        }
    }

    public void setPassword(String password) {
        if(!Jutil.isNullOrEmpty(password)){
            this.password = password;
        }
    }

    public void setMobile(String mobile) {
        if(!Jutil.isNullOrEmpty(mobile)){
            this.mobile = mobile;
        }
    }

    @Transient
    @Builder.Default
    private boolean found = true;

}
