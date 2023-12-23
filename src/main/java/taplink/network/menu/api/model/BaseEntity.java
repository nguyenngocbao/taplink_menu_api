package taplink.network.menu.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Base.ID, updatable = false)
    protected Long id;

    @Version
    @Column(name = Base.VERSION)
    protected Integer version;

    @CreatedDate
    @Column(name = Base.CREATED_AT)
    protected LocalDateTime createdAt;

    @CreatedBy
    @Column(name = Base.CREATED_BY)
    protected String createdBy;

    @LastModifiedDate
    @Column(name = Base.LAST_MODIFIED_AT)
    protected LocalDateTime lastModifiedAt;

    @LastModifiedBy
    @Column(name = Base.LAST_MODIFIED_BY)
    protected String lastModifiedBy;
}

class Base {
    static final String ID = "ID";
    static final String VERSION = "VERSION";
    static final String CREATED_AT = "CREATED_AT";
    static final String CREATED_BY = "CREATED_BY";
    static final String LAST_MODIFIED_AT = "LAST_MODIFIED_AT";
    static final String LAST_MODIFIED_BY = "LAST_MODIFIED_BY";
    private Base() {
    }
}
