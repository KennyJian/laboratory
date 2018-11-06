package com.kenny.laboratory.modular.laboratory.dto.student;

import java.math.BigDecimal;

public class StudentScoreDTO {

    private Long id;
    private String experimentName;
    private Integer attendanceNum;
    private BigDecimal score;
    private String teacherName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExperimentName() {
        return experimentName;
    }

    public void setExperimentName(String experimentName) {
        this.experimentName = experimentName;
    }

    public Integer getAttendanceNum() {
        return attendanceNum;
    }

    public void setAttendanceNum(Integer attendanceNum) {
        this.attendanceNum = attendanceNum;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
