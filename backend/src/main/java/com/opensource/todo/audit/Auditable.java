package com.opensource.todo.audit;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public class Auditable {

    @NotNull
    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date modifiedDate;

    @NotNull
    @CreatedBy
    private Long createdBy;

    @LastModifiedBy
    private Long modifiedBy;
}
