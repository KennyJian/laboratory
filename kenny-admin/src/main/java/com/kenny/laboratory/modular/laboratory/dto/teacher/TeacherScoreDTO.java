package com.kenny.laboratory.modular.laboratory.dto.teacher;

import java.math.BigDecimal;

public class TeacherScoreDTO {

    private Long id;

    private String studentName;

    private String experimentName;

    private Integer attendanceNum;

    private BigDecimal score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
}
