package com.domelchenko.webbank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "notice_details")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", nullable = false)
    private Integer id;

    @Column(name = "notice_summary", nullable = false, length = 200)
    private String noticeSummary;

    @Column(name = "notice_details", nullable = false, length = 500)
    private String noticeDetails;

    @Column(name = "notic_beg_dt", nullable = false)
    private LocalDate noticBegDt;

    @Column(name = "notic_end_dt")
    private LocalDate noticEndDt;

    @Column(name = "create_dt")
    private LocalDate createDt;

    @Column(name = "update_dt")
    private LocalDate updateDt;

}