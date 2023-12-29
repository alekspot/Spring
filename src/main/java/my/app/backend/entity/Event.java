package my.app.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "event", schema = "app", catalog = "test")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "event_date")
    private Timestamp date;

    @ManyToOne
    @JoinColumn(name = "event_category_id", referencedColumnName = "id", insertable=false, updatable=false)
    private EventCategory category;

    @Column(name = "event_category_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long categoryId;

    @Column(name = "user_id")
    private Long userId;
}
