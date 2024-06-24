package com.datn.maguirestore.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * thanhnvph25467 - Feedback.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feed_back")
public class FeedBack extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private Integer status;

    @ManyToOne
    private User user;

    @ManyToOne
    private ShoesDetails shoes;
}
